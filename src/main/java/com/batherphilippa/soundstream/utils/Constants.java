package com.batherphilippa.soundstream.utils;

/**
 * Los constantes de la aplicación
 */
public class Constants {

    // variables de entorno
    public static final String SPOTIFY_CLIENT_ID = "SPOTIFY_CLIENT_ID";
    public static final String SPOTIFY_CLIENT_SECRET = "SPOTIFY_CLIENT_SECRET";

    // constantes del servicio
    public static final String SPOTIFY_BASE_URL_FETCH = "https://api.spotify.com";
    public static final String SPOTIFY_BASE_URL_TOKEN = "https://accounts.spotify.com";
    public static final String SPOTIFY_GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";

    // constantes de peticiones
    public static final String SPOTIFY_SEARCH_REQUEST_TYPE_ARTIST = "artist";
    public static final String SPOTIFY_SEARCH_REQUEST_TYPE_TRACK = "track";
    public static final String SPOTIFY_TYPE_ARTIST = "&type=artist";

    // constantes de formateo de cadenas
    public static final String SPACE_ENCODING = "%2520";
    public static final String COLON_ENCODING = "%3A";

    // onstantes de la UI
    public static final String PROMPT_ALBUM_FILTER = "Filter for a word or release year";
    public static final String PROMPT_ARTIST_SEARCH = "Artist name";
    public static final String PROMPT_TRACK_SEARCH = "Track name";
    public static final String PROMPT_ARTIST_TRACK_SEARCH = "Artist name (optional)";
    public static final String PROMPT_TRACK_FILTER = "Filter for the track's audio features.";
    public static final String RADIO_BTN_ALBUM_EXPRESSION = "Album Name (enter expression)";
    public static final String RADIO_BTN_ARTIST_EXPRESSION = "Artist (enter expression)";
    public static final String RADIO_BTN_KEY = "Key";
    public static final String RADIO_BTN_RELEASE_YEAR = "Release Year";
    public static final String TAB_ALBUMS = "Albums";
    public static final String TAB_AUDIO_FEATURES = "Audio Features";
    public static final String UI_NOTIFICATION_BLANK_QUERY_ARTIST = "Please enter an artist or group's name as a search term.";
    public static final String UI_NOTIFICATION_BLANK_QUERY_TRACK = "Please enter the name of the track you wish to query.";
    public static final String UI_NOTIFICATION_UNSELECTED_FILTER = "Please select a filter option to apply a filter.";
}
