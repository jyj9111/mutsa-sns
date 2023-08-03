package com.mutsa.sns.global.error.status;

public class Status500Exception extends RuntimeException{
    public Status500Exception(String message) {
        super(message);
    }
}
