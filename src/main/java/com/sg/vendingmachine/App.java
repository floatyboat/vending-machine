/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.*;
import com.sg.vendingmachine.dao.*;
import com.sg.vendingmachine.service.*;
import com.sg.vendingmachine.ui.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author spenc
 */

/*
    how do we handle change input?
    change class?
answers:
    create text file for inventory
*/
public class App {
    
    public static void main(String[] args) {
        /*
        UserIO io = new UserIOConsoleImpl();
        VMView view = new VMView(io);
        VMDao dao = new VMDaoFileImpl();
        VMAuditDao auditDao = new VMAuditDaoFileImpl();
        VMServiceLayer service = new VMServiceLayerImpl(dao, auditDao);
        VMController controller = new VMController(view, service);
        */
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        VMController controller = ctx.getBean("VMController", VMController.class);
        controller.run();
    }
}
