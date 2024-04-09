package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AlbumSearchResults - object returned from the Spotify API for a search for a given artist's albums.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumSearchResults {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<Album> items;
}
