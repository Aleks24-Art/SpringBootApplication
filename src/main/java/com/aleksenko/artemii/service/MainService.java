package com.aleksenko.artemii.service;

import com.aleksenko.artemii.model.Album;
import com.aleksenko.artemii.model.Track;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksenko Artemii on 01.03.2020
 * @version 1.0
 */
@Component
@Service
public class MainService {
    public String getHttpResponseAboutAlbum(String artist, String album, String format) {
        HttpURLConnection connection = null;
        String line;
        String url = "http://ws.audioscrobbler.com/2.0/?method=album.getinfo&api_key=310d22580d72a57938fc4d7f713a0fab&artist=" + artist + "&album=" + album + "&format=" + format;
        StringBuilder sb = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream() , StandardCharsets.UTF_8));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }

            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        return sb.toString();
    }

    public Album getAlbumInfo(String s) {
        Album album = new Album();
        JSONObject json = new JSONObject(s);
        album.setName(json.getJSONObject("album").getString("name"));
        album.setArtist(json.getJSONObject("album").getString("artist"));
        album.setTrackList(getTrackList(json.getJSONObject("album").getJSONObject("tracks").getJSONArray("track")));
        album.setGenre(getGenreFromTags(json.getJSONObject("album").getJSONObject("tags").getJSONArray("tag")));
        return  album;
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

}
