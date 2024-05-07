package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AlbumArtist - información sobre un artista que ha salido en un álbum.
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
