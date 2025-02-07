package com.stream.service;

import com.stream.model.Video;
import com.stream.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;

    //  Publish a new video
    public Video publishVideo(Video video) {
        return videoRepository.save(video);
    }

    //  Get video by ID
    public Optional<Video> getVideo(UUID id) {
        return videoRepository.findById(id);
    }

    //  List only available (non-deleted) videos
    public List<Video> listVideos() {
        return videoRepository.findByIsActiveTrue();
    }

    //  Search videos by director
    public List<Video> searchByDirector(String director) {
        return videoRepository.findByDirectorContainingIgnoreCase(director);
    }

    //  Edit Video Metadata
    public Video updateVideo(UUID id, Video updatedData) {
        return videoRepository.findById(id).map(existingVideo -> {
            existingVideo.setTitle(updatedData.getTitle());
            existingVideo.setSynopsis(updatedData.getSynopsis());
            existingVideo.setDirector(updatedData.getDirector());
            existingVideo.setCast(updatedData.getCast());
            existingVideo.setYearOfRelease(updatedData.getYearOfRelease());
            existingVideo.setGenre(updatedData.getGenre());
            existingVideo.setRunningTime(updatedData.getRunningTime());
            return videoRepository.save(existingVideo);
        }).orElseThrow(() -> new RuntimeException("Video not found"));
    }

    //  Soft delete video
    public void delistVideo(UUID id) {
        videoRepository.findById(id).ifPresent(video -> {
            video.setActive(false);
            videoRepository.save(video);
        });
    }
}