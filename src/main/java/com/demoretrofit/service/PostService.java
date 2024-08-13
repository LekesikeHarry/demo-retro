package com.demoretrofit.service;

import com.demoretrofit.dto.request.ForCallingServiceREQUEST;
import com.demoretrofit.dto.request.GetUserWithPhoneReq;
import com.demoretrofit.dto.response.ErrorResponse;
import com.demoretrofit.dto.response.GetUserWithPhoneRes;
import com.demoretrofit.util.EncryptionUtil;
import com.demoretrofit.util.LiviaConstants;
import com.demoretrofit.util.RetroFitUtil;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Service
public class PostService {

    private Retrofit retrofit;
    private PostApi postApi;

    @Autowired
    private  EncryptionUtil encryptionUtil;
    @Autowired
    private Gson gson;


    public PostService() {
        retrofit = RetroFitUtil.getRetrofitInstance();
        postApi = retrofit.create(PostApi.class);
    }

    public Object getUserWithPhoneNumber(GetUserWithPhoneReq getUserWithPhoneReq) throws Exception {
        ForCallingServiceREQUEST serviceREQUEST = new ForCallingServiceREQUEST();
        serviceREQUEST.setData(encryptionUtil.encryptJsonString(getUserWithPhoneReq));
        System.out.println(serviceREQUEST);


        Call<ForCallingServiceREQUEST> postcall = postApi.createUser(serviceREQUEST);

        try {
            Response<ForCallingServiceREQUEST> response = postcall.execute();
            if (response.isSuccessful() && response.body() != null) {

                ForCallingServiceREQUEST encryptedResponse = response.body();
               System.out.println(encryptedResponse);
                GetUserWithPhoneRes getUserWithPhoneRes = encryptionUtil.decryptJsonString(encryptedResponse.getData(), GetUserWithPhoneRes.class);
                return getUserWithPhoneRes;

            }else if(response.code()==400) {
                return ResponseEntity.status(400).body(new ErrorResponse(response.code(),response.errorBody().string()));
            }
            System.out.println(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
