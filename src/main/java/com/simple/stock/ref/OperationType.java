package com.simple.stock.ref;

/**
 * Created by Alice on 18.03.2017.
 */
public enum OperationType {
    SELL("s"), BUY("b"), UNDEF("u");
    String code;

    OperationType(String code) { this.code = code; }

    @Override
    public String toString() {
        return "OperationType{" +
                "code='" + code + '\'' +
                '}';
    }

    public static OperationType fromString(String symbolOperation ){
        switch (symbolOperation) {
            case "s" : return OperationType.SELL;
            case "b" : return OperationType.BUY;
            default  : return OperationType.UNDEF;
        }
    }
}
