package com.mutsa.sns.domain.article.repo;

import com.mutsa.sns.domain.article.entity.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<FeedImage, Long> {
}
