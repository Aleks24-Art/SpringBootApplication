package com.aleksenko.artemii.parser;
import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.Track;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksenko Artemii on 05.03.2020
 * @version 1.0
 * Class-parser for album info
 * @see Album
 */

@Service
public class ParseAlbumInfo {

    /**
     * Pars JSON to Album POJO
     * @param info - JSON
     * @return Album POJO
     */
    public Album parseJSON(String info) {
        Album album = new Album();
        JSONObject json = new JSONObject(info);
        album.setName(json.getJSONObject("album").getString("name"));
        album.setArtist(json.getJSONObject("album").getString("artist"));
        album.setTrackList(getTrackList(json.getJSONObject("album").getJSONObject("tracks").getJSONArray("track")));
        album.setGenre(getGenreFromTags(json.getJSONObject("album").getJSONObject("tags").getJSONArray("tag")));
        album.setPoster(getImgFromTags(json.getJSONObject("album").getJSONArray("image")));
        return album;
    }

    private List<Track> getTrackList(JSONArray array) {
        Track track;
        List<Track> trackList = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            track = new Track();
            track.setName(array.getJSONObject(i).getString("name"));
            track.setDuration(array.getJSONObject(i).getInt("duration"));
            trackList.add(track);
        }
        return trackList;
    }

    private String getGenreFromTags(JSONArray o) {
        if (o.length() >= 1) {
            return o.getJSONObject(0).getString("name");
        } else {
            return "Genre is unknown";
        }
    }

    private String getImgFromTags(JSONArray o) {
        for (int i = 0; i < o.length(); i++) {
            if (o.getJSONObject(i).getString("size").equals("large")) {
                return o.getJSONObject(i).getString("#text");
            }
        }
        return null;
    }
}
