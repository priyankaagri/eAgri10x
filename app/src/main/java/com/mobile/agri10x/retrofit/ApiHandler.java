package com.mobile.agri10x.retrofit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.mobile.agri10x.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static final String development_BASE_URL = "https://data-uat.agri10x.com/";
    private static final String Production_BASE_URL = "https://data.agri10x.com/";
//"https://data.agri10x.com/"

    private static final long HTTP_TIMEOUT = TimeUnit.SECONDS.toMillis(60);
    private static AgriInvestor apiService;
    static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(development_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static AgriInvestor getApiService() {
        if (apiService == null) {
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(30, TimeUnit.SECONDS);
            httpClient.writeTimeout(15, TimeUnit.SECONDS);
//            httpClient.connectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
//            httpClient.writeTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
//            httpClient.readTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
            httpClient.retryOnConnectionFailure(true);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            Retrofit retrofit = builder.client(okHttpClient).build();//httpClient.build()
            apiService = retrofit.create(AgriInvestor.class);
            return apiService;
        }
        else {

            return apiService;
        }
    }
}
