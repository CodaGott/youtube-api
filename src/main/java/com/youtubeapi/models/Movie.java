package com.youtubeapi.models;

import lombok.Data;

@Data
public class Movie {

    private String title;
    private String url;
    private String thumbnailUrl;
    private String publishDate;
    private String description;

}
