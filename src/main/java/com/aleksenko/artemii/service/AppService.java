package com.aleksenko.artemii.service;

import com.aleksenko.artemii.model.Album;

/**
 * @author Aleksenko Artemii on 13.03.2020
 * @version 1.0
 * Interface with main services
 */
public interface AppService {
    /**
     * Method makes GET request and gets info about some album
     * @param artist name of artist from user's request
     * @param album name of album from user's request
     * @return JSON info about album
     */
    String getHttpResponseAboutAlbum(String artist, String album);

    /**
     * Method creates document in .doc format
     * @param album default Album POJO
     * @return Album POJO in byte array
     */
    byte[] createDocs(Album album);
}
