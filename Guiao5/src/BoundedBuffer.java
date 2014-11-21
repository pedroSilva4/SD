
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro
 */
public class BoundedBuffer {
    int[] array;
  
    int sizeMax;
    int lugares;
    int nextInsert = 0;
    int nextRemove = 0 ;
    Lock l;
    Condition c_NotEmpty,c_NotFull;
  
    BoundedBuffer(int size)
    {
        array = new int[size];
        sizeMax = size;
        lugares = size;
        l=new ReentrantLock();
        c_NotEmpty = l.newCondition();
        c_NotFull = l.newCondition();
        
    }
    
     void put(int v) throws InterruptedException
    {
        l.lock();
        try{
            while(lugares == 0)
            {
                 c_NotFull.await();
            }
        
            array[nextInsert] = v;
            lugares--;
            nextInsert = ((nextInsert + 1) % sizeMax);
            c_NotEmpty.signal();
     
        }
        finally
        {
            l.unlock();
        }
    }
    
  int  get() throws InterruptedException
    {
      int r;
      l.lock();
      try{
        while(lugares == sizeMax)
        {
            c_NotEmpty.await();
        }
        
        r = array[nextRemove];
        lugares++;
        nextRemove =((nextRemove+1) % sizeMax);        
        c_NotFull.signal();
      }
      finally 
      {
          l.unlock();
      }
     
      return r;
    
    }
}
