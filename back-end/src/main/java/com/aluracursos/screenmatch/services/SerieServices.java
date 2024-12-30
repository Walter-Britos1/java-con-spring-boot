package com.aluracursos.screenmatch.services;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SerieServices {

    @Autowired
    private SerieRepository repository;

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
