/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package guiao6v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Pedro
 */
public class Client_ex1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Socket sc  = new Socket("localhost", 50000);
            BufferedReader br =  new BufferedReader(new InputStreamReader(sc.getInputStream()));
            BufferedReader userBr =  new BufferedReader(new InputStreamReader(System.in));
            PrintWriter pw =new PrintWriter(sc.getOutputStream());
           
            while(true){
                String s = userBr.readLine();
                if(s==null) {break;}
                pw.println(s);
                pw.flush();
                String  response = br.readLine();
                System.out.println("Response : "+response);
              
            }
            sc.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Client_ex1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
