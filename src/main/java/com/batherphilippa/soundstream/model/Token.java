package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Token - define el objeto Token requerido para peticione a la Spotify API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String access_token;
    private int expires_in;
    private String token_type;
}
