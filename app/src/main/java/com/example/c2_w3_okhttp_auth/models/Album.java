package com.example.c2_w3_okhttp_auth.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Album {

    @SerializedName("data")
    private DataDTO data;

    public static class DataDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("songs")
        private List<SongsDTO> songs;
        @SerializedName("release_date")
        private String releaseDate;

        public static class SongsDTO {
            @SerializedName("id")
            private Integer id;
            @SerializedName("name")
            private String name;
            @SerializedName("duration")
            private String duration;
        }
    }
}
