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
        
        String menu1 = "# Warehouse Client #\n";
        menu1 += "Type:\n";
        menu1 += "\tregister <username>\t-\tto create account\n";
        menu1 += "\tlogin <username>\t-\tto login\n";    
        menu1 += "\tquit\t-\tto logout and exit application\n";
        menu1 += "\thelp\t-\tto list all available commands\n";
        
        String menu2 = "# Warehouse Client #\n";
        menu2 += "Type:\n";
        menu2 += "\tactivity\t-\tto list all active tasks\n";
        menu2 += "\twait_for <task ID 1> <Task ID 2> ...\t-\tto wait for the completion of task 1 and 2\n";
        menu2 += "\tsupply <tool name> <quantity>\t-\tto supply a tool at your choice\n";
        menu2 += "\tcompleted <task ID>\t-\tto report the completion of a task\n";
        menu2 += "\trequest <task name>\t-\tto request permission to execute a task\n";
        menu2 += "\tlist\t-\tto list all available tasks\n";
        menu2 += "\tdefine <task name> <tool 1:quantity required> <tool 2:quantity required> \t-\tto define a new task\n";
        menu2 += "\thelp\t-\tto list all available commands\n";
        menu2 += "\tlogout\t-\tto logout\n";
        
        System.out.println(menu1);
        while(true) {
            System.out.print(">>");
            input = stdin.readLine();
            if(input.equals("help"))
                System.out.println(menu1);
            try {
                stub = new WarehouseStub(host, port);
                String ret = parser.parseAndCall(input, stub, false, stdin);
                System.out.println("> " + ret);
                if(ret.equals("Login successful!")) {
                    System.out.println(menu2);
                    while(true) {
                        System.out.print(">>");
                        input = stdin.readLine();
                        if(input.equals("help"))
                            System.out.println(menu2);
                        ret = parser.parseAndCall(input, stub, true, stdin);
                        System.out.println("> " + ret);
                        if(ret.equals("Goodbye =)") || ret.equals("Logged out!"))
                            break;
                    }
                }
                if(ret.equals("Goodbye =)"))
                            break;
            } catch (IOException ex) {
                System.out.println("Connection lost!");
                break;
            }            
        }
  }  
}
