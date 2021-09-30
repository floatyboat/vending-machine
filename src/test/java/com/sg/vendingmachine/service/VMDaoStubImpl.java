/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VMDao;
import com.sg.vendingmachine.dao.VMPersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spenc
 */
public class VMDaoStubImpl implements VMDao {
    
    public Item onlyItem;
    private BigDecimal total = BigDecimal.ZERO;
    
    public VMDaoStubImpl() {
        onlyItem = new Item("Cheese Stick", new BigDecimal(0.75), 1);
    }
    
    public VMDaoStubImpl(Item testItem) {
        this.onlyItem = testItem;
    }

    @Override
    public List<Item> listItems() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item getItem(String name) {
        if(name.equals(onlyItem.getName())) {
            return onlyItem;
        }
        return null;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public BigDecimal addToTotal(BigDecimal value) {
        total = total.add(value);
        return total;
    }

    @Override
    public BigDecimal subtractTotal(BigDecimal value) {
        total = BigDecimal.ZERO;
        return total;
    }

    @Override
    public void startConnection() throws VMPersistenceException {
        return;
    }

    @Override
    public void endConnection() throws VMPersistenceException {
        return;
    }

    @Override
    public void vend(String name) {
        onlyItem.setInventory(0);
        total = BigDecimal.ZERO;
    }
    
}
