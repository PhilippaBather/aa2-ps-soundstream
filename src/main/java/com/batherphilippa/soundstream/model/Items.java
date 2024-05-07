package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Items - el objeto de un artista; un ítem es un objeto dentro el array de artistas en la clase Artists.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    private SpotifyUrl external_urls;
    private Followers followers;
    private List<String> genres;
    private String href;
    private String id;
    private List<Images> images;
    private String name;
    private int popularity;
    private String type;
    private String uri;
}
