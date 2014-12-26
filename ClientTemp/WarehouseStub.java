import java.net.*;
import java.io.*;

class WarehouseStub implements ManagerInterface, UsersInterface {

  public void define_task(String name,HashMap<String,Integer> tools);

  public HashMap<String,TaskType> get_taskTypes();

  public TaskType get_taskType(String name);

  public int task_request(String taskType,String user) throws InterruptedException;

  public void task_return(int task_id) throws TaskNotFoundException;

  public void add_tool(String name,int qtt,boolean ret);

  public int waiton(int[] tasks) throws InterruptedException;
  
  public List<Task> getActiveTasks();
}
