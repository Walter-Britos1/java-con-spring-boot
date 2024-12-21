package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.Main.ExampleStream;
import com.aluracursos.screenmatch.Main.Main;
import com.aluracursos.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
    @Autowired
    private SerieRepository repository;

	public static void main(String[] args) {SpringApplication.run(ScreenmatchApplication.class, args);}

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main(repository);
        main.showMenu();

//        ExampleStream exampleStream = new ExampleStream();
//        exampleStream.showNames();
    }
}
