package com.aleksenko.artemii.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Aleksenko Artemii on 01.03.2020
 * @version 1.0
 *
 * Default POJO class with all album info
 */
@Component
public class Album implements Serializable {

    private String name;
    private String artist;
    private String genre;
    private String poster;
    private List<Track> trackList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return Objects.equals(getName(), album.getName()) &&
                Objects.equals(getGenre(), album.getGenre()) &&
                Objects.equals(getArtist(), album.getArtist()) &&
                Objects.equals(getTrackList(), album.getTrackList()) &&
                Objects.equals(getPoster(), album.getPoster());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGenre(), getArtist(), getTrackList(), getPoster());
    }

    @Override
    public String toString() {
        String tracks = "";
        for (int i = 0; i < trackList.size(); i++) {
            tracks += (i + 1)  + " " + trackList.get(i).toString() + "\n";
        }
        return "Album = " + name + "\nGenre = " + genre + "\nArtist = " + artist + "\nPoster = "+ poster + "\nTracks: \n" + tracks;
    }
}
