package com.simple.stock.model;

public final class Client implements Comparable<Client>{
    private final String name;

    public Client() {
        this.name = "";
    }

    public Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Client{" + "name='" + name +  "\'}";
    }

    @Override
    public int compareTo(Client o) {
        return this.name.compareTo(o.name);
    }
}