package com.mobile.agri10x.retrofit;

import com.mobile.agri10x.models.QueryWearHouseForm;
import com.mobile.agri10x.models.DisplayQuickView;
import com.mobile.agri10x.models.FilterProducts;
import com.mobile.agri10x.models.FilterState;
import com.mobile.agri10x.models.GetADDWishlist;
import com.mobile.agri10x.models.GetAddAddress;
import com.mobile.agri10x.models.GetAddNewStock;
import com.mobile.agri10x.models.GetAddProductToCart;
import com.mobile.agri10x.models.GetBookOrder;
import com.mobile.agri10x.models.GetBookingCheckOutHandling;
import com.mobile.agri10x.models.GetBookingDeatils;
import com.mobile.agri10x.models.GetCategories;
import com.mobile.agri10x.models.GetCheckCollect;
import com.mobile.agri10x.models.GetCities;
import com.mobile.agri10x.models.GetCommodities;
import com.mobile.agri10x.models.GetCommodity;
import com.mobile.agri10x.models.GetCommodityById;
import com.mobile.agri10x.models.GetCreateBooking;
import com.mobile.agri10x.models.GetCreateCheckout;
import com.mobile.agri10x.models.GetCreateCheckoutDetails;
import com.mobile.agri10x.models.GetCreateOrder;
import com.mobile.agri10x.models.GetFeaturesbyCommodity;
import com.mobile.agri10x.models.GetFilterPrice;
import com.mobile.agri10x.models.GetGrades;
import com.mobile.agri10x.models.GetHomeProduct;
import com.mobile.agri10x.models.GetLiveTrades;
import com.mobile.agri10x.models.GetLot;
import com.mobile.agri10x.models.GetOTP;
import com.mobile.agri10x.models.GetOrderCheckOutHandling;
import com.mobile.agri10x.models.GetOrderList;
import com.mobile.agri10x.models.GetProductInWishList;
import com.mobile.agri10x.models.GetProductsInCart;
import com.mobile.agri10x.models.GetQueryDailyDeals;
import com.mobile.agri10x.models.GetQueryFeaturedOnly;
import com.mobile.agri10x.models.GetQueryTopicPicks;
import com.mobile.agri10x.models.GetRemoveFromWishlist;
import com.mobile.agri10x.models.GetRemoveProduct;
import com.mobile.agri10x.models.GetResendOTP;
import com.mobile.agri10x.models.GetStates;
import com.mobile.agri10x.models.GetStockByID;
import com.mobile.agri10x.models.GetWorkerForm;
import com.mobile.agri10x.models.GetUser;
import com.mobile.agri10x.models.GetVarieties;
import com.mobile.agri10x.models.NegotiateRate;
import com.mobile.agri10x.models.QueryCreateCheckOut;
import com.mobile.agri10x.models.QueryCreatebooking;
import com.mobile.agri10x.models.QueryWorkerForm;
import com.mobile.agri10x.models.QuerytransportForm;
import com.mobile.agri10x.models.UpdateCart;
import com.mobile.agri10x.models.UpdateUser;
import com.mobile.agri10x.models.VerifyOTP;
import com.mobile.agri10x.models.getAddress;
import com.mobile.agri10x.models.getCommAccToCat;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
    Call<GetUser>wsGetUserById(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/updateUser")
    Call<UpdateUser> wsGetUpdateUser(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/addAddress")
    Call<GetAddAddress>wsGetAddAddress(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/createBooking")
    Call<GetCreateBooking> wsCreateBooking(@Header("x-auth-token") String token, @Body QueryCreatebooking queryCreatebooking);
    @POST("m/getBookingDetails")
    Call<GetBookingDeatils> wsGetBookingDeatils(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/bookOrder")
    Call<GetBookOrder> wsBookOrder(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/createCheckout")
    Call<GetCreateCheckout> wsGetCreateCheckOut(@Header("x-auth-token") String token, @Body QueryCreateCheckOut queryCreateCheckOut);
    @POST("m/getCheckoutDetails")
    Call<GetCreateCheckoutDetails> wsGetCheckoutDeatils(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/createOrder")
    Call<GetCreateOrder> wsCheckOrder(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/orderCheckoutHandling")
    Call<GetOrderCheckOutHandling> wsCheckOrderCheckOutHandling(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/bookingCheckoutHandling")
    Call<GetBookingCheckOutHandling> wsCheckBookingCheckOutHandling(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/checkEcollect")
    Call<GetCheckCollect> wsCheckECollect(@Header("x-auth-token") String token, @Body RequestBody params);
    @GET("m/getStates")
    Call<GetStates> wsGetStates(@Header("x-auth-token") String token);
    @POST("m/getCities")
    Call<GetCities> wsGetCities(@Header("x-auth-token") String token, @Body RequestBody params);
    @GET("m/getCommodity")
    Call<GetCommodity> wsGetCommodity(@Header("x-auth-token") String token);
    @GET("m/getCommodities")
    Call<GetCommodities> wsGetCommodities(@Header("x-auth-token") String token);
    @POST("m/getCommodityById")
    Call<GetCommodityById> wsGetCommodityByID(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/getVarieties")
    Call<GetVarieties> wsGetVarieties(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/getFeatures")
    Call<GetFeaturesbyCommodity> wsGetFeaturesbyCommodity(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/addNewStock")
    Call<GetAddNewStock> wsGetAddNewStock(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/getStockbyUserID")
    Call<GetStockByID> wsGetStockByID(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/negotiateRate")
    Call<NegotiateRate> wsGetNegotiateRate(@Header("x-auth-token")String token, @Body RequestBody params);
    @POST("m/filterProducts")
    Call<FilterProducts> wsGetFilterProducts(@Header("x-auth-token") String token , @Body RequestBody params);
    @POST("m/filterPrice")
    Call<GetFilterPrice> wsGetFilterPrice(@Header("x-auth-token")String token, @Body RequestBody params);
    @GET("m/getLots")
    Call<GetLot> wsGetLots(@Header("x-auth-token")String token);
    @GET("m/getGrades")
    Call<GetGrades> wsGetGrades(@Header("x-auth-token") String token);
    @GET("m/filterState")
    Call<FilterState> wsFilterState(@Header("x-auth-token") String token);
    @POST("m/addToWishList")
    Call<GetADDWishlist> wsAddWishlist(@Header("x-auth-token") String token , @Body RequestBody params);
    @POST("m/getProductsInWishlist")
    Call<GetProductInWishList> wsGetProductInWhishlist(@Header("x-auth-token") String token, @Body RequestBody params);
    @POST("m/removeProductFromWishlist")
    Call<GetRemoveFromWishlist> wsGetRemoveFromWiishList(@Header("x-auth-token") String token, @Body RequestBody params);
@POST("m/submitContactForm")
    Call<GetWorkerForm> wsGetWorkerForm(@Header("x-auth-token")String token, @Body QueryWorkerForm querySubmitForm);
    @POST("m/submitContactForm")
    Call<GetWorkerForm> wsTransportForm(@Header("x-auth-token")String token, @Body QuerytransportForm querytransportForm);
    @POST("m/submitContactForm")
    Call<GetWorkerForm> wsWearHouseForm(@Header("x-auth-token")String token,@Body QueryWearHouseForm queryWearHouseForm);
    @POST("m/getOrderList")
    Call<GetOrderList> wsOrdeList(@Header("x-auth-token")String token, @Body RequestBody params);




}
