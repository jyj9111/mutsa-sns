package com.mutsa.sns.domain.user.exception;

import com.mutsa.sns.global.error.status.Status400Exception;

public class UsernameIsExist extends Status400Exception {
    public UsernameIsExist() {
        super("이미 존재하는 이름입니다.");
    }
}
