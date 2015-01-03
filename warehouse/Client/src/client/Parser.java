package client;


import java.io.Console;
import java.io.IOException;
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
        
        if(msg.matches("\\s+") || msg.equals(""))
            return "";
        
        String message[] = msg.split(" ");
        
        if(message.length < 1) 
            return "";
        
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
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
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
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                }
                break;
            case "quit":
                try {
                    stub.quit();
                } catch (IOException ex) {
                }
                returnStr = "Goodbye =)";
                break;
            default:
                returnStr = "Please login/register!";
                break;
            }
        } else {
            switch(command) {
                
            case "logout":
                try {
                    stub.logout(this.user);
                } catch (IOException ex) {
                }
                returnStr = "Logged out!";
                break;
            case "activity":
                List l;
                try {
                    l = stub.getActiveTasks();
                    if(l == null)
                        returnStr = "No activity at the moment!";
                    else {
                        returnStr = "\nActivity:\n";
                        for (Object l1 : l) {
                            Task t = (Task) l1;
                            returnStr += t.toString();
                        }
                    }
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                }
                break;
            case "wait_for":
                int[] tasks = new int[message.length - 1];
                for(int i = 1; i < message.length; i++)
                    tasks[i - 1] = Integer.parseInt(message[i]);
                try {
                    if(stub.waiton(tasks) == 1)
                        returnStr = "All tasks completed!";
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                }
                break;
            case "supply":
                String tool = message[1].replace("_", " ");
                try {
                    stub.add_tool(tool, Integer.parseInt(message[2]), true);
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                }
                break;
            case "completed":
                try {
                    stub.task_return(Integer.parseInt(message[1]));
                } catch (TaskNotFoundException ex) {
                    returnStr = "Task not found!";
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                } catch (WrongUserException ex) {
                    returnStr = "Task belongs to other user!";
                }
                break;
            case "request":
                try {
                    returnStr = "Task ID: " + Integer.toString(stub.task_request(message[1], this.user));
                    if(returnStr.equals("-1"))
                        returnStr = "Task not available!";
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                }
                break;
            case "list":
                returnStr = "\nTasks available:\n";
                HashMap<String, TaskType> map;
                try {
                    map = stub.get_taskTypes();
                if(map == null)
                    returnStr = "No tasks available at the moment!";
                else {
                    for(TaskType tt : map.values()) {
                        returnStr += tt.toString();
                    }
                }                    
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
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
                try {
                    stub.define_task(message[1], tools);
                } catch (TaskAlreadyDefinedException ex) {
                    returnStr = "Task " + message[1] + " already defined!";
                } catch (IOException ex) {
                    returnStr = "Connection lost!";
                }
                break;
            case "quit":
                try {
                    stub.quitWhileLoggedIn();
                } catch (IOException ex) {
                }
                returnStr = "Goodbye =)";
                break;
            case "help":
                break;
            default:
                returnStr = command + " not available. Type help to list all available commands!";
                break;
            }
        }     
        return returnStr;
    }
}
