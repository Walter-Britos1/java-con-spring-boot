package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.EpisodeDTO;
import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.services.SerieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieServices services;

    @GetMapping()
      public List<SerieDTO> getAllSeries() {
          return services.getAllSeries();
      }

      @GetMapping("/top5")
        public List<SerieDTO> getTop5() {
            return services.getTop5();
      }

    @GetMapping("/lanzamientos")
        public List<SerieDTO> getLatestReleases() {
        return services.getLatestReleases();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id) {
        return services.getSeriesById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllSeason(@PathVariable Long id) {
        return services.getAllSeason(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodeDTO> getSeasonByNumber(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return services.getSeasonByNumber(id, numeroTemporada);
    }
}
