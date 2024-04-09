package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;

public class AlbumTask extends Task<Integer> {
    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();

        // consumer recibe los detalles del obra desde el stream y duerme para simular concurrencia
        Consumer<String> consumer = (id) -> {
            Thread.sleep(250);

            Consumer<String> consumer1 = (album) -> {
                Thread.sleep(250);
                System.out.println("Album name: " + album);
            };
            musicService.getAlbumNames(id).subscribe(consumer1, Throwable::printStackTrace);
        };

        musicService.getArtist().subscribe(consumer, Throwable::printStackTrace);

        return null;

    }
}
