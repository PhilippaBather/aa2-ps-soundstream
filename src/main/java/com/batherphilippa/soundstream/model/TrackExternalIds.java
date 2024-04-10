package com.batherphilippa.soundstream.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TrackExternalIds - known external Ids for a track
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackExternalIds {
    private String isrc; // International Standard Recording Code
    private String ean; // International Article Number
    private String upc; // Universal Product Code
}
