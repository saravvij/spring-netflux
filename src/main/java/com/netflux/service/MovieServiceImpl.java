package com.netflux.service;

import com.netflux.domain.Movie;
import com.netflux.domain.MovieEvent;
import com.netflux.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Mono<Movie> getMovieById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Flux<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Flux<MovieEvent> getMovieEventsById(String id) {
        return Flux.<MovieEvent>generate(movieEventSynchronousSink ->
            movieEventSynchronousSink.next(new MovieEvent(id, new Date()))
        ).delayElements(Duration.ofSeconds(2));
    }
}
