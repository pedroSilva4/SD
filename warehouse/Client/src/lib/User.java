/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Pedro
 */
public class User {

    private final String username;
    private final String password;

    public User(String name, String pass) {
        username = name;
        password = pass;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public static String convertPassword(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");

            byte[] convertedPassword = digest.digest(s.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < convertedPassword.length; i++) {
                String hex = Integer.toHexString(0xff & convertedPassword[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException ex) {

        }

        return s;
    }

}
