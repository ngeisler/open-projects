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
        Customer customer = Customer.defaultInstance(new CoffeeAccount(dept.multiply(new BigDecimal(-1))));
        Account account = customer.account();
        account.payDept(dept);
        BigDecimal balance = account.balance();
        assertThat(balance, is(equalTo(new BigDecimal(0))));
    }
    
//    BlackGoldApplication sut;
//    
//    @Test
//    public void whenStartedTheApplicationShouldBeRunning() {
//        sut = new BlackGoldApplication();
//        assertThat(sut.runs(), is(true));
//    }
//    
//    @Test
//    public void whenNotStartedTheApplicationShouldNotBeRunning() {
//        BlackGoldApplication blackGoldApplication = new BlackGoldApplication();
//        assertThat(blackGoldApplication.runs(), is(false));
//    }
}
