package com.batherphilippa.soundstream.controller;

import com.batherphilippa.soundstream.model.dto.TrackDTOOut;
import com.batherphilippa.soundstream.task.TrackTask;
import com.batherphilippa.soundstream.utils.NotificationUtils;
import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.batherphilippa.soundstream.utils.Constants.*;
import static com.batherphilippa.soundstream.utils.NotificationUtils.showAlertDialog;
import static com.batherphilippa.soundstream.utils.NotificationUtils.showFileDirectoryChooser;
import static com.batherphilippa.soundstream.utils.StringUtils.formatCSVFilename;
import static com.batherphilippa.soundstream.utils.StringUtils.formatTrackQuery;

/**
 * TrackController - controlador para manejar el Task de un busqueda para una canción, listar los resultados en una
 * pestaña y manejar los filtarados apliacados a los resultados: la clave y el artista.
 * Implementa Initializable y MusicController.
 */
public class TrackController extends Component implements Initializable, MusicController {

    @FXML
    private AnchorPane responsePane;

    @FXML
    private TextField txtNotification;

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

    @FXML
    private ImageView imgView;

    @FXML
    private Button btnCSV;

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
        // crea un Observable Array List que puede reflejar cambios/actualizaciones
        this.tracks = FXCollections.observableArrayList();

        initializeListView();

        // crea y empieza el Task
        this.trackTask = new TrackTask(this.query, this.tracks);
        new Thread(trackTask).start();

        initializeUIFeatures();
        initializeThrobber();
        initializeOnSucceededActions();
    }

    /**
     * Inicializa la vista de la lista
     */
    private void initializeListView() {
        // vincula el Observable Array List a la ListView que está pintado en la aplicación
        this.respListView.setItems(this.tracks);
        // establece el modo de selección
        this.respListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // añade un listener para cambiar el imagén de un álbum depende del campo seleccionado
        this.respListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, trackDTOOut, t1) -> {
            String imgURL = t1.getImgURL();
            imgView.setImage(new Image(imgURL));
        }));

    }

    /**
     * Establece los textos y botones de radio para los filtros.
     */
    private void initializeUIFeatures() {
        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);
        this.filterBtn.requestFocus();
        this.radioBtnOne.setText(RADIO_BTN_KEY);
        this.radioBtnTwo.setText(RADIO_BTN_ARTIST_EXPRESSION);
        this.txtNotification.setVisible(false);
        this.btnCSV.setDisable(true);
    }

    /**
     * Establece el throbber (ícono de carga) y vincluralo al Task
     */
    private void initializeThrobber() {
        this.progIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.progIndicator.visibleProperty().bind(this.trackTask.runningProperty());
    }

    /**
     * Inicializa las acciones para actualizar cuando el task ha terminado con éxito.
     */
    private void initializeOnSucceededActions() {
        trackTask.setOnSucceeded(event -> {
            handleNoRecords();
            renderDefaultImage();
            this.btnCSV.setDisable(false);
        });
    }

    /**
     * Muestra el text field de notificación para informar al usuario si no campos están encontrados.
     */
    private void handleNoRecords() {
        if (respListView.getItems().size() == 0) {
            txtNotification.setVisible(true);
            txtNotification.setText("No records found.");
            txtNotification.setStyle("-fx-background-color: #f0ffff; -fx-text-fill: red; -fx-border-color: red;");
        }
    }

    /**
     * Establece y pinta el imagén del primer álbum en la lista cuando el Task ha terminado con éxito y si un ítem
     * en la lista está seleccionado.
     */
    private void renderDefaultImage() {
        if (respListView.getSelectionModel().getSelectedItem() == null) {
            String imgURL = tracks.get(0).getImgURL();
            imgView.setImage(new Image(imgURL));
        }
    }

    @FXML
    private void filterList(ActionEvent event) {
        // coge la condición de filtar (entrada del usuario)
        String filter = this.filterInputTxt.getText().toLowerCase();
        // re-establece el aviso
        this.filterInputTxt.setText(PROMPT_TRACK_FILTER);

        // filta la vista de listado de respuestas según el filtro selecionado y la condición
        // método filtered() no modifica la lista permanente
        if (radioBtnOne.isSelected()) {
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
     *
     * @param event - deshacer un filtro
     */
    @FXML
    void undoAppliedFilter(ActionEvent event) {
        this.respListView.setItems(this.tracks);
    }

    /**
     * Maneja escribir los registros a un archivo CSV.  Para guardar el archivo, el usuario debe elegir un directorio
     * desde el Directory Chooser; el nombre del archivo está formateado para reflejar la consulta introducida por el
     * usuario y el tipo de Audio Features.
     * @param event - botón pulsado
     * @throws IOException
     */
    @FXML
    void handleWriteDataToCSV(ActionEvent event) throws IOException {
        // formatea el nombre del archivo
        String filename = formatCSVFilename(this.query, TAB_AUDIO_FEATURES);
        String dir = showFileDirectoryChooser(btnCSV);

        String path;
        if (dir != null) {
            path = dir.concat(filename);
            try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
                // define la cabecera
                String[] header = {CSV_HEADER_ARTIST, CSV_HEADER_ALBUM, CSV_HEADER_KEY, CSV_HEADER_BPM, CSV_HEADER_TIME_SIGNATURE};
                writer.writeNext(header);

                // escribe los registros
                for (TrackDTOOut track : tracks) {
                    // formatado para prevenir la conversión automática a una fecha en Excel; resultado guardado por e.j. "4/4"
                    String timeSig = "\" " + track.getTimeSignature() + " \" ";
                    String[] record = {track.getArtist(), track.getAlbum(), track.getKey(), track.getBpm(), timeSig};
                    writer.writeNext(record);
                }
                showAlertDialog("CSV file " + filename + " saved in directory " + dir, Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                showAlertDialog(UI_NOTIFICATION_ERROR_WRITING_TO_CSV, Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }


    /**
     * Crea el tab; si el Task está en marcha, cuando el tab está cerrado, el Task será terminado.
     *
     * @param tab - pestaña que contiene los resultados de una busqueda
     */
    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
        tab.setOnClosed(e -> this.trackTask.cancel());
    }

}
