package com.batherphilippa.soundstream.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackDTOOut - la clase que reprsenta la canción para mostrar en la aplicación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackDTOOut {
    private String artist;
    private String album;
    private String key;
    private String bpm;
    private String timeSignature;
    private String imgURL;

    @Override
    public String toString() {
        return artist + " | " +
                "Album: " + album + " | " +
                "Key: " + key + " | " +
                "BPM: " + bpm + " | " +
                "Time Signature: " + timeSignature;
    }
}
