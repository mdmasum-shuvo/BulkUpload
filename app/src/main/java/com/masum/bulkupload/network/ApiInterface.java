package com.masum.bulkupload.network;

import com.google.gson.JsonObject;
import com.masum.bulkupload.model.response.FpInsertResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {




    @POST(HttpParams.API_INSERT_FINGER)
    Call<FpInsertResponse> addFingerResponse(
            @Body JsonObject object
    );



}
