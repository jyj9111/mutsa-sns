package com.mutsa.sns.domain.article.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleResponseDto {
    private Long id;
    private String username;
    private String message;
    private List<Long> imageIdList;

    public ArticleResponseDto(Long id, String username, String message, List<Long> imageIdList) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.imageIdList = imageIdList;
    }
}
