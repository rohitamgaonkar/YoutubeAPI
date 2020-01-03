package com.Youtube.videodemo.service;

import com.Youtube.videodemo.exception.VideoNotFoundException;
import com.Youtube.videodemo.exception.VideoPersistStorageException;
import com.Youtube.videodemo.model.Video;
import com.Youtube.videodemo.repository.DBVideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static com.Youtube.videodemo.controller.VideoController.viewCount;

@Service
public class DBVideoStorageService {

    @Autowired
    private DBVideoRepository dbFileRepository;

    public Video storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new VideoPersistStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(date);

            //Video dbFile = new Video(fileName, file.getContentType(), file.getBytes());
            //Video dbFile = new Video(fileName, file.getContentType(), file.getBytes());
            Video dbFile = new Video(fileName, file.getContentType(), file.getName(), file.getContentType(), strDate, String.valueOf(0), file.getName()
                    , String.valueOf(file.getSize()), file.getBytes());

            //Optional<Video> videoref = dbFileRepository.findById(dbFile.getId());

            return dbFileRepository.save(dbFile);
            /*if (dbFileRepository.existsById(dbFile.getTitle())) {
                return dbFileRepository.getOne(dbFile.getTitle());
            } else {
                return dbFileRepository.save(dbFile);
            }*/
            /*if(videoref.isPresent())
            {
                Video newEntity = videoref.get();
                newEntity.setViewCount(dbFile.);
                       // .setEmail(entity.getEmail());
                newEntity.setFirstName(entity.getFirstName());
                newEntity.setLastName(entity.getLastName());

                newEntity = repository.save(newEntity);

                return newEntity;
            } else {
                entity = repository.save(entity);

                return entity;
            }*/

        } catch (IOException ex) {
            throw new VideoPersistStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Video getFile(String fileId) {

        Integer fileIdlong = Integer.valueOf(fileId);
        Optional<Video> videoRef = Optional.ofNullable(dbFileRepository.findById(fileIdlong)
                .orElseThrow(() -> new VideoNotFoundException("File not found with id " + fileId)));


        if (videoRef.isPresent()) {
            Video newEntity = videoRef.get();
            newEntity.setViewCount(String.valueOf(viewCount));
            //dbFileRepository.delete(newEntity);
            dbFileRepository.save(newEntity);
        }
        return dbFileRepository.findById(fileIdlong)
                .orElseThrow(() -> new VideoNotFoundException("File not found with id " + fileId));

    }

/*
    public void UpdateEmployee(Video entity)  {
        Optional<Video> videoRef = dbFileRepository.findById(String.valueOf(entity.getId()));//findById(entity.getId());

        if (videoRef.isPresent()) {
            Video newEntity = videoRef.get();
            newEntity.setViewCount(String.valueOf(viewCount));
            newEntity = dbFileRepository.save(newEntity);
      }
      }
*/
}
