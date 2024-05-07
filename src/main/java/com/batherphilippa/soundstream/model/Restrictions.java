package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Restrictions -las restricciones aplicadas a un álbum.  Valores permitidos: "market", "product", and "explicit".
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restrictions {
    private String reason;
}
