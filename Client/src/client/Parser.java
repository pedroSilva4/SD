package client;


import java.io.Console;
import java.util.HashMap;
import java.util.List;
import lib.Task;
import lib.TaskType;
import lib.User;
import util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bruno
 */
public class Parser {
    
    private String user;
    
    public Parser() {
        this.user = "";
    }

    public String parseAndCall(String msg, WarehouseStub stub, boolean loggedin, Console stdin) {
        String returnStr = "";
        String message[] = msg.split(" ");
        
        if(message.length < 1) 
            return null;
        
        String command = message[0];
        
        if(loggedin == false) {
            switch(command) {
                
            case "register":
                try {
                    String usr = message[1];
                    System.out.println("Password:");
                    char[] pass = stdin.readPassword();
                    String password = "";
                    for(char c : pass)
                        password += c;
                    stub.register(usr, User.convertPassword(password));
                    returnStr = "Register successful.";
                } catch (AlreadyRegisteredException ex) {
                    returnStr = "The username " + message[1] + " already exists!"; 
                }
                break;
            case "login":
                this.user = message[1];
                System.out.println("Password:");
                char[] pass = stdin.readPassword();
                String password = "";
                for(char c : pass)
                    password += c;
                try {
                    stub.login(this.user, User.convertPassword(password));
                    returnStr = "Login successful!";
                } catch (UserNotFoundException ex) {
                    returnStr = this.user + " not found!";
                } catch (AlreadyLoggedException ex) {
                    returnStr = this.user + " already logged in!";
                } catch (WrongPasswordException ex) {
                    returnStr = "Wrong password!";
                }
                break;
            case "quit":
                stub.quit();
                returnStr = "Goodbye =)";
                break;
            default:
                returnStr = "Please login/register!";
                break;
            }
        } else {
            switch(command) {
                
            case "logout":
                stub.logout(this.user);
                returnStr = "Logged out!";
                break;
            case "activity":
                List l = stub.getActiveTasks();
                if(l == null)
                    returnStr = "No activity at the moment!";
                else {
                    returnStr = "\nActivity:\n";
                    for (Object l1 : l) {
                        Task t = (Task) l1;
                        returnStr += t.toString();
                    }
                }
                break;
            case "wait_for":
                int[] tasks = new int[message.length - 1];
                for(int i = 1; i < message.length; i++)
                    tasks[i] = Integer.parseInt(message[i]);
                try {
                    stub.waiton(tasks);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "supply":
                String tool = message[1].replace("_", " ");
                stub.add_tool(tool, Integer.parseInt(message[2]), true);
                break;
            case "completed":
                try {
                    stub.task_return(Integer.parseInt(message[1]));
                } catch (TaskNotFoundException ex) {
                    returnStr = "Task not found!";
                }
                break;
            case "request":
                try {
                    returnStr = "Task ID: " + Integer.toString(stub.task_request(message[1], this.user));
                    if(returnStr.equals("-1"))
                        returnStr = "Task not available!";
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "list":
                returnStr = "\nTasks available:\n";
                HashMap<String, TaskType> map = stub.get_taskTypes();
                if(map == null)
                    returnStr = "No tasks available at the moment!";
                else {
                    for(TaskType tt : map.values()) {
                        returnStr += tt.toString();
                    }
                }
                break;
            case "define":
                HashMap<String, Integer> tools = new HashMap<>();
                for(int i = 2; i < message.length; i++) {
                    String aux[] = message[i].split(":");
                    String toolAux = aux[0].replace("_", " ");
                    int quantity = Integer.parseInt(aux[1]);
                    tools.put(toolAux, quantity);
                }
                stub.define_task(message[1], tools);
                break;
            case "quit":
                stub.quitWhileLoggedIn();
                returnStr = "Goodbye =)";
                break;
            default:
                returnStr = "Say whaaaaat?";
                break;
            }
        }     
        return returnStr;
    }
}
