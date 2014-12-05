/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package warehouse;

import com.warehouse.server.WareHouse;

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
        // TODO code application logic here
    }
    
}
