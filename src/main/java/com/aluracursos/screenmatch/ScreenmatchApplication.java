package com.aluracursos.screenmatch;

import com.aluracursos.screenmatch.Main.ExampleStream;
import com.aluracursos.screenmatch.Main.Main;
import org.springframework.boot.CommandLineRunner;56a
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {SpringApplication.run(ScreenmatchApplication.class, args);}

    @Override
    public void run(String... args) throws Exception {
        Main main = new Main();
        main.showMenu();

//        ExampleStream exampleStream = new ExampleStream();
//        exampleStream.showNames();
    }
}
