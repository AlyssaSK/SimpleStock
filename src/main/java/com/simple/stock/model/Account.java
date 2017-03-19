package com.simple.stock.model;

import com.simple.stock.ref.OperationType;

import java.util.Map;

public class Account implements Map.Entry<Client, Balance>, Comparable<Account>{
    private final Client client;
    private Balance balance;

    public Account() {
        this.client = new Client();
    }

    public Account(String clientName, int balanceDollars, int balanceA, int balanceB, int balanceC, int balanceD) {
        this.client = new Client(clientName);
        this.balance = new Balance(balanceDollars, balanceA, balanceB, balanceC, balanceD);
    }

    public boolean apply( Order order ){
        if( client.equals( order.getKey() )){
            Operation operation = order.getValue();
            OperationType operationType = operation.getOperationType();
            int sum = operation.getPrice() * operation.getCount();
            switch (operationType){
                case BUY:
                    balance.subtractionDollar(sum);
                    balance.additionShare(operation.getShareType(), operation.getCount());

                    return true;
                case SELL:
                    balance.additionDollar(sum);
                    balance.subtractionShare(operation.getShareType(), operation.getCount());

                    return true;
                default:
                    return false;
            }
        }
        return false;
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
    public static Account getAccountFromString(String string){
        String[] strings = string.split("\t");
        Account account = null;
        try{
            account = new Account(strings[0]
                    , Integer.valueOf(strings[1])
                    , Integer.valueOf(strings[2])
                    , Integer.valueOf(strings[3])
                    , Integer.valueOf(strings[4])
                    , Integer.valueOf(strings[5]));
        } catch (Exception e){
            e.printStackTrace();
        }

        return account;
    }
   /*
    Имя клиента

    Баланс клиента по долларам
    Баланс клиента по ценной бумаге "A" в штуках
    Баланс по ценной бумаге "B"
    Баланс по ценной бумаге "C"
    Баланс по ценной бумаге "D"  */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append( client.getName() + "\t" );
        string.append( balance.getBalanceDollars() + "\t" );
        string.append( balance.getBalanceA() + "\t" );
        string.append( balance.getBalanceB() + "\t" );
        string.append( balance.getBalanceC() + "\t" );
        string.append( balance.getBalanceD() );

        return string.toString();
    }

    @Override
    public Client getKey() {
        return this.client;
    }

    @Override
    public Balance getValue() {
        return this.balance;
    }

    @Override
    public Balance setValue(Balance value) {
        Balance oldValue = this.balance;
        this.balance = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return client.equals(account.client) && balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return ( 31 * client.hashCode() + balance.hashCode() );
    }

    @Override
    public int compareTo(Account o) {
        if(this.equals(o)) return 0;
        return this.client.compareTo(o.client);
    }
}
