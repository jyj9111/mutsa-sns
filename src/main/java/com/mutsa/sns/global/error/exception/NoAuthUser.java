package com.mutsa.sns.global.error.exception;

import com.mutsa.sns.global.error.status.Status403Exception;

public class NoAuthUser extends Status403Exception {
    public NoAuthUser() {
        super("권한이 없는 사용자 입니다.");
    }
}
