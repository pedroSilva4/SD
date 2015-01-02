/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.io.Console;
import java.io.IOException;

/**
 *
 * @author bruno
 */
public class Client {

    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        Parser parser = new Parser();
        WarehouseStub stub;
        String input = "";
    
//        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        
        Console stdin = System.console();

        while(true) {
            System.out.print(">>");
            input = stdin.readLine();
            
            try {
                stub = new WarehouseStub(host, port);
                String ret = parser.parseAndCall(input, stub, false, stdin);
                System.out.println("> " + ret);
                if(ret.equals("Login successful!")) {
                    while(true) {
                        System.out.print(">>");
                        input = stdin.readLine();
                        ret = parser.parseAndCall(input, stub, true, stdin);
                        System.out.println("> " + ret);
                        if(ret.equals("Goodbye =)") || ret.equals("Logged out!"))
                            break;
                    }
                }
                if(ret.equals("Goodbye =)"))
                            break;
            } catch (IOException ex) {
                ex.printStackTrace();
            }            
        }
  }  
}
