package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.ParseAlbumInfo;
import com.aleksenko.artemii.service.AppService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Aleksenko Artemii on 29.02.2020
 * @version 1.0
 */
@Controller
public class MainController {
    private AppService service;
    private ParseAlbumInfo parser;
    private Album someAlbum;
    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    public MainController(AppService service, ParseAlbumInfo parser) {
        this.service = service;
        this.parser = parser;
    }

    @RequestMapping(value = {"/info/{artist}/{album}"}, method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUrl(@PathVariable(name = "artist") String artist,
                                         @PathVariable(name = "album") String album) {
        Album someAlbum = parser.parseJSON(service.getHttpResponseAboutAlbum(artist, album));
        service.createDocs(someAlbum);
        return ResponseEntity.ok(someAlbum);
    }

    public AppService getService() {
        return service;
    }

    public void setService(AppService service) {
        this.service = service;
    }

    public Album getSomeAlbum() {
        return someAlbum;
    }

    public void setSomeAlbum(Album someAlbum) {
        this.someAlbum = someAlbum;
    }

    public ParseAlbumInfo getParser() {
        return parser;
    }

    public void setParser(ParseAlbumInfo parser) {
        this.parser = parser;
    }

}
