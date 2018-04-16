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
public interface Account {

    public void payDept(BigDecimal dept);

    public BigDecimal balance();

}
