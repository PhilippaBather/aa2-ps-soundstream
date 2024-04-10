package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Track - detailed information about the track object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    private TrackAlbum album;
    private List<TrackArtist> trackArtists;
    private List<String> availableMarkets;
    private int discNumber;
    private int durationMs;
    private boolean explicit;
    private TrackExternalIds trackExternalIds;
    private SpotifyUrl externalUrls;
    private String href;
    private String id;
    private boolean isPlayable;
    // not required - linkedFrom object
    private Restrictions restrictions;
    private String name;
    private int popularity;
    private String previewUrl;
    private int trackNumber;
    private String type;
    private String uri;
    private boolean isLocal;

}
