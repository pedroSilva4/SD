/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

import com.warehouse.handlers.ConnectionsHandler;
import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;

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
        
        ConnectionsHandler conn  = new ConnectionsHandler(80, users, manager);
        conn.start();
        conn.join();
        
        
    }
    
}
