/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

import com.warehouse.handlers.ConnectionsHandler;
import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import com.warehouse.util.InitThread;
import com.warehouse.util.Log;
import java.io.File;

/**
 *
 * @author Pedro
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        Users users = new Users();
        Manager manager = new Manager();
        
        Log logger = new Log();
        
        InitThread init = new InitThread(logger, manager, users);
        init.start();
        init.join();
        
        ConnectionsHandler conn  = new ConnectionsHandler(50000, users, manager,logger);
        conn.start();
        conn.join();
        
        
    }
    
}
