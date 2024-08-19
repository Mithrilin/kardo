package com.kardoaward.kardo.media_file.service;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.media_file.MediaFileRepository;
import com.kardoaward.kardo.media_file.enums.FileType;
import com.kardoaward.kardo.media_file.model.MediaFile;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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



    private void createDirectory(String path, Event event) {
        File logoDirectory = new File(path);
        boolean hasDirectoryCreated = logoDirectory.mkdirs();

        if (!hasDirectoryCreated) {
            log.error("Не удалось создать директорию: " + event.getLogo());
            throw new FileContentException("Не удалось создать директорию: " + event.getLogo());
        }
    }
}
