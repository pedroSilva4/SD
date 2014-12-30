/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

/**
 *
 * @author Pedro
 */
public class Task {
    
    private int task_id;
    private String user;
    private String type;
    
    public Task(int id, String user, String type)
    {
        this.task_id = id;
        this.type = type;
        this.user = user;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("\tTask ID: ").append(task_id);
        sb.append("\tTask: ").append(type);
        sb.append("\tUser: ").append(user);
        sb.append("\n");
                
        return sb.toString();
    }
}
