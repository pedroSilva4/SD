/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula;
import java.util.*;



/**
 *
 * @author Pedro
 */
public class Aula2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException 
    { 
       Counter c = new Counter();
       ArrayList<ThreadTest> arr  = new ArrayList<>();
       for(int n = 0;n< 31;n++)
       {
           arr.add(new ThreadTest(n*100+1,c));
       }
       
       for(ThreadTest t:arr)
       {
           t.start();
       //  t.join();
       }
       for(ThreadTest t:arr)
       {
           t.join();
       }
       
       System.out.println("ACABOU : Counter "+c.count);
       
       
    }
    
}
