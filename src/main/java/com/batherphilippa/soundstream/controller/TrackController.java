package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.model.dto.TrackDTOOut;
import com.batherphilippa.soundstream.task.TrackTask;
import com.batherphilippa.soundstream.utils.NotificationUtils;
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

import static com.batherphilippa.soundstream.utils.Constants.PROMPT_TRACK_FILTER;
import static com.batherphilippa.soundstream.utils.Constants.UI_NOTIFICATION_UNSELECTED_FILTER;
import static com.batherphilippa.soundstream.utils.StringUtils.formatTrackQuery;

public class TrackController implements Initializable, MusicController {

    @FXML
    private AnchorPane responsePane;

    @FXML
    private Text titleTxt;

    @FXML
    private ListView<TrackDTOOut> respListView;

    @FXML
    private TextField filterInputTxt;

    @FXML
    private Button filterBtn;

    @FXML
    private Button undoBtn;

    @FXML
    private ToggleGroup filterRadBtnGroup;

    @FXML
    private RadioButton radioBtnOne;

    @FXML
    private RadioButton radioBtnTwo;

    @FXML
    private ProgressIndicator progIndicator;
    private String query;
    private Tab tab;

    private TrackTask trackTask;
    private ObservableList<TrackDTOOut> tracks;

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

        this.radioBtnOne.setText("Key");
        this.radioBtnTwo.setText("Artist (enter expression)");

        this.progIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.progIndicator.visibleProperty().bind(this.trackTask.runningProperty());
    }

    @FXML
    private void filterList(ActionEvent event) {
        String filter = this.filterInputTxt.getText().toLowerCase();
        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);
        if(radioBtnOne.isSelected()) {
            this.respListView.setItems(this.tracks.filtered(t -> t.getKey().toLowerCase().contains(filter)));
            this.radioBtnOne.setSelected(false); // reset btn to unselected
        } else if (radioBtnTwo.isSelected()) {
            this.respListView.setItems(this.tracks.filtered(t -> t.getArtist().toLowerCase().contains(filter)));
            this.radioBtnTwo.setSelected(false); // reset btn to unselected
        } else {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_UNSELECTED_FILTER, Alert.AlertType.INFORMATION);
        }
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
