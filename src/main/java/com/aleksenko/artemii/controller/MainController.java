package com.aleksenko.artemii.controller;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.parsers.ParseAlbumInfo;
import com.aleksenko.artemii.service.AppService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.util.concurrent.*;

/**
 * @author Aleksenko Artemii on 29.02.2020
 * @version 1.0
 *
 * Class where we have main logic
 */
@Controller
public class MainController {

    @Value("${number.of.threads}")
    private int numOfThread;
    private AppService service;
    private ParseAlbumInfo parser;

    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(MainController.class);

    /**
     * Default constructor with main parse and service objects
     * @param service service bean
     * @param parser parser bean
     */
    @Autowired
    public MainController(AppService service, ParseAlbumInfo parser) {
        this.service = service;
        this.parser = parser;
    }

    /**
     * Method to get request param from user
     * @param artist name of artist from user's request
     * @param album name of album from user's request
     * @return response with doc-file downloading
     */
    @RequestMapping(value = {"/info/{artist}/{album}"}, method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public ResponseEntity<?> getUrl(@PathVariable(name = "artist") String artist,
                                         @PathVariable(name = "album") String album) {
        Album someAlbum =  asynchronouslyRequest(artist, album);
        byte[] doc = service.createDocs(someAlbum);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(doc));
        String downloadDocName = someAlbum.getName() + ".doc";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downloadDocName)
                .contentLength(doc.length).body(resource);
    }


    @RequestMapping("/exit")
    public void exit() {
        logger.info("Finishing the program");
        System.exit(0);
    }
    private Album asynchronouslyRequest(String artist, String album) {
        Album someAlbum = null;
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThread);
        CompletionService<Album> completionService = new ExecutorCompletionService<>(executorService);
        logger.info("Starting asynchronously request");
        Future<Album> submit = completionService.submit(() -> parser.parseJSON(service.getHttpResponseAboutAlbum(artist, album)));
        try {
            someAlbum = submit.get();
            if (someAlbum == null) throw new InterruptedException();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception while getting Album from Future", e);
        }
        return someAlbum;
    }
}
