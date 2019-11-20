package com.masum.bulkupload.model.insert;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FingerprintRequest {

    @SerializedName("UserId")
    @Expose
    private String userId;
    @SerializedName("CreatedById")
    @Expose
    private String createdById;
    @SerializedName("CretadByName")
    @Expose
    private String cretadByName;
    @SerializedName("Fingerprints")
    @Expose
    private List<Fingerprint> fingerprints = null;
    public String getUserId() {
        return userId;
    }

    public List<Fingerprint> getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(List<Fingerprint> fingerprints) {
        this.fingerprints = fingerprints;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getCretadByName() {
        return cretadByName;
    }

    public void setCretadByName(String cretadByName) {
        this.cretadByName = cretadByName;
    }

}
