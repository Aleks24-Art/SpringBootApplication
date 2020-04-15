package com.aleksenko.artemii.service;

import com.aleksenko.artemii.util.UtilURIGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 */
@Service
public class AlbumRequest implements AlbumService {

    /**
     * To build URL for request
     */
    private final UtilURIGenerator uriGenerator;
    /**
     * To make request
     */
    private final Request request;

    /**
     * Default constructor with all beans autowiring
     * @param uriGenerator uriGenerator bean
     * @param request request bean
     */
    @Autowired
    public AlbumRequest(UtilURIGenerator uriGenerator, Request request) {
        this.uriGenerator = uriGenerator;
        this.request = request;
    }

    @Override
    public String getAlbumHttpResponse(String album, String artist) {
        return request.getHttpResponse(uriGenerator.generateURI(artist, album, "album"));
    }

}
