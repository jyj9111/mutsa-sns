package com.mutsa.sns.global.util;

import com.mutsa.sns.domain.article.entity.FeedImage;
import com.mutsa.sns.global.error.exception.ImageUploadFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class FileHandler {

    public String getProfileImgPath(String username, MultipartFile profileImg) {
        String imgName = LocalDateTime.now().toString()
                .replace(":","").replace("-","").replace(".","");
        String extension = "." + profileImg.getOriginalFilename().split("\\.")[1];
        String filename = imgName + extension;
        String imgDir = String.format("./images/profile/%s", username);

        try {
            Files.createDirectories(Paths.get(imgDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ImageUploadFailed();
        }

        File file = new File(Path.of(imgDir, filename).toUri());

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(profileImg.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ImageUploadFailed();
        }

        log.debug("[{}]: 이미지 등록 성공", username);
        return String.format("/static/profile/%s/%s", username, filename);
    }

    public String getFeedImgPath(Long id, MultipartFile image) {
        String imgName = LocalDateTime.now().toString()
                .replace(":","").replace("-","").replace(".","");
        String extension = "." + image.getOriginalFilename().split("\\.")[1];
        String filename = imgName + extension;
        String imgDir = String.format("./images/feed/%d", id);

        try {
            Files.createDirectories(Paths.get(imgDir));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ImageUploadFailed();
        }

        File file = new File(Path.of(imgDir, filename).toUri());

        try (OutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(image.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ImageUploadFailed();
        }

        log.debug("job: article-image-register, article_id: [{}], message: 게시글 이미지등록 성공", id);
        return String.format("/static/feed/%d/%s", id, filename);
    }
}
