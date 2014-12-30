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

        Parser parser = new Parser();
        WarehouseStub stub = null;
        String input = "";
    
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            try {
                input = stdin.readLine();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            try {
                stub = new WarehouseStub(host, port);
                String ret = parser.parseAndCall(input, stub, false);
                System.out.println("Response: " + ret);
                if(ret.equals("Login successful!")) {
                    while(true) {
                         input = stdin.readLine();
                         ret = parser.parseAndCall(input, stub, true);
                         System.out.println("> " + ret);
                         if(ret.equals("Goodbye =)"))
                             break;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }            
        }
  }  
}
