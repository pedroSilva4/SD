/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.util;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Pedro
 */
public class InitThread extends Thread {

    private final Log log;
    private final Users us;
    private final Manager man;

    public InitThread(Log log, Manager man, Users us) {
        this.log = log;
        this.us = us;
        this.man = man;

    }

    private void parser(String m) {
        String message[] = m.split(":");
        if (message.length < 1) {
            return;
        }

        switch (message[0]) {
            case "register": {
                if (message.length <= 2) {
                    break;
                }
                try {
                    this.us.register(message[1], message[2]);
                } catch (AlreadyRegisteredException ex) {
                    break;
                }
                break;
            }
            case "supply": {
                if (message.length <= 2 || !isNumber(message[2])) {
                    break;
                }

                int qtt = Integer.parseInt(message[2]);
                man.add_tool(message[1], qtt, true);
                break;

            }
            case "definetask": {
                String response = null;
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
                    man.define_task(taskname, tools);
                    break;

                } catch (TaskAlreadyDefinedException ex) {
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
                    int task_request = man.task_request(taskType, username);
                    break;

                } catch (InterruptedException ex) {
                    break;
                }
            }
            case "taskreturn": {
                if (message.length <= 1 || !isNumber(message[1])) {
                    break;
                }
                int task_id = Integer.parseInt(message[1]);
                try {
                    man.task_return(task_id);
                    break;
                } catch (TaskNotFoundException ex) {
                    break;
                }
            }
            default:
                break;

        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                String s = log.usrbr.readLine();
                if (s == null) {
                    break;
                }
                this.parser(s);
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
        while (true) {
            try {
                String s = log.toolsbr.readLine();
                if (s == null) {
                    break;
                }
                this.parser(s);
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
        while (true) {
            try {
                String s = log.tasksbr.readLine();
                if (s == null) {
                    break;
                }
                this.parser(s);
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }

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
