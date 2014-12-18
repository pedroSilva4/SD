/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.warehouse.handlers;

import com.warehouse.server.Manager;
import com.warehouse.users.Users;

/**
 *
 * @author Pedro
 */
class WareHouseSkeleton {
    Users users;
    Manager manager;

    WareHouseSkeleton(Users us, Manager m) {
       this.users = us;
       this.manager = m;
    }
    
    
    public String parseMassage(String m)
    {
     return null;   
    }
}
