package com.batherphilippa.soundstream.utils;

import static com.batherphilippa.soundstream.utils.Constants.SPACE_ENCODING;

public class StringUtils {

    public static String formatQuery(String query) {
        return query.trim().toLowerCase().replace(" ", SPACE_ENCODING);
    }
}
