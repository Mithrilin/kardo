package com.kardoaward.kardo.media_file.service;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.exception.FileContentException;
import com.kardoaward.kardo.media_file.repository.MediaFileRepository;
import com.kardoaward.kardo.media_file.enums.FileType;
import com.kardoaward.kardo.media_file.model.MediaFile;
import com.kardoaward.kardo.user.model.User;
import com.kardoaward.kardo.video_clip.model.VideoClip;
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

        MediaFile logo = createNewMediaFile(file, path, FileType.IMAGE);
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

    @Override
    @Transactional
    public void deleteEventDirectory(Event event) {
        File eventDirectory = new File(FOLDER_PATH + "/events/" + event.getId());

        if (!eventDirectory.exists()) {
            return;
        }

        deleteFileOrDirectory(eventDirectory.getPath());

        if (event.getLogo() != null) {
            MediaFile logo = event.getLogo();
            mediaFileRepository.delete(logo);
            event.setLogo(null);
        }
    }

    @Override
    @Transactional
    public void addAvatarToUser(User user, MultipartFile file) {
        String path = FOLDER_PATH + "/users/" + user.getId() + "/avatar/";

        if (user.getAvatar() != null) {
            deleteFileOrDirectory(user.getAvatar().getFilePath());
            mediaFileRepository.delete(user.getAvatar());
        } else {
            createDirectory(path);
        }

        MediaFile avatar = createNewMediaFile(file, path, FileType.IMAGE);
        uploadFile(file, avatar);
        MediaFile returnedMediaFile = mediaFileRepository.save(avatar);
        user.setAvatar(returnedMediaFile);
    }

    @Override
    @Transactional
    public void deleteAvatarFromUser(User user) {
        String path = FOLDER_PATH + "/events/" + user.getId() + "/logo/";
        deleteFileOrDirectory(path);
        mediaFileRepository.delete(user.getAvatar());
        user.setAvatar(null);
    }

    @Override
    @Transactional
    public void deleteUserDirectory(User user) {
        File userDirectory = new File(FOLDER_PATH + "/users/" + user.getId());

        if (!userDirectory.exists()) {
            return;
        }

        deleteFileOrDirectory(userDirectory.getPath());

        if (user.getAvatar() != null) {
            MediaFile avatar = user.getAvatar();
            mediaFileRepository.delete(avatar);
            user.setAvatar(null);
        }
    }

    @Override
    @Transactional
    public void addVideoClip(VideoClip videoClip, MultipartFile file) {
        String path = FOLDER_PATH + "/users/" + videoClip.getCreator().getId() + "/videos/";
        File directoryForVideo = new File(path);

        if (!directoryForVideo.exists()) {
            createDirectory(path);
        }

        MediaFile video = createNewMediaFile(file, path, FileType.VIDEO);
        uploadFile(file, video);
        MediaFile returnedMediaFile = mediaFileRepository.save(video);
        videoClip.setVideo(returnedMediaFile);
    }

    @Override
    @Transactional
    public void deleteVideo(VideoClip videoClip) {
        MediaFile video = videoClip.getVideo();
        deleteFileOrDirectory(video.getFilePath());
        mediaFileRepository.delete(video);
        videoClip.setVideo(null);
    }

    private MediaFile createNewMediaFile(MultipartFile file, String path, FileType fileType) {
        MediaFile logo = new MediaFile();
        logo.setFileName(file.getOriginalFilename());
        logo.setFileType(fileType);
        logo.setFilePath(path + file.getOriginalFilename());
        return logo;
    }

    private void uploadFile(MultipartFile file, MediaFile mediaFile) {
        try {
            file.transferTo(new File(mediaFile.getFilePath()));
        } catch (IOException e) {
            log.error("Не удалось сохранить файл: " + mediaFile.getFilePath());
            throw new FileContentException("Не удалось сохранить файл: " + mediaFile.getFilePath());
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
