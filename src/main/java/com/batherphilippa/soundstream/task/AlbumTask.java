package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Album;
import com.batherphilippa.soundstream.model.dto.AlbumDTOOut;
import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class AlbumTask extends Task<Integer> {

    private final String query;
    private ObservableList<AlbumDTOOut> albums;

    public AlbumTask(String query, ObservableList<AlbumDTOOut> albums) {
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
                AlbumDTOOut albumDTOOut = new AlbumDTOOut(album.getName(), album.getRelease_date(), album.getTotal_tracks());
                Platform.runLater(() -> albums.add(albumDTOOut));
            };
            musicService.getAlbumNames(id).subscribe(consumer1, Throwable::printStackTrace);
        };

        musicService.getArtist(query).subscribe(consumer, Throwable::printStackTrace);

        return null;

    }

}
