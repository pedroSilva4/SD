/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Pedro
 */
public class Users {
    private HashMap<String,User> users;
    private List<String> logged;
    
    private final Lock ulock = new ReentrantLock();
    private final Lock llock = new ReentrantLock();
   
    public Users()
    {
        this.users = new HashMap<String, User>();
        this.logged = new ArrayList<>();
    }
    
    public void register(String username, String password)
    {
        ulock.lock();
        try{
            users.put(username,new User(username,password));
        }
        finally{ulock.unlock();}
    }
    
    public boolean login(String username, String password)
    {
        User u = null;
        ulock.lock();
        try{
           u = this.users.get(username);
           if(u==null) return false;
        }
        finally{ulock.unlock();}
        
        
        return true;
    }
    
}
