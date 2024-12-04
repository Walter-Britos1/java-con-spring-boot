package com.aluracursos.screenmatch.Main;

import java.util.Arrays;
import java.util.List;

public class ExampleStream {
    public void showNames () {
        List<String> names = Arrays.asList("Walter", "Leyner", "Moises", "Yamila", "Gianela");

        // Usamos la API de stream para hacer operaciones encadenadas
        names.stream()
                // Ordenamos alfabeticamente
                .sorted()
                // Filtramos los nombres por el que empiece con la letra "Y"
                .filter(n -> n.startsWith("Y"))
                // Limitamos la lista
                .limit(3)
                // Mapeamos la lista para devolver el nombre en mayusculas
                .map(String::toUpperCase)
                // Imprimimos en consola el resultado
                .forEach(System.out::println);
    }
}
