/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author spenc
 */
public class Item {
    private String name;
    private BigDecimal cost;
    private int inventory;

    public Item(String name, BigDecimal cost, int inventory) {
        this.name = name;
        this.cost = cost;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
    
    public void dispense() {
        this.inventory--;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.cost);
        hash = 89 * hash + Objects.hashCode(this.inventory);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        if (!Objects.equals(this.inventory, other.inventory)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Item{" + "Name=" + name + ", cost=" + cost.toString() + ", inventory=" + inventory + '}';
    }
}
