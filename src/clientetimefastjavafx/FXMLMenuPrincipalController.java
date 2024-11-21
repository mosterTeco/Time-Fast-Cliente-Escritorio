/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private VBox bvxOpciones;
    @FXML
    private AnchorPane pnlContenido;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarEventosMenu();

        Node inicioNode = bvxOpciones.getChildren().get(0);
        if (inicioNode instanceof AnchorPane) {
            cambiarContenido((AnchorPane) inicioNode);
        }
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

        String archivoFXML = "";
        switch (textoSeccion) {
            case "Inicio":
                archivoFXML = "/clientetimefastjavafx/FXMLMenuInicio.fxml";
                break;
            case "Colaboradores":
                archivoFXML = "/clientetimefastjavafx/FXMLMenuColaboradores.fxml";
                break;
            default:
                Label errorLabel = new Label("Sección no encontrada");
                errorLabel.setStyle("-fx-font-size: 18; -fx-text-fill: red;");
                pnlContenido.getChildren().add(errorLabel);
                return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(archivoFXML));
            Node contenido = loader.load();
            pnlContenido.getChildren().add(contenido);
        } catch (IOException ex) {
            ex.printStackTrace();
            Label errorLabel = new Label("Error al cargar la sección: " + textoSeccion);
            errorLabel.setStyle("-fx-font-size: 18; -fx-text-fill: red;");
            pnlContenido.getChildren().add(errorLabel);
        }
    }
}
