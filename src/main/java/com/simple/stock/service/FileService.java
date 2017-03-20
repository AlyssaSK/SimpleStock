package com.simple.stock.service;

import com.simple.stock.model.Account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    private static final String PATH = "src/main/resources/";
    public static final String ORDERS_FILE_NAME  = PATH + "orders.txt";
    public static final String CLIENTS_FILE_NAME = PATH + "clients.txt";
    public static final String RESULT_FILE_NAME  = PATH + "results.txt";

    /**
     * Возвращает поток строк полученный из файла
     * @param pathToFile - расположение файла
     * @return Stream<String>
     */
    public static Stream<String> getStream(String pathToFile ) {
        Stream<String> stringStream = Stream.empty();
        try {
            stringStream = Files.lines(Paths.get( pathToFile ), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringStream;
    }

    /**
     * Записывает коллекцию счетов в файл
     * @param accounts  набор счетов
     * @param path      расположение целевого файла
     */
    public static void writeAccounts(Collection<Account> accounts, String path) {
        accounts = accounts.stream().sorted().collect(Collectors.toList());
        try( PrintWriter pw = new PrintWriter( path ) ) {
            AtomicInteger limit = new AtomicInteger(accounts.size()) ;
            accounts.forEach(
                    account -> {
                        pw.print( account );

                        if( limit.decrementAndGet() != 0 )
                            pw.append( "\n" );
                    });
            pw.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}