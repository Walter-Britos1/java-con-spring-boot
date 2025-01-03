package com.aluracursos.screenmatch.Main;

import com.aluracursos.screenmatch.models.*;
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
    private Optional<Serie> serieSearched;

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
                    4 - Buscar serie por nombre
                    5 - Ver el top 5 de mejores series
                    6 - Buscar serie por genero
                    7 - Filtrar series por evaluacion y temporadas
                    8 - Buscar capitulos por titulo
                    9 - Mostrar top 5 de mejores episodios por serie
                                  
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
                case 4:
                    searchSerieByName();
                    break;
                case 5:
                    searchTop5Series();
                    break;
                case 6:
                    searchSerieByGenre();
                    break;
                case 7:
                    filterSeriesByRatingAndSeasons();
                    break;
                case 8:
                    searchEpisodeByTitle();
                    break;
                case 9:
                    showTop5Episodes();
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

            serieFound.setEpisodes(episodeList);
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

    private void searchSerieByName() {
        System.out.println("Escriba el titulo de la serie que desea buscar");
        var nameSerie = input.nextLine();
        serieSearched = repository.findByTitleContainsIgnoreCase(nameSerie);

        if (serieSearched.isPresent()) {
            System.out.println("La serie que busca es: " + serieSearched.get());
        } else {
            System.out.println("No se encontro ninguna serie con el nombre '" + nameSerie + "'");
        }
    }

    private void searchTop5Series() {
        List<Serie> top5Series = repository.findTop5ByOrderByRatingsDesc();
        System.out.println("Top 5 mejores series:");
        top5Series.forEach(s ->
                System.out.println("\nTitle: " + s.getTitle() + "\nRating: " + s.getRatings()));
    }

    private void searchSerieByGenre() {
        System.out.println("¿Por que genero desea buscar?");
        var serieGenre = input.nextLine();
        var genre = Genre.fromEspanol(serieGenre);

        List<Serie> serieByGenre = repository.findByGenre(genre);
        System.out.println("Las series con el genero '" + serieGenre + "', son: ");
        serieByGenre.forEach(System.out::println);
    }

    private void filterSeriesByRatingAndSeasons() {
        System.out.println("¿Por cuantas temporadas desea filtrar las series?");
        var totalSeasons = input.nextInt();
        input.nextLine();
        System.out.println("¿Que evaluacion debe tener la serie?");
        var ratings = input.nextDouble();
        input.nextLine();

        List<Serie> filterSeries = repository.seriesBySeasonAndRatings(totalSeasons, ratings);
        System.out.println("\n*** Series encontradas ***");
        filterSeries.forEach(s ->
                System.out.println(s.getTitle() + " - Rating: " + s.getRatings()));
    }

    private void searchEpisodeByTitle(){
        System.out.println("Escriba el nombre del capitulo que desea buscar");
        var titleEpisode = input.nextLine();

        List<Episode> episodesByTitle = repository.searchEpisodeByTitle(titleEpisode);
        episodesByTitle.forEach(e ->
                System.out.printf("\nSerie: %s Temporadas: %s Episodio: %s Evaluacion: %s",
                        e.getSerie(), e.getSeason(), e.getNumberEpisode(), e.getRating()));
    }

    private void showTop5Episodes() {
        searchSerieByName();
        if(serieSearched.isPresent()) {
            Serie serie = serieSearched.get();
            List<Episode> episodeList = repository.findTop5(serie);

          episodeList.forEach(e ->
                  System.out.printf("Serie: %s Temporadas: %s Episodio: %s Evaluacion: %s\n",
                          e.getSerie().getTitle(), e.getSeason(), e.getTitle(), e.getRating()));
        }
    }
}
