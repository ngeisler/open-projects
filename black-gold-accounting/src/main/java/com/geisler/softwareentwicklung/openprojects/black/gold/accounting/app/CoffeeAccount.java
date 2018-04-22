/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.black.gold.accounting.app;

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
        if(amountToDeposit.isNegative()) {
            throw new NegativeDepositNotAllowed();
        }
        checkCurrency(amountToDeposit);
        this.balance = new Amount(this.balance.value().add(amountToDeposit.value()), this.balance.currency());
    }

    @Override
    public Amount balance() {
        return this.balance;
    }

    @Override
    public void payout(Amount amountToPayout) {
        if(amountToPayout.isNegative()) {
            throw new NegativePayoutNotAllowed();
        }
        checkCurrency(amountToPayout);
        this.balance = new Amount(this.balance.value().subtract(amountToPayout.value()), this.balance.currency());
    }
    
    private void checkCurrency(Amount amountToCheck) {
        if(!amountToCheck.currency().equals(this.balance.currency())) {
            throw new WrongCurrencyForAccount();
        }
    }
}
