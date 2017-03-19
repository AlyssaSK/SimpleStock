package com.simple.stock.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileService {
    private static final String PATH = "src/main/resources/";
    public static final String ORDERS_FILE_NAME  = PATH + "orders.txt";
    public static final String CLIENTS_FILE_NAME = PATH + "clients.txt";
    public static final String RESULT_FILE_NAME  = PATH + "results.txt";

    public static Stream<String> getStream(String pathToFile ) {
        Stream<String> stringStream = Stream.empty();
        try {
            stringStream = Files.lines(Paths.get( pathToFile ), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringStream;
    }
}