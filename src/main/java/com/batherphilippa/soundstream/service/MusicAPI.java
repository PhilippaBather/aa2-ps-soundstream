package com.batherphilippa.soundstream.service;

import com.batherphilippa.soundstream.model.AlbumSearchResults;
import com.batherphilippa.soundstream.model.ArtistSearchResult;
import com.batherphilippa.soundstream.model.Token;
import io.reactivex.Observable;
import retrofit2.http.*;

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

}

