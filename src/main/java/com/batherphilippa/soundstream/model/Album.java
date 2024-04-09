package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Album - information about a specified artist's album.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    private String albumType;
    private int totalTracks;
    private List<String> availableMarkets;
    private SpotifyUrl externalLinks;
    private String href;
    private String id;
    private List<Images> images;
    private String name;
    private String releaseDate;
    private String releaseDatePrecision;
    private Restrictions restrictions;
    private String type;
    private String uri;
    private List<SpotifyUrl> artists;
    private String albumGroup;
}
