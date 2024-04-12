package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AlbumArtist - information about an artist who has appeared on an album.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumArtist {
    private SpotifyUrl external_urls;
    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;
}
