/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.handlers;

import com.warehouse.tasks.*;
import com.warehouse.users.Users;
import com.warehouse.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Pedro
 */
public class WareHouseSkeleton {

    Users users;
    Manager manager;
    ClientHandler caller;

    WareHouseSkeleton(ClientHandler caller, Users us, Manager m) {
        this.users = us;
        this.manager = m;
        this.caller = caller;
    }

    public String parseMassage(String m) {
        System.out.println(m);
        String message[] = m.split(":");
        String response = null;
        if (message.length < 1) {
            return null;
        }

        switch (message[0]) {
            case "register": {
                if (message.length <= 2) {
                    break;
                }

                try {
                    users.register(message[1], message[2]);
                    //escrever no cenas
                    new Save2FileThread(m, caller.logger.usrPw).start();
                } catch (AlreadyRegisteredException ex) {
                    response = "Exception:alreadyregistered";
                    break;
                }

                response = "register:ok";
                break;
            }
            case "login": {
                if (message.length <= 2) {
                    break;
                }
                try {
                    users.login(message[1], message[2]);
                } catch (UserNotFoundException ex) {
                    response = "Exception:usernotfound";
                    break;
                } catch (WrongPasswordException ex) {
                    response = "Exception:wrongpassword";
                    break;
                } catch (AlreadyLoggedException ex) {
                    response = "Exception:alreadylogged";
                    break;
                }
                caller.username = message[1];
                response = "login:ok";
                break;
            }
            case "logout": {
                if (message.length <= 1) {
                    break;
                }
                users.logout(message[1]);
                response = "logout:ok";
                break;
            }
            case "supply": {
                if (message.length <= 2 || !isNumber(message[2])) {
                    break;
                }

                int qtt = Integer.parseInt(message[2]);
                manager.add_tool(message[1], qtt, true);
                //escrever no cenas
                new Save2FileThread(m, caller.logger.toolsPw).start();
                response = "supply:ok";
                break;
            }
            case "definetask": {
//              definetask:nometarefa:tool1:qtd:tool2:qtd
//              tem de ter pelo menos 4 argumentos 
                response = null;
                HashMap<String, Integer> tools = new HashMap<>();
                if (message.length <= 3) {
                    break;
                }
                String taskname = message[1];

                for (int i = 2; i < message.length - 1; i += 2) {
                    String name = message[i];
                    if (message[i + 1] == null || !isNumber(message[i + 1])) {
                        response = "Exception:wrongargumentsformat";
                        break;
                    }
                    int qtd = Integer.parseInt(message[i + 1]);
                    tools.put(name, qtd);
                }
                if (response != null) {
                    break;
                }

                try {
                    manager.define_task(taskname, tools);
                    //escrever no cenas
                    
                    response = "definetask:ok";
                    break;

                } catch (TaskAlreadyDefinedException ex) {
                    response = "Exception:taskalreadydefined";
                    break;
                }
            }
            case "taskrequest": {
                if (message.length <= 2) {
                    break;
                }
                String taskType = message[1];
                String username = message[2];
                try {
                    int task_request = manager.task_request(taskType, username);
                    response = "" + task_request;
                    //escrever no cenas
                    break;

                } catch (InterruptedException ex) {
                    response = "-1";
                    break;
                }
            }
            case "taskreturn": {
                if (message.length <= 1 || !isNumber(message[1])) {
                    break;
                }
                int task_id = Integer.parseInt(message[1]);
                try {
                    manager.task_return(task_id);
                    response = "taskreturn:ok";
                    //escrever no cenas
                    break;
                } catch (TaskNotFoundException ex) {
                    response = "Exception:tasknotfound";
                    break;

                }
            }
            case "list": {
                Collection<TaskType> types = manager.get_taskTypes().values();
                response = "";
                for (TaskType t : types) {
                    response += t.getType();
                    for (String s : t.getTools().keySet()) {
                        response += ":" + s + ":" + t.getTools().get(s);
                    }
                    response += ";";

                }
                System.out.println(response);
                break;

            }
            case "activity": {
                ArrayList<Task> tasks = (ArrayList<Task>) manager.getActiveTasks();
                response = "";
                for (Task t : tasks) {
                    response += t.get_Id() + ":" + t.getType() + ":" + t.getUsername() + ";";
                }
                break;
            }
            default: {
                response = "Exception:nosuchmethod";
                break;
            }
        }

        return response;
    }

    public boolean isNumber(String s) {

        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}
