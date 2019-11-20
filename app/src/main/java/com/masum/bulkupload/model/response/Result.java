package com.masum.bulkupload.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("CreatedAt")
    @Expose
    private String createdAt;
    @SerializedName("UpdatedAt")
    @Expose
    private Object updatedAt;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserType")
    @Expose
    private String userType;
    @SerializedName("UserName")
    @Expose
    private String userName;

    @SerializedName("CreatedById")
    @Expose
    private Integer createdById;
    @SerializedName("CreatedByName")
    @Expose
    private Object createdByName;
    @SerializedName("UpdatedById")
    @Expose
    private Object updatedById;
    @SerializedName("UpdatedByName")
    @Expose
    private Object updatedByName;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public Object getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(Object createdByName) {
        this.createdByName = createdByName;
    }

    public Object getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Object updatedById) {
        this.updatedById = updatedById;
    }

    public Object getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(Object updatedByName) {
        this.updatedByName = updatedByName;
    }

}
