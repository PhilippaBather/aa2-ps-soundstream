package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class TrackTask extends Task<Integer> {

    private String query;
    private ObservableList<String> tracks;

    public TrackTask(String query, ObservableList<String> tracks) {
        this.query = query;
        this.tracks = tracks;
    }

    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();

        Consumer<String> consumer = (trackId) -> {
            Thread.sleep(250);
            System.out.println(trackId);

            Consumer<Integer> consumer1 = (trackKey) -> {
                Thread.sleep(250);
                System.out.println(trackKey);
                tracks.add(trackKey.toString());
            };

            musicService.getTrackKey(trackId).subscribe(consumer1, Throwable::printStackTrace);
        };

        musicService.getTrackId(query).subscribe(consumer, Throwable::printStackTrace);
        return null;
    }
}
