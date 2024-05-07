package com.batherphilippa.soundstream.service;

import com.batherphilippa.soundstream.model.*;
import io.reactivex.Observable;
import retrofit2.http.*;

/**
 * MusicAPI - la interfaz que define los 'requests' a la API.
 */
public interface MusicAPI {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/token")
    Observable<Token> getToken(@Header("Authorization") String auth, @Field("grant_type") String grantType);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("v1/search")
    Observable<ArtistSearchResult> getArtists(@Header("Authorization") String auth, @Query("q") String q, @Query("type") String type, @Query("limit") int limit);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("v1/artists/{id}/albums")
    Observable<AlbumSearchResults> getArtistsAlbums(@Header("Authorization") String auth, @Path(value = "id") String id);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("v1/search")
    Observable<TrackSearchResults> getTrackByNameAndArtist(@Header("Authorization") String auth, @Query("q") String q, @Query("type") String type);

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @GET("v1/audio-features/{id}")
    Observable<TrackAudioFeatures> getTrackKey(@Header("Authorization") String auth, @Path(value = "id") String id);

}

