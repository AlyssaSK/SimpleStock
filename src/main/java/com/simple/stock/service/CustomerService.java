package com.simple.stock.service;

import com.simple.stock.model.Account;
import com.simple.stock.model.Customer;

import java.util.*;
import java.util.stream.Stream;

/**
 * Сервисный класс для обработки данных о клиентах
 */
public class CustomerService {
    /**
     * Обрабатывает Stream строк, преобразуя каждую строку в запись клиент<=>счет
     * В случае больше одной записи счета с одним клиентом считается,
     * что следующая запись актуальная и баланс клиента обновляется.
     * Для одного клиента заводится только один счет.
     * @param customerStream
     * @return отображение клиент<=>счет
     */
    public static Map<Customer, Account> getAccounts(Stream<String> customerStream) {
        Map<Customer,Account> customerAccountMap = new HashMap<>();
        customerStream.forEach( (line) ->  {
            Account account = Account.getAccountFromString(line);
            if( account != null ) customerAccountMap.put( account.getCustomer(), account );
        } );
        return customerAccountMap;
    }
}