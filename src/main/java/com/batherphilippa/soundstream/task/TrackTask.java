package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.concurrent.Task;

public class TrackTask extends Task<Integer> {

    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();

        Consumer<String> consumer = (trackId) -> {
            Thread.sleep(250);
            System.out.println(trackId);

            Consumer<Integer> consumer1 = (trackKey) -> {
                Thread.sleep(250);
                System.out.println(trackKey);
            };

            musicService.getTrackKey(trackId).subscribe(consumer1, Throwable::printStackTrace);
        };

        musicService.getTrackId().subscribe(consumer, Throwable::printStackTrace);
        return null;
    }
}
