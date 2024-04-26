package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ArtistSearchResults - el objeto devuelto desde la Spotify API por una búsqueda para un artista.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSearchResult {

    private Artists artists;
}
