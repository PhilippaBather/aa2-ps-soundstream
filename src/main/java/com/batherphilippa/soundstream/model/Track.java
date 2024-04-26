package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Track - información detallada sobre el objeto Track (canción).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    private TrackAlbum album;
    private List<TrackArtist> track_artists;
    private List<String> available_markets;
    private int discNumber;
    private int durationMs;
    private boolean explicit;
    private TrackExternalIds track_external_ids;
    private SpotifyUrl external_urls;
    private String href;
    private String id;
    private boolean isPlayable;
    // not required - linkedFrom object
    private Restrictions restrictions;
    private String name;
    private int popularity;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;
    private boolean isLocal;

}
