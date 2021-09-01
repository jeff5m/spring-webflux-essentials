package academy.devdojo.webflux.controller;

import academy.devdojo.webflux.domain.Anime;
import academy.devdojo.webflux.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("animes")
@Slf4j
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public Flux<Anime> listAll() {
        log.info("Listing all animes in database");
        return animeService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Mono<Anime> findById(@PathVariable int id) {
        log.info("Finding anime with id: {} in database", id);
        return animeService.findById(id)
                .switchIfEmpty(monoResponseStatusException())
                .log(); // not necessary
    }

    private <T> Mono<T> monoResponseStatusException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }
}
