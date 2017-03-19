package com.simple.stock.model;

import com.simple.stock.ref.OperationType;
import com.simple.stock.ref.ShareType;

import java.util.Map;

public final class Order implements Map.Entry<Client, Operation>{
    private final Client client;
    private Operation operation;
    private boolean approved;

    public Order(Client client, OperationType operationType, ShareType shareType, int price, int count) {
        this.client = client;
        this.operation = new Operation(operationType, shareType, price, count);
    }

    public void approve() {
        this.approved = true;
    }

    public boolean isApproved(){
        return approved;
    }

    public Order(String clientName, String symbolOperation, String shareName, int price, int count) {
        this.client = new Client(clientName);
        this.operation = new Operation(symbolOperation, shareName, price, count);
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client +
                ", operation=" + operation +
                ", approved=" + approved +
                '}';
    }

    @Override
    public Client getKey() {
        return this.client;
    }

    @Override
    public Operation getValue() {
        return this.operation;
    }

    @Override
    public Operation setValue(Operation value) {
        Operation oldValue = this.operation;
        this.operation = value;
        return oldValue;
    }

    public static Order getOrderFromString(String string) {
        String[] strings = string.split("\t");
        Order order = null;
        try{
            order = new Order(strings[0]
                    , strings[1]
                    , strings[2]
                    , Integer.valueOf(strings[3])
                    , Integer.valueOf(strings[4]));
        } catch (Exception e){
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (approved != order.approved) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        return operation != null ? operation.equals(order.operation) : order.operation == null;
    }

    @Override
    public int hashCode() {
        int result = client != null ? client.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (approved ? 1 : 0);
        return result;
    }

    public boolean isCorresponds(Order order ) {
        boolean isAnotherClient = !this.client.equals(order.client);
        boolean isOneShare      = this.operation.getShareType().equals( order.operation.getShareType() );
        boolean isOnePrice      = this.operation.getPrice() == order.operation.getPrice();
        boolean isOneCount      = this.operation.getCount() == order.operation.getCount();

        boolean isDifferentOperation = !this.operation.getOperationType().equals( order.operation.getOperationType());

        return isAnotherClient && isOneShare && isOnePrice && isOneCount && isDifferentOperation;
    }
}
