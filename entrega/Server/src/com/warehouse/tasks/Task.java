/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.tasks;

import java.util.HashMap;
import java.util.concurrent.locks.Condition;

/**
 *
 * @author Pedro
 */
public class Task {

    private int task_id;
    private String user;
    private TaskType type;
    private Condition c;

    public Task(int id, String user, TaskType t, Condition c) {
        this.task_id = id;
        this.type = t;
        this.user = user;
        this.c = c;
    }

    public synchronized String getType() {
        return type.getType();
    }

    public synchronized int get_Id() {
        return this.task_id;
    }

    public synchronized String getUsername() {
        return this.user;
    }

    public synchronized HashMap<String, Integer> getTools() {
        return this.type.getTools();
    }

    public void await() throws InterruptedException {
        this.c.await();
    }

    public void signal() {
        this.c.signal();
    }

    public void signalAll() {
        this.c.signalAll();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Task ID :").append(task_id);
        sb.append("\t");
        sb.append("Type :").append(type.getType());
        sb.append("\t");
        sb.append("User :").append(this.user);
        sb.append("\n");

        return sb.toString();
    }
}
