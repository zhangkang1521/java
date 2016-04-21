package com.autoserve.abc.dao.dataobject;

/**
 * 栏目信息 类articleClassDO.java的实现描述：TODO 类实现描述
 * 
 * @author liuwei 2014年11月28日 下午1:27:52
 */
public class ArticleClassDO {

    /**
     * 栏目ID ac_id
     */
    private Integer acId;

    /**
     * 栏目名称 ac_name
     */
    private String  acName;
    /**
     * 栏目类型 abc_article_class.ac_mode
     */
    private String  acMode;

    /**
     * abc_article_class.ac_path
     */
    private String  acPath;

    /**
     * abc_article_class.ac_template
     */
    private String  acTemplate;

    /**
     * abc_article_class.ac_content_template
     */
    private String  acContentTemplate;

    /**
     * abc_article_class.ac_desc
     */
    private String  acDesc;

    /**
     * abc_article_class.ac_key_word
     */
    private String  acKeyWord;

    /**
     * abc_article_class.ac_type
     */
    private Integer acType;

    /**
     * abc_article_class.ac_order
     */
    private Integer acOrder;

    /**
     * 父级栏目ID ac_class
     */
    private Integer acClass;

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public String getAcName() {
        return acName;
    }

    public void setAcName(String acName) {
        this.acName = acName;
    }

    public Integer getAcClass() {
        return acClass;
    }

    public void setAcClass(Integer acClass) {
        this.acClass = acClass;
    }

    public String getAcMode() {
        return acMode;
    }

    public void setAcMode(String acMode) {
        this.acMode = acMode;
    }

    public String getAcPath() {
        return acPath;
    }

    public void setAcPath(String acPath) {
        this.acPath = acPath;
    }

    public String getAcTemplate() {
        return acTemplate;
    }

    public void setAcTemplate(String acTemplate) {
        this.acTemplate = acTemplate;
    }

    public String getAcContentTemplate() {
        return acContentTemplate;
    }

    public void setAcContentTemplate(String acContentTemplate) {
        this.acContentTemplate = acContentTemplate;
    }

    public String getAcDesc() {
        return acDesc;
    }

    public void setAcDesc(String acDesc) {
        this.acDesc = acDesc;
    }

    public String getAcKeyWord() {
        return acKeyWord;
    }

    public void setAcKeyWord(String acKeyWord) {
        this.acKeyWord = acKeyWord;
    }

    public Integer getAcType() {
        return acType;
    }

    public void setAcType(Integer acType) {
        this.acType = acType;
    }

    public Integer getAcOrder() {
        return acOrder;
    }

    public void setAcOrder(Integer acOrder) {
        this.acOrder = acOrder;
    }

}
