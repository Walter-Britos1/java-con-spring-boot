package com.aluracursos.screenmatch.Main;

import com.aluracursos.screenmatch.models.DataEpisode;
import com.aluracursos.screenmatch.models.DataSeason;
import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.services.ApiClient;
import com.aluracursos.screenmatch.services.DataConverter;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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

        // Convertir toda la informacion a una lista del tipo "DataEpisode"
        List<DataEpisode> dataEpisodes = seasons.stream()
                .flatMap(s -> s.episodes().stream())
                .collect(Collectors.toList());

        // Top 5 episodeos
        System.out.println("Top 5 mejores episodios");
        dataEpisodes.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisode::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        // Convertir los datos en una lista de tipo Episode
        List<Episode> episodes = seasons.stream()
                .flatMap(t -> t.episodes().stream()
                        .map(d -> new Episode(t.number(), d)))
                .collect(Collectors.toList());

//        episodes.forEach(System.out::println);

        // Busqueda de episodios a partir de x año
        System.out.println("Por favor ingrese el año de los episodios que desea ver");
        var date = imput.nextInt();
        imput.nextLine();

        LocalDate searchDate = LocalDate.of(date, 1, 1);

        episodes.stream()
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println(
                        "Season: " + e.getSeason() +
                                "\n Episode: " + e.getTitle() +
                                "\n Release date: " + e.getReleaseDate()
                ));

        // Buscar episodio por titulo
        System.out.println("Por favor ingrese el nombre del titulo que desea ver");
        var searchTitle = imput.nextLine();

        Optional<Episode> titleSought = episodes.stream()
                .filter(e -> e.getTitle().toUpperCase().contains(searchTitle.toUpperCase()))
                .findFirst();

        if (titleSought.isPresent()) {
            System.out.println("Episodio encontrado");
            System.out.println("Datos del episodio: " + titleSought.get());
        } else {
            System.out.println("El ipisodio no se pudo encontrar, intente nuevamente");
        }

        // Creando estadisticas de temporadas
        Map<Integer, Double> reatingForSeason = episodes.stream()
                .filter(e -> e.getReating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getReating)));

        System.out.println(reatingForSeason);

    }
}
