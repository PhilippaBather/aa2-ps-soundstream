package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.model.dto.AlbumDTOOut;
import com.batherphilippa.soundstream.task.AlbumTask;
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

import static com.batherphilippa.soundstream.utils.Constants.PROMPT_ALBUM_FILTER;
import static com.batherphilippa.soundstream.utils.Constants.UI_NOTIFICATION_UNSELECTED_FILTER;
import static com.batherphilippa.soundstream.utils.StringUtils.formatQuery;

public class AlbumController implements Initializable, MusicController {

    @FXML
    private AnchorPane responsePane;

    @FXML
    private Text titleTxt;

    @FXML
    private ListView<AlbumDTOOut> respListView;

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
    private final String query;
    private Tab tab;

    private AlbumTask albumTask;
    private ObservableList<AlbumDTOOut> albums;

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

        this.radioBtnOne.setText("Release Year");
        this.radioBtnTwo.setText("Album Name (enter expression)");

        this.progIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.progIndicator.visibleProperty().bind(this.albumTask.runningProperty());
    }

    @FXML
    private void filterList(ActionEvent event) {
        String filter = this.filterInputTxt.getText().toLowerCase();
        this.filterInputTxt.setText(PROMPT_ALBUM_FILTER);

        if(radioBtnOne.isSelected()) {
            this.respListView.setItems(this.albums.filtered(a -> a.getRelease_date().contains(filter)));
            radioBtnOne.setSelected(false);  // reset btn to unselected
        } else if (radioBtnTwo.isSelected()){
            this.respListView.setItems(this.albums.filtered(a -> a.getName().toLowerCase().contains(filter)));
            radioBtnTwo.setSelected(false);  // reset btn to unselected
        } else {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_UNSELECTED_FILTER, Alert.AlertType.INFORMATION);
        }
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
