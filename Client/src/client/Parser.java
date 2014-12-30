package client;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lib.Task;
import lib.TaskType;
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
    
    public String parseAndCall(String msg, WarehouseStub stub, boolean loggedin) {
        String returnStr = "";
        String message[] = msg.split(" ");
        String user = "";
        
        if(message.length < 1) 
            return null;
        
        String command = message[0];
        
        if(loggedin == false) {
            switch(command) {
                
            case "register":
                try {
                    stub.register(message[1], message[2]);
                    returnStr = "Register successful.";
                } catch (AlreadyRegisteredException ex) {
                    returnStr = "The username " + message[1] + " already exists!"; 
                }
                break;
            case "login":
                user = message[1];
                try {
                    stub.login(user, message[2]);
                    returnStr = "Login successful!";
                } catch (UserNotFoundException ex) {
                    returnStr = user + " not found!";
                } catch (AlreadyLoggedException ex) {
                    returnStr = user + " already logged in!";
                } catch (WrongPasswordException ex) {
                    returnStr = "Wrong password!";
                }
                break;
            case "quit":
                stub.quit();
                break;
            default:
                returnStr = "Please login/register!";
                break;
            }
        } else {
            switch(command) {
                
            case "logout":
                stub.logout(user);
                returnStr = "Goodbye =)";
                break;
            case "activity":
                List l = new ArrayList<>();
                l = stub.getActiveTasks();
                returnStr = "\nActivity\n";
                for (Object l1 : l) {
                    Task t = (Task) l1;
                    returnStr += t.toString();
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
                stub.add_tool(message[1], Integer.parseInt(message[2]), true);
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
                    stub.task_request(message[1], message[2]);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "list":
                returnStr = "\nTasks available:\n";
                HashMap<String, TaskType> map = stub.get_taskTypes();
                for(TaskType tt : map.values()) {
                    returnStr += tt.toString();
                }
                break;
            case "define":
                HashMap<String, Integer> tools = new HashMap<>();
                for(int i = 2; i < message.length; i++) {
                    String aux[] = message[i].split(":");
                    String tool = aux[0];
                    int quantity = Integer.parseInt(aux[1]);
                    tools.put(tool, quantity);
                }
                stub.define_task(message[1], tools);
                break;
            default:
                returnStr = "Say whaaaaat?";
                break;
            }
        }     
        return returnStr;
    }
}
