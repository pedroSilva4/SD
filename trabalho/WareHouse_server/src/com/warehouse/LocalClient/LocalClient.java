/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.LocalClient;

import com.warehouse.tasks.Manager;
import com.warehouse.tasks.Task;
import com.warehouse.tasks.TaskType;
import com.warehouse.users.User;
import com.warehouse.users.Users;
import com.warehouse.util.AlreadyLoggedException;
import com.warehouse.util.AlreadyRegisteredException;
import com.warehouse.util.Log;
import com.warehouse.util.Save2FileThread;
import com.warehouse.util.TaskNotFoundException;
import com.warehouse.util.UserNotFoundException;
import com.warehouse.util.WrongPasswordException;
import com.warehouse.util.WrongUserException;
import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.logging.Level;
import java.util.logging.Logger;

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
        if(s.matches("\\s+") || s.equals("") ) return logged;
        
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
                            String pwd = User.convertPassword(password);
                            users.login(user, pwd);
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
                  break;
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
                            String pwd = User.convertPassword(password);
                            users.register(user, pwd);
                            String reg = "register:"+user+":"+pwd;
                            new Save2FileThread(reg, logger.usrPw).start();
                          
                        } catch (AlreadyRegisteredException ex) { 
                            System.out.println("Error : Already Registered!");
                    } 
               }
              else{ 
                  System.out.println("Warning : You can't do that!");
              }
              break;
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
            case "list":{
                HashMap<String, TaskType> map = man.get_taskTypes();
                if(map == null)
                    System.out.println("No tasks available at the moment!");
                else {
                    System.out.println("\nTasks available:\n");
                    for(TaskType tt : map.values()) {
                       System.out.println(tt.toString());
                    }
                }
                break;
            }
            case "activity":{
                ArrayList<Task> tasks = new ArrayList<>(man.getActiveTasks());
                System.out.println("\nActivity :\n");
                for(Task t : tasks)
                    System.out.println(t.toString());
            }
            case "supply":{
                if(logged == true){
                    String tool = args[1].replace("_", " ");
                    man.add_tool(tool, Integer.parseInt(args[2]), true);
                    String at = "supply:"+tool+":"+args[2];
                    new Save2FileThread(s, logger.toolsPw).start();
                }else{
                     System.out.println("Warning : You can't do that!");
                }
                break;
            }
            case "wait_for":{
                if(logged==true){
                    int[] tasks = new int[args.length - 1];
                    for(int i = 1; i < args.length; i++)
                        tasks[i] = Integer.parseInt(args[i]);
                    try {
                        man.waiton(tasks);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }else{
                     System.out.println("Warning : You can't do that!");
                }
                break;
            }
            case "completed":{
                if(logged == true){
                    try {
                       man.task_return(Integer.parseInt(args[1]),this.username);
                    } catch (TaskNotFoundException ex) {
                         System.out.println("Task not found!");
                    } catch (WrongUserException ex) {
                        System.out.println("You are not the Owner of this Task!");
                    }
                }else{
                     System.out.println("Warning : You can't do that!");
                }
                break;
            }
            case "request":{
                if(logged==true){
                    try {
                        String res  = Integer.toString(man.task_request(args[1], this.username));
                        if(res.equals("-1"))
                            System.out.println("Task not available!");
                        else{
                       System.out.println("Task ID: " + res);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }else{
                     System.out.println("Warning : You can't do that!");
                }
                break;
            }
            default :{
                System.out.println("Warning : You can't do that!");
                break;
            }
        }
        return logged;
    }
    @Override
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
