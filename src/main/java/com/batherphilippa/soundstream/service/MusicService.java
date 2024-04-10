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

public class MusicService {

    private final String BASE_URL_FETCH = "https://api.spotify.com";
    private final MusicAPI musicAPI;

    public MusicService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // configure HTTP client
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Google json parser
        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_FETCH)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                // allows us to use observables and simulate a reactive api
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // crear la interfaz
        this.musicAPI = retrofit.create(MusicAPI.class);
    }

    public Observable<String> getArtist(String q) {
        String type = "artist";
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getArtists(auth, q, type, 1)
                .map(ArtistSearchResult::getArtists)
                .map(Artists::getItems)
                .flatMapIterable(items -> items)
                .map(Items::getId);
    }

    public Observable<String> getAlbumNames(String id) {
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getArtistsAlbums(auth, id)
                .map(AlbumSearchResults::getItems)
                .flatMapIterable(album -> album)
                .map(Album::getName);
    }

    public Observable<String> getTrackId(String q){
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getTrackByNameAndArtist(auth, q, "track", 1)
                .map(TrackSearchResults::getTracks)
                .map(Tracks::getItems)
                .flatMapIterable(track -> track)
                .map(Track::getId);
    }

    public Observable<Integer> getTrackKey(String trackId) {
        String auth = "Bearer " + TokenTask.accessToken;
        return this.musicAPI.getTrackKey(auth, trackId)
                .map(TrackAudioFeatures::getKey);

    }
}
