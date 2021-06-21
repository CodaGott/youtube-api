package com.youtubeapi.models;

import lombok.Data;
@Data
public class MovieSearchCriteria {


//    @Size(min=5, max=64, message="Search term must be between 5 and 64 characters")
    private String queryTerm;
}
