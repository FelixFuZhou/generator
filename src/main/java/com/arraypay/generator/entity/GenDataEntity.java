package com.arraypay.generator.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 数据Entity类
 * @author ThinkGem
 * @version 2014-05-16
 */
public abstract class GenDataEntity<T> extends GenBaseEntity<T> {

    private static final long serialVersionUID = 1L;

    protected String          remarks;              // 备注
//    protected User            createBy;             // 创建者
    protected Date            createDate;           // 创建日期
//    protected User            updateBy;             // 更新者
    protected Date            updateDate;           // 更新日期
    protected String          delFlag;              // 删除标记（0：正常；1：删除；2：审核）
    protected long            createDateS;
    protected long            createDateE;          // 创建时间
    protected long            updateDateS;
    protected long            updateDateE;          // 创建时间

    public long getCreateDateS(){
        return createDateS;
    }

    public void setCreateDateS(long createDateS){
        this.createDateS = createDateS;
    }

    public long getCreateDateE(){
        return createDateE;
    }

    public void setCreateDateE(long createDateE){
        this.createDateE = createDateE;
    }

    public GenDataEntity() {
        super ();
        this.delFlag = DEL_FLAG_NORMAL;
    }

    public GenDataEntity(String id) {
        super (id);
    }

//    /**
//     * 插入之前执行方法，需要手动调用
//     */
//    @Override
//    public void preInsert(){
//        // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
//        if (!this.isNewRecord) {
//            setId (IdGen.uuid ());
//        }
//        User user = UserUtils.getUser ();
//        if (StringUtils.isNotBlank (user.getId ())) {
//            this.updateBy = user;
//            this.createBy = user;
//        } else {
//            this.updateBy = new User (USER_ID,USER_NAME);
//            this.createBy = new User (USER_ID,USER_NAME);
//        }
//        this.updateDate = new Date ();
//        this.createDate = this.updateDate;
//    }

//    /**
//     * 更新之前执行方法，需要手动调用
//     */
//    @Override
//    public void preUpdate(){
//        User user = UserUtils.getUser ();
//        if (StringUtils.isNotBlank (user.getId ())) {
//            this.updateBy = user;
//        } else {
//            this.updateBy = new User (USER_ID,USER_NAME);
//        }
//        this.updateDate = new Date ();
//    }

    @Length(min = 0,max = 255)
    public String getRemarks(){
        return remarks;
    }

    public void setRemarks(String remarks){
        this.remarks = remarks;
    }

//    @JsonIgnore
//    public User getCreateBy(){
//        return createBy;
//    }
//
//    public void setCreateBy(User createBy){
//        this.createBy = createBy;
//    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate(){
        return createDate;
    }

    public void setCreateDate(Date createDate){
        this.createDate = createDate;
    }

//    @JsonIgnore
//    public User getUpdateBy(){
//        return updateBy;
//    }
//
//    public void setUpdateBy(User updateBy){
//        this.updateBy = updateBy;
//    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate(){
        return updateDate;
    }

    public void setUpdateDate(Date updateDate){
        this.updateDate = updateDate;
    }

    @JsonIgnore
    @Length(min = 1,max = 1)
    public String getDelFlag(){
        return delFlag;
    }

    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public void clean(){
        this.delFlag = null;
    }

//    public String getCreatrName(){
//        return createBy == null ? "" : createBy.getUserName ();
//    }
//
//    public String getLoginName(){
//        return createBy == null ? "" : createBy.getLoginName ();
//    }
//
//    public String getCreateOfficeName(){
//        return createBy == null ? "" : createBy.getOfficeName ();
//    }
//
//    public String getUpdateByName(){
//        return updateBy == null ? "" : updateBy.getUserName ();
//    }
//
//    public String getUpdateByOfficeName(){
//        return updateBy == null ? "" : updateBy.getOfficeName ();
//    }

    public long getUpdateDateS(){
        return updateDateS;
    }

    public void setUpdateDateS(long updateDateS){
        this.updateDateS = updateDateS;
    }

    public long getUpdateDateE(){
        return updateDateE;
    }

    public void setUpdateDateE(long updateDateE){
        this.updateDateE = updateDateE;
    }
}
