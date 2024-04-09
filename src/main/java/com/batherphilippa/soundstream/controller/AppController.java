package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.AlbumTask;
import com.batherphilippa.soundstream.task.TokenTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private TextField artistInput;

    @FXML
    private Button searchBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TokenTask tokenTask = new TokenTask();
        new Thread(tokenTask).start();
    }

    @FXML
    void searchAlbums(ActionEvent event) {
        String requestedArtist = this.artistInput.getText();
        this.artistInput.clear();
        this.artistInput.requestFocus();
        AlbumTask albumTask = new AlbumTask(requestedArtist);
        new Thread(albumTask).start();
    }
}
