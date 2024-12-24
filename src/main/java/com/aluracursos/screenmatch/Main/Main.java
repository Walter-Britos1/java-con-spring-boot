package com.aluracursos.screenmatch.Main;

import com.aluracursos.screenmatch.models.DataSeason;
import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.models.Episode;
import com.aluracursos.screenmatch.models.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.services.ApiClient;
import com.aluracursos.screenmatch.services.DataConverter;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner input = new Scanner(System.in);
    private ApiClient apiClient = new ApiClient();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e559a79d";
    private DataConverter converter = new DataConverter();
    private List<DataSeries> dataSeries = new ArrayList<>();
    private SerieRepository repository;
    private List<Serie> serieList;

    public Main(SerieRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    \n####### Menú #######
                    \n1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    searchSerieWeb();
                    break;
                case 2:
                    searchEpisodeOfSerie();
                    break;
                case 3:
                    showSerieSearched();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DataSeries getDataSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nameSerie = input.nextLine();
        var json = ApiClient.getData(URL_BASE + nameSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DataSeries data = converter.getData(json, DataSeries.class);
        return data;
    }

    private void searchEpisodeOfSerie() {
        showSerieSearched();
        System.out.println("¿De que serie desea buscar un episodio?");
        var serieName = input.nextLine();

        Optional<Serie> serie = serieList.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(serieName.toLowerCase()))
                .findFirst();

        if (serie.isPresent()) {
            var serieFound = serie.get();
            List<DataSeason> seasons = new ArrayList<>();

            for (int i = 1; i <= serieFound.getTotalSeasons(); i++) {
                var json = ApiClient.getData(URL_BASE + serieFound.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                DataSeason dataSeason = converter.getData(json, DataSeason.class);
                seasons.add(dataSeason);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodeList = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.number(), e)))
                    .collect(Collectors.toList());

            serieFound.setTitle(episodeList.toString());
            repository.save(serieFound);
        }

    }

    private void searchSerieWeb() {
        DataSeries data = getDataSerie();
        Serie serie = new Serie(data);
        repository.save(serie);
//        dataSeries.add(data);
        System.out.println(data);
    }

    private void showSerieSearched() {
        serieList = repository.findAll();

        serieList.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }
}
