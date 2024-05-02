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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

import static com.batherphilippa.soundstream.utils.Constants.*;
import static com.batherphilippa.soundstream.utils.StringUtils.formatQuery;

/**
 * AlbumnController - controlador para manejar el Task de un busqueda para un álbum, listar los resultados
 * en una pestaña y manejar los filtrados aplicados: año de lanzamiento ('release year') y nombre de album.
 * Implementa Initializable y MusicController.
 */
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

    @FXML
    private ImageView imgView;
    private final String query;
    private Tab tab;

    private AlbumTask albumTask;
    private ObservableList<AlbumDTOOut> albums;
    private ObservableList<String> urlHref;

    public AlbumController(String query) {
        this.query = formatQuery(query);  // formatea la cadena
        this.tab = new Tab();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // crea un Observable Array List que puede refleja cambios/actualizaciones
        this.albums = FXCollections.observableArrayList();
        this.urlHref = FXCollections.observableArrayList();
        // vincula el Observable Array List a la ListView que está pintado en la aplicación
        this.respListView.setItems(this.albums);

        // crea y empieza el Task
        this.albumTask = new AlbumTask(this.query, this.albums, this.urlHref);
        new Thread(albumTask).start();

        // establece el texto y botones de radio para los filtros
        this.filterInputTxt.setText(PROMPT_ALBUM_FILTER);
        this.filterBtn.requestFocus();
        this.radioBtnOne.setText(RADIO_BTN_RELEASE_YEAR);
        this.radioBtnTwo.setText(RADIO_BTN_ALBUM_EXPRESSION);

        // establece el throbber (ícono de carga) y vincluralo al Task
        this.progIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        this.progIndicator.visibleProperty().bind(this.albumTask.runningProperty());

        // carga el imagen cuando el Task ha terminado
        renderImageOnTaskSucceeded();
    }

    /**
     * Filtra la lista de resultados según el filtro seleccionado y la condición dado.
     *
     * @param event - filtar
     */
    @FXML
    private void filterList(ActionEvent event) {
        // coge la condición de filtar (entrada del usuario)
        String filter = this.filterInputTxt.getText().toLowerCase();
        // re-establece el aviso
        this.filterInputTxt.setText(PROMPT_ALBUM_FILTER);

        // filta la vista de listado de respuestas según el filtro selecionado y la condición
        // método filtered() no modifica la lista permanente
        if (radioBtnOne.isSelected()) {
            this.respListView.setItems(this.albums.filtered(a -> a.getRelease_date().contains(filter)));
            radioBtnOne.setSelected(false);  // re-establece el botón a no seleccionado
        } else if (radioBtnTwo.isSelected()) {
            this.respListView.setItems(this.albums.filtered(a -> a.getName().toLowerCase().contains(filter)));
            radioBtnTwo.setSelected(false);   // re-establece el botón a no seleccionado
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
        this.respListView.setItems(this.albums);
    }

    /**
     * Establece y pinta el imagén cuando el Task ha terminado con éxito.
     */
    private void renderImageOnTaskSucceeded() {
        albumTask.setOnSucceeded(event -> {
            String imgURL = albums.get(0).getImgURL();
            imgView.setImage(new Image(imgURL));
        });
    }

    /**
     * Crea el tab; si el Task está en marcha, cuando el tab está cerrado, el Task será terminado.
     *
     * @param tab - pestaña que contiene los resultados de una busqueda
     */
    @Override
    public void setTab(Tab tab) {
        this.tab = tab;
        tab.setOnClosed(e -> this.albumTask.cancel());
    }

}
