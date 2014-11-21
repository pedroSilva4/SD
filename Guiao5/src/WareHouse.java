
import java.util.HashMap;
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
public class WareHouse {
    class Produto
    {
      String nome;
        int qtd;
        Condition c;
        
        public Produto(String s, int qtd,Condition c)
        {
            this.c=c;
            this.qtd=qtd;
            nome=s;
        }
    }
    Lock l;
    HashMap<String,Produto> warehouse;
    
    public WareHouse()
    {
        l= new ReentrantLock();
        warehouse =new HashMap<>();
    }
    private Produto get(String s)
    {
        Produto p = warehouse.get(s);
        if(p!=null) return p;
        p =new Produto(s, 0, l.newCondition());
        warehouse.put(s,p);
        return p;
    }
    
    
    void supply(String item, int quantity)
    {
        l.lock();
        try{
           Produto p = get(item);
           p.qtd+=quantity;
           p.c.signalAll();
        }
        finally
        {
            l.unlock();
        }
    }
    void consume(String[] items) throws InterruptedException
    {
        l.lock();
        try{
          for(String s:items){
                Produto p = get(s);
                while(p.qtd==0)
                      p.c.await();
                p.qtd--;
          }
        }
        finally
        {
            l.unlock();
        }
    }
    
    void consume_Comp(String[] items) throws InterruptedException
    {
        l.lock();
        try{
            boolean notReady = true;
            while(notReady){
                 notReady = false;
                 Produto p;
                 for (String item : items) {
                    p = get(item);
                    if(p.qtd==0)
                    {
                        notReady = true;
                        p.c.await();
                        break;
                    }
                 }   
           }
           for(String s:items)
               warehouse.get(s).qtd--;
        }
        finally{
            l.unlock();
        }
    }
    
}
