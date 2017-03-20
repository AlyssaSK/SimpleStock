package com.simple.stock.service;

import com.simple.stock.model.Account;
import com.simple.stock.model.Customer;
import com.simple.stock.model.Order;
import com.simple.stock.ref.OperationType;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class OrderServiceTest {

    @Test
    public void getOrderMap() throws Exception {
        String[] strings = {
                "C8\ts\tC\t15\t4",
                "C1\tb\tC\t14\t5",
                "C2\ts\tC\t13\t2",
                "C3\tb\tC\t14\t5"};
        List<Order> orders = new ArrayList<>();
        Arrays.stream(strings).forEach( string ->  orders.add(Order.getOrderFromString(string)) );
        Stream<String> ordersStream = Stream.of(strings);
        final Map<OperationType, List<Order>> orderMap = OrderService.getOrderMap(ordersStream);
        Order expSell1  = Order.getOrderFromString("C8\ts\tC\t15\t4");
        Order expBuy1   = Order.getOrderFromString("C1\tb\tC\t14\t5");
        Order expSell2  = Order.getOrderFromString("C2\ts\tC\t13\t2");
        Order expBuy2   = Order.getOrderFromString("C3\tb\tC\t14\t5");

        assertTrue( orderMap.get(OperationType.BUY).size()  == 2 );
        assertTrue( orderMap.get(OperationType.SELL).size() == 2 );
        assertTrue( orderMap.get(expSell1.getValue().getOperationType()).contains(expSell1) );
        assertTrue( orderMap.get(OperationType.SELL).containsAll( Arrays.asList(expSell1, expSell2) ) );
        assertTrue( orderMap.get(OperationType.BUY).containsAll( Arrays.asList(expBuy1, expBuy2) ) );
        assertFalse( orderMap.get(OperationType.BUY).contains(expSell1) );
        assertFalse( orderMap.get(OperationType.SELL).contains(expBuy1) );
    }

    private Map<Customer, Account> accounts;
    private Map<Customer, Account> accountsExpected;

    private Map<OperationType, List<Order>> orders;
    @Before
    public void setUp() {
        setCustomerAccountMap();
        setExpectedCustomerAccountMap();

        Stream<String> stringStream =
                Stream.of(  "C4\ts\tD\t15\t4",  // false: Нет соответствующей заявки на покупку
                            "C1\ts\tC\t15\t5",  // true : Обработается C1 +75$ -5C
                            "C2\tb\tC\t15\t5",  // true : Обработается C2 -75$ +5C
                            "C4\tb\tC\t15\t5",  // false: Новая заявка на покупку акций,
                                                // предложение к этому времени уже должно быть закрыто
                            "C4\ts\tC\t15\t5",  // false: Продажа самому себе
                            "C1\ts\tC\t15\t4",  // false: Предложение на продажу тех же акций, но в другом количестве
                            "C2\ts\tC\t14\t5",  // false: Предложение на продажу тех же акций, но по другой цене
                            "C1\ts\tD\t15\t5"); // false: Предложение на продажу другого вида акций
        orders = OrderService.getOrderMap(stringStream);
    }

    private void setCustomerAccountMap() {
        this.accounts = new HashMap<>();
        this.accounts.put( new Customer("C1"), Account.getAccountFromString("C1\t1000\t130\t240\t760\t320"));
        this.accounts.put( new Customer("C2"), Account.getAccountFromString("C2\t4350\t370\t120\t950\t560"));
        this.accounts.put( new Customer("C3"), Account.getAccountFromString("C3\t2760\t0\t0\t0\t0"));
        this.accounts.put( new Customer("C4"), Account.getAccountFromString("C4\t560\t450\t540\t480\t950"));
        this.accounts.put( new Customer("C5"), Account.getAccountFromString("C5\t1500\t0\t0\t400\t100"));
    }

    private void setExpectedCustomerAccountMap() {
        // Обработаются две заявки:
        // "C1\ts\tC\t15\t5"
        // "C2\tb\tC\t15\t5"
        this.accountsExpected = new HashMap<>();
        this.accountsExpected.put( new Customer("C1"), Account.getAccountFromString("C1\t1075\t130\t240\t755\t320"));
        this.accountsExpected.put( new Customer("C2"), Account.getAccountFromString("C2\t4275\t370\t120\t955\t560"));

        this.accountsExpected.put( new Customer("C3"), Account.getAccountFromString("C3\t2760\t0\t0\t0\t0"));
        this.accountsExpected.put( new Customer("C4"), Account.getAccountFromString("C4\t560\t450\t540\t480\t950"));
        this.accountsExpected.put( new Customer("C5"), Account.getAccountFromString("C5\t1500\t0\t0\t400\t100"));
    }

    @Test
    public void processOrders() throws Exception {
        assertEquals( accountsExpected, OrderService.processOrders(accounts, orders));
    }
}