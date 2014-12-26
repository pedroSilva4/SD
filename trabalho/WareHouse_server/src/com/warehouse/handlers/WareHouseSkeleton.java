/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.handlers;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import com.warehouse.util.AlreadyLoggedException;
import com.warehouse.util.AlreadyRegisteredException;
import com.warehouse.util.UserNotFoundException;
import com.warehouse.util.WrongPasswordException;


/**
 *
 * @author Pedro
 */
class WareHouseSkeleton {
    Users users;
    Manager manager;
    ClientHandler caller;

    WareHouseSkeleton(ClientHandler caller,Users us, Manager m) {
       this.users = us;
       this.manager = m;
       this.caller = caller;
    }
    
    
    public String parseMassage(String m)
    {
      String message[] = m.split(":");
      String response = null;
      if(message.length < 1) return null;
      
      switch(message[0]){
          case "register":{
                if(message.length <= 2) break;
                
                try {
                    users.register(message[1], message[2]);

                } catch (AlreadyRegisteredException ex) {
                    response = "Exception:alreadyregistered";
                    break;
                }
                
                response = "register:ok";
                break;
          }
          case "login":{
                if(message.length <= 2) break;
                try {
                    users.login(message[1], message[2]);
                } catch (UserNotFoundException ex) {
                    response = "Exception:usernotfound";
                    break;
                } catch (WrongPasswordException ex) {
                    response = "Exception:wrongpassword";
                    break;
                } catch (AlreadyLoggedException ex) {
                    response = "Exception:alreadylogged";
                    break;
                }
                caller.username = message[1];
                response = "login:ok";
                break;
          }
          case "logout":{
                if(message.length <= 1) break;
                users.logout(message[1]);
                response = "logout:ok";
                break;
          }
          case "supply":{
               if(message.length <= 2 || isNumber(message[2])) break;
               
               int qtt = Integer.parseInt(message[2]);
               manager.add_tool(message[1], qtt, true);
               response = "supply:ok";
               break;
          }
          
          default:{
                response = "Execption:nosuchmethod";
                break;
          }
      }
      
      return response;
    }
    
    public boolean isNumber(String s){
        
        try{
            int i = Integer.parseInt(s);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
        
        
    }
}