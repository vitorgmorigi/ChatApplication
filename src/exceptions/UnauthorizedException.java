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
public class UnauthorizedException extends Exception {
    private String message;

    public UnauthorizedException(String message) {
        this.message = message;
    }
    
}
