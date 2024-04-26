package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Images - define el arte de la tapa de un álbum.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Images {
    private int height;
    private String url;
    private String width;
}
