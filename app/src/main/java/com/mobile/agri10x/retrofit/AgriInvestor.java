package com.mobile.agri10x.retrofit;

import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.models.GetResendOTP;
import com.mobile.agri10x.models.VerifyOTP;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AgriInvestor {
    @POST("getOTP")
    Call<GetOTP> wsgetOTP(@Body RequestBody params);
    @POST("verifyOTP")
    Call<VerifyOTP> wsgetVerifyOTP(@Body RequestBody params);
    @POST("resendOTP")
    Call<GetResendOTP> wsgetresendOTP(@Body RequestBody params);
}
