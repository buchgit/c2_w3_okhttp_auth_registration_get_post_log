package com.example.c2_w3_okhttp_auth;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiUtils {
    private static OkHttpClient client;

    public static OkHttpClient getBasicAuthClient(String email, String password, boolean newInstance) {
        if (newInstance || client == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            final String credentials = Credentials.basic(email, password);
            builder.authenticator(new Authenticator() {
                @NotNull
                @Override
                public Request authenticate(@Nullable Route route, @NotNull Response response) {
                    return response.request().newBuilder().header("Authorization", credentials).build();
                }
            });
            //log
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

            client = builder.build();
        }
        return client;
    }

}
