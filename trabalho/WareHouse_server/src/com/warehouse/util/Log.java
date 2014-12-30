/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.util;

import com.warehouse.tasks.Task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class Log {
    
    private File users;
    private File tools;
    private File tasks;
    //public PrintWriter pw;
    public BufferedReader usrbr;
    public BufferedReader toolsbr;
    public BufferedReader tasksbr;
    
    
    public Log() {
        File theDir = new File("logger");
   
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            } catch(SecurityException se){
                  
            }
        }
        theDir = null;
        boolean b;
        users  = new File("logger\\users.log");
        tools  = new File("logger\\tools.log");
        tasks  = new File("logger\\tasks.log");
        try {
            if(!users.exists())
                 b = users.createNewFile();
            if(!tools.exists())
                 b = tools.createNewFile();
            if(!tasks.exists())
                 b = tasks.createNewFile();
            
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
         
            usrbr = new BufferedReader(new FileReader(users));
            toolsbr = new BufferedReader(new FileReader(tools));
            tasksbr = new BufferedReader(new FileReader(tasks));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
     
     
}
