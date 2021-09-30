/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VMServiceLayer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
public class VMDaoFileImplTest {
    
    VMDao testDao;
    
    public VMDaoFileImplTest() {
        //testDao = new VMDaoFileImpl(true);
        ApplicationContext ctx = 
            new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = 
            ctx.getBean("VMDao", VMDao.class);
    }
    
    @BeforeAll
    public static void setUpClass() throws Exception {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        testDao.subtractTotal(testDao.getTotal());
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testList() {
        Item item = testDao.getItem("Cheese Stick");
        
        List<Item> list = testDao.listItems();
        assertTrue(list.contains(item));
    }
    
    @Test
    public void testTotalOps() {
        testDao.subtractTotal(testDao.getTotal());
        BigDecimal halfDollar = new BigDecimal(".50");
        testDao.addToTotal(halfDollar);
        assertTrue(halfDollar.equals(testDao.getTotal()));
        testDao.subtractTotal(halfDollar);
        assertEquals(testDao.getTotal().intValue(), 0);
    }
    
    @Test
    public void testVend() {
        testDao.vend("Cheese Stick");
        Item item = testDao.getItem("Cheese Stick");
        assertEquals(item.getInventory(), 0);
    }
}
