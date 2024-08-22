package com.kardoaward.kardo.media_file;

import com.kardoaward.kardo.exception.FileContentException;
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

    public void addAvatarToUser(String oldAvatarPath, MultipartFile file, String path) {

        if (oldAvatarPath != null) {
            deleteFileOrDirectory(oldAvatarPath);
        } else {
            createDirectory(path);
        }

        String filePath = path + file.getOriginalFilename();
        uploadFile(file, filePath);
    }

    public void deleteAvatarFromUser(String path) {
        deleteFileOrDirectory(path);
    }

    public void addLogoToEvent(String oldLogoPath, MultipartFile file, String path) {

        if (oldLogoPath != null) {
            deleteFileOrDirectory(oldLogoPath);
        } else {
            createDirectory(path);
        }

        String filePath = path + file.getOriginalFilename();
        uploadFile(file, filePath);
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
