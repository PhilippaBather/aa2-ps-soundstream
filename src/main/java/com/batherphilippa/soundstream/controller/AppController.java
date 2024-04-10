package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.task.TokenTask;
import com.batherphilippa.soundstream.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.batherphilippa.soundstream.utils.Constants.UI_NOTIFICATION_BLANK_QUERY;

public class AppController implements Initializable {

    @FXML
    private TextField artistInput;

    @FXML
    private Button searchBtn;

    @FXML
    private TabPane tabPaneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // get auth token on app initialisation
        TokenTask tokenTask = new TokenTask();
        new Thread(tokenTask).start();

        tabPaneManager.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
    }

    @FXML
    void searchAlbums(ActionEvent event) {
        String artistQuery = this.artistInput.getText();

        if(artistQuery.trim().length() == 0) {
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_BLANK_QUERY, Alert.AlertType.INFORMATION);
            return;
        } else {
            launchTabController(artistQuery);
        }

        this.artistInput.clear();
        this.artistInput.requestFocus();

    }

    private void launchTabController(String query) {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/soundstream/progress_pane.fxml")));
        TabController tabController = new TabController(query);
        loader.setController(tabController);
        openTab(loader, query, tabController);
    }

    private void openTab(FXMLLoader loader, String query, TabController controller) {
        Tab tab;
        try {
            tab = new Tab(query, loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        controller.setTab(tab);
        tabPaneManager.getTabs().add(tab);
    }

}
