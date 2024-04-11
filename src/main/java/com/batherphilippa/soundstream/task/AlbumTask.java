package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Album;
import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class AlbumTask extends Task<Integer> {

    private final String query;
    private ObservableList<String> albums;

    public AlbumTask(String query, ObservableList<String> albums) {
        this.query = query;
        this.albums = albums;
    }

    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();

        // consumer recibe los detalles del obra desde el stream y duerme para simular concurrencia
        Consumer<String> consumer = (id) -> {
            Thread.sleep(250);

            Consumer<Album> consumer1 = (album) -> {
                Thread.sleep(250);
                String result = parseData(album);
                albums.add(result);
            };
            musicService.getAlbumNames(id).subscribe(consumer1, Throwable::printStackTrace);
        };

        musicService.getArtist(query).subscribe(consumer, Throwable::printStackTrace);

        return null;

    }

    private String parseData(Album album) {
        return new StringBuilder()
                .append("Album: ")
                .append(album.getName())
                .append(" | Total tracks: ")
                .append(album.getTotal_tracks())
                .append(" | Released: ")
                .append(album.getRelease_date()).toString();
    }
}
