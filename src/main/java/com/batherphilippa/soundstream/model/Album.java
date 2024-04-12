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
    private String album_group;
    private String album_type;
    private List<ExternalUrls> artists;
    private List<String> available_markets;
    private SpotifyUrl external_urls;
    private String href;
    private String id;
    private List<Images> images;
    private String name;
    private String release_date;
    private String release_date_precision;
    private int total_tracks;
    private String type;
    private String uri;
}
