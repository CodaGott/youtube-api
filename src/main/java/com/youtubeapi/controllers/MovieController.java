package com.youtubeapi.controllers;

import com.youtubeapi.models.Movie;
import com.youtubeapi.models.MovieSearchCriteria;
import com.youtubeapi.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/youtube")
    public String youtube(Model model){
        MovieSearchCriteria youtubeSearchCriteria = new MovieSearchCriteria();

        model.addAttribute("youtubeSearchCriteria");
        return "youtubeDemo";
    }

    @PostMapping("/youtube")
    public String formSubmit(@ModelAttribute("youtubeSearchCriteria") @Valid MovieSearchCriteria youtubeSearchCriteria, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "youtubeDemo";
        }

        List<Movie> movies = movieService.getVideos(youtubeSearchCriteria.getQueryTerm());

        if (movies != null && movies.size() > 0){
            model.addAttribute("numberOfVideos", movies.size());
        }else {
            model.addAttribute("numberOfVideos", 0);
        }

        model.addAttribute("videos", movies);

        model.addAttribute("youtubeSearchCriteria", youtubeSearchCriteria);

        return "showYoutubeResults";
    }

    @RequestMapping("/")
    public String home(Model model){
        return "redirect:youtubeDemo";
    }
}
