package com.mobile.agri10x.retrofit;

import com.mobile.agri10x.models.DisplayQuickView;
import com.mobile.agri10x.models.GetAddAddress;
import com.mobile.agri10x.models.GetAddProductToCart;
import com.mobile.agri10x.models.GetBookingDeatils;
import com.mobile.agri10x.models.GetCategories;
import com.mobile.agri10x.models.GetCheckCollect;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCreateBooking;
import com.mobile.agri10x.models.GetCreateOrder;
import com.mobile.agri10x.models.GetFeatureOnlyProduct;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetLiveTrades;
import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.models.GetProductsInCart;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.GetQueryFeaturedOnly;
import com.mobile.agri10x.models.GetQueryTopicPicks;
import com.mobile.agri10x.models.GetRemoveProduct;
import com.mobile.agri10x.models.GetResendOTP;
import com.mobile.agri10x.models.GetUserByID;
import com.mobile.agri10x.models.QueryCreatebooking;
import com.mobile.agri10x.models.UpdateCart;
import com.mobile.agri10x.models.VerifyOTP;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getAddressData;
import com.mobile.agri10x.models.getCommAccToCat;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AgriInvestor {
    @POST("m/getOTPNew")
    Call<GetOTP> wsgetOTP(@Header("x-auth-token") String token,@Body RequestBody params);
    @POST("m/verifyOTPNew")
    Call<VerifyOTP> wsgetVerifyOTP(@Header("x-auth-token") String token,@Body RequestBody params);
    @POST("m/resendOTP")
    Call<GetResendOTP> wsgetresendOTP(@Header("x-auth-token") String token,@Body RequestBody params);
    @POST("m/homepageProducts")
    Call<GetHomeProduct> wsgetHomeProduct(@Header("x-auth-token") String token,@Body GetQueryDailyDeals getQueryDailyDeals);
    @GET("m/getCategories")
    Call<GetCategories> getCategories(@Header("x-auth-token") String token);
    @POST("m/homepageProducts")
    Call<GetHomeProduct> wsgetHomeProductTopic(@Header("x-auth-token") String token,@Body GetQueryTopicPicks getQueryTopicPicks);
    @POST("m/displayProducts")
    Call<DisplayQuickView> wsgetdisplayQuickView(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/homepageProducts")
    Call<GetHomeProduct> wsgetFeatureOnlyProduct(@Header("x-auth-token") String token, @Body GetQueryFeaturedOnly getQueryFeaturedOnly);
    @POST("m/displayProducts")
    Call<GetLiveTrades>wsgetlivetrades(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/getProductsInCart")
    Call<GetProductsInCart>wsgetProductinCart(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/addToCart")
    Call<GetAddProductToCart>wsGetAddproducttocart(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/removeProduct")
    Call<GetRemoveProduct>wsGetRemoveProduct(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/updateCart")
    Call<UpdateCart>wsGetUpdateCart(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/getAddress")
    Call<getAddress>wsGetAddress(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/getCommAccToCat")
    Call<getCommAccToCat>wsGetCommAccToCat(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/getUserByID")
    Call<GetUserByID>wsGetUserById(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/addAddress")
    Call<GetAddAddress>wsGetAddAddress(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/createBooking")
    Call<GetCreateBooking> wsCreateBooking(@Header("x-auth-token") String token, @Body QueryCreatebooking queryCreatebooking);
    @POST("m/getBookingDetails")
    Call<GetBookingDeatils> wsGetBookingDeatils(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/checkEcollect")
    Call<GetCheckCollect> wsCheckECollect(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/createOrder")
    Call<GetCreateOrder> wsCheckOrder(@Header("x-auth-token") String token, @Body RequestBody params);


}
