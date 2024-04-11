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
        StringBuilder strBuilder = new StringBuilder();
        strBuilder
                .append("track")
                .append(COLON_ENCODING)
                .append(formattedTrack);

        // append artist query parameter only if supplied
        if (formattedArtist.length() != 0) {
            strBuilder
                .append(SPACE_ENCODING)
                .append("artist")
                .append(COLON_ENCODING)
                .append(formattedArtist);
        }
        return strBuilder.toString();

    }

    public static String formatTabTitle(String query, String searchType) {
        String[] queryWords = query.trim().toLowerCase().split(" ");
        String[] formattedWords = new String[queryWords.length];
        for (int i = 0; i < queryWords.length; i++) {
            formattedWords[i] = queryWords[i].substring(0, 1).toUpperCase() + queryWords[i].substring(1);
        }
        return String.join( " ", formattedWords).concat("'s " ).concat(searchType);
    }
}
