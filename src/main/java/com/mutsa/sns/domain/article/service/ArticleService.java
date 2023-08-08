package com.mutsa.sns.domain.article.service;

import com.mutsa.sns.domain.article.dto.ArticleFeedDto;
import com.mutsa.sns.domain.article.dto.ArticleFeedListDto;
import com.mutsa.sns.domain.article.dto.ArticleLikeResponseDto;
import com.mutsa.sns.domain.article.dto.ArticleResponseDto;
import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.article.entity.FeedImage;
import com.mutsa.sns.domain.article.exception.ArticleNotExist;
import com.mutsa.sns.domain.article.repo.ArticleRepository;
import com.mutsa.sns.domain.article.repo.ImageRepository;
import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.entity.User;
import com.mutsa.sns.domain.user.exception.UsernameNotExist;
import com.mutsa.sns.domain.user.repo.UserRepository;
import com.mutsa.sns.global.common.ResponseDto;
import com.mutsa.sns.global.error.exception.NoAuthUser;
import com.mutsa.sns.global.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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
    private final UserRepository userRepository;

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

    // 게시글(피드) 목록 조회
    public Page<ArticleFeedListDto> readAll(String username, Integer page, Integer limit) {
        if (!manager.userExists(username)) {
            log.warn("job: article-readAll, username:[{}], message: 존재하지 않는 유저", username);
            throw new UsernameNotExist();
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
        Page<Article> articlePage = articleRepository.findAllByUserUsername(username, pageable);
        Page<ArticleFeedListDto> dtoPage = articlePage.map(ArticleFeedListDto::fromEntity);
        return dtoPage;
    }

    // 게시글(피드) 단독 조회
    public ArticleFeedDto readArticle(Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            log.warn("job: article-read, article_id: {}, message: 존재하지 않는 게시글(피드)", articleId);
            throw new ArticleNotExist();
        }
        return ArticleFeedDto.fromEntity(optionalArticle.get());
    }

    public ResponseDto deleteArticle(String username, Long articleId) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            log.warn("job: article-delete, article_id: {}, message: 존재하지 않는 게시글(피드)", articleId);
            throw new ArticleNotExist();
        }

        Article article = optionalArticle.get();

        if (!article.getUser().getUsername().equals(username)) {
            log.warn("job: article-update, username:[{}], message: 잘못된 사용자의 접근", username);
            throw new NoAuthUser();
        }

        // 삭제한 시간 기록
        String now = LocalDateTime.now().toString().split("\\.")[0];
        article.setDeletedAt(now);
        articleRepository.save(article);

        // 이미지 삭제
        for (FeedImage feedImage : article.getImages()) {
            imageRepository.deleteById(feedImage.getId());
        }

        return new ResponseDto("해당 게시글(피드)가 삭제되었습니다.");
    }

    // 게시글(피드) 좋아요 상태 변경
    public ArticleLikeResponseDto updateLike(String username, Long articleId) {
        // 게시글 존재 확인
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            log.warn("job: article-like, article_id: {}, message: 존재하지 않는 게시글(피드)", articleId);
            throw new ArticleNotExist();
        }
        Article article = optionalArticle.get();

        // 등록되어있는 유저인지 확인
        if (!manager.userExists(username)) {
            log.warn("job: article-like, username: [{}], message: 존재하지 않는 유저", username);
            throw new UsernameNotExist();
        }
        // 확인이 되면 유저정보를 가져옴.
        User user = User.fromUserDetails((CustomUserDetails)manager.loadUserByUsername(username));

        // 게시글 작성자는 좋아요를 누를수 없다.
        if (article.getUser().getUsername().equals(username)) {
            log.warn("job: article-like, username: [{}], message: 잘못된 사용자의 접근", username);
            throw new NoAuthUser();
        }

        Article updateArticle;
        String status;
        // 이미 좋아요가 되있는 경우
        if (checkLike(username, article)) {
            article.getLikeUsers().remove(user);
            updateArticle = articleRepository.save(article);
            if (user.getLikeArticles() == null) {
                user.setLikeArticles(new ArrayList<>());
            } else {
                user.getLikeArticles().remove(article);
            }
            userRepository.save(user);
            status = "null";
        }
        // 좋아요가 안되어있는 경우
        else {
            article.setLikeUsers(user);
            updateArticle = articleRepository.save(article);
            if (user.getLikeArticles() == null) {
                List<Article> articles = new ArrayList<>();
                articles.add(article);
                user.setLikeArticles(articles);
            } else {
                user.getLikeArticles().add(article);
            }
            userRepository.save(user);

            status = "like";
        }

        return new ArticleLikeResponseDto(updateArticle.getId(), status);
    }

    private boolean checkLike(String username, Article article) {
        for (User user : article.getLikeUsers()) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }
}
