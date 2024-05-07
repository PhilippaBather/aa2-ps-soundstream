package com.batherphilippa.soundstream.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AlbumDTOOut - la clase que representa al álbum para mostrar en la aplicación.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTOOut {
    private String name;
    private String release_date;
    private int total_tracks;
    private String imgURL;

    @Override
    public String toString() {
        return
                name + " | " +
                        "Total Tracks: " + total_tracks + " | " +
                        "Release Date: " + release_date;
    }
}
