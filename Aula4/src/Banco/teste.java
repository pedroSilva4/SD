/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Banco;

import Banco.Banco.Account;

/**
 *
 * @author Pedro
 */
public class teste {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException  
    {   final Banco b = new Banco();
        class AccThread extends Thread
        {public void run(){b.createAccount(2500.0f);}}
        class TransThread extends Thread
        {
          Account c1,c2;
        }
        AccThread[] d =  new AccThread[250]; 
       for(int i = 0;i<250;i++)
       {
         d[i] = new AccThread();
         d[i].start();  
         
       }

      for(int i = 0;i<250;i++)
       {
         
         d[i].join();   
       }
       
    
    
    }
    
}
