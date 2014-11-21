
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro
 */
public class TesteClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
       final BoundedBuffer bb = new BoundedBuffer(5);
    
       
       
    
        class Produtor extends Thread
        {
            int name;
            
            public Produtor (int n)
            {
                name = n;
            }
            private synchronized void produz() throws InterruptedException
            {
                    wait(400);   
            }
            
            public  void run()
            {
                 while(true)
                   {
                     try {
                         produz();
                         bb.put(1);
                         
                     } catch (InterruptedException ex) {
                         Logger.getLogger(TesteClass.class.getName()).log(Level.SEVERE, null, ex);
                     }
                   }
            }
        }
        class Consumidor extends Thread
        {
            int name;
            public Consumidor(int i)
            {
                name =i;
            }
             private synchronized void consome() throws InterruptedException
            {
              wait(400);
            }
            public void run()
            {
                while(true)
                {
                    try {
                        bb.get();
                        consome();
                       
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TesteClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        
        
        Produtor p = new Produtor(1);
       Produtor p2 = new Produtor(2);
        Consumidor c  = new Consumidor(1);
        Consumidor c2 = new Consumidor(2);
        
       p.start();
       p2.start();
       c.start();
       c2.start();
       p.join();
       p2.join();
       c.join();
       c2.join();
       
    }
}
