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

    private String album_type;
    private int total_tracks;
    private List<String> available_markets;
    private SpotifyUrl external_urls;
    private String href;
    private String id;
    private List<Images> images;
    private String name;
    private String release_date;
    private String release_date_precision;
    private Restrictions restrictions;
    private String type;
    private String uri;
    private List<TrackArtistBasic> artists;
}
