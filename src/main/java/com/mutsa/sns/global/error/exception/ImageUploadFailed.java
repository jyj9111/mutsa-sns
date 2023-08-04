package com.mutsa.sns.global.error.exception;

import com.mutsa.sns.global.error.status.Status500Exception;

public class ImageUploadFailed extends Status500Exception {
    public ImageUploadFailed() {
        super("이미지 등록과정에서 문제가 발생하였습니다.");
    }
}
