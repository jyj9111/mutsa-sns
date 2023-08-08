package com.mutsa.sns.domain.article.entity;

import com.mutsa.sns.domain.comment.entity.Comment;
import com.mutsa.sns.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    // 댓글과 1:N 관계
    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    // 유저와 M:N 관계
    @ManyToMany
    @JoinTable(
            name = "like_article",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likeUsers = new ArrayList<>();



    public static Article newEntity(User user, String title, String content) {
        return Article.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }

    public void updateArticle(String title, String content, List<FeedImage> feedImages) {
        this.title = title;
        this.content = content;
        this.images = new ArrayList<>(feedImages);
    }

    public void setImages(List<FeedImage> images) {
        this.images = new ArrayList<>(images);
    }
    public void setDeletedAt(String time) {
        this.deletedAt = time;
    }
    public void setLikeUsers(User user) {
        this.likeUsers.add(user);
    }

    public List<Long> getImageIdList(List<FeedImage> images) {
        List<Long> imgIdList = new ArrayList<>();
        for (FeedImage image : images) {
            imgIdList.add(image.getId());
        }
        return imgIdList;
    }

}
