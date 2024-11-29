package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.models.DataSeries;
import com.aluracursos.screenmatch.services.ApiClient;
import com.aluracursos.screenmatch.services.DataConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {SpringApplication.run(ScreenmatchApplication.class, args);}

    @Override
    public void run(String... args) throws Exception {
        var apiClient = new ApiClient();
        var json = apiClient.getData("https://www.omdbapi.com/?t=game+of+thrones&apikey=e559a79d");

        System.out.println(json);

        DataConverter converter = new DataConverter();

        var data = converter.getData(json, DataSeries.class);

        System.out.println(data);
    }
}
