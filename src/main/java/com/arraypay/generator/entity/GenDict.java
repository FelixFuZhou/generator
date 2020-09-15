package com.arraypay.generator.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class GenDict implements Serializable {

    private static final long serialVersionUID = 1L;
    private String            dictValue;            // 数据值
    private String            dictName;             // 字典名称
    private String            dictType;             // 字典分类
    private String            dictStatus;           // 字典状态
    private String            description;          // 描述
    private Integer           sort;                 // 排序
    private String            isSys;                // 系统级1-是,0-否
    private String            parentId;             // 父级编号
    private String            dLable1;              // 扩展字段1
    private String            dLable2;              // 扩展字段2
    private String            dLable3;              // 扩展字段3
    
    @XmlAttribute(name = "value")
    public String getDictValue(){
        return dictValue;
    }

    public void setDictValue(String dictValue){
        this.dictValue = dictValue;
    }
    
    @XmlAttribute(name = "label")
    public String getDictName(){
        return dictName;
    }

    public void setDictName(String dictName){
        this.dictName = dictName;
    }

    public String getDictType(){
        return dictType;
    }

    public void setDictType(String dictType){
        this.dictType = dictType;
    }

    public String getDictStatus(){
        return dictStatus;
    }

    public void setDictStatus(String dictStatus){
        this.dictStatus = dictStatus;
    }
    
    @XmlAttribute(name = "description")
    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public Integer getSort(){
        return sort;
    }

    public void setSort(Integer sort){
        this.sort = sort;
    }

    public String getIsSys(){
        return isSys;
    }

    public void setIsSys(String isSys){
        this.isSys = isSys;
    }

    public String getParentId(){
        return parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getdLable1(){
        return dLable1;
    }

    public void setdLable1(String dLable1){
        this.dLable1 = dLable1;
    }

    public String getdLable2(){
        return dLable2;
    }

    public void setdLable2(String dLable2){
        this.dLable2 = dLable2;
    }

    public String getdLable3(){
        return dLable3;
    }

    public void setdLable3(String dLable3){
        this.dLable3 = dLable3;
    }

}
