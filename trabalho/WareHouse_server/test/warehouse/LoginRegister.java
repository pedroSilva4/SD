/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package warehouse;

import com.warehouse.handlers.ClientHandler;
import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Pedro Silva
 */
public class LoginRegister {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ServerSocket ss = new ServerSocket(50000);
        while(true){
            Socket sc = ss.accept();
            new ClientHandler(sc, new Users(), new Manager()).start();
            
        }
    }
    
    
}
