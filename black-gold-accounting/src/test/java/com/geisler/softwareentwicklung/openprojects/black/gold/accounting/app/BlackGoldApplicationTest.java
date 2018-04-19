/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.black.gold.accounting.app;

import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


/**
 *
 * @author Nico
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BlackGoldApplicationTest {
    
    @Test
    public void whenACustomerPaysHisDeptTheAccountBalanceShouldBeZero() {
        BigDecimal dept = new BigDecimal(25);
        Customer customer = Customer.defaultInstance(new CoffeeAccount(new Amount(dept.multiply(new BigDecimal(-1)), Currency.EURO)));
        Account account = customer.account();
        account.deposit(new Amount(dept, Currency.EURO));
        Amount balance = account.balance();
        assertThat(balance.value(), is(equalTo(new BigDecimal(0))));
    }

    @Test(expected = NegativeDepositNotAllowed.class)
    public void whenADepositAmountWillBeNegativeAnExceptionShouldBeThrown() {
        Account account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
        account.deposit(new Amount(new BigDecimal(-10), Currency.EURO));
    }
    
    @Test(expected = WrongCurrencyForAccountDeposit.class)
    public void whenADepositCurrencyIsNotEqualToAccountCurrencyAnExceptionShouldBeThrown() {
        Account account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
        account.deposit(new Amount(BigDecimal.ONE, Currency.DOLLAR));
    }
}
