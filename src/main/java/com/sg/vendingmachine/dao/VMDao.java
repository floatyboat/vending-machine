/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author spenc
 */
public interface VMDao {
    
    List<Item> listItems();
    
    Item getItem(String name);
    
    BigDecimal getTotal();
    
    BigDecimal addToTotal(BigDecimal value);
    
    BigDecimal subtractTotal(BigDecimal value);
    
    void startConnection() throws VMPersistenceException;
    
    void endConnection() throws VMPersistenceException;
    
    void vend(String name);
}
