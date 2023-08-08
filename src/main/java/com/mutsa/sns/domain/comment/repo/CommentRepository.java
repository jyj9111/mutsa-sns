package com.mutsa.sns.domain.comment.repo;

import com.mutsa.sns.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
