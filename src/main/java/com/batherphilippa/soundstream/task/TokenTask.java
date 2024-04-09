package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Token;
import com.batherphilippa.soundstream.service.TokenService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;

public class TokenTask extends Task<Integer> {

    public static String accessToken;
    @Override
    protected Integer call() throws Exception {
        TokenService tokenService = new TokenService();

        // consumer recibe los detalles del obra desde el stream y duerme para simular concurrencia
        Consumer<Token> consumer = (token) -> {
            accessToken = token.getAccess_token();
            Thread.sleep(250);
        };

        tokenService.getToken().subscribe(consumer);
        return null;
    }
}
