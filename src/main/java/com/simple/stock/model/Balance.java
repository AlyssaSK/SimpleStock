package com.simple.stock.model;

import com.simple.stock.ref.ShareType;

public class Balance {
    private int balanceDollars;
    private int balanceA;
    private int balanceB;
    private int balanceC;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (balanceDollars != balance.balanceDollars) return false;
        if (balanceA != balance.balanceA) return false;
        if (balanceB != balance.balanceB) return false;
        if (balanceC != balance.balanceC) return false;
        return balanceD == balance.balanceD;
    }

    @Override
    public int hashCode() {
        int result = balanceDollars;
        result = 31 * result + balanceA;
        result = 31 * result + balanceB;
        result = 31 * result + balanceC;
        result = 31 * result + balanceD;
        return result;
    }

    private int balanceD;

    @Override
    public String toString() {
        return "Balance{" +
                "balanceDollars=" + balanceDollars +
                ", balanceA=" + balanceA +
                ", balanceB=" + balanceB +
                ", balanceC=" + balanceC +
                ", balanceD=" + balanceD +
                '}';
    }


    Balance(int balanceDollars, int balanceA, int balanceB, int balanceC, int balanceD) {
        this.balanceDollars = balanceDollars;
        this.balanceA = balanceA;
        this.balanceB = balanceB;
        this.balanceC = balanceC;
        this.balanceD = balanceD;
    }

    void subtractionDollar(int sum ){
        this.balanceDollars -= sum;
    }

    void additionDollar(int sum ){
        this.balanceDollars += sum;
    }

    void subtractionShare(ShareType shareType, int count ){
        switch (shareType){
            case A: this.balanceA -= count; break;
            case B: this.balanceB -= count; break;
            case C: this.balanceC -= count; break;
            case D: this.balanceD -= count; break;
        }
    }

    void additionShare(ShareType shareType, int count ){
        switch (shareType){
            case A: this.balanceA += count; break;
            case B: this.balanceB += count; break;
            case C: this.balanceC += count; break;
            case D: this.balanceD += count; break;
        }
    }

    public int getBalanceDollars() { return balanceDollars; }

    public int getBalanceA() { return balanceA; }

    public int getBalanceB() { return balanceB; }

    public int getBalanceC() { return balanceC; }

    public int getBalanceD() { return balanceD; }
}