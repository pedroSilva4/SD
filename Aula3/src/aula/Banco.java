/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pedro
 */
public class Banco 
{
    
    private class Conta
    {
        int cliente;
        int saldo = 0 ;
        public Conta(int s)
        {
            cliente  =s;
        }
        
       synchronized  public void consulta()
        {
              System.out.println("Saldo Conta "+cliente+" : "+saldo);
        }
        synchronized public void inc(int val)
        {
            saldo += val;
        }
        synchronized public void dec(int val)
        {
            saldo -= val;
        }
    }

    private Conta[] contas;
    
    public Banco()
    {
        contas = new Conta[100];
        for(int i=0;i<100;i++)
            contas[i] = new Conta(i);
    }
    
     void consulta(int s)
    {
           
              contas[s].consulta();   
    }
    
    void credito(int s,int val)
    {
       
              contas[s].inc(val);
    }
    
     void debito(int s, int val)
    {
        
              contas[s].dec(val);
    }
    
   void transferir(int dest,int src,int val)
   {
       int maior,menor;
     if(dest>src) 
     {
         maior= dest;
         menor=src;
     }
     else
     {
         maior =src; 
         menor =dest;
     }
     
    synchronized(contas[menor]){
        synchronized(contas[maior]){
           debito(src, val);
            credito(dest, val);
                }
        }
   }
}


