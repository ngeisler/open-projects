/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.black.gold.accounting.app;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 *
 * @author Nico
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlackGoldApplicationTest {
    
    @Test
    public void whenStartedTheApplicationShouldBeRunning() {
        BlackGoldApplication blackGoldApplication = new BlackGoldApplication();
        blackGoldApplication.start(new String[0]);
        assertThat(blackGoldApplication.runs(), is(true));
    }
    
    @Test
    public void whenNotStartedTheApplicationShouldNotBeRunning() {
        BlackGoldApplication blackGoldApplication = new BlackGoldApplication();
        assertThat(blackGoldApplication.runs(), is(false));
    }
}
