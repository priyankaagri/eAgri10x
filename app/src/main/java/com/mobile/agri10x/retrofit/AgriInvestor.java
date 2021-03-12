package com.mobile.agri10x.retrofit;

import com.mobile.agri10x.models.DisplayQuickView;
import com.mobile.agri10x.models.GetCategories;
import com.mobile.agri10x.models.GetFeatureOnlyProduct;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetLiveTrades;
import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.GetQueryFeaturedOnly;
import com.mobile.agri10x.models.GetQueryTopicPicks;
import com.mobile.agri10x.models.GetResendOTP;
import com.mobile.agri10x.models.VerifyOTP;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AgriInvestor {
    @POST("e/getOTP")
    Call<GetOTP> wsgetOTP(@Body RequestBody params);
    @POST("e/verifyOTP")
    Call<VerifyOTP> wsgetVerifyOTP(@Body RequestBody params);
    @POST("e/resendOTP")
    Call<GetResendOTP> wsgetresendOTP(@Body RequestBody params);
    @POST("m/homepageProducts")
    Call<GetHomeProduct> wsgetHomeProduct(@Header("x-auth-token") String token,@Body GetQueryDailyDeals getQueryDailyDeals);
    @GET("e/getCategories")
    Call<GetCategories> getCategories();
    @POST("m/homepageProducts")
    Call<GetHomeProduct> wsgetHomeProductTopic(@Header("x-auth-token") String token,@Body GetQueryTopicPicks getQueryTopicPicks);
    @POST("m/displayProducts")
    Call<DisplayQuickView> wsgetdisplayQuickView(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/homepageProducts")
    Call<GetHomeProduct> wsgetFeatureOnlyProduct(@Header("x-auth-token") String token, @Body GetQueryFeaturedOnly getQueryFeaturedOnly);
    @POST("m/displayProducts")
    Call<GetLiveTrades>wsgetlivetrades(@Header("x-auth-token") String token, @Body RequestBody params);

}
