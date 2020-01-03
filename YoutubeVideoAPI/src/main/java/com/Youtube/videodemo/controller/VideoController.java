package com.Youtube.videodemo.controller;

import com.Youtube.videodemo.exception.VideoPersistStorageException;
import com.Youtube.videodemo.model.Video;
import com.Youtube.videodemo.payload.UploadVideoResponse;
import com.Youtube.videodemo.service.DBVideoStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.net.HttpURLConnection.*;

@RestController
@Api("Youtube Video API") //For swagger documentation
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
    public static int viewCount;
    @Autowired
    private DBVideoStorageService dbFileStorageService;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "uploadFile file Post Mapping ", httpMethod = "POST",
            response = UploadVideoResponse.class)

    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Successfully uploadFile file."
                    , response = UploadVideoResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "HTTP BAD REQUEST"
                    , response = VideoPersistStorageException.class),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "HTTP INTERNAL ERROR"
                    , response = VideoPersistStorageException.class)
    })
    public UploadVideoResponse uploadFile(@RequestParam("file") MultipartFile file) {
        Video dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(dbFile.getId()))
                .toUriString();

        return new UploadVideoResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    @ApiOperation(value = "Upload Multiple Files file Post Mapping ", httpMethod = "POST",
            response = UploadVideoResponse.class)

    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Successfully Upload Multiple Files"
                    , response = UploadVideoResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "HTTP BAD REQUEST"
                    , response = VideoPersistStorageException.class),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "HTTP INTERNAL ERROR"
                    , response = VideoPersistStorageException.class)
    })
    public List<UploadVideoResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    @ApiOperation(value = "Get file Mapping  ", httpMethod = "GET",
            response = ResponseEntity.class)

    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Get operation file complete."
                    , response = UploadVideoResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "HTTP BAD REQUEST"
                    , response = VideoPersistStorageException.class),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "HTTP INTERNAL ERROR"
                    , response = VideoPersistStorageException.class)
    })
    public ResponseEntity<Resource> watchFileViewCounter(@PathVariable String fileId) {
        viewCount = viewCount + 1;

        // get file from database
        Video dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}
