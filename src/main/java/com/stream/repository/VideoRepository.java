package com.stream.repository;

import com.stream.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    List<Video> findByIsActiveTrue();
    List<Video> findByDirectorContainingIgnoreCase(String director);
}
