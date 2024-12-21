package com.aluracursos.screenmatch.Main;

import com.aluracursos.screenmatch.models.DataSeason;
import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.models.Serie;
import com.aluracursos.screenmatch.repository.SerieRepository;
import com.aluracursos.screenmatch.services.ApiClient;
import com.aluracursos.screenmatch.services.DataConverter;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private Scanner imput = new Scanner(System.in);
    private ApiClient apiClient = new ApiClient();
    private final String URL_BASE = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e559a79d";
    private DataConverter converter = new DataConverter();
    private List<DataSeries> dataSeries = new ArrayList<>();
    private SerieRepository repository;

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
            option = imput.nextInt();
            imput.nextLine();

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
        var nameSerie = imput.nextLine();
        var json = ApiClient.getData(URL_BASE + nameSerie.replace(" ", "+") + API_KEY);
        System.out.println(json);
        DataSeries data = converter.getData(json, DataSeries.class);
        return data;
    }

    private void searchEpisodeOfSerie() {
        DataSeries dataSeries = getDataSerie();
        List<DataSeason> seasons = new ArrayList<>();

        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            var json = ApiClient.getData(URL_BASE + dataSeries.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = converter.getData(json, DataSeason.class);
            seasons.add(dataSeason);
        }
        seasons.forEach(System.out::println);
    }
    private void searchSerieWeb() {
        DataSeries data = getDataSerie();
        Serie serie = new Serie(data);
        repository.save(serie);
//        dataSeries.add(data);
        System.out.println(data);
    }

    private void showSerieSearched() {
        List<Serie> serieList = repository.findAll();

        serieList.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }
}
