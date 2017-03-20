package com.simple.stock.model;

import com.simple.stock.ref.OperationType;
import com.simple.stock.ref.ShareType;

public class Account implements Comparable<Account>{
    private final Customer customer;
    private int balanceDollars;
    private int balanceA;
    private int balanceB;
    private int balanceC;
    private int balanceD;

    Account(String customerName, int balanceDollars, int balanceA, int balanceB, int balanceC, int balanceD) {
        this.customer = new Customer(customerName);
        this.balanceDollars = balanceDollars;
        this.balanceA = balanceA;
        this.balanceB = balanceB;
        this.balanceC = balanceC;
        this.balanceD = balanceD;
    }

    private Account() {
        this.customer = new Customer();
        this.balanceDollars = 0;
        this.balanceA = 0;
        this.balanceB = 0;
        this.balanceC = 0;
        this.balanceD = 0;
    }

    public boolean apply( Order order ){
        if( customer.equals( order.getKey() )){
            Operation operation = order.getValue();
            OperationType operationType = operation.getOperationType();
            int sum = operation.getPrice() * operation.getCount();
            switch (operationType){
                case BUY:
                    this.balanceDollars -= sum;
                    additionShare(operation.getShareType(), operation.getCount());

                    return true;
                case SELL:
                    this.balanceDollars += sum;
                    subtractionShare(operation.getShareType(), operation.getCount());

                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    private void subtractionShare(ShareType shareType, int count ){
        switch (shareType){
            case A: this.balanceA -= count; break;
            case B: this.balanceB -= count; break;
            case C: this.balanceC -= count; break;
            case D: this.balanceD -= count; break;
        }
    }

    private void additionShare(ShareType shareType, int count){
        switch (shareType){
            case A: this.balanceA += count; break;
            case B: this.balanceB += count; break;
            case C: this.balanceC += count; break;
            case D: this.balanceD += count; break;
        }
    }

    /**
     * @param string - строка с разделителем в формате :
     *               [0] Имя клиента
     *               [1] Баланс клиента по долларам
     *               [2] Баланс клиента по ценной бумаге "A" в штуках
     *               [3] Баланс по ценной бумаге "B"
     *               [4] Баланс по ценной бумаге "C"
     *               [5] Баланс по ценной бумаге "D"
     *               с разделителем '\t'
     * @return Состояние счета
     */
    public static Account getAccountFromString(String string) throws IllegalArgumentException{
        if( !string.isEmpty() ) {
            try {
                String[] strings =  string.contains("\t") ? string.split("\t") : string.split(" ");
                if(strings.length == 6){
                    return new Account(strings[0]
                            , Integer.valueOf(strings[1])
                            , Integer.valueOf(strings[2])
                            , Integer.valueOf(strings[3])
                            , Integer.valueOf(strings[4])
                            , Integer.valueOf(strings[5]));
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Недопустимый формат строки: " + string);
            }
        }
        return Account.emptyAccount();
    }

    static Account emptyAccount() {
        return new Account();
    }

    @Override
    public String toString() {
        return customer.getName() + "\t" +
                    this.balanceDollars + "\t" +
                    this.balanceA + "\t" +
                    this.balanceB + "\t" +
                    this.balanceC + "\t" +
                    this.balanceD;
    }


    public Customer getCustomer() {
        return this.customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return (balanceDollars == account.balanceDollars)
                && (balanceA == account.balanceA)
                && (balanceB == account.balanceB)
                && (balanceC == account.balanceC)
                && (balanceD == account.balanceD)
                && customer.equals(account.customer);
    }

    @Override
    public int hashCode() {
        int result = customer.hashCode();
        result = 31 * result + balanceDollars;
        result = 31 * result + balanceA;
        result = 31 * result + balanceB;
        result = 31 * result + balanceC;
        result = 31 * result + balanceD;
        return result;
    }

    @Override
    public int compareTo(Account o) {
        if( this.equals(o) ) return 0;
        if( this.customer.equals(o.customer) ) {
            // Этот блок, предположительно не будет использоваться, при построение словаря customer<=>account
            // По логике для одного клиента только один счет, но на случай наличия нескольких счетов в списке,
            // его можно будет отсортировать по балансам в порядке приоритета от долларов до акций вида D.
            if ( this.balanceDollars != o.balanceDollars )
                return Integer.valueOf( this.balanceDollars ).compareTo(o.balanceDollars);
            if ( this.balanceA != o.balanceA )
                return Integer.valueOf( this.balanceA ).compareTo(o.balanceA);
            if ( this.balanceB != o.balanceB )
                return Integer.valueOf( this.balanceB ).compareTo(o.balanceB);
            if ( this.balanceC != o.balanceC )
                return Integer.valueOf( this.balanceC ).compareTo(o.balanceC);
            if ( this.balanceD != o.balanceD )
                return Integer.valueOf( this.balanceD ).compareTo(o.balanceD);
        }
        return this.customer.compareTo(o.customer);
    }
}