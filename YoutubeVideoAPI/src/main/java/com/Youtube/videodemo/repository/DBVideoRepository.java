package com.Youtube.videodemo.repository;

import com.Youtube.videodemo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DBVideoRepository extends JpaRepository<Video, Integer> {

}
