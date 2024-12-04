package com.aluracursos.screenmatch.Main;

import com.aluracursos.screenmatch.models.DataEpisode;
import com.aluracursos.screenmatch.models.DataSeason;
import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.services.ApiClient;
import com.aluracursos.screenmatch.services.DataConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner imput = new Scanner(System.in);
    private ApiClient apiClient = new ApiClient();

    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e559a79d";

    private DataConverter converter = new DataConverter();


    public void showMenu() {

        System.out.println("Ingrese el nombre de la serie que desea buscar");

        // Busca los datos generales de las series
        var nameSerie = imput.nextLine();
        var json = apiClient.getData(URL_BASE + nameSerie.replace(" ", "+") + API_KEY);
        var data = converter.getData(json, DataSeries.class);
//        System.out.println(data);

        // Busca los datos de todas las temporadas
        List<DataSeason> seasons = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons(); i++) {
            json = apiClient.getData(URL_BASE + nameSerie.replace(" ", "+") + "&Season="+i+API_KEY);
            var dataSeason = converter.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }

        // Mostrar solo los titulos de los episodios para las temporadas
        // Utilizando bucles for
        for (int i = 0; i < data.totalSeasons(); i++) {
            List<DataEpisode> episodesSeason = seasons.get(i).episodes();
            for (int j = 0; j < episodesSeason.size(); j++) {
                System.out.println("Nombre del episodio: " + episodesSeason.get(j).title());
            }
        }

        // Utilizando funciones lambdas
        seasons.forEach(s -> s.episodes().forEach(e -> System.out.println("Nombre del episodio: " + e.title())));
    }

}
