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
public class TaskType {

    private final String type;
    private final HashMap<String, Integer> tools;

    public TaskType(String tsk, HashMap<String, Integer> t) {
        type = tsk;
        tools = new HashMap<>(t);
    }

    public HashMap<String, Integer> getTools() {
        return new HashMap<>(this.tools);
    }

    public String getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t");
        sb.append(type);
        sb.append("\n");
        for (String tool : tools.keySet()) {
            sb.append("\t\t").append(tool).append(" ").append(tools.get(tool).toString()).append("\n");
        }

        return sb.toString();
    }
}
