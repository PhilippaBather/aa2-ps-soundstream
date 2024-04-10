package com.batherphilippa.soundstream.utils;

import static com.batherphilippa.soundstream.utils.Constants.COLON_ENCODING;
import static com.batherphilippa.soundstream.utils.Constants.SPACE_ENCODING;

public class StringUtils {

    public static String formatQuery(String query) {
        return query.trim().toLowerCase().replace(" ", SPACE_ENCODING);
    }

    public static String formatTrackQuery(String queryArtist, String queryTrack) {
        String formattedArtist = formatQuery(queryArtist);
        String formattedTrack = formatQuery(queryTrack);
        StringBuilder strBuilder = new StringBuilder();
        return strBuilder
                .append("song")
                .append(COLON_ENCODING)
                .append(formattedTrack)
                .append("artist")
                .append(COLON_ENCODING)
                .append(formattedArtist).toString();
    }
}
