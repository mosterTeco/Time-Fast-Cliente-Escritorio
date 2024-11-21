/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuInicioController implements Initializable {

    @FXML
    private Label lbHora;
    @FXML
    private Label lbFecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerHora();
    }

    public void obtenerHora() {
        SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Date now = new Date();

                javafx.application.Platform.runLater(() -> {
                    lbHora.setText(horaFormato.format(now));
                    lbFecha.setText(fechaFormato.format(now));
                });
            }
        }, 0, 1000);
    }

}
