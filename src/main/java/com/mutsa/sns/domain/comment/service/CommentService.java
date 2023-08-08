package com.mutsa.sns.domain.comment.service;

import com.mutsa.sns.domain.article.entity.Article;
import com.mutsa.sns.domain.article.exception.ArticleNotExist;
import com.mutsa.sns.domain.article.repo.ArticleRepository;
import com.mutsa.sns.domain.comment.dto.CommentRequestDto;
import com.mutsa.sns.domain.comment.dto.CommentResponseDto;
import com.mutsa.sns.domain.comment.entity.Comment;
import com.mutsa.sns.domain.comment.repo.CommentRepository;
import com.mutsa.sns.domain.user.entity.CustomUserDetails;
import com.mutsa.sns.domain.user.entity.User;
import com.mutsa.sns.domain.user.exception.UsernameNotExist;
import com.mutsa.sns.global.common.ResponseDto;
import com.mutsa.sns.global.error.exception.NoAuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserDetailsManager manager;

    // 댓글 등록
    public CommentResponseDto createComment(String username, Long articleId, CommentRequestDto dto) {
        // 등록되어있는 유저인지 확인
        if (!manager.userExists(username)) {
            log.warn("job: comment-create, username: [{}], message: 존재하지 않는 유저", username);
            throw new UsernameNotExist();
        }
        // 확인이 되면 유저정보를 가져옴.
        User user = User.fromUserDetails((CustomUserDetails)manager.loadUserByUsername(username));

        // 게시글 존재 확인
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            log.warn("job: comment-create, id: {}, message: 해당 게시글이 존재하지 않음", articleId);
            throw new ArticleNotExist();
        }
        Article article = optionalArticle.get();

        return CommentResponseDto.fromEntity(
                commentRepository.save(Comment.newEntity(user, article, dto))
        );
    }

    public CommentResponseDto updateComment(
            String username, Long articleId, Long commentId, CommentRequestDto dto
    ) {
        // 게시글 존재 확인
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            log.warn("job: comment-update, id: {}, message: 해당 게시글이 존재하지 않음", articleId);
            throw new ArticleNotExist();
        }
        Article article = optionalArticle.get();

        // 댓글 존재 확인
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            log.warn("job: comment-update, id: {}, message: 해당 댓글이 존재하지 않음", commentId);
        }
        Comment comment = optionalComment.get();

        // 댓글 작성자인지 확인
        if (!comment.getUser().getUsername().equals(username)) {
            log.warn("job: comment-update, username: [{}], message: 잘못된 사용자의 접근", username);
            throw new NoAuthUser();
        }

        comment.setContent(dto);

        return CommentResponseDto.fromEntity(
                commentRepository.save(comment)
        );
    }
}
