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

import static com.batherphilippa.soundstream.utils.Constants.*;

/**
 * TokenSevice - el servicio que maneja las llamadas a la API para obtener el Token requerido para peticiones posteriores.
 */
public class TokenService {

    private final String CLIENT_ID = System.getenv(SPOTIFY_CLIENT_ID);
    private final String CLIENT_SECRET = System.getenv(SPOTIFY_CLIENT_SECRET);
    private final MusicAPI musicAPI;
    public TokenService() {

        // interceptor para logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // configura el cliente HTTP
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // inicializa un objeto de Google json parser
        Gson gsonParser = new GsonBuilder()
                .setLenient()
                .create();

        // inicializa el cliente Retrofit pasando el OkHttpClient y gsonParser
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_BASE_URL_TOKEN)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gsonParser))
                // permite el uso de observables y la simulación de una API reactiva
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        // crea la interfaz
        this.musicAPI = retrofit.create(MusicAPI.class);
    }

    /**
     * Realiza la petición para obtener el Token por llamar la interfaz MusicAPI.
     * Pasa el identificador del cliente y el secreto convertido en codificación de Base64 y el tipo
     * de credenciales asociado.
     * @return Observable de tipo Token
     */
    public Observable<Token> getToken() {
        String auth = CLIENT_ID + ":" + CLIENT_SECRET;
        String base64 = Base64.getEncoder().withoutPadding().encodeToString(auth.getBytes());
        String base64Auth = "Basic " + base64;
        return this.musicAPI.getToken(base64Auth, SPOTIFY_GRANT_TYPE_CLIENT_CREDENTIALS);
    }
}
