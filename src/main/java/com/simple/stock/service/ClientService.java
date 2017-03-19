package com.simple.stock.service;

import com.simple.stock.model.Account;
import com.simple.stock.model.Client;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientService {
    public static Map<Client, Account> getAccounts(Stream<String> clientsStream) {
        Map<Client,Account> clientAccountMap = new HashMap<>();
        clientsStream.forEach( (line) ->  {
            Account account = Account.getAccountFromString(line);
            if( account != null ) clientAccountMap.put( account.getKey(), account );
        } );
        return clientAccountMap;
    }

    public static void writeAccounts(Collection<Account> accounts, String path) {
        accounts = accounts.stream().sorted().collect(Collectors.toList());
        try(PrintWriter pw = new PrintWriter( path )) {
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