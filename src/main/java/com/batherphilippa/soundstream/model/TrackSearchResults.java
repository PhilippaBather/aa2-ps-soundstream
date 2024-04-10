package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackSearchResults - object returned from the Spotify API for a search for a given artist's specified track.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackSearchResults {
    private Tracks tracks;
}
