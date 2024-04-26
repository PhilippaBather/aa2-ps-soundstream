package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackArtistBasic - un objeto simplificado de un artista que incluye enlaces a información más detallada sobre el artista.
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
