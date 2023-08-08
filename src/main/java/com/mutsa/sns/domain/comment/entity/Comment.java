package com.mutsa.sns.domain.comment.entity;

import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.comment.dto.CommentRequestDto;
import com.mutsa.sns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    private String content;

    public static Comment newEntity(User user, Article article, CommentRequestDto dto) {
        return Comment.builder()
                .user(user)
                .article(article)
                .content(dto.getContent())
                .build();
    }
}
