package com.example.rahul.goodplays.Model;

import java.util.HashMap;
import java.util.Map;

public class TrackList {

    private Track track;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
