/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.black.gold.accounting.app;

import java.math.BigDecimal;

/**
 *
 * @author Nico
 */
public class CoffeeCustomer implements Customer {

    private final Account account;
    
    public CoffeeCustomer() {
        this.account = new CoffeeAccount(new Amount(BigDecimal.ZERO, Currency.EURO));
    }

    CoffeeCustomer(Amount amount) {
        this.account = new CoffeeAccount(amount);
    }

    @Override
    public Account account() {
        return this.account;
    }
    
}
