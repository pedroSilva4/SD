/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.util;

import java.io.PrintWriter;

/**
 *
 * @author Pedro
 */
public class Save2FileThread  extends Thread {
    
    
    private final String message;
    private final PrintWriter pw;
    
    public Save2FileThread(String m,PrintWriter pw){
        this.message = m;
        this.pw = pw;
    }
    
    
    @Override
    public void run(){
        pw.println(message);
        pw.flush();
    }
}
