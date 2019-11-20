package com.masum.bulkupload.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    @SerializedName("msz")
    @Expose
    private String msz;
    @SerializedName("error")
    @Expose
    private String error;

    public String getMsz() {
        return msz;
    }

    public void setMsz(String msz) {
        this.msz = msz;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}