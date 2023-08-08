package com.mutsa.sns.domain.article.dto;

import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.article.entity.FeedImage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleFeedDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private List<String> imgUrlList;

    public static ArticleFeedDto fromEntity(Article article) {
        ArticleFeedDto dto = new ArticleFeedDto();
        dto.setId(article.getId());
        dto.setUsername(article.getUser().getUsername());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());

        List<String> imgUrlList = new ArrayList<>();
        if (article.getImages().isEmpty())
            imgUrlList.add("/static/feed/base.png");
        else {
            for (FeedImage feedImage : article.getImages()) {
                imgUrlList.add(feedImage.getImageUrl());
            }
        }

        dto.setImgUrlList(new ArrayList<>(imgUrlList));

        return dto;
    }
}
