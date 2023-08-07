package com.mutsa.sns.domain.article.dto;

import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.article.entity.FeedImage;
import lombok.Data;

@Data
public class ArticleFeedListDto {
    private String username;
    private String title;
    private String firstImgUrl;

    public static ArticleFeedListDto fromEntity(Article article) {
        ArticleFeedListDto dto = new ArticleFeedListDto();
        dto.setUsername(article.getUser().getUsername());
        dto.setTitle(article.getTitle());
        if (article.getImages().isEmpty())
            dto.setFirstImgUrl("/static/feed/base.png");
        else {
            dto.setFirstImgUrl(article.getImages().get(0).getImageUrl());
        }
        return dto;
    }
}
