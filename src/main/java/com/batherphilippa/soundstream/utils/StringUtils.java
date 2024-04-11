package com.batherphilippa.soundstream.utils;

import static com.batherphilippa.soundstream.utils.Constants.COLON_ENCODING;
import static com.batherphilippa.soundstream.utils.Constants.SPACE_ENCODING;

public class StringUtils {

    public static String formatQuery(String query) {
        return query.trim().toLowerCase().replace(" ", SPACE_ENCODING);
    }

    public static String formatTrackQuery(String queryTrack, String queryArtist) {
        String formattedTrack = formatQuery(queryTrack);
        String formattedArtist = formatQuery(queryArtist);
        return new StringBuilder()
                .append("track")
                .append(COLON_ENCODING)
                .append(formattedTrack)
                .append(SPACE_ENCODING)
                .append("artist")
                .append(COLON_ENCODING)
                .append(formattedArtist).toString();
    }
}
