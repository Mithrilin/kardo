package com.kardoaward.kardo.media_file;

import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class FileManager {

    private final String FOLDER_PATH;

    public FileManager(@Value("${folder.path}") String folderPath) {
        FOLDER_PATH = folderPath;
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
