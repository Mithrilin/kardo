package com.kardoaward.kardo.media_file;

import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.user.model.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@NoArgsConstructor
public class FileManager {

    public void addAvatarToUser(User user, MultipartFile file, String path) {

        if (user.getAvatarPhoto() != null) {
            deleteFileOrDirectory(user.getAvatarPhoto());
        } else {
            createDirectory(path);
        }

        String filePath = path + file.getOriginalFilename();
        uploadFile(file, filePath);
    }

    public void deleteAvatarFromUser(String path) {
        deleteFileOrDirectory(path);
    }

    private void uploadFile(MultipartFile file, String path) {
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            log.error("Не удалось сохранить файл: " + path);
            throw new FileContentException("Не удалось сохранить файл: " + path);
        }
        log.info("Файл сохранён: " + path);
    }

    private void createDirectory(String path) {
        File logoDirectory = new File(path);
        boolean hasDirectoryCreated = logoDirectory.mkdirs();

        if (!hasDirectoryCreated) {
            log.error("Не удалось создать директорию: " + path);
            throw new FileContentException("Не удалось создать директорию: " + path);
        }
        log.info("Создана директория: " + path);
    }

    private void deleteFileOrDirectory(String path) {
        try {
            FileUtils.forceDelete(new File(path));
        } catch (IOException e) {
            log.error("Не удалось удалить файл/директорию: " + path);
            throw new FileContentException("Не удалось удалить файл/директорию: " + path);
        }
        log.info("Удалён файл/директория: " + path);
    }
}
