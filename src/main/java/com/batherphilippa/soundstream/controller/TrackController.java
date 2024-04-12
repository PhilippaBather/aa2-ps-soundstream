package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.TrackTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.batherphilippa.soundstream.utils.Constants.PROMPT_TRACK_FILTER;
import static com.batherphilippa.soundstream.utils.StringUtils.formatTrackQuery;

public class TrackController implements Initializable, MusicController {

    @FXML
    private AnchorPane responsePane;

    @FXML
    private Text titleTxt;

    @FXML
    private ListView<String> respListView;

    @FXML
    private TextField filterInputTxt;

    @FXML
    private Button filterBtn;

    @FXML
    private Button undoBtn;
    private String query;
    private Tab tab;

    private TrackTask trackTask;
    private ObservableList<String> tracks;

    public TrackController(String trackQuery, String artistQuery) {
        this.query = formatTrackQuery(trackQuery, artistQuery);
        this.tab = new Tab();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tracks = FXCollections.observableArrayList();
        this.respListView.setItems(this.tracks);
        this.trackTask = new TrackTask(this.query, this.tracks);
        new Thread(trackTask).start();

        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);
        this.filterBtn.requestFocus();
    }

    @FXML
    private void filterList(ActionEvent event) {
        String filter = this.filterInputTxt.getText();
        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);
        this.respListView.setItems(this.tracks.filtered((track) -> track.contains(filter)));
    }

    @FXML
    void undoAppliedFilter(ActionEvent event) {
        this.respListView.setItems(this.tracks);
    }

    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
        tab.setOnClosed(e -> this.trackTask.cancel());
    }

}
