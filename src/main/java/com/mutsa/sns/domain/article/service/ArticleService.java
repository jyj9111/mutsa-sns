package com.mutsa.sns.domain.article.service;

import com.mutsa.sns.domain.article.dto.ArticleResponseDto;
import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.article.entity.FeedImage;
import com.mutsa.sns.domain.article.exception.ArticleNotExist;
import com.mutsa.sns.domain.article.repo.ArticleRepository;
import com.mutsa.sns.domain.article.repo.ImageRepository;
import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.entity.User;
import com.mutsa.sns.domain.user.exception.UsernameNotExist;
import com.mutsa.sns.global.error.exception.NoAuthUser;
import com.mutsa.sns.global.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        // 해당 게시글의 이미지 등록
        List<FeedImage> feedImages = new ArrayList<>();
        if (images.get(0).getContentType() != null) {
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
                "피드 등록에 성공하였습니다.",
                article.getImageIdList(article.getImages())
        );
    }

    // 게시글(피드) 수정
    public ArticleResponseDto updateArticle(
            String username,
            Long articleId,
            String title,
            String content,
            List<String> deleteList,
            List<MultipartFile> images
    ) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            log.warn("job: article-update, id: {}, message: 해당 게시글이 존재하지 않음", articleId);
            throw new ArticleNotExist();
        }
        Article article = optionalArticle.get();

        if (!article.getUser().getUsername().equals(username)) {
            log.warn("job: article-update, username:[{}], message: 잘못된 사용자의 접근", username);
            throw new NoAuthUser();
        }

        // 이미지 삭제 리스트가 존재한다면 DB에서 삭제
        if (!deleteList.isEmpty()) {
            for (String id : deleteList) {
                imageRepository.deleteById(Long.valueOf(id));
            }
        }

        // 추가된 이미지 등록
        List<FeedImage> feedImages = new ArrayList<>(article.getImages());

        if (images.get(0).getContentType() != null) {
            log.info("요기");
            for (MultipartFile image : images) {
                String imgUrl = fileHandler.getFeedImgPath(article.getId(), image);
                feedImages.add(imageRepository.save(FeedImage.newEntity(article, imgUrl)));
            }
        }
        article.updateArticle(title, content, feedImages);
        articleRepository.save(article);

        return new ArticleResponseDto(
                article.getId(),
                article.getUser().getUsername(),
                "게시글(피드) 수정을 완료했습니다.",
                article.getImageIdList(article.getImages())
        );
    }
}
