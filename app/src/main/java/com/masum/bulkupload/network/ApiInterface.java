package com.masum.bulkupload.network;

import com.google.gson.JsonObject;
import com.masum.bulkupload.model.response.FpInsertResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {


    @POST(HttpParams.API_INSERT_FINGER)
    Observable<FpInsertResponse> addFingerResponse(
            @Body JsonObject object
    );



}
