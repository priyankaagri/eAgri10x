package com.mobile.eagri10x.retrofit;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.mobile.eagri10x.R;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {
    private static Retrofit retrofit = null;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static SSLContext getSSLConfig(Context context) throws CertificateException, IOException,
            KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        // Loading CAs from an InputStream
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");

        Certificate ca;
        // I'm using Java7. If you used Java6 close it manually with finally.
        try (InputStream cert = context.getResources().openRawResource(R.raw.allagri10x)) {
            ca = cf.generateCertificate(cert);
        }

        // Creating a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore   = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Creating an SSLSocketFactory that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }
    public static Retrofit getClient(Context context) {

        try {
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl("");
            OkHttpClient okHttp = new OkHttpClient.Builder()
                    .sslSocketFactory(getSSLConfig(context).getSocketFactory())
                    .build();

            if (retrofit==null) {
                retrofit = builder.client(okHttp)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        } catch (KeyManagementException ex) {

        } catch (NoSuchAlgorithmException ex) {

        } catch (KeyStoreException ex) {

        } catch (CertificateException ex) {

        } catch (IOException ex) {

        }

        return retrofit;
    }
}
