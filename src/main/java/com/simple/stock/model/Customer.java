package com.simple.stock.model;

public final class Customer implements Comparable<Customer>{
    private final String name;

    public Customer() {
        this.name = "";
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" + "name='" + name +  "\'}";
    }

    @Override
    public int compareTo(Customer o) {
        return this.name.compareTo(o.name);
    }
}