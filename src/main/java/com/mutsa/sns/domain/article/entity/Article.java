package com.mutsa.sns.domain.article.entity;

import com.mutsa.sns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유저와 N:1 관계
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String content;
    private String deletedAt;

    // 이미지와 1:N 관계
    @OneToMany(mappedBy = "article")
    private List<FeedImage> images;

    public static Article newEntity(User user, String title, String content) {
        return Article.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }

    public void setImages(List<FeedImage> images) {
        this.images = images;
    }
}
