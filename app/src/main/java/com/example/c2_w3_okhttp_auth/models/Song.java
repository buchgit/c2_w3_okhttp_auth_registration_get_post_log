package com.example.c2_w3_okhttp_auth.models;

import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("data")
    private DataDTO data;

    public static class DataDTO {

        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("duration")
        private String duration;
    }
}
