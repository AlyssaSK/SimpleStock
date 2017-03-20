package com.simple.stock.model;

import com.simple.stock.ref.OperationType;
import com.simple.stock.ref.ShareType;

public final class Operation {
    private OperationType operationType;

    private ShareType shareType;
    private int price;
    private int count;

    Operation(String symbolOperation, String shareName, int price, int count) {
        this.operationType  = OperationType.fromString(symbolOperation);
        this.shareType = ShareType.valueOf(shareName);
        this.price = price;
        this.count = count;
    }

    Operation(OperationType operationType, ShareType shareType, int price, int count) {
        this.operationType = operationType;
        this.shareType = shareType;
        this.price = price;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationType=" + operationType +
                ", shareType=" + shareType +
                ", price=" + price +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        return price == operation.price
                && count == operation.count
                && operationType == operation.operationType
                && shareType == operation.shareType;
    }

    @Override
    public int hashCode() {
        int result = operationType.hashCode();
        result = 31 * result + shareType.hashCode();
        result = 31 * result + price;
        result = 31 * result + count;
        return result;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    ShareType getShareType() {
        return shareType;
    }

    int getPrice() {
        return price;
    }

    int getCount() {
        return count;
    }
}
