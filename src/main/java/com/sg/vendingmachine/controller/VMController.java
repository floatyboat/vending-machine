/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VMPersistenceException;
import static com.sg.vendingmachine.dto.Change.*;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.*;
import com.sg.vendingmachine.ui.VMView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author spenc
 */
public class VMController {
    
    private final VMView view;
    private final VMServiceLayer service;

    public VMController(VMView view, VMServiceLayer service) {
        this.view = view;
        this.service = service;
    }
    
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            service.init();
            displayItemsGetCoins();
            while(keepGoing) {
                try {
                    menuSelection = getMenuSelection();
                    switch(menuSelection) {
                        case 1:
                            getItemChoiceDispense();
                            returnChange();
                            break;
                        case 2:
                            getCoins();
                            break;
                        case 3:
                            returnChange();
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();
                    }
                } catch(NoItemInventoryException | InsufficientFundsException e) {
                    view.displayError(e.getMessage());
                }
            }
            service.end();
        } catch(VMPersistenceException e) {
            view.displayError(e.getMessage());
        }
        
        
    }
    
    public void getCoins() {
        boolean inserting = true;
        int inserted = 0;
        view.displayInsertChange();
        while(inserting) {
            inserted = view.displayGetInsert(service.getTotal());
            switch(inserted) {
                case 1:
                    //quarter
                    service.addToTotal(QUARTER.value);
                    break;
                case 2:
                    //dime
                    service.addToTotal(DIME.value);
                    break;
                case 3:
                    //nickel
                    service.addToTotal(NICKEL.value);
                    break;
                case 4:
                    //penny
                    service.addToTotal(PENNY.value);
                    break;
                case 5:
                    //done
                    inserting = false;
                    break;
                default:
                    unknownCommand();
            }
        }
    }
    
    public int getMenuSelection() {
        return view.printMenuGetSelection();
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void displayItems() {
        List<Item> items = service.listItems();
        view.displayItemList(items);
    }

    private void getItemChoiceDispense() throws InsufficientFundsException, 
                                                NoItemInventoryException,
                                                VMPersistenceException {
        List<Item> items = service.listItems();
        int selection = view.getItemChoice(items) - 1;
        String picked = items.get(selection).getName();
        service.dispenseItem(picked);
        printConfirmation(picked);
    }
    
    private void printConfirmation(String name) {
        view.printSuccess(name);
    }
    
    public void returnChange() {
        if(service.getTotal().compareTo(new BigDecimal(0)) > 0) {
            int[] returnedCoins = service.calculateChange();
            view.displayReturnedChange(returnedCoins);
        } else {
            view.displayTotal(service.getTotal());
        }
    }

    private void displayItemsGetCoins() {
        displayItems();
        getCoins();
    }
}
