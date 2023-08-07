package com.mutsa.sns.domain.article.repo;

import com.mutsa.sns.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByUserUsername(String username, Pageable pageable);
}
