package com.aleksenko.artemii.service;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.Track;

import java.util.List;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 * Service makes asynchronous request for getting track id
 *
 * This asynchronous approach is optional,
 * but we use it to demonstrate how we can work with asynchronous request in this way
 */
public interface TrackService {
    /**
     * Get track list from album ->
     * create asynchronous request on every track from track list ->
     * update their fields with new info from request.
     *
     * @param album album where we get start rack list
     * @return new track list
     */
    List<Track> getTracksIDAsynchronously(Album album);
}
