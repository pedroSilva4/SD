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
}
