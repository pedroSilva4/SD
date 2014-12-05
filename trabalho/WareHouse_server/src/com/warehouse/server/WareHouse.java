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
    
    public void add_tool(String name,int qtt,boolean ret)
    {
        lockInv();
        try{
            Tool tool = new Tool(name, qtt, inv_lock.newCondition(),ret);
            inventory.put(name, tool);
            
            tool.signalAll();//ver qual a melhor solução para isto
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
    
    private Tool getTool(String name)
    {
        Tool t = inventory.get(name);
        
        if(t!=null) return t;
        
        t = new Tool(name, this.inv_lock.newCondition(),true);
        inventory.put(name, t);
        
        return t;
    }
    
    public void task_request(String task_id) throws InterruptedException
    {
        HashMap<String,Integer> tools  = null;
        lockTsk();
        try{
            tools = tasks.get(task_id).getTools();//nome das ferramentas e quantidades que precisa
        }
        finally{unlockTsk();}
        
        if(tools!=null){
            lockInv();
            try{
                Tool t;
                boolean notready = true;
                while(notready){
                    notready = false;
                    for(String s: tools.keySet()){
                            t = getTool(s);
                        
                            if(tools.get(s) >= t.qtd()){
                                         notready=true;
                                         t.await();
                                         break;
                            }
                    }
                }
                for(String s: tools.keySet()){
                    t = getTool(s);
                    t.dec(tools.get(s));
                }
            }
            finally{unlockInv();}
        }
    }
    
    public void task_return(String task_id)
    {
        HashMap<String,Integer> tools  = null;
        lockTsk();
        try{
            tools = tasks.get(task_id).getTools();
        }
        finally{unlockTsk();}
        
        if(tools!=null){
            lockInv();
            try{
                Tool t;
                for(String s: tools.keySet()){
                       t = getTool(s);
                       if(t.is_returnable()){
                                t.inc(tools.get(s));
                                t.signalAll();
                       }
                }
            }
            finally{unlockInv();}
        }
    }
}

