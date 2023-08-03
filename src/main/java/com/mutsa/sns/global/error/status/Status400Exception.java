package com.mutsa.sns.global.error.status;

public class Status400Exception extends RuntimeException{
    public Status400Exception(String message) {
        super(message);
    }
}
