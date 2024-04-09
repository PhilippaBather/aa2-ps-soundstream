package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.TokenTask;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TokenTask tokenTask = new TokenTask();
        new Thread(tokenTask).start();
    }
}
