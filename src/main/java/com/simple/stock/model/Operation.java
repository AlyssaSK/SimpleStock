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

    public Operation(OperationType operationType, ShareType shareType, int price, int count) {
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

        if (price != operation.price) return false;
        if (count != operation.count) return false;
        if (operationType != operation.operationType) return false;
        return shareType == operation.shareType;
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

    public ShareType getShareType() {
        return shareType;
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }
}
