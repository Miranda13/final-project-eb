package com.dh.movieservice.api.controller;

import com.dh.movieservice.api.queue.MovieSender;
import com.dh.movieservice.api.service.MovieService;
import com.dh.movieservice.domain.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("")
public class MovieController {

    private MovieService movieService;
    private MovieSender movieSender;

    @Autowired
    public MovieController(MovieService movieService, MovieSender movieSender) {
        this.movieService = movieService;
        this.movieSender = movieSender;
    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<Movie>> findAllByGenre(@PathVariable String genre) {
        log.info("Get movies by genre "+ genre);
        return ResponseEntity.ok().body(movieService.findAllByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
        log.info("Save movie "+ movie);
        var resultado = movieService.saveMovie(movie);
        movieSender.send(resultado);
        return ResponseEntity.ok().body(resultado);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        log.info("Get all movies");
        return ResponseEntity.ok().body(movieService.getAllMovies());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        movieService.deleteByName(name);
        log.info("Deleted movie with name "+ name);
        return ResponseEntity.ok().body("Deleted");
    }

}
