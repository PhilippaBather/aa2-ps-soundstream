package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackAudioFeatures - las características de audio de un objeto Track (la canción).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackAudioFeatures {
    private float danceability;
    private float energy;
    private int key;
    private float loudness;
    private int mode;
    private float speechiness;
    private float acousticness;
    private float instrumentalness;
    private float liveness;
    private float valence;
    private float tempo;
    private String type;
    private String id;
    private String uri;
    private String trackHref;
    private String analysisUrl;
    private int duration_mss;
    private int time_signature;
}
