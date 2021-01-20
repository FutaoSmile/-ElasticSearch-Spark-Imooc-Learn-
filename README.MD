### # ES

* 名词
    * 索引indexName = mysql-database
    * ~~类型type = mysql-table~~ es7已废弃
    * Document = mysql-一行数据

* 倒排索引
* TF: Token frequency  词频 一个词在一个Document中出现的次数（未考虑占比）
* DF: Document Frequency 文档频率，该分词在整个库中存在的频率
* IDF: Inverse Document Frequency DF取反- 逆文档频率
* TFNORM: Token frequency Normalized 词频归一化，分词占比

### # ES相关功能：

* 自定义Converter:
    * Long -> LocalDateTime
    * Long -> LocalDate

### # 查询语法

* match: 会将查询条件分词
* term: 不会将查询条件分词
    * 相当于会忽略分词器

* 返回执行过程: `"explain": true`
```http request
GET /cdp_user/_search
{
  "query": {
    "bool": {
      "filter": [
        {
          "term": {
            "nickName": "猪123"
          }
        }
      ]
    }
  }, 
  "from": 0,
  "size": 20
}

GET /cdp_user/_search
{
  "query": {
    "bool": {
      "filter": [
        {
          "match": {
            "nickName": "猪123"
          }
        }
      ]
    }
  }, 
  "from": 0,
  "size": 20
}
```

```http request
# 使用analysis api查看分词状态
GET /cdp_user/_analyze
{
  "field": "nickName",
  "text": ["一只特立独行的猪","山不过来，我就过去","I need apple"]
}
```
* match 的and和or与最小匹配数
    * operator：
        * and 必须匹配每一个分词
        * or 匹配任意一个
    * minimum_should_match
        * 当operator为or时，这个字段的含义是，需要匹配minimum_should_match个分词
```http request
GET /cdp_user/_search
{
  "query": {
    "match": {
      "nickName": {
        "query": "一只特立独行的猪",
        "operator": "or",
        "minimum_should_match": 3
      }
    }
  }
}
```
* 短语查询：不分词
    * match-> match_phrase
* 分词分析
```http request
# 分析
GET /_analyze
{
  "text": " I am a pig",
  "analyzer": "english"
}

GET /_analyze
{
  "text": " I am a pig",
  "analyzer": "standard"
}

GET /_analyze
{
  "text": " I am a pig",
  "analyzer": "ik_smart"
}
```


* analysis分析过程：
    * 默认的分词器处理过程：
        1. 字符过滤
        2. 字符处理
        3. 分词过滤（分词转换）
    * english analyze处理过程：
        1. 字符过滤（过滤英文场景下的特殊外加量词，如the等）
        2. 字符处理
        3. 分词过滤(分词转换)

* 类型
    * Text 能被分析分词的索引字符串类型
    * keyword 不能被分析分词，只能精准匹配的索引字符串类型
    * Date 日期类型，一般配合formatter使用
    * 数值类型 int long double
    * boolean
    * Array ["a","b"]
    * Object: json嵌套
    * Ip
    * Geo_point 地理位置

### # 集群搭建

> target: 搭建具有三个node节点的ES集群


1. 从下载的压缩包中解压出三份EsServer，重命名文件夹为：
  * es-node-1
  * es-node-2
  * es-node-3

2. 依次修改每个节点的配置文件 elasticsearch.yml
  * `cluster.name: dianping-app`
      * 集群名称，三个配置确保相同
  * `node.name: node-1`
      * 节点名称，在集群中，每个节点的node.name不相同，如node-1,node-2....
  * `network.host: 127.0.0.1`
      * 绑定IP
  * `http.port: 9201`
      * 响应Restful接口的端口
  * `transport.tcp.port: 9301`
      * 集群之间通信的端口
  * `http.cors.enabled: true`
      * 允许跨域访问
  * `http.cors.allow-origin: "*"`
      * 允许跨域访问的origin
  * `discovery.seed_hosts: ["127.0.0.1:9301","host2","host3"]`
      * 配置集群中的所有节点
      * 结构为: `network.host`:`transport.tcp.port`
  * `cluster.initial_master_nodes: ["127.0.0.1:9301","host2","host3"]`
      * 有资格竞选主节点的节点，可直接复制`discovery.seed_hosts`
    
