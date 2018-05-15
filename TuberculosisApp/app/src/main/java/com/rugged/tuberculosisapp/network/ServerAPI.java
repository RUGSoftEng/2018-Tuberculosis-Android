package com.rugged.tuberculosisapp.network;

import com.rugged.tuberculosisapp.calendar.CalendarJSONHolder;
import com.rugged.tuberculosisapp.notes.QuestionToPhysician;
import com.rugged.tuberculosisapp.signin.Account;
import com.rugged.tuberculosisapp.signin.Identification;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerAPI {

    @Headers("Content-Type: application/json")
    @POST("accounts/login")
    Call<Identification> login(
            @Body Account account
    );

    @Headers("Content-Type: application/json")
    @GET("accounts/patients/{id}/dosages/scheduled")
    Call<List<CalendarJSONHolder>> getCalendarData(
            @Path("id") int patient_id,
            @Query("from") String from_date, @Query("until") String until_date,
            @Header("access_token") String access_token
    );

    @Headers("Content-Type: application/json")
    @POST("accounts/patients/{id}/dosages/scheduled")
    Call<ResponseBody> updateTakenState(
            @Path("id") int patient_id,
            @Header("access_token") String access_token,
            @Body List<CalendarJSONHolder> JSONData
    );

    @Headers("Content-Type: application/json")
    @PUT("accounts/patients/{id}/notes")
    Call<ResponseBody> askPhysician(
            @Path("id") int patient_id,
            @Header("access_token") String access_token,
            @Body QuestionToPhysician note
    );

}
