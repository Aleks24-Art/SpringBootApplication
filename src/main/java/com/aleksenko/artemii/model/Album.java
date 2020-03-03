package com.aleksenko.artemii.model;

import java.util.List;
import java.util.Objects;

/**
 * @author Aleksenko Artemii on 01.03.2020
 * @version 1.0
 */

public class Album {
    private String name;
    private String genre;
    private String artist;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return Objects.equals(getName(), album.getName()) &&
                Objects.equals(getGenre(), album.getGenre()) &&
                Objects.equals(getArtist(), album.getArtist()) &&
                Objects.equals(getTrackList(), album.getTrackList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGenre(), getArtist(), getTrackList());
    }

    @Override
    public String toString() {
        String tracks = "";
        for (int i = 0; i < trackList.size(); i++) {
            tracks += (i + 1)  + " " + trackList.get(i).toString() + "\n";
        }
        return "Album = " + name + "\nGenre = " + genre + "\nArtist = " + artist + "\nTracks: \n" + tracks;
    }
}
