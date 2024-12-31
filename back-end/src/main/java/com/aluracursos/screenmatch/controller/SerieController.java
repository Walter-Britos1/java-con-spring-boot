package com.aluracursos.screenmatch.controller;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.services.SerieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SerieController {

    @Autowired
    private SerieServices services;

    @GetMapping("/series")
      public List<SerieDTO> getAllSeries() {
          return services.getAllSeries();
      }

      @GetMapping("/series/top5")
    public List<SerieDTO> getTop5() {
        return services.getTop5();
      }
}
