/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.shared;

import com.warehouse.util.AlreadyLoggedException;
import com.warehouse.util.AlreadyRegisteredException;
import com.warehouse.util.UserNotFoundException;
import com.warehouse.util.WrongPasswordException;

/**
 *
 * @author Pedro
 */
public interface UsersInterface {
     public void login(String username, String password) throws UserNotFoundException, WrongPasswordException, AlreadyLoggedException;
     public void register(String username, String password) throws AlreadyRegisteredException;
     public void logout(String username);
    
}
