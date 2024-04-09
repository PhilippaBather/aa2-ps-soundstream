package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ArtistSearchResults - object returned from Spotify API for a search for a specified artist.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistSearchResult {

    private Artists artists;
}
