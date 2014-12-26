/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.users;

import com.warehouse.util.AlreadyRegisteredException;
import com.warehouse.util.AlreadyLoggedException;
import com.warehouse.util.WrongPasswordException;
import com.warehouse.util.UserNotFoundException;
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
    private HashMap<String , User> users;
    private List<String> logged;
    
    private final Lock ulock = new ReentrantLock();
    private final Lock llock = new ReentrantLock();
   
    public Users()
    {
        this.users = new HashMap<String, User>();
        this.logged = new ArrayList<>();
    }
    
    public void register(String username, String password) throws AlreadyRegisteredException
    {
        ulock.lock();
        try{
            
            if(this.users.containsKey(username)) throw new AlreadyRegisteredException();
            
            users.put(username,new User(username,password));
        }
        finally{ulock.unlock();}
        
    }
    
    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException, AlreadyLoggedException
    {
        User u = null;
        ulock.lock();
        try{
           u = this.users.get(username);
        }
        finally{ulock.unlock();}
        
        if(u==null) throw new UserNotFoundException();
        
        if(!password.equals(u.getPassword())) throw new WrongPasswordException();
        
        llock.lock();
        try{
            if(logged.contains(username)) throw new AlreadyLoggedException();
            //aviso
            this.logged.add(username);
           
        }
        finally{llock.unlock();}
    }
    
    public void logout(String username)
    {
        llock.lock();
        try{
            this.logged.remove(username);
        }
        finally{llock.unlock();}
    }
    
    
}
