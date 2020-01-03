package com.Youtube.videodemo.model;


import io.swagger.annotations.ApiModel;

import javax.persistence.*;

@Entity
@Table(name = "files")
@ApiModel
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String fileid;

    private String fileName;


    private String fileType;

    private String title;


    private String Description;
    private String UploadDateTime;
    private String ViewCount;
    private String CreatorID;
    private String VideoLength;

    @Lob
    private byte[] data;

    public Video() {

    }

    public Video(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public Video(String fileName, String fileType, String title, String description, String uploadDateTime, String viewCount, String creatorID, String videoLength, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.title = title;
        this.Description = description;
        this.UploadDateTime = uploadDateTime;
        this.ViewCount = viewCount;
        this.CreatorID = creatorID;
        this.VideoLength = videoLength;
        this.data = data;
        this.fileid = fileName;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public Integer getId() {
        return id;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUploadDateTime() {
        return UploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        UploadDateTime = uploadDateTime;
    }

    public String getViewCount() {
        return ViewCount;
    }

    public void setViewCount(String viewCount) {
        ViewCount = viewCount;
    }

    public String getCreatorID() {
        return CreatorID;
    }

    public void setCreatorID(String creatorID) {
        CreatorID = creatorID;
    }

    public String getVideoLength() {
        return VideoLength;
    }

    public void setVideoLength(String videoLength) {
        VideoLength = videoLength;
    }
}
