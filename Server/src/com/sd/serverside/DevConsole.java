/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sd.serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 *
 * @author Pedro
 */
public class DevConsole {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        
        User u = new User("pedrosilva", User.covertPassword("helloworld1"));
        System.out.print("Login::\nusername:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();
        System.out.print("password:");
        String pass = br.readLine();
        
        if(name.equals(u.username)&& Arrays.equals(u.password, User.covertPassword(pass))) System.out.println("\n::Logged IN ::");
        else System.out.println("\n::Password errada ou equals mal ::");
    }
    
}
