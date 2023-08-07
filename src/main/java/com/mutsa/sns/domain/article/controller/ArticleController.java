package com.mutsa.sns.domain.article.controller;

import com.mutsa.sns.domain.article.dto.ArticleResponseDto;
import com.mutsa.sns.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
}
