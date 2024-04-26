package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Track;
import com.batherphilippa.soundstream.model.TrackAudioFeatures;
import com.batherphilippa.soundstream.model.dto.TrackDTOOut;
import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class TrackTask extends Task<Integer> {

    private final String query;
    private final ObservableList<TrackDTOOut> tracks;

    public TrackTask(String query, ObservableList<TrackDTOOut> tracks) {
        this.query = query;
        this.tracks = tracks;
    }

    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();

        // el consumidor recibe los detalles del identificador desde el stream y duerme para simular concurrencia
        Consumer<Track> consumer = (track) -> {
            Thread.sleep(300);

            Consumer<TrackAudioFeatures> consumer1 = (audioFeatures) -> {
                // duereme para simular cocurrencia
                Thread.sleep(300);
                // analiza sintácticamente y convierte un Track en un TrackDTOOut
                TrackDTOOut trackData = parseData(track, audioFeatures);
                // realiza la tarea en el 'main thread' en el futuro; previene errores
                Platform.runLater(() -> tracks.add(trackData));
            };

            // suscripción interna: subscribe al stream del Observable de tipo TrackAudioFeatures; usa el valor de ID
            musicService.getTrackKey(track.getId()).subscribe(consumer1, Throwable::printStackTrace);
        };

        // subscribe al stream del Observable de tipo Track
        musicService.getTrack(query).subscribe(consumer, Throwable::printStackTrace);

        return null;
    }

    /**
     * Analiza sintácticamente los objetos Track and TrackAudioFeature para crear un objeto del tipo TrackDTOOut
     * @param track - Track
     * @param audioFeatures - TrackAudioFeatures
     * @return TrackDTOOut
     */
    private TrackDTOOut parseData(Track track, TrackAudioFeatures audioFeatures) {
        String artistName = track.getAlbum().getArtists().get(0).getName();
        String albumName = track.getAlbum().getName();
        // clave musical
        String key = getDominantKey(audioFeatures);
        // bpm ('beats per minute'): pulsos por minuto
        String bpm = (int) audioFeatures.getTempo() + "bpm";
        // signatura de compás
        String timeSignature = getTimeSignature(audioFeatures);
        return new TrackDTOOut(artistName, albumName, key, bpm, timeSignature);
    }


    /**
     * Convierte la clase de tono 'pitch class' en clave de música convencional.
     * @param audioFeatures - objeto contiendo las características de audio de una canción
     * @return String - la clave musical dominante
     */
    private String getDominantKey(TrackAudioFeatures audioFeatures) throws IllegalStateException {
        String key = switch (audioFeatures.getKey()) {
            case 0 -> "C";
            case 1 -> "Db";
            case 2 -> "D";
            case 3 -> "Eb";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "Gb";
            case 7 -> "G";
            case 8 -> "Ab";
            case 9 -> "A";
            case 10 -> "Bb";
            case 11 -> "B";
            default -> throw new IllegalStateException("Unexpected value: " + audioFeatures.getKey());
        };

        // 0 reprsenta la clave mayor; 1 la clave menor
        return key.concat(audioFeatures.getMode() == 0 ? " maj" : " min");
    }

    /**
     * Devuelve la signatura de compás estimada.  En la API de Spotify, la signatura devuelta varía de 3 a 7,
     * lo que indica tipos de compás de '3/4' a '7/4'.
     * @param audioFeatures - características de audio
     * @return una cadena representando la compás
     */
    private String getTimeSignature(TrackAudioFeatures audioFeatures) {
        return audioFeatures.getTime_signature() + "/4";
    }
}
