package com.batherphilippa.soundstream.service;

import com.batherphilippa.soundstream.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Base64;

public class TokenService {

    private final String CLIENT_ID = System.getenv("SPOTIFY_CLIENT_ID");
    private final String CLIENT_SECRET = System.getenv("SPOTIFY_CLIENT_SECRET");
    private final MusicAPI musicAPI;
    public TokenService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // configure HTTP client
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Google json parser
        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        String BASE_URL = "https://accounts.spotify.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                // allows us to use observables and simulate a reactive api
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // crear la interfaz
        this.musicAPI = retrofit.create(MusicAPI.class);
    }

    public Observable<Token> getToken() {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        String base64 = Base64.getEncoder().withoutPadding().encodeToString(auth.getBytes());
        String base64Auth = "Basic " + base64;
        return this.musicAPI.getToken(base64Auth, "client_credentials");
    }
}
