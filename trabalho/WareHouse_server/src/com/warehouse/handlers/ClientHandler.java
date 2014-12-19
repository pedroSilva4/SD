/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.handlers;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class ClientHandler extends Thread{
    Socket sc;
    WareHouseSkeleton skeleton;
    
    public ClientHandler(Socket sc,Users us,Manager m)
    {
        this.sc  = sc;
        skeleton = new WareHouseSkeleton(us,m);
    }
    
    public void run()
    {
        while(true){
          
            try {
                    InputStreamReader ir = new InputStreamReader(sc.getInputStream());
                    BufferedReader br = new BufferedReader(ir);
                    PrintWriter pw = new PrintWriter(sc.getOutputStream());
                    String s = br.readLine();
                   
                    String response = skeleton.parseMassage(s);
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    
}
