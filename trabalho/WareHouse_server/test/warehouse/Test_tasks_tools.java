/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouse;

import com.warehouse.server.Tool;
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
    public static void main(String[] args) {
        
        final WareHouse wh = new WareHouse();
        class suplly extends Thread
        {
           
           @Override
           public void run()
           {
               for(int i = 0;i<10;i++)
               {
                   wh.add_tool("tool"+i, 5, true);
               }
           }
        }
        
        class definer extends Thread{
           
        }
        
        class consulter extends Thread {
            @Override
            public void run() {
                while(true) {
                    HashMap<String,Tool> inv = wh.get_inventory();
                    for(Tool t : inv.values()) {
                        System.out.println("Tool: " + t.getId() + " Quantity: " + t.qtd());
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Test_tasks_tools.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        
        
        
        // TODO code application logic here
    }
    
}
