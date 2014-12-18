/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.users;

/**
 *
 * @author Pedro
 */
public class User {
     
     private String username;
     private String password;
     
     public User(String name, String pass)
     {
         username = name;
         password= pass;
     }

    Object getPassword() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
   
    
    
    
}
