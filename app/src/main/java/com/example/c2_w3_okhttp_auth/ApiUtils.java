package com.example.c2_w3_okhttp_auth;

import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {
    private static OkHttpClient client;
    private static Retrofit retrofitClient;
    private static Gson gson;

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

    public static Retrofit getRetrofitClient(){
        if (gson==null){
            gson = new Gson();
        }
        if (retrofitClient==null){
            Retrofit.Builder builder = new Retrofit.Builder();
            return builder
                    .baseUrl(BuildConfig.SERVER_URL)
                    //need for interceptor
                    .client(getBasicAuthClient("","",false))//"все пустые, так как берем данные из OkHttpClient"- х.з.,что это значит
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitClient;

    }

    public static void setRetrofitClient(Retrofit retrofitClient) {
        ApiUtils.retrofitClient = retrofitClient;
    }
}
