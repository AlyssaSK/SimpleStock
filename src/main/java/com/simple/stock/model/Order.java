package com.simple.stock.model;

import com.simple.stock.ref.OperationType;
import com.simple.stock.ref.ShareType;

import java.util.Map;

public final class Order implements Map.Entry<Customer, Operation>{
    private final Customer customer;
    private Operation operation;
    private boolean approved;

    Order(Customer customer, OperationType operationType, ShareType shareType, int price, int count) {
        this.customer = customer;
        this.operation = new Operation(operationType, shareType, price, count);
    }

    public void approve() throws UnsupportedOperationException{
        if( !this.approved )
            this.approved = true;
        else
            throw new UnsupportedOperationException("Невозможно одобрить уже одобренную заявку");
    }

    public boolean isApproved(){
        return approved;
    }

    private Order(String clientName, String symbolOperation, String shareName, int price, int count) {
        this.customer = new Customer(clientName);
        this.operation = new Operation(symbolOperation, shareName, price, count);
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", operation=" + operation +
                ", approved=" + approved +
                '}';
    }

    @Override
    public Customer getKey() {
        return this.customer;
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

        return approved == order.approved
                && (customer != null ? customer.equals(order.customer) : order.customer == null)
                && (operation != null ? operation.equals(order.operation) : order.operation == null);
    }

    @Override
    public int hashCode() {
        int result = customer != null ? customer.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (approved ? 1 : 0);
        return result;
    }

    public boolean isCorresponds(Order order ) {
        boolean isAnotherClient = !this.customer.equals(order.customer);
        boolean isOneShare      = this.operation.getShareType().equals( order.operation.getShareType() );
        boolean isOnePrice      = this.operation.getPrice() == order.operation.getPrice();
        boolean isOneCount      = this.operation.getCount() == order.operation.getCount();

        boolean isDifferentOperation = !this.operation.getOperationType().equals( order.operation.getOperationType());

        return isAnotherClient && isOneShare && isOnePrice && isOneCount && isDifferentOperation;
    }
}
