/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

/**
 *
 * @author Pedro
 */
public class Tool {

    private final String tool_name;
    private int quantity;
    private boolean returnable;

    public Tool(String tl_name, int qtt, boolean returnable) {
        tool_name = tl_name;
        quantity = qtt;
        this.returnable = returnable;
    }
}
