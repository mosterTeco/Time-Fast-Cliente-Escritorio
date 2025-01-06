/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.LoginDAO;
import clientetimefastjavafx.pojo.Login;
import clientetimefastjavafx.pojo.UsuarioSesion;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author reyes
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField tfNumeroPersonal;
    @FXML
    private TextField tfPassword;
    @FXML
    private Label lbErrorNoPersonal;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private TextField tfPasswordVisible;

    private boolean passwordVisible = false;

    private final String ICONO_OJO_ABIERTO = getClass().getResource("recursos/IconoOjoAbierto.png").toExternalForm();
    private final String ICONO_OJO_CERRADO = getClass().getResource("recursos/IconoOjoCerrado.png").toExternalForm();

    @FXML
    private ImageView ivTogglePassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfPasswordVisible.textProperty().bindBidirectional(tfPassword.textProperty());
    }

    @FXML
    private void onClickIrPantallaPrincipal(ActionEvent event) {
        String numeroPersonal = tfNumeroPersonal.getText();
        String password = tfPassword.getText();

        if (validarCampos(numeroPersonal, password)) {
            verificarCredencialesSistema(numeroPersonal, password);
        }
    }

    private void verificarCredencialesSistema(String numeroPersonal, String password) {
        Login respuestaLogin = LoginDAO.iniciarSesion(numeroPersonal, password);
        if (!respuestaLogin.getError()) {
            UsuarioSesion.getInstancia().setNombreCompleto(respuestaLogin.getColaborador().getNombre() + " "
                    + respuestaLogin.getColaborador().getApellidoPaterno() + " "
                    + respuestaLogin.getColaborador().getApellidoMaterno());
            UsuarioSesion.getInstancia().setNumeroPersonal(numeroPersonal);

            Utilidades.mostrarAlertaSimple("Bienvenid@", "Bienvenid@ al sistema Time Fast, "
                    + UsuarioSesion.getInstancia().getNombreCompleto(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal();
        } else {
            Utilidades.mostrarAlertaSimple("Error", respuestaLogin.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private boolean validarCampos(String numeroPersonal, String password) {
        boolean camposValidos = true;
        lbErrorNoPersonal.setText("");
        lbErrorPassword.setText("");

        if (numeroPersonal.isEmpty()) {
            camposValidos = false;
            lbErrorNoPersonal.setText("Número de personal obligatorio");
        }

        if (password.isEmpty()) {
            camposValidos = false;
            lbErrorPassword.setText("Contraseña obligatoria");
        }

        return camposValidos;
    }

    public void irPantallaPrincipal() {
        try {
            Stage escenarioBase = (Stage) tfNumeroPersonal.getScene().getWindow();
            Parent pantallaPrincipal = FXMLLoader.load(getClass().getResource("FXMLMenuPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(pantallaPrincipal);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Pantalla principal");
            escenarioBase.show();
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el menu", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void togglePasswordVisibility(MouseEvent event) {
        passwordVisible = !passwordVisible;

        if (passwordVisible) {
            tfPasswordVisible.setVisible(true);
            tfPassword.setVisible(false);
            ivTogglePassword.setImage(new Image(ICONO_OJO_ABIERTO));
        } else {
            tfPasswordVisible.setVisible(false);
            tfPassword.setVisible(true);
            ivTogglePassword.setImage(new Image(ICONO_OJO_CERRADO));
        }
    }

}
