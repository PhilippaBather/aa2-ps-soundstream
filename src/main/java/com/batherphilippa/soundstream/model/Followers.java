package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Followers - information about the artist's followers.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Followers {
    private String href;
    private int total;
}
