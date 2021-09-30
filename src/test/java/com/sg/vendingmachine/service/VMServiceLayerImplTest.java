/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VMAuditDao;
import com.sg.vendingmachine.dao.VMDao;
import com.sg.vendingmachine.dao.VMPersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author spenc
 */
public class VMServiceLayerImplTest {
    
    private VMServiceLayer service;
    private VMDao dao;
    
    public VMServiceLayerImplTest() {
        /*
        dao = new VMDaoStubImpl();
        VMAuditDao auditDao = new VMAuditDaoStubImpl();
        
        service = new VMServiceLayerImpl(dao, auditDao);
        */
        ApplicationContext ctx = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
        service = 
            ctx.getBean("VMServiceLayer", VMServiceLayer.class);
        dao = ctx.getBean("VMDaoStubImpl", VMDaoStubImpl.class);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        dao.subtractTotal(service.getTotal());
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testDispenseAddSubtractTotal() {
        BigDecimal add = new BigDecimal(".75");
        
        service.addToTotal(add);
        assertTrue(add.equals(service.getTotal()));
        try {
            service.dispenseItem("Cheese Stick");
        } catch(InsufficientFundsException e) {
            fail("Should have enough funds");
        } catch(NoItemInventoryException e) {
            fail("Should have one in inventory");
        } catch(Exception e) {
            fail("This should not be possible to get here.");
        }
        Item item = dao.getItem("Cheese Stick");
        BigDecimal total = service.getTotal();
        assertTrue(BigDecimal.ZERO.equals(total));
        assertEquals(0, item.getInventory());
    }
    
    @Test
    public void testCalculateChange() {
        BigDecimal quarter = service.addToTotal(new BigDecimal(".50"));
        int[] change = service.calculateChange();
        assertEquals(2, change[0]);
    }
    
    @Test
    public void testListItems() {
        List<Item> list = service.listItems();
        String cheeseStick = list.get(0).getName();
        assertTrue(cheeseStick.equals("Cheese Stick"));
    }
    
    @Test
    public void testStartEndConnection() {
        try {
            service.init();
            
        } catch(VMPersistenceException e) {
            fail("Could not load");
        }
        
        try {
            service.end();
        } catch(VMPersistenceException e){
            fail("Could not save");
        }
    }
}
