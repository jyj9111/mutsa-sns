package com.mutsa.sns.domain.article.controller;

import com.mutsa.sns.domain.article.dto.ArticleResponseDto;
import com.mutsa.sns.domain.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping(value = "/feed", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ArticleResponseDto create(
            Principal principal,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("image") List<MultipartFile> images
    ) {
        return articleService.createArticle(principal.getName(), title, content, images);
    }
}
