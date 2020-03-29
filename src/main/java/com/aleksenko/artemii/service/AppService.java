package com.aleksenko.artemii.service;

import com.aleksenko.artemii.model.Album;

/**
 * @author Aleksenko Artemii on 13.03.2020
 * @version 1.0
 */

public interface AppService {
    void createDocs(Album album) ;
    String getHttpResponseAboutAlbum(String artist, String album);
}
