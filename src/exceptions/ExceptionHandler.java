/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author vitor
 */
public class ExceptionHandler extends Exception {
    private String message;

    public ExceptionHandler(String message) {
        this.message = message;
    }
    
}
