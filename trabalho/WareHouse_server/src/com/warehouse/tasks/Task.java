/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.tasks;

import java.util.HashMap;

/**
 *
 * @author Pedro
 */
public class Task {
    
    private int task_id;
    private String user;
    private TaskType type;

    
    public Task(int id,String user, TaskType t)
    {
        this.task_id = id;
        this.type = t;
        this.user = user;
    }
    public String getType() {
       return type.getType();
    }

    public HashMap<String, Integer> getTools() {
       return this.type.getTools();
    }
    
}
