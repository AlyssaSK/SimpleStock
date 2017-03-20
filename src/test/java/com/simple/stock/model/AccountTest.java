package com.simple.stock.model;

import com.simple.stock.ref.OperationType;
import com.simple.stock.ref.ShareType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest{
    Customer customerC1 = new Customer("C1");

    @Test
    public void apply() throws Exception {
        Account accountC1 = new Account("C1", 1000,130,240,760,320 );
        Order orderC1 = Order.getOrderFromString("C1\ts\tC\t15\t4");
        assertTrue(accountC1.apply(orderC1));
    }

    @Test
    public void applyAccountSell() throws Exception {
        int price = 15;
        int count = 4;
        int sum = price * count;
        Account accountC1 =
                new Account("C1", 1000,130,240,760,320 );
        Account accountExpected =
                new Account("C1", 1000 + sum,130,240,760-count,320 );

        Order orderC1 = new Order(customerC1, OperationType.SELL, ShareType.C, price, count);
        accountC1.apply(orderC1);
        assertEquals(accountExpected, accountC1);
    }

    @Test
    public void applyAccountBuy() throws Exception {
        int price = 15, count = 4;
        int sum = price * count;
        Account accountC1 =
                new Account("C1", 1000,130,240,760,320 );
        Account accountExpected =
                new Account("C1", 1000 - sum,130,240,760 + count,320 );

        Order orderC1 = new Order(customerC1, OperationType.BUY, ShareType.C, price, count);
        accountC1.apply(orderC1);
        assertEquals(accountExpected, accountC1);
    }

    @Test
    public void testGetAccountFromString() throws Exception {
        String testString = "C1\t1000\t130\t240\t760\t320";
        Account account = new Account("C1", 1000,130,240,760,320);
        assertEquals(account, Account.getAccountFromString(testString));
    }
    @Test
    public void testGetAccountFromEmptyString() throws Exception {
        assertEquals(Account.emptyAccount(), Account.getAccountFromString(""));
    }

    @Test
    public void testGetAccountFromIllegalStringLength() throws Exception {
        assertEquals(Account.emptyAccount(), Account.getAccountFromString("C1\t1000") );
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetAccountFromIllegalStringFormat() throws Exception {
        Account.getAccountFromString("C1\tA\t130\t240\t760\t320")  ;
    }

    @Test
    public void testToString() {
        String expectedString = "C1\t1000\t130\t240\t760\t320";
        Account account = new Account("C1", 1000,130,240,760,320);
        assertEquals(expectedString, account.toString());
    }

    @Test
    public void testCompareTo(){
        Account account1 = new Account("C1", 1000,130,240,760,320);
        Account account2 = new Account("C2", 1000,130,240,760,320);
        assertTrue( account1.compareTo(account2) < 0);
        assertTrue( account2.compareTo(account1) > 0);
    }

    /**
     * В приложении такие случаи сортировки счетов одного клиента не рассматриваются, но возможность реализована
     */
    @Test
    public void testCompareToOneCustomerAccount(){
        Account account1 = new Account("C1", 1000,130,240,760,320);
        Account account12 = new Account("C1", 100,130,240,760,320);
        Account account2 = new Account("C1", 1000,130,240,760,320);
        assertEquals( 0 , account1.compareTo(account2) );
        assertTrue( account1.compareTo(account1) == 0);
        assertTrue( account1.compareTo(account12) > 0);
    }
}