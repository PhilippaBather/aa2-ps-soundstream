package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Restrictions - restrictions applied to the album.  Allowed values: "market", "product", and "explicit".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restrictions {
    private String reason;
}
