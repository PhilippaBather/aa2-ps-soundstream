package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Images - defines the album cover art.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Images {
    private int height;
    private String url;
    private String width;
}
