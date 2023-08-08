package com.mutsa.sns.domain.article.dto;

import lombok.Data;

@Data
public class ArticleLikeResponseDto {
    private Long articleId;
    private String status;

    public ArticleLikeResponseDto(Long articleId, String status) {
        this.articleId = articleId;
        this.status = status;
    }
}
