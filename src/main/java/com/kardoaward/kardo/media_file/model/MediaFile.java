package com.kardoaward.kardo.media_file.model;

import com.kardoaward.kardo.media_file.enums.FileType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "media_files")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MediaFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    private FileType fileType;
    @Column(name = "file_path")
    private String filePath;
}
