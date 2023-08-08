package com.mutsa.sns.domain.comment.exception;

import com.mutsa.sns.global.error.status.Status404Exception;

public class CommentNotExist extends Status404Exception {
    public CommentNotExist() {
        super("해당 댓글이 존재하지 않습니다.");
    }
}
