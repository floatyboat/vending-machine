/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import static com.sg.vendingmachine.dto.Change.*;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author spenc
 */
public class VMView {
    private UserIO io;

    public VMView(UserIO io) {
        this.io = io;
    }
    
    public void displayTotal(BigDecimal total) {
        io.print("Inserted: $" + total.toString());
    }
    
    public int displayGetInsert(BigDecimal total) {
        displayTotal(total);
        return io.readInt("Select a coin to insert from the choices above", 1, 5);
    }
    
    public void displayInsertChange() {
        io.print("Coin Slot:");
        io.print("1. Quarter");
        io.print("2. Dime");
        io.print("3. Nickel");
        io.print("4. Penny");
        io.print("5. Done");

    }
    
    public void displayError(String msg) {
        io.print(msg);
        io.print("");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command");
    }
    
    public void displayItemList(List<Item> items) {
        io.print("Items:");
        int listNumber = 1;
        items.stream()
                .forEach((i) -> {   
                    io.print("*" + i.getName() + ":$" + i.getCost().toString());
                });
        io.print("");
    }

    public int printMenuGetSelection() {
        io.print("Main Menu:");
        io.print("1.Dispense Item");
        io.print("2.Insert More Coins");
        io.print("3.Exit and Return Change");

        return io.readInt("Please select from the above choices.", 1, 3);
    }
    
    public int getItemChoice(List<Item> items) {
        int current = 1;
        int itemMax = items.size();
        for(Item item : items) {
            io.print(current + ":" + item.getName());
            current++;
        }
        return io.readInt("Please select from the choice above.", 1, itemMax);
    }
    
    public void displayReturnedChange(int[] returnedCoins) {
        io.print("Change returned:");
        String[] coinNames = {QUARTER.name, DIME.name, NICKEL.name, PENNY.name};
        for(int i = 0; i < returnedCoins.length; i++) {
            if(returnedCoins[i] >= 1) {
                io.print(returnedCoins[i] + " " + coinNames[i] + " returned.");
            }
        }
    }

    public void printSuccess(String name) {
        io.print("Enjoy your " + name + "!");
    }
}