### # 中文分词器
> IK分词器
* 插件位置 plugins
* 安装插件 `bin/elasticsearch-plugins install https://github.com/medcl/elasticsearch/analysis-ik/dowload/v7.3.0/elassticsearch-analysis-ik-7.3.0.zip`
* 原理
    * 过程:
        1. 字符过滤，过滤特殊符号外加量词，如 `的`。stopWord，通用词
        2. 字符处理（基于词库，词典拆分）
        3. 分词过滤
  
* 常用分词器
```java
    /**
     * 中文IK分词器-智能分词-宁缺毋滥-最高效使用的词
     */
    String IK_SMART = "ik_smart";
    /**
     * 中文IK分词器-尽最大努力的分词
     */
    String IK_MAX_WORD = "ik_max_word";
```
* analyzer指定的是构建索引时的分词器
* search_analyzer指定的是查询时使用的分词器
* 最佳实践：在构建索引时使用`ik_max_word`,在查询时使用`ik_smart`。
* 词库

### # 索引构建
* 全量索引构建
* 增量索引构建

* logstash-input-jdbc
    * 数据源
    * 数据目标
    * 同步方式
    
* 安装插件: `logstash/bin/logstash-plugin install logstash-input-jdbc`
  
#### # 全量索引构建/增量索引构建
* 增量： *查询DB中修改时间大于某个时间的数据进行索引构建*

* 拷贝mysql-jdbc驱动
* 创建文件 jdbc.conf，jdbc.sql
* jdbc.conf:
```json
input {
    jdbc {
      #设置timezone
      jdbc_default_timezone => "Asia/Shanghai"
      # mysql 数据库链接,dianpingdb为数据库名
      jdbc_connection_string => "jdbc:mysql://127.0.0.1:3306/dianpingdb"
      # 用户名和密码
      jdbc_user => "root"
      jdbc_password => "root"
      # 驱动
      jdbc_driver_library => "/Users/hzllb/Desktop/devtool/logstash-7.3.0/bin/mysql/mysql-connector-java-5.1.34.jar"
      # 驱动类名
      jdbc_driver_class => "com.mysql.jdbc.Driver"
      jdbc_paging_enabled => "true"
      jdbc_page_size => "50000"
      last_run_metadata_path => "/Users/hzllb/Desktop/devtool/logstash-7.3.0/bin/mysql/last_value_meta" 
    # 执行的sql 文件路径+名称;
      statement_filepath => "/Users/hzllb/Desktop/devtool/logstash-7.3.0/bin/mysql/jdbc.sql"
      # 设置监听间隔  各字段含义（由左至右）分、时、天、月、年，全部为*默认含义为每分钟都更新
      schedule => "* * * * *"
    }
}
              
output {
    elasticsearch {
      # ES的IP地址及端口
        hosts => ["localhost:9200"]
      # 索引名称
        index => "shop"
  	document_type => "_doc"
      # 自增ID 需要关联的数据库中有有一个id字段，对应索引的id号
        document_id => "%{id}"
    }
    stdout {
     # JSON格式输出
        codec => json_lines
    }
}
```
* jdbc.sql
```sql
select a.id,
       a.name,
       a.tags,
       concat(a.latitude, ',', a.longitude) as location,
       a.remark_score,
       a.price_per_man,
       a.category_id,
       b.name                               as category_name,
       a.seller_id,
       c.remark_score                       as seller_remark_score,
       c.disabled_flag                      as seller_disabled_flag
from shop a
         inner join category b on a.category_id = b.id
         inner join seller c on c.id = a.seller_id
where a.updated_at > :sql_last_value
   or b.updated_at > :sql_last_value
   or c.updated_at > :sql_last_value
```
* 执行： logstash -f mysql/jdbc.conf


* ES中存储的其实是宽表，所以需要将mysql中的很多表的数据整合在一个对象，对于关联关系，ES中需要存储关联的整个对象