package com.batherphilippa.soundstream.utils;

import static com.batherphilippa.soundstream.utils.Constants.*;

/**
 * StringUtils - contiene métodos estáticos para formatear cadenas.
 */
public class StringUtils {

    /**
     * Formatea una consulta.
     *
     * @param query - la consulta
     * @return una cadena formateado.
     */
    public static String formatQuery(String query) {
        return query.trim().toLowerCase();
    }

    /**
     * Formatea la consulta de una canción.
     *
     * @param queryTrack  - consulta de una canción
     * @param queryArtist - consulta de un artista (opcional)
     * @return la consulta formateada
     */
    public static String formatTrackQuery(String queryTrack, String queryArtist) {
        String formattedTrack = queryTrack.trim().toLowerCase();
        // quita el prompt de la consulta
        String formattedArtist = queryArtist.trim().equalsIgnoreCase(PROMPT_ARTIST_TRACK_SEARCH) ? "" : queryArtist.trim().toLowerCase();

        // refactored: url encoding resulting in inconsistent formatting
        StringBuilder strBuilder = new StringBuilder();
        if (formattedArtist.length() == 0) {
            strBuilder
                    .append(formattedTrack);
        } else {
            // adjunta la consulta de artista solo si está proporcionada
            strBuilder
                    .append(SPOTIFY_SEARCH_REQUEST_TYPE_TRACK)
                    .append(":")
                    .append(formattedTrack)
                    .append(" ")
                    .append(SPOTIFY_SEARCH_REQUEST_TYPE_ARTIST)
                    .append(":")
                    .append(formattedArtist);
        }
        return strBuilder.toString();
    }

    /**
     * Formatea el título de una pestaña.
     *
     * @param query      - la consulta
     * @param searchType - el tipo de búsqueda
     * @return cadena formateada
     */
    public static String formatTabTitle(String query, String searchType) {
        String[] formattedWords = capitaliseQuery(query);

        // re-concatena las palabras formateadas y añade el posesivo correcto depende de la última letra
        String finalWord = formattedWords[formattedWords.length - 1];
        char finalLetter = finalWord.charAt(finalWord.length() - 1);
        if (finalLetter == 's' | finalLetter == 'x') {
            return String.join(" ", formattedWords).concat("' ").concat(searchType);
        } else {
            return String.join(" ", formattedWords).concat("'s ").concat(searchType);
        }
    }

    /**
     * Formatea el nombre del archivo CSV.
     * @param query - la consulta
     * @param controllerType - el típo de consulta
     * @return String - nombre del archivo
     */
    public static String formatCSVFilename(String query, String controllerType) {
        String formattedStr = query.toLowerCase().replace(" ", "_");
        return new StringBuilder().append(formattedStr).append("_").append(controllerType.toLowerCase()).append(".csv").toString();
    }

    /**
     * Formatea el nombre del artista, por ejemplo, 'mark lettieri', está convertiendo en 'Mark Lettieri'.
     * @param query - la consulta
     * @return String - la consulta formateada
     */
    public static String formatArtistName(String query) {
        String[] formattedWords = capitaliseQuery(query);
        return String.join(" ", formattedWords);
    }

    /**
     * Capitaliza la primera letra de cada palabra.
     * @param query - la consulta
     * @return String[] - las palabras capitalizadas
     */
    private static String[] capitaliseQuery(String query) {
        // separa las palabras en la consulta
        String[] queryWords = query.trim().toLowerCase().split(" ");
        // crea un array del tamaño de queryWords
        String[] formattedWords = new String[queryWords.length];

        for (int i = 0; i < queryWords.length; i++) {
            // pasa al array los elementos del array queryWords formateados
            formattedWords[i] = queryWords[i].substring(0, 1).toUpperCase() + queryWords[i].substring(1);
        }
        return formattedWords;
    }
}
