package com.mutsa.sns.domain.article.exception;

import com.mutsa.sns.global.error.status.Status404Exception;

public class ArticleNotExist extends Status404Exception {
    public ArticleNotExist() {
        super("해당 게시글(피드)가 존재하지 않습니다.");
    }
}
