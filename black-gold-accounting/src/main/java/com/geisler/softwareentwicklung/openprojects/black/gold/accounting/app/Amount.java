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
public class Amount {
    private final BigDecimal value;
    private final Currency currency;

    public Amount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }
    
    BigDecimal value() {
        return this.value;
    }
    
    Currency currency() {
        return Currency.valueOf(this.currency.name());
    }
}
