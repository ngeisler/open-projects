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
interface Customer {
    
    public static DefaultCustomer defaultInstance (Account account) {
        return new DefaultCustomer(account);
    }

    public Account account();
    
    public class DefaultCustomer implements Customer {
        
        private final Account account;
        
        public DefaultCustomer(Account account) {
            this.account = account;
        }

        @Override
        public Account account() {
            return this.account;
        }
        
    }
}
