package com.batherphilippa.soundstream;

import com.batherphilippa.soundstream.controller.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * App - punto de entrada de la aplicación que carga el recurso de main.fxml y muestra el 'stage'; extiende Application.
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/soundstream/main.fxml"));
        loader.setController(new AppController());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}