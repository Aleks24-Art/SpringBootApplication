package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.ParseAlbumInfo;
import com.aleksenko.artemii.service.AppService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
                                         @PathVariable(name = "album") String album,
                                         Model model) {
        String uri = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&" +
                "api_key=310d22580d72a57938fc4d7f713a0fab" +
                "&artist={artist}&album={album}&format={format}";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
        String someInfo = service.getHttpResponseAboutAlbum(uriComponents);
        model.addAttribute("textWithOutPars", someInfo);
        someAlbum = parser.parseJSON(someInfo);
        System.out.println(someAlbum);
        model.addAttribute("answer", someAlbum);
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

    private void clearFile(File file) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write("");
        } catch (IOException e) {
            logger.error("An exception occurred while writing in the file ", e);
        }
    }
}
