package com.demoretrofit.service;

import com.demoretrofit.dto.request.ForCallingServiceREQUEST;
import com.demoretrofit.dto.request.GetUserWithPhoneReq;
import com.demoretrofit.dto.response.GetUserWithPhoneRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface PostApi {

    @Headers({"Content-Type: application/json", "LiviaApp-username:JHILT@L@825", "LiviaApp-password: TS]Ua~jhS6E6xhw"})
    @POST("/api/auth")
    Call<ForCallingServiceREQUEST> createUser(@Body ForCallingServiceREQUEST serviceREQUEST);
}
