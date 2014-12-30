/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.handlers;

import com.warehouse.tasks.Manager;
import com.warehouse.users.Users;
import com.warehouse.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Pedro
 */
public class ClientHandler extends Thread {

    private final Socket sc;
    private final WareHouseSkeleton skeleton;
    private final String address;
    String username;
    public Log logger;

    public ClientHandler(Socket sc, Users us, Manager m, Log logger) {
        this.sc = sc;
        skeleton = new WareHouseSkeleton(this, us, m);
        address = sc.getInetAddress().getHostAddress();
        this.logger = logger;
    }

    public void run() {
        try {

            boolean run = true;
            while (run) {

                InputStreamReader ir = new InputStreamReader(sc.getInputStream());
                BufferedReader br = new BufferedReader(ir);
                PrintWriter pw = new PrintWriter(sc.getOutputStream());

                String s = br.readLine();
                if (s == null) {
                    break;
                }

                String response = skeleton.parseMassage(s);

                pw.println(response);
                pw.flush();
            }
            sc.close();
            String r = skeleton.parseMassage("logout:" + username);
            System.out.println("Client " + username + " on : " + address + " disconnected," + r);

        } catch (IOException ex) {
            String r = skeleton.parseMassage("logout:" + username);
            System.out.println("Client " + username + " on : " + address + " disconnected," + r);
        }
    }

}
