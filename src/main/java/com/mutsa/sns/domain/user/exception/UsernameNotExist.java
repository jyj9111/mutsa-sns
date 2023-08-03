package com.mutsa.sns.domain.user.exception;

import com.mutsa.sns.global.error.status.Status400Exception;

public class UsernameNotExist extends Status400Exception {
    public UsernameNotExist() {
        super("존재하지 않는 이름입니다.");
    }
}
