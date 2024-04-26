package com.batherphilippa.soundstream.utils;

import javafx.scene.control.Alert;

/**
 * NotificationUtils - contiene métodos estáticos para mostrar notificaciones al usuario.
 */
public class NotificationUtils {

    /**
     * Muestra un diálogo de alerta.
     * @param msg - el mensaje para mostrar
     * @param alertType - el tipo de alerta requerida
     */
    public static void showAlertDialog(String msg, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.show();
    }
}
