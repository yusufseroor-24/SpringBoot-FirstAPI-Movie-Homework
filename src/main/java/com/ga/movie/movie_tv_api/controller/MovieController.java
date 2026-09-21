package com.ga.movie.movie_tv_api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api-hw")
public class MovieController {
    private ArrayList<HashMap<String, Object>> movies = new ArrayList<>();
    private int nextId = 1;

    private void addMovie(String title, String type, String genre, int year, double rating, String status){
        HashMap<String, Object> movie = new HashMap<>();
        movie.put("id", nextId);
        movie.put("title", title);
        movie.put("type", type);
        movie.put("genre", genre);
        movie.put("year", year);
        movie.put("rating", rating);
        movie.put("status", status);
        movies.add(movie);
        nextId++;
    }
    public MovieController(){
        addMovie("Inception", "Movie", "Sci-Fi", 2010, 8.8, "Watched");
        addMovie("Breaking Bad", "TV Show", "Crime Drama", 2008, 9.5, "Watched");
        addMovie("Stranger Things", "TV Show", "Sci-Fi", 2016, 8.7, "Watching");
        addMovie("Parasite", "Movie", "Thriller", 2019, 8.6, "Watched");
        addMovie("The Office", "TV Show", "Comedy", 2005, 9.0, "Watched");
        addMovie("Dark", "TV Show", "Thriller", 2017,9.8, "Watching");
    }

    @GetMapping("/Movies&TvshowsList")
    public ArrayList<HashMap<String, Object>> getAllMovies(){
        return movies;
    }

    @GetMapping("/search")
    public ArrayList<HashMap<String, Object>> search(@RequestParam (value = "title") String title) {
        ArrayList<HashMap<String, Object>> searchResult = new ArrayList<>();
        for(HashMap<String, Object> movie : movies){
            String movieTitle = movie.get("title").toString();
            if(movieTitle.toLowerCase().contains(title.toLowerCase())){
                searchResult.add(movie);
            }
        }
        return searchResult;

    }

    @GetMapping("/{id}")
    public HashMap<String, Object> getMovieById(@PathVariable ("id") int id){
        for(HashMap<String, Object> movie : movies){
            if((int) movie.get("id") == id){
                return movie;
            }
        }
        HashMap<String, Object> errorMessageIfNotFound = new HashMap<>();
        errorMessageIfNotFound.put("Error", "Movie ID not found");
        return errorMessageIfNotFound;
    }

    @GetMapping("/filter")
    public ArrayList<HashMap<String, Object>> filter(@RequestParam(value = "genre") String genre){
        ArrayList<HashMap<String, Object>> filterResult = new ArrayList<>();
        for(HashMap<String, Object> movie : movies){
            if(((String) movie.get("genre")).equalsIgnoreCase(genre)){
                filterResult.add(movie);
            }
        }
        return filterResult;
    }

    @PostMapping("/createMovie")
    public HashMap<String, Object> createMovie(@RequestBody HashMap<String, Object> newMovie){
        newMovie.put("id", nextId);
        movies.add(newMovie);
        nextId++;
        return newMovie;
    }

    @PutMapping("/{id}")
    public HashMap<String, Object> updateMovie(@PathVariable(value = "id") int id,
                                               @RequestBody HashMap<String, Object> updatedMovie){
        for(HashMap<String, Object> movie : movies){
            if((int) movie.get("id") == id){
                movie.putAll(updatedMovie);
                movie.put("id", id);
                return movie;
            }
        }
        HashMap<String, Object> errorMessageIfNotFound = new HashMap<>();
        errorMessageIfNotFound.put("Error", "Movie not found");
        return errorMessageIfNotFound;
    }

    @DeleteMapping("/{id}")
    public HashMap<String, Object> deleteMovie(@PathVariable (value = "id") int id){
        HashMap<String, Object> movieToBeRemoved = null;
        for(HashMap<String, Object> movie : movies){
            if((int) movie.get("id") == id){
                movieToBeRemoved = movie;
                break;
            }
        }
        HashMap<String, Object> response = new HashMap<>();
        movies.remove(movieToBeRemoved);
        response.put("message", "Movie deleted");
        return response;
    }

    @GetMapping("/stats")
    public HashMap<String, Object> getStats(){
        int totalMovies = 0;
        int totalTvShows = 0;

        for(HashMap<String, Object> movie : movies){
            String type = (String) movie.get("type");
            if(type.equalsIgnoreCase("Movie")){
                totalMovies++;
            } else if (type.equalsIgnoreCase("TV Show")) {
                totalTvShows++;
            }
        }

        HashMap<String, Object> stats = new HashMap<>();
        stats.put("totalMovies", totalMovies);
        stats.put("totalTvShows", totalTvShows);
        stats.put("totalWatchedItems", movies.size());
        return stats;
    }

    @GetMapping("/high-ratings")
    public ArrayList<HashMap<String, Object>> highRated() {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        for (HashMap<String, Object> movie : movies) {
            double rating = (double) movie.get("rating");
            if(rating > 8){
                result.add(movie);
            }
        }
        return result;
    }


}
