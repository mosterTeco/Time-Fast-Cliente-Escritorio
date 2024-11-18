/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuPrincipalController implements Initializable {

    @FXML
    private Label lbHora;
    @FXML
    private Label lbFecha;
    @FXML
    private VBox bvxOpciones;
    @FXML
    private AnchorPane pnlContenido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerHora();
        configurarEventosMenu();
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
    
    private void configurarEventosMenu() {
        
        for (Node node : bvxOpciones.getChildren()) {
            if (node instanceof AnchorPane) {
                node.setOnMouseClicked(event -> {
                    cambiarContenido((AnchorPane) node);
                });
            }
        }
    }
    
     private void cambiarContenido(AnchorPane seccionSeleccionada) {
        
        pnlContenido.getChildren().clear();

        String textoSeccion = ((Label) seccionSeleccionada.getChildren().get(1)).getText();

        Label nuevoContenido = new Label("Contenido de: " + textoSeccion);
        nuevoContenido.setStyle("-fx-font-size: 24; -fx-text-fill: #333;");
        nuevoContenido.setLayoutX(50);
        nuevoContenido.setLayoutY(50);

        pnlContenido.getChildren().add(nuevoContenido);
    }
}
