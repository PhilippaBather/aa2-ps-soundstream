package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackArtistBasic - a simplified artist object including links to more detailed information about the artist.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackArtistBasic {
    private SpotifyUrl external_urls;
    private String href;
    private String id;
    private String name;
    private String type;
    private String uri;
}
