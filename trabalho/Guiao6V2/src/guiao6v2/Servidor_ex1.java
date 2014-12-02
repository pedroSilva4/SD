/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guiao6v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Pedro
 */
public class Servidor_ex1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
         ServerSocket ss = new ServerSocket(50000);
        while(true)
        {
             try (Socket cs = ss.accept()) {
                 
                 while(true){
                     InputStreamReader ir = new InputStreamReader(cs.getInputStream());
                     BufferedReader br = new BufferedReader(ir);
                     PrintWriter pw = new PrintWriter(cs.getOutputStream());
                     String s = br.readLine();
                     if(s==null) break;
                   
                            pw.println(s);
                            pw.flush();
                     
                 }
             }
        }
       
        
    }
        catch(IOException ex){ex.printStackTrace();}
    }
    
}
