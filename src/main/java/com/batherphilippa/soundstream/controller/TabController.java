package com.batherphilippa.soundstream.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class TabController {

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

    public TabController(String query) {
        this.query = query;
        this.tab = new Tab();
    }

    @FXML
    private void filterList(ActionEvent event) {

    }

    public void setTab(Tab tab) {
        this.tab = tab;
//        tab.setOnClosed(e -> albumTask.cancel());
    }

}
