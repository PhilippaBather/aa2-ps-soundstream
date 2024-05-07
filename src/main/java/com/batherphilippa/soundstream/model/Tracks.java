package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Tracks - una lista de canciones devuelta que corresponde a el nombre de la canción consultado.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tracks {
    private String href;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;
    private List<Track> items;
}
