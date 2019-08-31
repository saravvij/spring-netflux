package com.netflux.bootstrap;

import com.netflux.domain.Movie;
import com.netflux.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


@Component
@AllArgsConstructor
public class BootstrapCLR implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapCLR.class);

    private MovieRepository movieRepository;

    @Override
    public void run(String... args)  {
        logger.info("Loading data to mongo");

        movieRepository.deleteAll()
                .doOnSuccess(data -> loadData())
                    .subscribe();


    }

    private void loadData() {
        Flux.just("Titanic", "Coco", "Avenger The End Game", "Lion King", "Bugs", "The Interview")
                .map(title -> new Movie(title))
                .flatMap(movieRepository::save)
                .subscribe(null, null, () ->
                        movieRepository.findAll().subscribe(data -> logger.info(data.toString()))
                );
    }

}
