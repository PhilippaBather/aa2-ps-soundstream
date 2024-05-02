package com.batherphilippa.soundstream.service;

import com.batherphilippa.soundstream.model.*;
import com.batherphilippa.soundstream.task.TokenTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.batherphilippa.soundstream.utils.Constants.*;

/**
 * MusicService - el servicio que maneja las llamadas a la API para obtener información sobre artistas y canciones.
 */
public class MusicService {

    private final MusicAPI musicAPI;

    public MusicService() {

        // interceptor para logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // configura el cliente HTTP
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // inicializa un objeto de Google json parser
        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        // inicializa el cliente Retrofit pasando el OkHttpClient y gsonParser
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_BASE_URL_FETCH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                // permite el uso de observables y la simulación de una API reactiva
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // crea la interfaz
        this.musicAPI = retrofit.create(MusicAPI.class);
    }

    /**
     * Realiza la petición Fetch por llamar la interfaz MusicAPI para obtener el identificador de un artista.
     * Se necesita el identificador para realizar la petición sobre albumes de un artista.
     *
     * @param q - la consulta
     * @return Observable de tipo cadena que representa el identificador del artista.
     */
    public Observable<Items> getArtist(String q) {
        // tipo de petición
        String type = SPOTIFY_SEARCH_REQUEST_TYPE_ARTIST;
        // obtener el Token
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getArtists(auth, q, type, 1, 0)
                .map(ArtistSearchResult::getArtists)
                .map(Artists::getItems)
                .flatMapIterable(items -> items);
    }


    /**
     * Realiza la petición Fetch por llamar a la interfaz MusicAPI para obtener los albumes de un artista.
     *
     * @param id - el identificador del artista
     * @return Observable de tipo Album
     */
    public Observable<Album> getAlbumNames(String id) {
        // obtener el Token
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getArtistsAlbums(auth, id)
                .map(AlbumSearchResults::getItems)
                .flatMapIterable(album -> album);
    }

    /**
     * Realiza la petición Fetch por llamar a la interfaz MusicAPI para obtener una canción.
     *
     * @param q - la consulta (nombre de una cancíon)
     * @return Observable de tipo Track
     */
    public Observable<Track> getTrack(String q) {
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getTrackByNameAndArtist(auth, q, SPOTIFY_SEARCH_REQUEST_TYPE_TRACK)
                .map(TrackSearchResults::getTracks)
                .map(Tracks::getItems)
                .flatMapIterable(track -> track);
    }

    /**
     * Realiza la petición Fetch por llamar a la interfaza MusicAPI para obtener las características de audio de una
     * canción.
     *
     * @param trackId - el identificador de una canción
     * @return Observable de tipo TrackAudioFeatures
     */
    public Observable<TrackAudioFeatures> getTrackKey(String trackId) {
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getTrackKey(auth, trackId)
                .map(audioFeatures -> audioFeatures);
    }
}
