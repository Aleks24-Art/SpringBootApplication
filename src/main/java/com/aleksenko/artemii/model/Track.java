package com.aleksenko.artemii.model;

import java.util.Objects;

/**
 * @author Aleksenko Artemii on 02.03.2020
 * @version 1.0
 */
public class Track {
    private String name;
    private int duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "Track" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        Track track = (Track) o;
        return getDuration() == track.getDuration() &&
                Objects.equals(getName(), track.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDuration());
    }
}
