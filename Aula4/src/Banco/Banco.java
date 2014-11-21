/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package Banco;


import java.util.*;
import java.util.concurrent.locks.*;
/**
 *
 * @author Pedro
 */
public class Banco {

     static class InvalidAccount extends Exception {

        public InvalidAccount() {
         super();
         
        }
    }

    static class NotEnoughFunds extends Exception {

        public NotEnoughFunds() {
            super();
        }
    }
    public class Account
    {
        int cliente;
        float saldo;
        Lock accountLock;
        public Account(int c,float s)
        {
            cliente  =c;
            saldo = s;
            accountLock = new ReentrantLock();
        }
        
        public void consult()
        {
              System.out.println("Saldo Conta "+cliente+" : "+saldo);
        }
       public void inc(float val)
        {
            saldo += val;
        }
       public void dec(float val) throws NotEnoughFunds
        {
            
            if(saldo-val < 0) {
                throw new NotEnoughFunds();
            }else
            saldo -= val;
        }
    }
   
   int proxAcc;
   HashMap<Integer,Account> accounts;
   Lock bankLock; 
   
   public Banco()
   {
       proxAcc = 1 ;
       accounts = new HashMap<>();
       bankLock = new ReentrantLock();
   }
   
   int createAccount(float initialbalance)
   {
       
       Account c = new Account(proxAcc,initialbalance);
       int id;
       bankLock.lock();
     try{
       id = proxAcc;
       accounts.put(id,c);
       proxAcc++;
       bankLock.unlock();
       System.out.println("Conta "+id+" saldo : "+initialbalance);
       return id;
       }
       finally{
       bankLock.unlock(); 
       }
      
   }
   Account get(int id) throws InvalidAccount
   {
       Account c = accounts.get(id);
       if(c==null) throw new InvalidAccount();
       return c;
   }
   
   float closeAccount(int id) throws InvalidAccount
   {
       Account c;
       float bal;
       bankLock.lock();
      try{
            c= get(id);
           
            accounts.remove(id);
             c.accountLock.lock();
         }
      finally{
          bankLock.unlock();
        }
      try{
                bal= c.saldo;
                return bal;
         }
      finally{
                c.accountLock.unlock();
             }
      
   }
   
void transfer(int from, int to, float amount) throws InvalidAccount, NotEnoughFunds
    {    Account src,dest;
        bankLock.lock();
        try
        {
            
            src = get(from);
            dest= get(to);
            if(from < to){
                 src.accountLock.lock();
                 dest.accountLock.lock();
            }
            else
            {
                dest.accountLock.lock();
                src.accountLock.lock();
            }
        }
        finally{
                bankLock.unlock();
               }
        try
           {
            src.dec(amount);
            dest.inc(amount);
           }
           finally{
            src.accountLock.unlock();
            dest.accountLock.unlock();
           }
    }
    float totalBalance(int accts[]) throws InvalidAccount
    {
        float total = 0.0f;
        Account[] acs =new Account[accts.length]; 
        accts = accts.clone();
        Arrays.sort(accts);  
        bankLock.lock();
            try
            {
                for(int i = 0 ; i< accts.length;i++)
                {
                    acs[i] = get(accts[i]);
                }
                 for(int i = 0 ; i< accts.length;i++)
                {
                    acs[i].accountLock.lock();
                }
                
            }
            finally
            {
            bankLock.unlock();
            }
            for(int i = 0 ; i< accts.length;i++){
                 total += acs[i].saldo;
                  acs[i].accountLock.unlock();
             }
        
        return total;
    }
   
}
   

