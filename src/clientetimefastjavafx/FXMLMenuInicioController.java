/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.pojo.UsuarioSesion;
import clientetimefastjavafx.utilidades.Utilidades;
import java.net.URL;
import java.text.SimpleDateFormat;
import javafx.util.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    @FXML
    private Label lbNombre;
    @FXML
    private ImageView imgCarrusel;

    private final List<String> rutasImagenes = Arrays.asList(
            getClass().getResource("/clientetimefastjavafx/recursos/imagen8.jpg").toExternalForm(),
            getClass().getResource("/clientetimefastjavafx/recursos/imagen4.jpg").toExternalForm(),
            getClass().getResource("/clientetimefastjavafx/recursos/imagen9.jpg").toExternalForm(),
            getClass().getResource("/clientetimefastjavafx/recursos/imagen10.jpg").toExternalForm(),
            getClass().getResource("/clientetimefastjavafx/recursos/imagen11.jpg").toExternalForm(),
            getClass().getResource("/clientetimefastjavafx/recursos/imagen12.jpg").toExternalForm(),
            getClass().getResource("/clientetimefastjavafx/recursos/imagen13.jpg").toExternalForm()
    );

    private int indiceActual = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obtenerHora();

        String nombreEdicion = UsuarioSesion.getInstancia().getNombreCompleto();
        lbNombre.setText("¡Hola " + nombreEdicion + "!!");

        iniciarCarrusel();

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

    private void iniciarCarrusel() {
        Timeline timeline = new Timeline(
                new KeyFrame(javafx.util.Duration.seconds(5), event -> {
                    try {
                        Image nuevaImagen = new Image(
                                rutasImagenes.get(indiceActual),
                                imgCarrusel.getFitWidth(),
                                imgCarrusel.getFitHeight(),
                                true,
                                true
                        );
                        imgCarrusel.setImage(nuevaImagen);
                        indiceActual = (indiceActual + 1) % rutasImagenes.size();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void onClickCerrarSesion(MouseEvent event) {
        irPantallaLogin();
    }

    public void irPantallaLogin() {
        try {
            Stage escenarioBase = (Stage) lbHora.getScene().getWindow();
            Parent pantallaLogin = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Scene escenaPrincipal = new Scene(pantallaLogin);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Login");
            escenarioBase.show();
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el login", Alert.AlertType.ERROR);
        }
    }

}
