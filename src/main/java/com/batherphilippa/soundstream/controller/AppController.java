package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.TokenTask;
import com.batherphilippa.soundstream.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.batherphilippa.soundstream.controller.ControllerType.ALBUM;
import static com.batherphilippa.soundstream.controller.ControllerType.TRACK;
import static com.batherphilippa.soundstream.utils.Constants.*;
import static com.batherphilippa.soundstream.utils.StringUtils.formatTabTitle;

/**
 * AppController - controlador de la aplicación.
 * Al inicializado, lanza el Token Task para obtiene el 'token' para realizar busqueaas.
 * Maneja las busquedas para artista y detalles de una canción por lanzar los controladores asociados y abre un tab.
 * Implementa Initializable.
 */
public class AppController implements Initializable {

    @FXML
    private TextField artistInput;

    @FXML
    private Button albumSearchBtn;

    @FXML
    private TextField trackTitleInput;
    @FXML
    private TextField trackArtistInput;

    @FXML
    private Button trackSearchBtn;

    @FXML
    private TabPane tabPaneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // get auth token on app initialisation
        TokenTask tokenTask = new TokenTask();
        new Thread(tokenTask).start();

        tabPaneManager.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    }

    /**
     * Maneja la busqueda para información sobre un artista
     * @param event
     */
    @FXML
    void searchAlbums(ActionEvent event) {
        // obtiene la entrada de busqueda
        String artistQuery = this.artistInput.getText();

        // si el usuario ha intentado realizar una busqueda sin introducir información sobre un artista, notifícalo
        if(artistQuery.trim().length() == 0 || artistQuery.equals(PROMPT_ARTIST_SEARCH)) {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_BLANK_QUERY_ARTIST, Alert.AlertType.INFORMATION);
            // re-estable los avisos
            this.artistInput.setText(PROMPT_ARTIST_SEARCH);
            return;
        } else {
            // lanza el controlador de AlbumController
            launchAlbumController(artistQuery);
        }

        // re-establece los inputs
        this.artistInput.clear();
        this.artistInput.setText(PROMPT_ARTIST_SEARCH);
        this.artistInput.requestFocus();
    }

    /**
     * Maneja la busqueda para información sobre una canción
     * @param event
     */
    @FXML
    void searchTrack(ActionEvent event) {
        // obtiene la entrada de busqueda: canción y artista
        String trackQuery = this.trackTitleInput.getText();
        String artistQuery = this.trackArtistInput.getText();

        // si el usuario ha intentado realizar una busqueda sin introducir información sobre una canción o artista, notifícalo
        if (trackQuery.trim().length() == 0 || trackQuery.equals(PROMPT_TRACK_SEARCH)) {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_BLANK_QUERY_TRACK, Alert.AlertType.INFORMATION);
            // re-estable los avisos
            this.trackTitleInput.setText(PROMPT_TRACK_SEARCH);
            this.trackArtistInput.setText(PROMPT_ARTIST_TRACK_SEARCH);
            return;
        } else {
            // lanza el controlador de TrackController
            launchTrackController(trackQuery, artistQuery);
        }

        // re-establece los inputs
        this.trackTitleInput.clear();
        this.trackTitleInput.setText(PROMPT_TRACK_SEARCH);
        this.trackArtistInput.setText(PROMPT_ARTIST_TRACK_SEARCH);
    }

    /**
     * Lanza el AlbumController
     * @param query - la busqueda de una artista
     */
    private void launchAlbumController(String query) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/soundstream/progress_pane.fxml")));
        AlbumController albumController = new AlbumController(query);
        loader.setController(albumController);
        openTab(loader, query, albumController, ALBUM);
    }

    /**
     * Lanaza el TrackController
     * @param trackQuery - la busqueda de canción
     * @param artistQuery - la busqueda de artista
     */
    private void launchTrackController(String trackQuery, String artistQuery) {
        FXMLLoader loader = new FXMLLoader(((getClass().getResource("/soundstream/progress_pane.fxml"))));
        TrackController trackController = new TrackController(trackQuery, artistQuery);
        loader.setController(trackController);
        openTab(loader, trackQuery, trackController, TRACK);
    }

    /**
     * Abre un nuevo pestaña.
     * @param loader - carga una jeraquía de objetos desde un documento XML
     * @param query - pregunta de la busqueda
     * @param controller - controlador: interface por flexibilidad
     * @param controllerType - tipo de controlador por el título de tab
     */
    private void openTab(FXMLLoader loader, String query, MusicController controller, ControllerType controllerType) {
        Tab tab;
        // usa el controllerType para establecer un título para el tab
        String searchType = controllerType.name().equals(ALBUM.name()) ? TAB_ALBUMS : TAB_AUDIO_FEATURES;
        String tabTitle = formatTabTitle(query, searchType);

        try {
            tab = new Tab(tabTitle, loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // establece y añade el pestaña usando el objeto tabPaneManager
        controller.setTab(tab);
        tabPaneManager.getTabs().add(tab);
    }

}
