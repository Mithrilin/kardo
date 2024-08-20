package com.kardoaward.kardo.media_file.service;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.media_file.MediaFileRepository;
import com.kardoaward.kardo.media_file.enums.FileType;
import com.kardoaward.kardo.media_file.model.MediaFile;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
public class MediaFileServiceImpl implements MediaFileService {

    private final MediaFileRepository mediaFileRepository;

    private final String FOLDER_PATH;

    public MediaFileServiceImpl(MediaFileRepository mediaFileRepository,
                                @Value("${folder.path}") String folderPath) {
        this.mediaFileRepository = mediaFileRepository;
        this.FOLDER_PATH = folderPath;
    }

    @Override
    @Transactional
    public void addLogoToEvent(Event event, MultipartFile file) {
        String path = FOLDER_PATH + "/events/" + event.getId() + "/logo/";

        if (event.getLogo() != null) {
            deleteFileOrDirectory(event.getLogo().getFilePath());
            mediaFileRepository.delete(event.getLogo());
        } else {
            createDirectory(path);
        }

        MediaFile logo = createNewMediaFile(file, path);
        uploadFile(file, logo);
        MediaFile returnedMediaFile = mediaFileRepository.save(logo);
        event.setLogo(returnedMediaFile);
    }

    @Override
    @Transactional
    public void deleteLogoFromEvent(Event event) {
        String path = FOLDER_PATH + "/events/" + event.getId() + "/logo/";
        deleteFileOrDirectory(path);
        mediaFileRepository.delete(event.getLogo());
        event.setLogo(null);
    }

    private MediaFile createNewMediaFile(MultipartFile file, String path) {
        MediaFile logo = new MediaFile();
        logo.setFileName(file.getOriginalFilename());
        logo.setFileType(FileType.IMAGE);
        logo.setFilePath(path + file.getOriginalFilename());
        return logo;
    }

    private void uploadFile(MultipartFile file, MediaFile logo) {
        try {
            file.transferTo(new File(logo.getFilePath()));
        } catch (IOException e) {
            log.error("Не удалось сохранить файл: " + logo.getFilePath());
            throw new FileContentException("Не удалось сохранить файл: " + logo.getFilePath());
        }
    }

    private void createDirectory(String path) {
        File logoDirectory = new File(path);
        boolean hasDirectoryCreated = logoDirectory.mkdirs();

        if (!hasDirectoryCreated) {
            log.error("Не удалось создать директорию: " + path);
            throw new FileContentException("Не удалось создать директорию: " + path);
        }
    }

    private void deleteFileOrDirectory(String path) {
        try {
            FileUtils.forceDelete(new File(path));
        } catch (IOException e) {
            log.error("Не удалось удалить файл/директорию: " + path);
            throw new FileContentException("Не удалось удалить файл/директорию: " + path);
        }
    }
}
