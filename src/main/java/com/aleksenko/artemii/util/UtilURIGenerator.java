package com.aleksenko.artemii.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 * Generate URL for request
 */
@Service
public class UtilURIGenerator {
    /**
     * User-key which used in URL-building
     */
    @Value("${api.key}")
    private String apiKey;

    public String generateURI(String artist, String albumOrTrack, String method) {
        System.out.println();
        String url = UriComponentsBuilder.newInstance()
                .scheme("http").host("ws.audioscrobbler.com").path("/2.0/")
                .query("method="+ method +".getinfo").query("api_key=" + apiKey)
                .query("artist=" + artist).query(method + "=" + albumOrTrack)
                .query("format=json").build().toString();
        System.out.println("This is URL TO GET TRACK INFO" + url);
        return url;
    }
}
