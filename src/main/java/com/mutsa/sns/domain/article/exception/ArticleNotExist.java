package com.mutsa.sns.domain.article.exception;

import com.mutsa.sns.global.error.status.Status400Exception;

public class ArticleNotExist extends Status400Exception {
    public ArticleNotExist() {
        super("해당 게시글(피드)가 존재하지 않습니다.");
    }
}
