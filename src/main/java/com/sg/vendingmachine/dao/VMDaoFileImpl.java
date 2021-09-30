/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author spenc
 */
public class VMDaoFileImpl implements VMDao{
    
    private final String INV_FILE;
    private Map<String, Item> inventory = new HashMap<>();
    private BigDecimal total = new BigDecimal("0");
    private static final String DELIMITER = "::";

    public VMDaoFileImpl() {
        this.INV_FILE = "inventory.txt";
    }

    public VMDaoFileImpl(String test) {
        this.INV_FILE = "testinventory.txt";
        Item item = new Item("Cheese Stick", new BigDecimal(".75"), 1);
        inventory.put(item.getName(), item);
        total = new BigDecimal("0");
    }

    @Override
    public List<Item> listItems() {
        return new ArrayList<Item>(inventory.values());
    }

    @Override
    public void startConnection() throws VMPersistenceException {
        loadInventory();
    }

    @Override
    public void endConnection() throws VMPersistenceException {
        writeInventory();
    }

    @Override
    public Item getItem(String name) {
        return inventory.get(name);
    }
    
    @Override
    public void vend(String name) {
        Item item = getItem(name);
        item.setInventory(item.getInventory()-1);
        subtractTotal(item.getCost());
    }

    @Override
    public BigDecimal addToTotal(BigDecimal value) {
        total = total.add(value);
        return total;
    }

    @Override
    public BigDecimal subtractTotal(BigDecimal value) {
        total = total.subtract(value);
        return total;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }
    
    private void loadInventory() throws VMPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INV_FILE)));
        } catch (FileNotFoundException e) {
            throw new VMPersistenceException(
                    "Could not load inventory data into memory.", e);
        }

        String currentLine;
        Item currentItem;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);

            inventory.put(currentItem.getName(), currentItem);
        }
        
        scanner.close();
    }
    
    private void writeInventory() throws VMPersistenceException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(INV_FILE));
        } catch(IOException e) {
            throw new VMPersistenceException(
                "Could not save inventory data.", e);
        }
        
        String itemAsText;
        List<Item> itemList = this.listItems();
        
        for(Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
    
    private Item unmarshallItem(String itemsAsText) {
        String[] itemTokens = itemsAsText.split(DELIMITER);
        
        String name = itemTokens[0];
        BigDecimal cost = new BigDecimal(itemTokens[1]);
        int count = Integer.valueOf(itemTokens[2]);
        
        Item itemFromFile = new Item(name, cost, count);
        
        return itemFromFile;
    }
    
    private String marshallItem(Item anItem) {
        String itemAsText = anItem.getName() + DELIMITER;
        
        itemAsText += anItem.getCost() + DELIMITER;
        
        itemAsText += anItem.getInventory();
        
        return itemAsText;
    }
}
