package com.mutsa.sns.domain.article.dto;

import lombok.Data;

@Data
public class ArticleResponseDto {
    private Long id;
    private String username;
    private String message;

    public ArticleResponseDto(Long id, String username, String message) {
        this.id = id;
        this.username = username;
        this.message = message;
    }
}
