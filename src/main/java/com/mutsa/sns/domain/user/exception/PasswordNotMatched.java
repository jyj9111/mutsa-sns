package com.mutsa.sns.domain.user.exception;

import com.mutsa.sns.global.error.status.Status400Exception;

public class PasswordNotMatched extends Status400Exception {
    public PasswordNotMatched() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
