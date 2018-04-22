/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.black.gold.accounting.app;

import java.math.BigDecimal;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.junit.Test;


/**
 *
 * @author Nico
 */
public class CoffeeCustomerTest {
       
    @Test
    public void afterCustomerInstantiationTheCustomerMustHaveAnAccount() {
        Customer customer = new CoffeeCustomer();
        Account account = customer.account();
        assertThat(account, instanceOf(Account.class));
    }
    
    @Test
    public void whenCustomerComesWithStartBalanceTheAccountBalanceShouldBeSet() {
        Customer customer = new CoffeeCustomer(new Amount(BigDecimal.ONE, Currency.EURO));
        Account account = customer.account();
        assertThat(account.balance().value(), is(greaterThan(BigDecimal.ZERO)));
    }
    
//    @Test
//    public void whenACustomerPaysHisDeptTheAccountBalanceShouldBeZero() {
//        BigDecimal dept = new BigDecimal(25);
//        Customer customer = new CoffeeCustomer();
//        Account account = customer.account();
//        account.deposit(new Amount(dept, Currency.EURO));
//        Amount balance = account.balance();
//        assertThat(balance.value(), is(equalTo(new BigDecimal(0))));
//    }
     
}
