package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackSearchResults - el objeto devuelto desde la Spotify API para una búsqueda de una canción de un artista.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackSearchResults {
    private Tracks tracks;
}
