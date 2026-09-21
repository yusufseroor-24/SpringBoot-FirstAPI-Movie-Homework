package com.ga.movie.movie_tv_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api-hw")
public class HomeController {
    @GetMapping("/welcome")
    public HashMap<String, String> welcome(){
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("Application", "TVTrack");
        response.put("Developer", "Yusuf Seroor");
        response.put("Description", "A REST API for browsing and rating my personal movies.");
        response.put("Theme", "Movies & TV Profile");
        return response;
    }

    @GetMapping("/profile")
    public HashMap<String, String> profile(){
        HashMap<String, String> response = new HashMap<String, String>();
        response.put("name", "Yusuf Seroor");
        response.put("Bio",  "Watching movies and tv shows is one of my favorite hobbies :)");
        response.put("favorite Movie", "Interstellar");
        return response;
    }


}
