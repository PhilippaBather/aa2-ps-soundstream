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

    @FXML
    void searchAlbums(ActionEvent event) {
        String artistQuery = this.artistInput.getText();

        if(artistQuery.trim().length() == 0 || artistQuery.equals(PROMPT_ARTIST_SEARCH)) {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_BLANK_QUERY_ARTIST, Alert.AlertType.INFORMATION);
            this.artistInput.setText(PROMPT_ARTIST_SEARCH);
            return;
        } else {
            launchAlbumController(artistQuery);
        }

        this.artistInput.clear();
        this.artistInput.setText(PROMPT_ARTIST_SEARCH);
        this.artistInput.requestFocus();
    }

    @FXML
    void searchTrack(ActionEvent event) {
        System.out.println("Search track");

        String trackQuery = this.trackTitleInput.getText();
        String artistQuery = this.trackArtistInput.getText();

        if (trackQuery.trim().length() == 0 || trackQuery.equals(PROMPT_TRACK_SEARCH)) {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_BLANK_QUERY_TRACK, Alert.AlertType.INFORMATION);
            this.trackTitleInput.setText(PROMPT_TRACK_SEARCH);
            this.trackArtistInput.setText(PROMPT_ARTIST_TRACK_SEARCH);
            return;
        } else {
            launchTrackController(trackQuery, artistQuery);
        }

        this.trackTitleInput.clear();
        this.trackTitleInput.setText(PROMPT_TRACK_SEARCH);
        this.trackArtistInput.setText(PROMPT_ARTIST_TRACK_SEARCH);
    }

    private void launchAlbumController(String query) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/soundstream/progress_pane.fxml")));
        AlbumController albumController = new AlbumController(query);
        loader.setController(albumController);
        openTab(loader, query, albumController, ALBUM);
    }

    private void launchTrackController(String trackQuery, String artistQuery) {
        FXMLLoader loader = new FXMLLoader(((getClass().getResource("/soundstream/progress_pane.fxml"))));
        TrackController trackController = new TrackController(trackQuery, artistQuery);
        loader.setController(trackController);
        openTab(loader, trackQuery, trackController, TRACK);
    }

    private void openTab(FXMLLoader loader, String query, MusicController controller, ControllerType controllerType) {
        Tab tab;
        String searchType = controllerType.name().equals("ALBUM") ? "Albums" : "Audio Features";
        String tabTitle = formatTabTitle(query, searchType);
        try {
            tab = new Tab(tabTitle, loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.setTab(tab);
        tabPaneManager.getTabs().add(tab);
    }

}
