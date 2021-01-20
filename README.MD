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