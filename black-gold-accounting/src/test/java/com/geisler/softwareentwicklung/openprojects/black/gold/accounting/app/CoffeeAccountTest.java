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
public class CoffeeAccountTest {

    @Test(expected = NegativeDepositNotAllowed.class)
    public void whenADepositAmountWillBeNegativeAnExceptionShouldBeThrown() {
        Account account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
        account.deposit(new Amount(new BigDecimal(-10), Currency.EURO));
    }
    
    @Test(expected = WrongCurrencyForAccount.class)
    public void whenADepositCurrencyIsNotEqualToAccountCurrencyAnExceptionShouldBeThrown() {
        Account account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
        account.deposit(new Amount(BigDecimal.ONE, Currency.DOLLAR));
    }
    
    @Test(expected = NegativePayoutNotAllowed.class)
    public void whenAPayoutAmountWillBeNegativeAnExceptionShouldBeThrown() {
        Account account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
        account.payout(new Amount(new BigDecimal(-10), Currency.EURO));
    }
        
    @Test(expected = WrongCurrencyForAccount.class)
    public void whenAPayoutCurrencyIsNotEqualToAccountCurrencyAnExceptionShouldBeThrown() {
        Account account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
        account.payout(new Amount(BigDecimal.ONE, Currency.DOLLAR));
    }

}
