package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.service.MainService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author Aleksenko Artemii on 29.02.2020
 * @version 1.0
 */
@Controller
public class MainController {
    private MainService service;
    private Album someAlbum;

    @Autowired
    public MainController(MainService service) {
        this.service = service;
    }

    @GetMapping("/infoo")
    public String getUrl(@RequestParam(name = "artist", defaultValue = "Cher") String artist,
                              @RequestParam(name = "album", defaultValue = "Believe") String album,
                              @RequestParam(name = "format", defaultValue = "json") String format,
                              Model model) {
        String someInfo = service.getHttpResponseAboutAlbum(artist, album, format);
        someAlbum = service.getAlbumInfo(someInfo);
        System.out.println(someAlbum);
        model.addAttribute("answer", someAlbum);
        return "info";
    }

    public MainService getService() {
        return service;
    }

    public void setService(MainService service) {
        this.service = service;
    }

    public Album getSomeAlbum() {
        return someAlbum;
    }

    public void setSomeAlbum(Album someAlbum) {
        this.someAlbum = someAlbum;
    }
}
