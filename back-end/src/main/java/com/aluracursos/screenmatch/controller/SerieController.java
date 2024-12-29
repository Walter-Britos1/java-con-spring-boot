package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository repository;

    @GetMapping("/series")
    public List<SerieDTO> getAllSeries() {
        return repository.findAll().stream()
                .map(s -> new SerieDTO(
                        s.getTitle(),
                        s.getTotalSeasons(),
                        s.getRatings(),
                        s.getGenre(),
                        s.getSynopsis(),
                        s.getPoster(),
                        s.getActors())).collect(Collectors.toList());
    }
}
