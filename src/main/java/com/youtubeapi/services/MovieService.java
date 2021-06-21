package com.youtubeapi.services;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.youtubeapi.models.Movie;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {

    private static final long MAX_SEARCH_RESULTS = 5;

    public List<Movie> getVideos(String queryTerm){
        List<Movie> videos = new ArrayList<>();

        try{
            YouTube youTube = getYouTube();

            YouTube.Search.List search = youTube.search().list("id,snippet");

            String apiKey = "AIzaSyAj5TrumoUcbgjr8uVXQJYlI1sfVKYvYYA";
            search.setKey(apiKey);

            search.setQ(queryTerm);

            search.setType("video");

            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url)");

            search.setMaxResults(MAX_SEARCH_RESULTS);

            DateFormat df = new SimpleDateFormat("MMM dd, yyyy");

            SearchListResponse searchListResponse = search.execute();
            List<SearchResult> searchResults = searchListResponse.getItems();
            if(searchResults != null){
                for (SearchResult result: searchResults) {
                    Movie movie = new Movie();
                    movie.setTitle(result.getSnippet().getTitle());
                    movie.setUrl(buildVideoUrl(result.getSnippet().getThumbnails().getDefault().getUrl()));
                    movie.setDescription(result.getSnippet().getDescription());

                    DateTime dateTime = result.getSnippet().getPublishedAt();
                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    movie.setPublishDate(dateString);

                    videos.add(movie);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return videos;
    }

    private String buildVideoUrl(String videoId) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.youtube.com/watch?v=");
        builder.append(videoId);

        return builder.toString();
    }

    private YouTube getYouTube() {
        YouTube youTube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                (request) -> {}).setApplicationName("MovieApiConsumer").build();
        return youTube;
    }

    public static void main(String[] args) {
        new MovieService().getVideos("java");
    }
}
