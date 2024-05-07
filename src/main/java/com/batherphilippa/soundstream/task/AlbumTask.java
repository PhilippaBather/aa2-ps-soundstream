package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Album;
import com.batherphilippa.soundstream.model.Items;
import com.batherphilippa.soundstream.model.dto.AlbumDTOOut;
import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * AlbumTask - el Task permite tareas asíncronos en el fondo en un hilo diferente del 'main application thread'.
 * AlbumTask maneja la peticiones al MusicService para obtener información sobre albumes.
 */
public class AlbumTask extends Task<Integer> {

    private final String query;
    private final ObservableList<AlbumDTOOut> albums;

    public AlbumTask(String query, ObservableList<AlbumDTOOut> albums) {
        this.query = query;
        this.albums = albums;
    }

    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();
        final String[] imgURL = new String[1];

        // el consumidor recibe los detalles del identificador desde el stream y duerme para simular concurrencia
        Consumer<Items> consumer = (item) -> { // item
            Thread.sleep(300);

            String id = item.getId();
            imgURL[0] = item.getImages().get(1).getUrl();


            // el segundo consumidor consigue información sobre albumes
            Consumer<Album> consumer1 = (album) -> {
                // duereme para simular cocurrencia
                Thread.sleep(300);
                // analiza sintácticamente y convierte un Album album en un AlbumDTOOut
                AlbumDTOOut albumDTOOut = new AlbumDTOOut(album.getName(), album.getRelease_date(), album.getTotal_tracks(), imgURL[0]);
                // realiza la tarea en el 'main thread' en el futuro; previene errores
                Platform.runLater(() -> albums.add(albumDTOOut));
            };

            // suscripción interna: subscribe al stream del Observable de objetos de Album; usa el valor de ID
            musicService.getAlbumNames(id).subscribe(consumer1, Throwable::printStackTrace);
        };

        // subscribe al stream del Observable de una cadena que representa el ID de un artista
        musicService.getArtist(query).subscribe(consumer, Throwable::printStackTrace);

        return null;

    }

}
