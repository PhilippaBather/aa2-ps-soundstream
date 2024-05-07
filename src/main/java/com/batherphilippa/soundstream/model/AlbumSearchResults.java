package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AlbumSearchResults - el objeto devuelto desde la Sportify API por una búsqueda para los albumes de un artista.
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
