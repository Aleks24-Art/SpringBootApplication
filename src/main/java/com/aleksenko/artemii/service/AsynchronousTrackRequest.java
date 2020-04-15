package com.aleksenko.artemii.service;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.Track;
import com.aleksenko.artemii.util.UtilURIGenerator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Aleksenko Artemii on 14.04.2020
 * @version 1.0
 */

@Service
public class AsynchronousTrackRequest implements TrackService {

    /**
     * Field to log info in file
     */
    private final Logger logger = Logger.getLogger(Request.class);

    /**
     * User-key which used in URL-building to make request
     */
    @Value("${number.of.threads}")
    private int numOfThread;
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
    public AsynchronousTrackRequest(UtilURIGenerator uriGenerator, Request request) {
        this.uriGenerator = uriGenerator;
        this.request = request;
    }

    @Override
    public List<Track> getTracksIDAsynchronously(Album album) {
        ExecutorService executorService = Executors.newFixedThreadPool(numOfThread);
        CompletionService<Track> completionService = new ExecutorCompletionService<>(executorService);
        Future<Track> submit;
        List<Track> newTrackListWithID = new ArrayList<>();
        try {
            for (Track curTrack : album.getTrackList()) {
                submit = completionService.submit(() -> {
                    curTrack.setUrl
                            (getTrackURLFromJson(request.getHttpResponse
                                    (uriGenerator.generateURI
                                            (album.getArtist(), curTrack.getName(), "track"))));
                    return curTrack;
                });
                newTrackListWithID.add(submit.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Exception while getting track list from Future submit", e);
        }
        return newTrackListWithID;
    }

    private String getTrackURLFromJson(String info) {
        String trackURL = new JSONObject(info).getJSONObject("track").getString("url");
        System.out.println(trackURL);
        return trackURL;
    }
}
