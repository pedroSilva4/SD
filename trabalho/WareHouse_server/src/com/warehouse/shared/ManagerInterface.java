/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.shared;

import com.warehouse.tasks.Task;
import com.warehouse.tasks.TaskType;
import com.warehouse.util.TaskAlreadyDefinedException;
import com.warehouse.util.TaskNotFoundException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Pedro
 */
public interface ManagerInterface {
     
     public void define_task(String name,HashMap<String,Integer> tools) throws TaskAlreadyDefinedException;
     public HashMap<String,TaskType> get_taskTypes();
     public TaskType get_taskType(String name);
     public int task_request(String taskType,String user) throws InterruptedException;
     public void task_return(int task_id) throws TaskNotFoundException;
     public void add_tool(String name,int qtt,boolean ret);
     public int waiton(int[] tasks) throws InterruptedException;
     public List<Task> getActiveTasks();
    
}
