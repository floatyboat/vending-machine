/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VMPersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author spenc
 */
public interface VMServiceLayer {
    
    void init() throws VMPersistenceException;
    
    void end() throws VMPersistenceException;
    
    void dispenseItem(String name) throws InsufficientFundsException, NoItemInventoryException, VMPersistenceException;
    
    BigDecimal getTotal();
    
    BigDecimal addToTotal(BigDecimal value);
    
    List<Item> listItems();
    
    int[] calculateChange();
}
