package com.simple.stock.ref;

/**
 * Created by Alice on 18.03.2017.
 */
public enum ShareType {
    A("A"), B("B"), C("C"), D("D");

    @Override
    public String toString() {
        return "ShareType{" +
                "code='" + code + '\'' +
                '}';
    }

    String code;

    ShareType(String code) { this.code = code; }
}
