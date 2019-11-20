package com.masum.bulkupload.model.insert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fingerprint {

    @SerializedName("FingerName")
    @Expose
    private String fingerName;
    @SerializedName("IsoTemplate")
    @Expose
    private String isoTemplate;


    public String getFingerName() {
        return fingerName;
    }

    public void setFingerName(String fingerName) {
        this.fingerName = fingerName;
    }

    public String getIsoTemplate() {
        return isoTemplate;
    }

    public void setIsoTemplate(String isoTemplate) {
        this.isoTemplate = isoTemplate;
    }


}
