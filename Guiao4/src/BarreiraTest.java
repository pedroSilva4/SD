
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
public class BarreiraTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
    
    final Barreira b =  new Barreira(5);
    
    class barT extends Thread
    {
        public void run()
        {
            try {
                
            
              
                b.espera();
            
               System.out.println("ja esperei");
               
                b.espera();
            
               System.out.println("ja esperei");
           
            } catch (InterruptedException ex) {
                Logger.getLogger(BarreiraTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    int i = 0;
    barT[] ts = new barT[5];
    while(i<5){
        ts[i] = new barT();
        ts[i].start();
        i++;
    }
    i=0;
    while(i<5){
        
        ts[i].join();
    }
    
    return  ;
    }
    
}
