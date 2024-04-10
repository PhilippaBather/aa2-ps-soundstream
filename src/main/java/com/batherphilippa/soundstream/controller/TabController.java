package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.AlbumTask;
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

public class TabController implements Initializable {

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

    private ObservableList<String> albums;

    public TabController(String query) {
        this.query = query;
        this.tab = new Tab();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.albums = FXCollections.observableArrayList();
        this.respListView.setItems(this.albums);
        AlbumTask albumTask = new AlbumTask(this.query, this.albums);
        new Thread(albumTask).start();
    }

    @FXML
    private void filterList(ActionEvent event) {

    }

    public void setTab(Tab tab) {
        this.tab = tab;
//        tab.setOnClosed(e -> albumTask.cancel());
    }

}
