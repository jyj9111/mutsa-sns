package com.mutsa.sns.domain.user.entity;

import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.user.dto.UserRegisterDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String password;

    private String email;
    private String phone;
    private String profileImg;

    // 피드와 1:N 관계
    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    // 피드와 M:N 관계
    @ManyToMany
    @JoinTable(
            name = "like_article",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private List<Article> likeArticles = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "following"),
            inverseJoinColumns = @JoinColumn(name = "follower")
    )
    private List<User> followings = new ArrayList<>();

    @ManyToMany(mappedBy = "followings")
    private List<User> followers =  new ArrayList<>();




    public static User fromUserDetails(CustomUserDetails userDetails) {
        return User.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .email(userDetails.getEmail())
                .phone(userDetails.getPhone())
                .profileImg(userDetails.getProfileImg())
                .build();

    }

    public void updateProfileImg(String imgUrl) {
        this.profileImg = imgUrl;
    }
    public void setLikeArticles(List<Article> articles) {
        this.likeArticles = new ArrayList<>(articles);
    }
    public void setFollowings(List<User> followings) {
        this.followings = new ArrayList<>(followings);
    }

    public void setFollowers(List<User> followers) {
        this.followers = new ArrayList<>(followers);
    }

}
