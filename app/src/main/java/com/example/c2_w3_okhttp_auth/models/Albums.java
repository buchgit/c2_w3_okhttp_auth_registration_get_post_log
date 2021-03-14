package com.example.c2_w3_okhttp_auth.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Albums {
    @SerializedName("data")
    private List<DataDTO> data;

    public static class DataDTO {
        @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;
        @SerializedName("songs_count")
        private Integer songsCount;
        @SerializedName("release_date")
        private String releaseDate;
    }
}
