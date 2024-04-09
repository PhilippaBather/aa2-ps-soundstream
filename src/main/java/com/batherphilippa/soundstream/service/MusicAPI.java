package com.batherphilippa.soundstream.service;

import com.batherphilippa.soundstream.model.Token;
import io.reactivex.Observable;
import retrofit2.http.*;

public interface MusicAPI {

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api/token")
    Observable<Token> getToken(@Header("Authorization") String auth, @Field("grant_type") String grantType);

}

