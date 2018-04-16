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

    private BigDecimal balance;
    
    public CoffeeAccount(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public void payDept(BigDecimal dept) {
        this.balance = this.balance.add(dept);
    }

    @Override
    public BigDecimal balance() {
        return this.balance;
    }
    
}
