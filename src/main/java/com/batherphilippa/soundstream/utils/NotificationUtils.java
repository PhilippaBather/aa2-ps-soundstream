package com.batherphilippa.soundstream.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

import static com.batherphilippa.soundstream.utils.Constants.UI_NOTIFICATION_CSV_DIR_NOT_SELECTED;

/**
 * NotificationUtils - contiene métodos estáticos para mostrar notificaciones al usuario.
 */
public class NotificationUtils {

    /**
     * Muestra un diálogo de alerta.
     *
     * @param msg       - el mensaje para mostrar
     * @param alertType - el tipo de alerta requerida
     */
    public static void showAlertDialog(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }

    public static String showFileDirectoryChooser(Button btn) {
        // coge la escena por el botón pulsado
        Stage stage = (Stage) btn.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(stage);

        if (dir != null) {
            // añade la barra para para guardar archivos dentro la carpeta
            return dir.getAbsolutePath() + "/";
        } else {
            showAlertDialog(UI_NOTIFICATION_CSV_DIR_NOT_SELECTED, Alert.AlertType.INFORMATION);
        }
        return null;
    }
}
