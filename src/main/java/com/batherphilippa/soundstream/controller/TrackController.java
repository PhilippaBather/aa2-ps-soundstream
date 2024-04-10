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
    private String query;
    private Tab tab;

    private ObservableList<String> tracks;
    public TrackController(String trackQuery, String artistQuery) {
        this.query = formatTrackQuery(trackQuery, artistQuery);
        this.tab = new Tab();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tracks = FXCollections.observableArrayList();
        this.respListView.setItems(this.tracks);
        TrackTask trackTask = new TrackTask(this.query, this.tracks);
        new Thread(trackTask).start();
    }

    @FXML
    private void filterList(ActionEvent event) {

    }
    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
//        tab.setOnClosed(e -> trackTask.cancel());
    }

}