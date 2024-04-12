package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.AlbumTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.batherphilippa.soundstream.utils.Constants.PROMPT_ALBUM_FILTER;
import static com.batherphilippa.soundstream.utils.StringUtils.formatQuery;

public class AlbumController implements Initializable, MusicController {

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

    @FXML
    private ProgressIndicator progIndicator;
    private final String query;
    private Tab tab;

    private AlbumTask albumTask;
    private ObservableList<String> albums;

    public AlbumController(String query) {
        this.query = formatQuery(query);
        this.tab = new Tab();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.albums = FXCollections.observableArrayList();
        this.respListView.setItems(this.albums);
        this.albumTask = new AlbumTask(this.query, this.albums);
        new Thread(albumTask).start();

        this.filterInputTxt.setText(PROMPT_ALBUM_FILTER);
        this.filterBtn.requestFocus();

        progIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progIndicator.visibleProperty().bind(this.albumTask.runningProperty());
    }

    @FXML
    private void filterList(ActionEvent event) {
        String filter = this.filterInputTxt.getText();
        this.filterInputTxt.setText(PROMPT_ALBUM_FILTER);
        this.respListView.setItems(this.albums.filtered((album) -> album.contains(filter)));
    }


    @FXML
    void undoAppliedFilter(ActionEvent event) {
        this.respListView.setItems(this.albums);
    }

    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
        tab.setOnClosed(e -> this.albumTask.cancel());
    }
}
