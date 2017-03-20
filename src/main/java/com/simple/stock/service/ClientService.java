package com.simple.stock.service;

import com.simple.stock.model.Account;
import com.simple.stock.model.Client;

import java.util.*;
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
}