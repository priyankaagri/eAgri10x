package com.mobile.agri10x.retrofit;

import java.io.IOException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class SSLCertificateManagment {

    private static HttpsURLConnection conn;
    private static TrustManager[] trustAllCerts ;
    private static final String alias = "agri10x";


//    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
//        @Override
//        public boolean verify(String hostname, SSLSession session) {
//            HostnameVerifier hv =
//                    HttpsURLConnection.getDefaultHostnameVerifier();
//            return hv.verify("*.agri10x.com", session);
//
//
//        }
//    };
final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
        if(hostname.equalsIgnoreCase("data.agri10x.com")) {

            return true;
        } else {
            return false;
        }
    }
};
//    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
//        public boolean verify(String hostname, SSLSession session) {
//            HostnameVerifier hv =
//                    HttpsURLConnection.getDefaultHostnameVerifier();
//            return hv.verify("example.com", session);
//            return true;
//        }
//    };

    public static void trustAllHosts() throws NoSuchAlgorithmException, KeyManagementException {

        trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                try {
//                    chain[0].checkValidity();
//                } catch (Exception e) {
//                    throw new CertificateException("Certificate not valid or trusted.");
//                }

            }
        }};
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    }




    public static HttpsURLConnection getConnection(URL url) throws KeyManagementException, NoSuchAlgorithmException, IOException {

        if (url.getProtocol().toLowerCase().equals("https")) {
            trustAllHosts();
            HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
            https.setHostnameVerifier(DO_NOT_VERIFY);
            conn = https;

        }
//        else {
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            return http;
//        }

        return conn;
    }


    }

