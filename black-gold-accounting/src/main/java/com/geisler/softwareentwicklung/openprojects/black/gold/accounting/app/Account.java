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
public interface Account {

    public void deposit(Amount amountToDeposit);

    public Amount balance();

    public void payout(Amount amountToPayout);

}
