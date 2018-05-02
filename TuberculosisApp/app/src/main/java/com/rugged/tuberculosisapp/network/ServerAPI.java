package com.rugged.tuberculosisapp.network;

import com.rugged.tuberculosisapp.signin.Account;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ServerAPI {

    @Headers("Content-Type: application/json")
    @POST("accounts/login")
    Call<ResponseBody> login(
            @Body Account account
    );
}
