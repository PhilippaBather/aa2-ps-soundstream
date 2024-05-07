package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Followers - información sobre los seguidores de un artista.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Followers {
    private String href;
    private int total;
}
