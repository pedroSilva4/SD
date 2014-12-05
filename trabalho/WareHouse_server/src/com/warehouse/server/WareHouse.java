/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * @author Pedro
 */
public class WareHouse {
    
    private final Lock inv_lock  =new ReentrantLock();
    private final Lock tsk_lock  =new ReentrantLock();
    private HashMap<String,Tool> inventory;
    private HashMap<String,Task> tasks;
    
    
    public WareHouse()
    {
        inventory = new HashMap<>();
        tasks  = new HashMap<>();
    }
    
    public void lockInv(){inv_lock.lock();}
    public void unlockInv(){inv_lock.unlock();}
    public void lockTsk(){tsk_lock.lock();}
    public void unlockTsk(){tsk_lock.unlock();}
    
    public void add_tool(String name,int qtt)
    {
        lockInv();
        try{
            inventory.put(name, new Tool(name, qtt, inv_lock.newCondition()));
        }
        finally{unlockInv();}
    }
    
    public void add_tool(String name)
    {
        lockInv();
        try{ 
            inventory.put(name, new Tool(name, 0, inv_lock.newCondition()));
        }
        finally{unlockInv();}
    }
    
    public void define_task(String name,HashMap<String,Integer> tools)
    {
       lockTsk();
       try{
           tasks.put(name,new Task(name, tools));
       }
       finally{unlockTsk();}
    }
    
    public HashMap<String,Task> get_tasks()
    {
        HashMap<String,Task> map;
        lockTsk();
        try {
            map = tasks;
            return map;
        } finally { unlockTsk(); }
    }
    
    public Task get_task(String name)
    {
        Task t;
        
        lockTsk();
        try {
            t = tasks.get(name);
            return t;
        } finally { unlockTsk(); }
    }
    
    public HashMap<String,Tool> get_inventory()
    {
        HashMap<String,Tool> map;
        
        lockInv();
        try {
            map = inventory;
            return map;
        } finally { unlockInv(); }
    }
}

