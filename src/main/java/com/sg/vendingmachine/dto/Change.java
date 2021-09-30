/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author spenc
 */
public enum Change {
    COIN_NUM ("4", "4"), //number of coins supported
    QUARTER (".25", "Quarters"),
    DIME (".1", "Dimes"),
    NICKEL (".05", "Nickels"),
    PENNY (".01", "Pennies");
    
    public final BigDecimal value;
    public final String name;
    
    Change(String value, String name) {
        this.value = new BigDecimal(value);
        this.name = name;
    }
    
    
    BigDecimal value() {
        return value;
    }
}
