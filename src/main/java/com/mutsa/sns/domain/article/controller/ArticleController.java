package com.mutsa.sns.domain.article.controller;

import com.mutsa.sns.domain.article.dto.ArticleFeedDto;
import com.mutsa.sns.domain.article.dto.ArticleFeedListDto;
import com.mutsa.sns.domain.article.dto.ArticleLikeResponseDto;
import com.mutsa.sns.domain.article.dto.ArticleResponseDto;
import com.mutsa.sns.domain.article.service.ArticleService;
import com.mutsa.sns.global.common.ResponseDto;
import com.oracle.svm.core.annotate.Delete;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    // 게시글(피드) 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto create(
            Principal principal,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("image") List<MultipartFile> images
    ) {
        return articleService.createArticle(principal.getName(), title, content, images);
    }

    // 게시글(피드) 수정
    @PutMapping(value = "/{article_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto update(
            Principal principal,
            @PathVariable("article_id") Long articleId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("delete-img") List<String> deleteList,
            @RequestParam("image") List<MultipartFile> images
    ) {
        return articleService.updateArticle(
                principal.getName(),articleId, title, content, deleteList, images
        );
    }

    // 게시글(피드) 목록 조회
    @GetMapping("/read/{username}")
    public Page<ArticleFeedListDto> readAll(
            @PathVariable("username") String username,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit
    ) {
        return articleService.readAll(username, page, limit);
    }

    // 게시글(피드) 단독 조회
    @GetMapping("/{article_id}")
    public ArticleFeedDto read(
            @PathVariable("article_id") Long articleId
    ) {
        return articleService.readArticle(articleId);
    }

    // 게시글(피드) 삭제
    @DeleteMapping("/{article_id}")
    public ResponseDto delete(
            Principal principal,
            @PathVariable("article_id") Long articleId
    ) {
        return articleService.deleteArticle(principal.getName(), articleId);
    }

    // 게시글(피드) 좋아요 기능
    @PostMapping("/{article_id}/like")
    public ArticleLikeResponseDto updateLike(
            Principal principal,
            @PathVariable("article_id") Long articleId
    ) {
        return articleService.updateLike(principal.getName(), articleId);
    }
}
