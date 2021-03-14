package com.example.c2_w3_okhttp_auth.api;

import com.example.c2_w3_okhttp_auth.models.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServerApi {

    @POST("registration")
    Call<Void> registration(@Body User user);

    @GET("albums")
    Call<Albums> getAlbums();

    @GET("albums/{id}")
    Call<Album> getAlbum(@Path("id") int id);

    @GET("songs")
    Call<Songs> getSongs();

    @GET("songs/{id}")
    Call<Song>getSong(@Path("id") int id);


}
