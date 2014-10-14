/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aula;

import java.util.ArrayList;

/**
 *
 * @author Pedro
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
       
        
        
        Banco banco =  new Banco();
        ThreadBanc[] threads = new ThreadBanc[100];
        transBanc[] transf = new transBanc[50];
        for(int i =0 ; i<100;i++)
        {
            threads[i] = new ThreadBanc(banco, i);
            threads[i].start();
        }
           
        for(int i =0 ; i<100;i++)
        {
               threads[i].join();
        }
        int i = 0;
        for(int j=0 ; j<50;j++)
        {
            
            transf[j] =new transBanc(banco, i,(i+1));
            transf[j].start();
            i+=2;
        }
         for(int j =0 ; j<50;j++)
        {
             transf[j].join();
        }
        
        for(int j=0;j<100;j++)
        {
            banco.consulta(j);
        }
    }
     
} 

  class ThreadBanc extends Thread
        {
            Banco banco;
            int nThread;
            public ThreadBanc(Banco b, int i)
            {
                banco = b;
                nThread = i;
            }
            @Override
            public void run()
            {
                banco.credito(nThread, 1000);
              //  banco.consulta(nThread);
            }
        }

class transBanc extends Thread
{
    Banco banco;
    int Conta1;
    int Conta2;
    public transBanc(Banco b, int a,int c)
    {
        banco = b;
        Conta1 = a;
        Conta2 = c;
    }
    public void run()
    {
        banco.transferir((int)(Math.random()*100),(int)(Math.random()*100) ,(int)(Math.random()*500));
    }
}
        
    
    

