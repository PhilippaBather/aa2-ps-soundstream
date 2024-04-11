package com.batherphilippa.soundstream.task;

import com.batherphilippa.soundstream.model.Track;
import com.batherphilippa.soundstream.model.TrackAudioFeatures;
import com.batherphilippa.soundstream.service.MusicService;
import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import static com.batherphilippa.soundstream.utils.Constants.SEPARATOR;

public class TrackTask extends Task<Integer> {

    private final String query;
    private final ObservableList<String> tracks;

    public TrackTask(String query, ObservableList<String> tracks) {
        this.query = query;
        this.tracks = tracks;
    }

    @Override
    protected Integer call() throws Exception {
        MusicService musicService = new MusicService();

        Consumer<Track> consumer = (track) -> {
            Thread.sleep(250);

            Consumer<TrackAudioFeatures> consumer1 = (audioFeatures) -> {
                Thread.sleep(250);
                String trackData = parseData(track, audioFeatures);
                Platform.runLater(() -> tracks.add(trackData));
            };

            musicService.getTrackKey(track.getId()).subscribe(consumer1, Throwable::printStackTrace);
        };

        musicService.getTrackId(query).subscribe(consumer, Throwable::printStackTrace);
        return null;
    }

    private String parseData(Track track, TrackAudioFeatures audioFeatures) {
        String artistName = track.getAlbum().getArtists().get(0).getName();
        String albumName = track.getAlbum().getName();
        String key = getDominantKey(audioFeatures);
        String bpm = (int) audioFeatures.getTempo() + "bpm";
        String timeSignature = getTimeSignature(audioFeatures);
        return new StringBuilder()
                .append(artistName)
                .append(SEPARATOR)
                .append(albumName)
                .append(SEPARATOR)
                .append(key)
                .append(SEPARATOR)
                .append(bpm)
                .append(SEPARATOR)
                .append(timeSignature).toString();
    }


    /**
     * Converts pitch class to conventional musical key.
     * @param audioFeatures - object containing a track's audio features
     * @return String - dominant musical key
     */
    private String getDominantKey(TrackAudioFeatures audioFeatures) throws IllegalStateException {
        String key = switch (audioFeatures.getKey()) {
            case 0 -> "C";
            case 1 -> "C#";
            case 2 -> "D";
            case 3 -> "D#";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "F#";
            case 7 -> "G";
            case 8 -> "G#";
            case 9 -> "A";
            case 10 -> "A#";
            case 11 -> "B";
            default -> throw new IllegalStateException("Unexpected value: " + audioFeatures.getKey());
        };

        return key.concat(audioFeatures.getMode() == 0 ? " maj" : " min");
    }

    private String getTimeSignature(TrackAudioFeatures audioFeatures) {
        return audioFeatures.getTime_signature() + "/4";
    }
}
