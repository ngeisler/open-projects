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
public class CoffeeAccount implements Account {

    private Amount balance;
    
    public CoffeeAccount(Amount balance) {
        this.balance = balance;
    }

    @Override
    public void deposit(Amount amountToDeposit) {
        if(amountToDeposit.value().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeDepositNotAllowed();
        }
        if(!amountToDeposit.currency().equals(this.balance.currency())) {
            throw new WrongCurrencyForAccountDeposit();
        }
        this.balance = new Amount(this.balance.value().add(amountToDeposit.value()), this.balance.currency());
    }

    @Override
    public Amount balance() {
        return this.balance;
    }
    
}
