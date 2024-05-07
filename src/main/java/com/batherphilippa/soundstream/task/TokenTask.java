package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Token;
import com.batherphilippa.soundstream.service.TokenService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;

/**
 * TokenTask - el Task permite tareas asíncronos en el fondo en un hilo diferente del 'main application thread'.
 * TokenTask maneja la peticiones al TokenService para obtener el Token requerido para realizar peticiones posteriores.
 */
public class TokenTask extends Task<Integer> {

    public static String accessToken;
    @Override
    protected Integer call() throws Exception {
        TokenService tokenService = new TokenService();

        // el consumidor recibe los detalles del obra desde el stream y duerme para simular concurrencia
        Consumer<Token> consumer = (token) -> {
            accessToken = token.getAccess_token();
            Thread.sleep(250);
        };

        // subscire al stream de un Observable de tipo Token
        tokenService.getToken().subscribe(consumer);
        return null;
    }
}
