package client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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
    private String user;

    public WarehouseStub(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void define_task(String name, HashMap<String, Integer> tools) throws TaskAlreadyDefinedException, IOException{
        String msg = "definetask:" + name + ":";

        for (String s : tools.keySet()) {
            msg += s;
            msg += ":";
            msg += tools.get(s);
            msg += ":";
        }

        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            if(response.equals("Exception:taskalreadydefined"))
            throw new TaskAlreadyDefinedException();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public HashMap<String, TaskType> get_taskTypes() throws IOException{
        String msg = "list";
        out.println(msg);
        out.flush();

        String response = "";

        try {
            response = in.readLine();
        } catch (IOException e) {
            throw new IOException();
        }

        HashMap<String, TaskType> map = new HashMap<>();
        String array[] = response.split(";");

        for (String array1 : array) {
            String[] aux = array1.split(":");
            String type = aux[0];
            HashMap<String, Integer> tools = new HashMap<>();
            for (int i = 1; i < aux.length; i += 2) {
                tools.put(aux[i], Integer.parseInt(aux[i + 1]));
            }
            TaskType tt = new TaskType(type, tools);
            map.put(type, tt);
        }

        return map;
    }

    @Override
    public int task_request(String taskType, String user) throws InterruptedException, IOException {
        String msg = "taskrequest:" + taskType + ":" + user;
        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            if (!response.equals("-1")) {
                return Integer.parseInt(response);
            }
        } catch (IOException e) {
            throw new IOException();
        }

        return -1;
    }

    @Override
    public void task_return(int task_id) throws TaskNotFoundException, IOException, WrongUserException {
        String msg = "taskreturn:" + task_id;
        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            if(response.equals("Exception:tasknotfound"))
            throw new TaskNotFoundException();
            if(response.equals("Exception:WrongUser"))
            throw new WrongUserException();
        } catch (IOException ex) {
            throw new IOException();
        }
    }

    @Override
    public void add_tool(String name, int qtt, boolean ret) throws IOException {
        String msg = "supply:" + name + ":" + qtt;
        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public int waiton(int[] tasks) throws InterruptedException, IOException {
        String msg = "wait_for:";

        for (int i = 0; i < tasks.length; i++) {
            msg += tasks[i];
            msg += ":";
        }

        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            if(response.equals("waitfor:alltasksterminated"))
            return 1;
        } catch (IOException e) {
            throw new IOException();
        }

        return 0;
    }

    @Override
    public List<Task> getActiveTasks() throws IOException {
        String msg = "activity";
        out.println(msg);
        out.flush();

        String response = "";

        try {
            response = in.readLine();
        } catch (IOException ex) {
            throw new IOException();
        }

        if(response.equals(""))
        return null;

        List<Task> list = new ArrayList<>();
        String[] array = response.split(";");
        for (String s : array) {
            String[] aux = s.split(":");
            Task t = new Task(Integer.parseInt(aux[0]), aux[2], aux[1]);
            list.add(t);
        }
        return list;
    }

    @Override
    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException, AlreadyLoggedException, IOException {
        String msg = "login:" + username + ":" + password;
        this.user = username;
        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            switch (response) {
                case "Exception:alreadylogged":
                throw new AlreadyLoggedException();
                case "Exception:wrongpassword":
                throw new WrongPasswordException();
                case "Exception:usernotfound":
                throw new UserNotFoundException();
            }

        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void register(String username, String password) throws AlreadyRegisteredException, IOException {
        String msg = "register:" + username + ":" + password;
        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            if (response.equals("Exception:alreadyregistered")) {
                throw new AlreadyRegisteredException();
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public void logout(String username) throws IOException{
        String msg = "logout:" + username;
        out.println(msg);
        out.flush();

        try {
            String response = in.readLine();
            // do something
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public void quitWhileLoggedIn() throws IOException {
        try {
            logout(user);
            socket.close();
        } catch (IOException ex) {
            throw new IOException();
        }
    }

    public void quit() throws IOException{
        try {
            socket.close();
        } catch (IOException ex) {
            throw new IOException();
        }
    }
}
