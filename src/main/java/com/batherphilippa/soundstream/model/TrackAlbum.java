package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TrackAlbum - information about the album on which the track appears.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackAlbum {

    private String albumType;
    private int totalTracks;
    private List<String> availableMarkets;
    private SpotifyUrl externalUrls;
    private String href;
    private String id;
    private List<Images> images;
    private String name;
    private String releaseDate;
    private String releaseDatePrecision;
    private Restrictions restrictions;
    private String type;
    private String uri;
    private List<TrackArtistBasic> artists;
}
