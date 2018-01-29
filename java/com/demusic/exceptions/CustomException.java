package com.demusic.exceptions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gennaro
 */
public class CustomException extends Exception {
    private String x;
    public CustomException(String s){
        this.x=s;
    }
    
    public String toString(){
        return this.x;
    }
}
