package com.simple.stock.service;

import com.simple.stock.model.Order;
import com.simple.stock.ref.OperationType;
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

    @Test
    public void processOrders() throws Exception {

    }

}