/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import util.*;
import lib.*;

/**
 *
 * @author bruno
 */
public interface ManagerInterface {
     public void define_task(String name,HashMap<String,Integer> tools) throws TaskAlreadyDefinedException, IOException;
     public HashMap<String,TaskType> get_taskTypes() throws IOException;
     public int task_request(String taskType,String user) throws InterruptedException, IOException;
     public void task_return(int task_id) throws TaskNotFoundException, WrongUserException, IOException;
     public void add_tool(String name,int qtt,boolean ret) throws IOException;
     public int waiton(int[] tasks) throws InterruptedException, IOException;
     public List<Task> getActiveTasks() throws IOException;
    
}
