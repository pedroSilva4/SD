/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.tools;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * @author Pedro
 */
public class WareHouse {
    
    private final Lock inv_lock  =new ReentrantLock();
    private HashMap<String,Tool> inventory;
    
    
    
    public WareHouse()
    {
        inventory = new HashMap<>();
    }
    
    public void lockInv(){inv_lock.lock();}
    public void unlockInv(){inv_lock.unlock();}
    
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
    
    public void tools_request(HashMap<String,Integer> tools) throws InterruptedException
    {
            lockInv();
            try{
                Tool t;
                boolean notready = true;
                while(notready){
                    notready = false;
                    for(String s: tools.keySet()){
                            t = getTool(s);
                        
                            if(tools.get(s) > t.qtd()){
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
    
    public void tools_return(HashMap<String,Integer> tools)
    {
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

