/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.handlers;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Pedro
 */
public class ConnectionsHandler extends Thread{
    
    private final int port;
    private final Users users;
    private final Manager manager;
    private boolean run;
    
    public ConnectionsHandler(int port,Users u, Manager m){
        this.manager = m;
        this.users = u;
        this.port = port;
      
    }
    
    
    @Override
    public void run()
    {
        ServerSocket ss;
        try {
            ss = new ServerSocket(port);
            System.out.println("Server initialized on port : "+port);
            
            while(true){
               Socket sc = ss.accept();
               try{
               new ClientHandler(sc, users, manager).start();
               }catch(Exception e1){
                   System.out.println(e1.getMessage());
               }
               
           }
            
        } catch (IOException e2) {
           System.out.println(e2.getMessage());
        }
    }
    
}
