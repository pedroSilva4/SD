
import java.util.ArrayList;
import java.util.concurrent.locks.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro
 */
class BoundedBuffer {
    int[] array;
    //Lock lock;
    int sizeMax;
    int lugares;
    int nextInsert = 0;
    int nextRemove = 0 ;
    
  
    BoundedBuffer(int size)
    {
        array = new int[size];
        sizeMax = size;
        lugares = size;
        
    }
    
    synchronized void put(int v) throws InterruptedException
    {
        
        while(lugares == 0)
        {
            System.out.println("Esta cheio!!!");
           wait();
        }
        
        array[nextInsert] = v;
        lugares--;
        nextInsert = ((nextInsert + 1) % sizeMax);
      
         /**for(int i:array)
                if(i==1)
                System.out.print("[$]");
            System.out.println("\n");
        
        **/
        notifyAll();
        
    }
    
    synchronized int  get() throws InterruptedException
    {
         
      
        while(lugares == sizeMax)
        {
             System.out.println("Esta Vazio!!!");
            wait();
        }
         int r = array[nextRemove];
        lugares++;
        nextRemove =((nextRemove+1) % sizeMax);
        
        
        
     /*   for(int i:array)
                if(i==1)
                System.out.print("[$]");
            System.out.println("\n");
       
             */
        notifyAll();
    return r;
    
    }
}
