/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

import java.util.concurrent.locks.Condition;

/**
 *
 * @author Pedro
 */
public class Tool {
    
    private final Condition tl_condition;
    private final String tool_name;
    private int quantity;
  
    public Tool(String tl_name,int qtt,Condition c)
    {
        tool_name  =tl_name;
        quantity = qtt;
        tl_condition = c;
    }
    
    public Tool(String tl_name,Condition c)
    {
        tool_name = tl_name;
        quantity = 0;
         tl_condition = c;
    }
    
    public void await() throws InterruptedException
    {
        this.tl_condition.await();
    }
    
    public void signal()
    {
        this.tl_condition.signal();
    }
    
    public void signalAll()
    {
        this.tl_condition.signalAll();
    }
    
    public int qtd()
    {
        return this.quantity;
    }
    
    public void dec(int qtd)
    {
        this.quantity-=qtd;
    }
    
    public void inc(int qtd)
    {
        this.quantity+=qtd;
    }
}
