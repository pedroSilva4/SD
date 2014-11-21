/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

/**
 *
 * @author Pedro
 */
public class Tool {
    
    private final String tool_name;
    private int quantity;
  
    public Tool(String tl_name,int qtt)
    {
        tool_name  =tl_name;
        quantity = qtt;
    }
    
    public Tool(String tl_name)
    {
        tool_name = tl_name;
        quantity = 0;
    }
}
