package com.futao.imooc.learn.dianpin.model.constant;

/**
 * ES分词器
 *
 * @author ft
 * @date 2021/1/20
 */
public interface EsAnalyzeConstants {
    /**
     * 标准分词器
     * - 中文按字拆分
     * - 英文按单词拆分
     */
    String STANDARD = "standard";
    /**
     * 英文分词器
     * 会解析出词根
     * 对英文支持友好，如原文为Eating，则查询eat也能匹配。
     */
    String ENGLISH = "english";
    /**
     * 以空格分词
     */
    String WHITESPACE = "whitespace";
    /**
     * 中文IK分词器-智能分词-宁缺毋滥-最高效使用的词
     */
    String IK_SMART = "ik_smart";
    /**
     * 中文IK分词器-尽最大努力的分词
     */
    String IK_MAX_WORD = "ik_max_word";
}
