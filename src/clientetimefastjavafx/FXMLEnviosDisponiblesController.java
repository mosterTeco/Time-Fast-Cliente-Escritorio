/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ClienteDAO;
import clientetimefastjavafx.modelo.dao.EnvioDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Envio;
import clientetimefastjavafx.utilidades.Utilidades;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLEnviosDisponiblesController implements Initializable {

    private NotificadorOperacion observador;

    @FXML
    private TableView<Envio> tblEnvios;
    @FXML
    private TableColumn<?, ?> colNumeroGuia;
    @FXML
    private TableColumn<?, ?> colCliente;
    @FXML
    private TableColumn<?, ?> colOrigen;
    @FXML
    private TableColumn<?, ?> colDestino;
    @FXML
    private TableColumn<?, ?> colCosto;
    @FXML
    private Button btn;

    private Integer idEnvio;

    private ObservableList<Envio> envios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicializarValores(observador);
        configurarTabla();
        cargarInformacionTabla();
    }

    void inicializarValores(NotificadorOperacion observador) {
        this.observador = observador;
    }

    @FXML
    private void envioSeleccionado(MouseEvent event) {
        idEnvio = tblEnvios.getSelectionModel().getSelectedItem().getId();
    }

    @FXML
    private void SeleccionarEnvio(ActionEvent event) {
        if (idEnvio != null && idEnvio > 0) {
            observador.notificarOperacion("idEnvio", idEnvio.toString());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("ERROR", "No ha seleccionado ningún envío", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void CerrarVentana(ActionEvent event) {
        Stage base = (Stage) tblEnvios.getScene().getWindow();
        base.close();
    }

    private void configurarTabla() {
        colNumeroGuia.setCellValueFactory(new PropertyValueFactory("numeroGuia"));
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colOrigen.setCellValueFactory(new PropertyValueFactory("origen"));
        colDestino.setCellValueFactory(new PropertyValueFactory("destino"));
        colCosto.setCellValueFactory(new PropertyValueFactory("costo"));
    }

    private void cargarInformacionTabla() {
        envios = FXCollections.observableArrayList();
        List<Envio> listaWS = EnvioDAO.obtenerEnvios();

        if (listaWS != null) {
            envios.addAll(listaWS);
            tblEnvios.setItems(envios);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Por el momento no se puede cargar la informacion de los Clientes. Intentalo más tarde.", Alert.AlertType.ERROR);
        }
    }

    private void cerrarVentana() {
        Stage base = (Stage) tblEnvios.getScene().getWindow();
        base.close();
    }

}
