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

import static com.batherphilippa.soundstream.utils.Constants.*;
import static com.batherphilippa.soundstream.utils.StringUtils.formatTrackQuery;

/**
 * TrackController - controlador para manejar el Task de un busqueda para una canción, listar los resultados en una
 * pestaña y manejar los filtarados apliacados a los resultados: la clave y el artista.
 * Implementa Initializable y MusicController.
 */
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
    private final String query;
    private Tab tab;

    private TrackTask trackTask;
    private ObservableList<TrackDTOOut> tracks;

    public TrackController(String trackQuery, String artistQuery) {
        this.query = formatTrackQuery(trackQuery, artistQuery);  // formatea la cadena
        this.tab = new Tab();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // crea un Observable Array List que puede refleja cambios/actualizaciones
        this.tracks = FXCollections.observableArrayList();
        // vincula el Observable Array List a la ListView que está pintado en la aplicación
        this.respListView.setItems(this.tracks);

        // crea y empieza el Task
        this.trackTask = new TrackTask(this.query, this.tracks);
        new Thread(trackTask).start();

        // establece el texto y botones de radio para los filtros
        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);
        this.filterBtn.requestFocus();
        this.radioBtnOne.setText(RADIO_BTN_KEY);
        this.radioBtnTwo.setText(RADIO_BTN_ARTIST_EXPRESSION);

        // establece el throbber (ícono de carga) y vincluralo al Task
        this.progIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.progIndicator.visibleProperty().bind(this.trackTask.runningProperty());
    }

    @FXML
    private void filterList(ActionEvent event) {
        // coge la condición de filtar (entrada del usuario)
        String filter = this.filterInputTxt.getText().toLowerCase();
        // re-establece el aviso
        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);

        // filta la vista de listado de respuestas según el filtro selecionado y la condición
        // método filtered() no modifica la lista permanente
        if(radioBtnOne.isSelected()) {
            this.respListView.setItems(this.tracks.filtered(t -> t.getKey().toLowerCase().contains(filter)));
            this.radioBtnOne.setSelected(false);  // re-establece el botón a no seleccionado
        } else if (radioBtnTwo.isSelected()) {
            this.respListView.setItems(this.tracks.filtered(t -> t.getArtist().toLowerCase().contains(filter)));
            this.radioBtnTwo.setSelected(false);   // re-establece el botón a no seleccionado
        } else {
            // notifica al usuario si ha intenado a aplicar un filtro sin selecionar una opción de filtrar
            NotificationUtils.showAlertDialog(UI_NOTIFICATION_UNSELECTED_FILTER, Alert.AlertType.INFORMATION);
        }
    }

    /**
     * Resetablece la lista con los álbums sin filtro
     * @param event - deshacer un filtro
     */
    @FXML
    void undoAppliedFilter(ActionEvent event) {
        this.respListView.setItems(this.tracks);
    }

    /**
     * Crea el tab; si el Task está en marcha, cuando el tab está cerrado, el Task será terminado.
     * @param tab - pestaña que contiene los resultados de una busqueda
     */
    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
        tab.setOnClosed(e -> this.trackTask.cancel());
    }

}
