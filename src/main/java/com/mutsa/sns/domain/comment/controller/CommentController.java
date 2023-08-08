package com.mutsa.sns.domain.comment.controller;

import com.mutsa.sns.domain.comment.dto.CommentRequestDto;
import com.mutsa.sns.domain.comment.dto.CommentResponseDto;
import com.mutsa.sns.domain.comment.service.CommentService;
import com.mutsa.sns.global.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 등록
    @PostMapping("/{article_id}")
    public CommentResponseDto create(
            Principal principal,
            @PathVariable("article_id") Long articleId,
            @RequestBody CommentRequestDto dto
    ) {
       return commentService.createComment(principal.getName(), articleId, dto);
    }
}
