/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import util.*;

/**
 *
 * @author bruno
 */
public interface UsersInterface {

    public void login(String username, String password) throws UserNotFoundException, WrongPasswordException, AlreadyLoggedException, IOException;

    public void register(String username, String password) throws AlreadyRegisteredException, IOException;

    public void logout(String username) throws IOException;
}
