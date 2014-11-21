/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sd.serverside;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Pedro
 */
public class User implements Serializable{
    
     String username;
     byte[] password;
     
     public User(String name, byte[] pass)
     {
         username = name;
         password= pass;
     }
     
    public static byte[] covertPassword(String s) throws NoSuchAlgorithmException
    {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] convertedPassword = digest.digest(s.getBytes());
            
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < convertedPassword.length; i++) 
            {
                String hex = Integer.toHexString(0xff & convertedPassword[i]);
                //if(hex.length() == 1) 
                    //hexString.append('0');
                
                hexString.append(hex);
            }
            
            System.out.println("Coverted "+s+" to: "+hexString.toString());
            return convertedPassword;
    }
    
   
}
