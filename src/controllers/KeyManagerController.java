/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

/**
 *
 * @author vitor
 */
public class KeyManagerController {
    private static KeyManagerController instance;
    
    public static KeyManagerController getInstance() {
        if (instance == null) {
            instance = new KeyManagerController();
        }
        return instance;
    }
    
    public void create(String password) {
        
    }
    
    public void load(String path) {
        
    }
}
