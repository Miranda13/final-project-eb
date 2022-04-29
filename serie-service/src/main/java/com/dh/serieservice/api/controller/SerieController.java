package com.dh.serieservice.api.controller;

import com.dh.serieservice.api.queue.SerieSender;
import com.dh.serieservice.api.service.SerieService;
import com.dh.serieservice.domain.model.Serie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("")
public class SerieController {

    private final SerieService serieService;
    public final SerieSender serieSender;

    @Autowired
    public SerieController(SerieService serieService, SerieSender serieSender) {
        this.serieService = serieService;
        this.serieSender = serieSender;
    }

    @GetMapping
    public ResponseEntity<?> findAllSerie(){
        log.info("Get all series");
        return ResponseEntity.ok().body(serieService.getAllSerie());
    }

    @GetMapping("/{genre}")
    public ResponseEntity<?> findAllSerieByGenre(@PathVariable String genre){
        log.info("Get all series by "+ genre);
        return ResponseEntity.ok().body(serieService.findAllByGenre(genre));
    }

    @PostMapping
    public ResponseEntity<Serie> saveSerie(@RequestBody Serie serie) {
        log.info("Saved serie "+ serie);
        var resultado = serieService.saveSerie(serie);
        serieSender.send(resultado);
        return ResponseEntity.ok().body(resultado);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteByName(@PathVariable String name) {
        serieService.deleteByName(name);
        return ResponseEntity.ok().body("Deleted");
    }


}
