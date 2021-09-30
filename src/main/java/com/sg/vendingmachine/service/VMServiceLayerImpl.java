/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.*;
import com.sg.vendingmachine.dto.Change;
import static com.sg.vendingmachine.dto.Change.*;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author spenc
 */
public class VMServiceLayerImpl implements VMServiceLayer {

    private final VMDao dao;
    private final VMAuditDao auditDao;

    public VMServiceLayerImpl(VMDao dao, VMAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    public void init() throws VMPersistenceException {
        dao.startConnection();
    }
    
    @Override
    public void dispenseItem(String name) throws InsufficientFundsException, 
                                                 NoItemInventoryException, 
                                                 VMPersistenceException {
        Item item = dao.getItem(name);
        boolean validInventory = validateInventory(item);
        boolean validMoney = validateMoney(item);
        if(validInventory && validMoney) {
            dao.vend(name);
            auditDao.writeAuditEntry("1 " + name + " dispensed." 
                                   + dao.getItem(name).getInventory() 
                                   + " remaining in stock.");
        } else if(!validInventory){
            throw new NoItemInventoryException("**Out of Stock**");
        } else if(!validMoney) {
            throw new InsufficientFundsException("**Insufficient Funds**");
        }
    }
    
    @Override
    public BigDecimal addToTotal(BigDecimal value) { //may change parameter
        return dao.addToTotal(value);
    }
    
    @Override
    public BigDecimal getTotal() {
        return dao.getTotal();
    }
    
    private boolean validateInventory(Item item) {
        return item.getInventory() > 0;
    }
    
    private boolean validateMoney(Item item) {
        return (getTotal().compareTo(item.getCost()) == 0) ||
               (getTotal().compareTo(item.getCost()) == 1); 
    }

    @Override
    public List<Item> listItems() {
        return dao.listItems();
    }

    @Override
    public int[] calculateChange() { //gives biggest denomination of coin possible based on inserted amount
        int coinNum = COIN_NUM.value.intValue();
        int[] returnedCoins = new int[coinNum];
        Change[] coins = {QUARTER, DIME, NICKEL, PENNY};
        for(int i = 0; i < coinNum; i++) {
            BigDecimal total = getTotal();
            BigDecimal amt = total.divideAndRemainder(coins[i].value)[0];
            dao.subtractTotal(coins[i].value.multiply(amt));
            returnedCoins[i] = amt.intValue();
        }
        return returnedCoins;
    }

    @Override
    public void end() throws VMPersistenceException {
        dao.endConnection();
    }
}
