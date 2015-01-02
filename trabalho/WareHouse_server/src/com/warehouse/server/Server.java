/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.server;

import com.warehouse.LocalClient.LocalClient;
import com.warehouse.handlers.ConnectionsHandler;
import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import com.warehouse.util.InitThread;
import com.warehouse.util.Log;

/**
 *
 * @author Pedro
 */
public class Server {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
      try{
        if(args.length<1) {System.out.println("Missing PORT!");return;}
            
        int PORT = Integer.parseInt(args[0]);
       
        Users users = new Users();
        Manager manager = new Manager();

        Log logger = new Log();

        InitThread init = new InitThread(logger, manager, users);
        init.start();
        init.join();

        ConnectionsHandler conn = new ConnectionsHandler(PORT, users, manager, logger);
        conn.start();
        LocalClient lclient = new LocalClient(users, manager, logger);
        lclient.start();
        
        conn.join();
        lclient.join();
        
      }catch(NumberFormatException ex){
            System.out.println("Cannot Initiate Server on port : "+args[0]);
        }
    }

}
