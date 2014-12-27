package client;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
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

    final Socket socket;
    final BufferedReader in;
    final PrintWriter out;
    
    public WarehouseStub(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());
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
        String msg = "logout";
        out.println(msg);
        out.flush();
    }

}
