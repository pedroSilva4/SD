/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Pedro
 */
public class Task {
    
    private final String task_id;
    private final List<Tool> tools;
    
    
    public Task(String tsk_id,List<Tool> obj)
    {
        task_id = tsk_id;
        tools  = new ArrayList<>(obj);
    }
}
    
