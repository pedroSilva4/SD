
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        // TODO code application logic here
        
        ServerSocket ss = new ServerSocket(50000);
        while(true)
        {
            Socket cs  = ss.accept();
            boolean flag = true;
            while(flag){
            InputStreamReader ir = new InputStreamReader(cs.getInputStream());
            BufferedReader br = new BufferedReader(ir);
            PrintWriter pw = new PrintWriter(cs.getOutputStream());
            String s = br.readLine();
            if(s == null)flag =false;
            pw.print(s);
            pw.flush();
            }
        }
        
    }
    
}
