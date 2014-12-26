/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.handlers;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import com.warehouse.util.AlreadyRegisteredException;
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
    private Socket sc;
    private WareHouseSkeleton skeleton;
    private String adress;
    String username;
    
    public ClientHandler(Socket sc,Users us,Manager m)
    {
        this.sc  = sc;
        skeleton = new WareHouseSkeleton(this,us,m);
        adress = sc.getInetAddress().getHostAddress();
    }
    
    public void run()
    {       
        try {
            
            boolean run = true;
            while(run){
                
                InputStreamReader ir = new InputStreamReader(sc.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                PrintWriter pw = new PrintWriter(sc.getOutputStream());
                
                String s = br.readLine();
                if(s==null) break;
                
                String response = skeleton.parseMassage(s);
                
                pw.println(response);
                pw.flush();
            }
            sc.close();
            String  r = skeleton.parseMassage("logout:"+username);
            System.out.println("Client "+username+" on : "+adress+ " disconnected,"+r);
            
        } catch (IOException ex) {
            String  r = skeleton.parseMassage("logout:"+username);
            System.out.println("Client "+username+" on : "+adress+ " disconnected,"+r);
        }
    }
    
}
