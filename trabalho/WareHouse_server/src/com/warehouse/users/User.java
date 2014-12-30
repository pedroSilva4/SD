/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.warehouse.users;

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

}
