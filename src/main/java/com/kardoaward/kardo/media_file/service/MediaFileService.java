package com.kardoaward.kardo.media_file.service;

import com.kardoaward.kardo.event.model.Event;
import com.kardoaward.kardo.user.model.User;
import org.springframework.web.multipart.MultipartFile;

public interface MediaFileService {

    void addLogoToEvent(Event event, MultipartFile file);

    void deleteLogoFromEvent(Event event);

    void deleteEventDirectory(Event event);

    void addAvatarToUser(User user, MultipartFile file);

    void deleteAvatarFromUser(User user);
}
