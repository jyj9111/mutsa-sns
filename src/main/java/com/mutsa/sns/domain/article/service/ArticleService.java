package com.mutsa.sns.domain.article.service;

import com.mutsa.sns.domain.article.dto.ArticleResponseDto;
import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.article.entity.FeedImage;
import com.mutsa.sns.domain.article.repo.ArticleRepository;
import com.mutsa.sns.domain.article.repo.ImageRepository;
import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.entity.User;
import com.mutsa.sns.domain.user.exception.UsernameNotExist;
import com.mutsa.sns.global.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final FileHandler fileHandler;
    private final UserDetailsManager manager;
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;

    // 게시글(피드) 등록
    public ArticleResponseDto createArticle(
            String username, String title, String content, List<MultipartFile> images
    ) {
        // 등록되어있는 유저인지 확인
        if (!manager.userExists(username)) {
            log.warn("job: article-create, username: [{}], message: 존재하지 않는 유저", username);
            throw new UsernameNotExist();
        }
        // 확인이 되면 유저정보를 가져옴.
        User user = User.fromUserDetails((CustomUserDetails)manager.loadUserByUsername(username));

        // 새로운 article을 DB에 저장
        Article newArticle = articleRepository.save(Article.newEntity(user, title, content));

        List<FeedImage> feedImages = new ArrayList<>();
        if (images == null) {
            String imgUrl = "/static/feed/base.png";
            feedImages.add(imageRepository.save(FeedImage.newEntity(newArticle, imgUrl)));
        }
        else {
            for (MultipartFile image : images) {
                String imgUrl = fileHandler.getFeedImgPath(newArticle.getId(), image);
                feedImages.add(imageRepository.save(FeedImage.newEntity(newArticle, imgUrl)));
            }
        }

        newArticle.setImages(feedImages);
        Article article = articleRepository.save(newArticle);

        return new ArticleResponseDto(
                article.getId(),
                article.getUser().getUsername(),
                "피드 등록에 성공하였습니다."
        );
    }
}
