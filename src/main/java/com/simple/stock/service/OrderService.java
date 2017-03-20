package com.simple.stock.service;

import com.simple.stock.model.Account;
import com.simple.stock.model.Customer;
import com.simple.stock.model.Order;
import com.simple.stock.ref.OperationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class OrderService{
    /**
     * @param ordersStream - Поток строк, представляющих заявки
     * @return Возвращает заявки сгруппированные по типу операции
     */
    public static Map<OperationType, List<Order>> getOrderMap(Stream<String> ordersStream) {
        Map<OperationType, List<Order>> ordersMap = new HashMap<>();
        ordersMap.put(OperationType.BUY,  new ArrayList<>());
        ordersMap.put(OperationType.SELL, new ArrayList<>());

        ordersStream.forEach( (orderLine) ->  {
            Order order = Order.getOrderFromString(orderLine);
            if( order != null ) {
                ordersMap.get(order.getValue().getOperationType()).add(order);
            }
        });

        return ordersMap;
    }

    /**
     * Возвращает обработанное отображение счетов. Каждому Customer(ключ) соответствует Account(значение)
     * @param accounts  исходный список счетов
     * @param orders    заявки
     * @return обработанное отображение счетов клиентов
     */
    public static Map<Customer, Account> processOrders(Map<Customer, Account> accounts, Map<OperationType, List<Order>> orders){
        List<Order> ordersBuy  = orders.get(OperationType.BUY);
        List<Order> ordersSell = orders.get(OperationType.SELL);

        ordersBuy.forEach(orderBuy -> ordersSell.forEach(
                orderSell -> {
                    if (orderBuy.isCorresponds(orderSell)
                            && !(orderBuy.isApproved() || orderSell.isApproved())) {
                        // Исполняем заявки
                        final boolean applyBuy  = accounts.get(orderBuy.getKey()).apply(orderBuy);
                        final boolean applySell = accounts.get(orderSell.getKey()).apply(orderSell);
                        // Помечаем заявки как исполненные
                        if( applyBuy && applySell ) {
                            orderBuy.approve();
                            orderSell.approve();
                        }
                    }
                }));
        return accounts;
    }
}