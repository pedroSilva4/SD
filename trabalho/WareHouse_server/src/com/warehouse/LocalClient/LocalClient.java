/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.LocalClient;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import com.warehouse.util.AlreadyLoggedException;
import com.warehouse.util.AlreadyRegisteredException;
import com.warehouse.util.Log;
import com.warehouse.util.UserNotFoundException;
import com.warehouse.util.WrongPasswordException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.util.Password;

/**
 *
 * @author Pedro
 */
public class LocalClient extends Thread{
    
    private final Manager man;
    private final Users users;
    private final Log logger;
    private String username;
    
    public LocalClient(Users us,Manager man,Log logger){
        this.man = man;
        this.users =us;
        this.logger = logger;
    }
    
    private boolean inputHandler(boolean logged,String s,Console con){
        if(s.matches("\\s\\t")) return logged;
        
        String[] args = s.split(" "); 
        
        if(args.length < 1) return logged;
        switch(args[0]){
            case "login":{
              if(logged==false) {
              
                        try {
                            String user = args[1];
                            System.out.print("Password :");
                            char[] pass = con.readPassword();
                            String  password = "";
                            for(char c:pass){
                                password+=c;
                            }
                            this.username = user;
                            users.login(user, password);
                            logged = true;
                          
                        } catch (UserNotFoundException ex) {
                            System.out.println("ERROR : User not found!");
                        } catch (WrongPasswordException ex) {
                            System.out.println("ERROR : Wrong Password!");
                        } catch (AlreadyLoggedException ex) {
                            System.out.println("ERROR : User Already Logged in!");
                        } 
               }
              else{ 
                  System.out.println("Warning : You can't do that!");
              }
              break;
            }
            case "register" : {
                if(logged==false) {
              
                        try {
                            String user = args[1];
                            System.out.print("Password :");
                            char[] pass = con.readPassword();
                            String  password = "";
                            for(char c:pass){
                                password+=c;
                            }
                            users.register(user, password);
                            logged = true;
                          
                        } catch (AlreadyRegisteredException ex) { 
                            System.out.println("Error : Already Registered!");
                    } 
               }
              else{ 
                  System.out.println("Warning : You can't do that!");
              }
              break;
            }
            default :{
                System.out.println("Warning : You can't do that!");
            }
            case "logout":{
                if(logged == true){
                     users.logout(this.username);
                     logged = false;
                     
                } else{ 
                  System.out.println("Warning : You can't do that!");
                  
              }
                break;
            }
            case "logged":{
                ArrayList<String> us = users.getLogged();
                System.out.println("Logged:");
                for(String st : us){
                       System.out.println("\t"+st);
                }
                break;
            }
        }
        return logged;
    }
    public void run(){
        try {
            sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(LocalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean run = true;
       Console con = System.console();
         String input;
        while(run){
            boolean logged = false;
            
            System.out.print(">> ");
            input = con.readLine();
            logged = inputHandler(logged,input,con);
            
            while(logged){
                System.out.print(">> ");
                input = con.readLine();
                logged = inputHandler(logged,input,con);
            }
        }
                
    }
    
}
