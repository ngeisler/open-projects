/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.geisler.softwareentwicklung.openprojects.black.gold.accounting.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *
 * @author Nico
 */
@SpringBootApplication
public class BlackGoldApplication {

    private boolean runningStatus;

    public BlackGoldApplication() {
        this.runningStatus = false;
    }

    public void start(String[] args) {
        SpringApplication.run(BlackGoldApplication.class, args);
        this.runningStatus = true;
    }

    boolean runs() {
        return this.runningStatus;
    }
}
