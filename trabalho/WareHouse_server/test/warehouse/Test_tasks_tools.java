/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouse;

import com.warehouse.server.WareHouse;
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
        
        final WareHouse wh = new WareHouse();
        
        class supply extends Thread
        {
           
           @Override
           public void run()
           {
               for(int i = 0;i<10;i++)
               {
                   wh.add_tool("tool"+i, 5, true);
               }
               System.out.println("supply done");
           }
        }
        
        class definer extends Thread{
           public void run()
           {
               HashMap<String,Integer> tools = new HashMap<>();
               
               tools.put("tool0",2);
               tools.put("tool4",2);
               tools.put("tool6",2);
               
               wh.define_task("Escacar Pedra", tools);
           }
        }
           
         class worker extends Thread{
             public void run(){
                 while(true){
                     try {
                         wh.task_request("Escacar Pedra");
                         System.out.println("toua realizar tasks");
                         sleep(4000);
                         wh.task_return("Escacar Pedra");
                         
                     } catch (InterruptedException ex) {
                         ex.printStackTrace();
                     }
                 }
             }
         }
         
        
         supply sp  = new supply();
         sp.start();
         sp.join();
         
         definer df = new definer();
         df.start();
         df.join();
         worker[] wks = new worker[5];
         
         
         for(int i = 0;i<5;i++){
         wks[i] = new worker();
         wks[i].start();    
         }
        // TODO code application logic here
    }
    
}
