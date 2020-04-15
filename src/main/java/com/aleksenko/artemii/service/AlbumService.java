package com.aleksenko.artemii.service;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 * Service gets JSON string about album from request
 */
public interface AlbumService {
    /**
     * Make request and get info about some album
     * @param artist name of artist from user's request
     * @param album name of album from user's request
     * @return JSON info about album
     */
    String getAlbumHttpResponse(String album, String artist);
}
