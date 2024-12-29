package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.models.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository repository;

    @GetMapping("/series")
    public List<Serie> getAllSeries() {
        return repository.findAll();
    }
}
