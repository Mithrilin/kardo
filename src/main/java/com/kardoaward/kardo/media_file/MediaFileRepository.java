package com.kardoaward.kardo.media_file;

import com.kardoaward.kardo.media_file.model.MediaFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {
}
