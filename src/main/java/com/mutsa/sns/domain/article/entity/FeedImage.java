package com.mutsa.sns.domain.article.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "article_images")
public class FeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시글과 N:1관계
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private String imageUrl;

    public static FeedImage newEntity(Article article, String imageUrl) {
        return FeedImage.builder()
                .article(article)
                .imageUrl(imageUrl)
                .build();
    }

}
