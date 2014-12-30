/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouse;

import com.warehouse.tasks.Manager;
import com.warehouse.tools.Tool;
import com.warehouse.tools.*;
import com.warehouse.util.TaskAlreadyDefinedException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class Test_tasks_tools {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        final Manager m = new Manager();
        
        class supply extends Thread
        {
           
           @Override
           public void run()
           {
               for(int i = 0;i<10;i++)
               {
                   m.add_tool("tool"+i, 5, true);
               }
               System.out.println("supply done");
           }
        }
        
        class definer extends Thread{
           public void run()
           {
               HashMap<String,Integer> tools = new HashMap<>();
               
               tools.put("tool0",2);
               tools.put("tool4",4);
               tools.put("tool6",2);
               
               try {
                   m.define_task("Escacar Pedra", tools);
               } catch (TaskAlreadyDefinedException ex) {
                   Logger.getLogger(Test_tasks_tools.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        }
           
         class worker extends Thread{
             public void run(){
                 while(true){
                     System.out.println("toua realizar tasks");
                 }
             }
         }
         
        
       
        
       /** class consulter extends Thread {
            @Override
            public void run() {
                while(true) {
                    HashMap<String,Tool> inv = m.get_inventory();
                    for(Tool t : inv.values()) {
                        System.out.println("Tool: " + t.getId() + " Quantity: " + t.qtd());
                    }
                    try {
                        sleep(6000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Test_tasks_tools.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        ***/
        
         supply sp  = new supply();
         sp.start();
         sp.join();
         
        /* consulter cs = new consulter();
         cs.start();
         definer df = new definer();
         df.start();
         df.join();
         worker[] wks = new worker[5];
       
         
         for(int i = 0;i<5;i++){
         wks[i] = new worker();
         wks[i].start();    
         }
         cs.join();
         */
        // TODO code application logic here
    }
    
}
