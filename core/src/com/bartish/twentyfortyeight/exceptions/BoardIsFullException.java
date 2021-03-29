package com.bartish.twentyfortyeight.exceptions;

public class BoardIsFullException extends RuntimeException{
    public BoardIsFullException(String s) {
        super(s);
    }
}
