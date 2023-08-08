package com.mutsa.sns.global.error.status;

public class Status403Exception extends RuntimeException{
    public Status403Exception(String message) {
        super(message);
    }
}
