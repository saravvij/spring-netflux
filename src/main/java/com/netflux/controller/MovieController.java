package com.netflux.controller;

import com.netflux.domain.Movie;
import com.netflux.domain.MovieEvent;
import com.netflux.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    // private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    @GetMapping("/{id}")
    public Mono<Movie> getMovieById(@PathVariable("id") String id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    public Flux<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MovieEvent> streamMovieEvents(@PathVariable("id") String id) {
        return movieService.getMovieEventsById(id);
    }
}
