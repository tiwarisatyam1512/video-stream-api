package com.stream.repository;

import com.stream.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing video metadata in the database.
 * <p>
 * This repository provides CRUD operations for the {@link Video} entity
 * and includes custom query methods for filtering active videos and
 * searching videos by director.
 * <p>
 * Extends {@link JpaRepository} to leverage Spring Data JPA functionalities.
 */
public interface VideoRepository extends JpaRepository<Video, UUID> {

    /**
     * Retrieves a list of videos that are currently active.
     * <p>
     * This method filters out videos that have been soft deleted.
     *
     * @return A list of active videos.
     */
    List<Video> findByIsActiveTrue();

    /**
     * Searches for videos by a director's name, ignoring case sensitivity.
     * <p>
     * This method finds all videos where the director's name contains the given keyword.
     *
     * @param director The partial or full name of the director.
     * @return A list of videos directed by the specified director.
     */
    List<Video> findByDirectorContainingIgnoreCase(String director);
}
