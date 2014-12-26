/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

import com.warehouse.tasks.Task;
import com.warehouse.tasks.TaskType;
import com.warehouse.tools.WareHouse;
import com.warehouse.util.TaskNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



/**
 *
 * @author Pedro
 */
public class Manager {
    
    WareHouse wh;
    int nextId = 0;
    
    HashMap<String,TaskType> taskTypes;
    HashMap<Integer,Task> activeTasks;
    
    ///locks!
    private final Lock tsktype_lock  =new ReentrantLock();
    private final Lock tsk_lock  =new ReentrantLock();
    private final Lock counterLock = new ReentrantLock();
    private final Lock activeTasksLock = new ReentrantLock();


    public void lockTskType(){tsktype_lock.lock();}
    public void unlockTskType(){tsktype_lock.unlock();}
    
    public void lockTsk(){tsk_lock.lock();}
    public void unlockTsk(){tsk_lock.unlock();}
    
    public void clock(){counterLock.lock();}
    public void cunlock(){counterLock.unlock();}

    //metodos
    public void define_task(String name,HashMap<String,Integer> tools)
    {
       lockTskType();
       try{
           taskTypes.put(name,new TaskType(name, tools));
       }
       finally{unlockTskType();}
    }
   
    public HashMap<String,TaskType> get_taskTypes()
    {
        HashMap<String,TaskType> map;
        lockTskType();
        try {
            map = taskTypes;
            return map;
        } finally { unlockTskType(); }
    }
    
    public TaskType get_taskType(String name)
    {
        TaskType t;
        
        lockTskType();
        try {
            t = taskTypes.get(name);
            return t;
        } finally { unlockTskType(); }
    }
    
    public int task_request(String taskType,String user) throws InterruptedException
    {
        int id = -1;
        clock();
        try{
            nextId++;
            id = nextId;
        }
        finally{cunlock();}
        
        TaskType t = null;
        
        lockTskType();
        try{
             t = taskTypes.get(taskType);
        }
        finally{unlockTskType();}
        
        Task task = new Task(id,user, t);
        HashMap<String,Integer> tools  = t.getTools();
        if(tools==null) return -1;
        
        wh.tools_request(tools);
        
        lockTsk();
        try{
            this.activeTasks.put(id, task);
        }
        finally{unlockTsk();}
        
        return id;
    }
    
    public void task_return(int task_id) throws TaskNotFoundException
    {
        Task t;
        HashMap<String,Integer> tools = null;
        
        lockTsk();
        try{
           tools = activeTasks.get(task_id).getTools();
        }
        finally{unlockTsk();}
        
        if(tools == null) throw new TaskNotFoundException();
        
        wh.tools_return(tools);
        
        lockTsk();
        try{
           activeTasks.remove(task_id);
        }
        finally{unlockTsk();}   
    }
    
     public void add_tool(String name,int qtt,boolean ret)
     {
         wh.add_tool(name, qtt, ret);
     }
     
   /** public int waiton(int[] tasks)
     {
         throw new NotImplementedException();
     }
    **/
     
     public List<Task> getActiveTasks() {
        List l = new ArrayList<>();
        activeTasksLock.lock();
        try{
            l.addAll(activeTasks.values());
        } finally { activeTasksLock.unlock(); }
        return l;
     }
}
