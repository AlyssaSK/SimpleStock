package com.simple.stock.service;

import com.simple.stock.model.Account;
import com.simple.stock.model.Customer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CustomerServiceTest {
    private String[] strings = new String[4];
    private Stream<String> stream = Stream.empty();
    @Before
    public void setUp() {
        strings[0] = "C1\t1000\t130\t240\t760\t320";
        strings[1] = "C2\t4350\t370\t120\t950\t560";
        strings[2] = "C3\t2760\t0\t0\t0\t0";
        strings[3] = "C4\t560\t450\t540\t480\t950";
        stream = Arrays.stream(strings);

    }

    @Test
    public void testGetAccountsOK() throws Exception {
        Account account1 = Account.getAccountFromString(strings[0]);
        Account account2 = Account.getAccountFromString(strings[1]);

        final Map<Customer, Account> accounts = CustomerService.getAccounts(stream);

        assertTrue( accounts.get(new Customer("C1")).equals(account1));
        assertTrue( accounts.values().contains(account1));
        assertTrue( accounts.values().contains(account2));
        assertFalse( accounts.get(new Customer("C2")).equals(account1));
    }

    @Test
    public void testGetAccountsFromEmptyStream() throws Exception {
        assertEquals( 0, CustomerService.getAccounts(Stream.empty()).size() );
    }

    @Test
    public void testGetAccountsFromEmptyStreamWithDuplicateClientsRecord() throws Exception {
        String[] stringsNew = new String[5];
        System.arraycopy(strings, 0, stringsNew, 0, 4 );
        stringsNew[4] = strings[0];
        final Map<Customer, Account> accounts = CustomerService.getAccounts(Stream.of(stringsNew));
        assertEquals( 4, accounts.size() );
        assertTrue(accounts.values().contains(Account.getAccountFromString(stringsNew[0]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[1]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[2]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[3]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[3]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[4]))
        );
    }

    /**
     * Проверяет ситуацию, если в массиве счетов будет запись с тем же клиентом.
     * Считается, что следующая запись актуальная и баланс клиента обновляется.
     * Для одного клиента заводится только один счет.
     */
    @Test
    public void testGetAccountsFromEmptyStreamWithDuplicateClients() throws Exception {
        String[] stringsNew = new String[5];
        System.arraycopy(strings, 0, stringsNew, 0, 4 );
        stringsNew[4] = "C1\t100\t130\t240\t760\t320";
        final Map<Customer, Account> accounts = CustomerService.getAccounts(Stream.of(stringsNew));
        assertEquals( 4, accounts.size() );
        assertFalse(accounts.values().contains(Account.getAccountFromString(stringsNew[0])));

        assertTrue(accounts.values().contains(Account.getAccountFromString(stringsNew[4]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[1]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[2]))
                && accounts.values().contains(Account.getAccountFromString(stringsNew[3]))
        );
    }
}