package com.mutsa.sns.domain.comment.dto;

import com.mutsa.sns.domain.comment.entity.Comment;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String username;
    private String content;

    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();
        dto.setId(comment.getId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setContent(comment.getContent());
        return dto;
    }
}
