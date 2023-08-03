package com.mutsa.sns.domain.user.exception;

import com.mutsa.sns.global.error.status.Status400Exception;

public class PasswordDuplicateCheckFail extends Status400Exception {
    public PasswordDuplicateCheckFail() {
        super("비밀번호와 비밀번호확인이 일치하지 않습니다.");
    }
}
