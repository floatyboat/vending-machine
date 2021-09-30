/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VMAuditDao;
import com.sg.vendingmachine.dao.VMPersistenceException;

/**
 *
 * @author spenc
 */
public class VMAuditDaoStubImpl implements VMAuditDao{

    @Override
    public void writeAuditEntry(String entry) throws VMPersistenceException {
        //do nothing
    }
    
}
