/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author bruno
 */
public class Client {
  public static void main(String[] args) {
    String host = args[0];
    int port = Integer.parseInt(args[1]);
    
    WarehouseStub stub = null;
    String input = "";
    
    try {
        stub = new WarehouseStub(host, port);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    
    while(true) {
        try {
            input = stdin.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        if(input == null) {
            stub.logout();
            break;
        }
        
        String ret = stub.parseMessage(input);
        
        System.out.println("Response: " + ret);
    }
  }  
}
