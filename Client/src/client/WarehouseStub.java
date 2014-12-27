package client;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.*;
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
class WarehouseStub implements ManagerInterface, UsersInterface {
    
    private String returnStr;
    final Socket socket;
    final BufferedReader in;
    final PrintWriter out;
    
    public WarehouseStub(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());
        this.returnStr = "";
    }
    
    @Override
    public void define_task(String name, HashMap<String, Integer> tools) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, lib.TaskType> get_taskTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskType get_taskType(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int task_request(String taskType, String user) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void task_return(int task_id) throws TaskNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add_tool(String name, int qtt, boolean ret) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int waiton(int[] tasks) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<lib.Task> getActiveTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException, AlreadyLoggedException {
        String msg = "login:" + username + ":" + password;
        out.println(msg);
        out.flush();
        
        try {
            String response = in.readLine();
            switch(response) {
                case "Exception:alreadylogged": 
                    throw new AlreadyLoggedException();
                case "Exception:wrongpassword":
                    throw new WrongPasswordException();
                case "Exception:usernotfound":
                    throw new UserNotFoundException();
            }
            
        } catch(IOException e) { e.printStackTrace(); }  
    }

    @Override
    public void register(String username, String password) throws AlreadyRegisteredException {
        String msg = "register:" + username + ":" + password;
        out.println(msg);
        out.flush();
        
        try {
            String response = in.readLine();
            if(response.equals("Exception:alreadyregistered"))
                throw new AlreadyRegisteredException();
        } catch(IOException e) {e.printStackTrace(); }
    }

    @Override
    public void logout(String username) {
        String msg = "logout:username";
        out.println(msg);
        out.flush();
        
        try {
            String response = in.readLine();
            if(response.equals("logout:ok"))
                socket.close();
        } catch(IOException e) {e.printStackTrace(); }
    }
    
    public String parseMessage(String msg) {
        String message[] = msg.split(" ");
        String response = null;
        if(message.length < 1) return null;
        
        String command = message[0];
        
        switch(command) {
            case "register":
                try {
                    register(message[1], message[2]);
                } catch (AlreadyRegisteredException ex) {
                    ex.printStackTrace();
                }
                break;
            case "login":
                try {
                    login(message[1], message[2]);
                } catch (UserNotFoundException ex) {
                    ex.printStackTrace();
                } catch (WrongPasswordException ex) {
                    ex.printStackTrace();
                } catch (AlreadyLoggedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "logout":
                logout(message[1]);
                break;
            case "activity":
                getActiveTasks();
                break;
            case "wait_for":
                int[] tasks = new int[message.length - 1];
                for(int i = 1; i < message.length; i++)
                    tasks[i] = Integer.parseInt(message[i]);
                try {
                    waiton(tasks);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "supply":
                add_tool(message[1], Integer.parseInt(message[2]), Boolean.parseBoolean(message[3]));
                break;
            case "completed":
                try {
                    task_return(Integer.parseInt(message[1]));
                } catch (TaskNotFoundException ex) {
                    ex.printStackTrace();
                }
                break;
            case "request":
                try {
                    task_request(message[1], message[2]);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "type":
                if(message[1] == null)
                    get_taskTypes();
                else
                    get_taskType(message[1]);
                break;
            case "define":
                HashMap<String, Integer> tools = new HashMap<>();
                for(int i = 2; i < message.length; i++) {
                    String aux[] = message[i].split(":");
                    String tool = aux[0];
                    int quantity = Integer.parseInt(aux[1]);
                    tools.put(tool, quantity);
                }
                define_task(message[1], tools);
                break;
            default:
                returnStr = "Say whaaaaat?";
        }
        
        return returnStr;
    }

}
