package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * TrackArtist - información sobre un artista que ha salido en la canción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackArtist {

    private SpotifyUrl external_urls;
    private Followers followers;
    private List<String> genres;
    private String href;
    private String id;
    private List<Images> images;
    private String name;
    private String type;
    private String uri;

}
