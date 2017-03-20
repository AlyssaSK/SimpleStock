package com.simple.stock.model;

import com.simple.stock.ref.OperationType;
import com.simple.stock.ref.ShareType;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void getOrderFromString() throws Exception {
        String testString = "C1\ts\tA\t15\t4";
        Order order = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        assertEquals(order, Order.getOrderFromString(testString));
    }

    @Test
    public void isCorresponds() throws Exception {
        Order orderC1 = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        Order orderC2 = new Order( new Customer("C2"), OperationType.BUY,  ShareType.A,15,4);

        assertTrue(orderC1.isCorresponds(orderC2));
    }

    @Test
    public void isNotCorrespondsOneOperation() throws Exception {
        Order orderC1 = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        Order orderC2 = new Order( new Customer("C2"), OperationType.SELL,  ShareType.A,15,4);

        assertFalse(orderC1.isCorresponds(orderC2));
    }

    @Test
    public void isNotCorrespondsOneClient() throws Exception {
        Order orderC1 = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        Order orderC2 = new Order( new Customer("C1"), OperationType.BUY,  ShareType.A,15,4);

        assertFalse(orderC1.isCorresponds(orderC2));
    }

    @Test
    public void isNotCorrespondsDifferentShareType() throws Exception {
        Order orderC1 = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        Order orderC2 = new Order( new Customer("C2"), OperationType.BUY,  ShareType.C,15,4);

        assertFalse(orderC1.isCorresponds(orderC2));
    }

    @Test
    public void isNotCorrespondsDifferentPrice() throws Exception {
        Order orderC1 = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        Order orderC2 = new Order( new Customer("C2"), OperationType.BUY,  ShareType.A,14,4);

        assertFalse(orderC1.isCorresponds(orderC2));
    }

    @Test
    public void isNotCorrespondsDifferentCount() throws Exception {
        Order orderC1 = new Order( new Customer("C1"), OperationType.SELL, ShareType.A,15,4);
        Order orderC2 = new Order( new Customer("C2"), OperationType.BUY,  ShareType.A,15,0);

        assertFalse(orderC1.isCorresponds(orderC2));
    }
}