package com.kardoaward.kardo.media_file.service;

import com.kardoaward.kardo.event.model.Event;
import org.springframework.web.multipart.MultipartFile;

public interface MediaFileService {

    Event addLogoToEvent(Event event, MultipartFile file);
}
