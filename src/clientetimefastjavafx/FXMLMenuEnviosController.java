/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.EnvioDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Envio;
import clientetimefastjavafx.pojo.Paquete;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuEnviosController implements Initializable, NotificadorOperacion {
    
    private ObservableList<Envio> envios;

    @FXML
    private TableView<Envio> tblEnvios;
    @FXML
    private TableColumn colNoGuia;
    @FXML
    private TableColumn colCliente;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colCosto;
    @FXML
    private TableColumn colCalleO;
    @FXML
    private TableColumn colColoniaO;
    @FXML
    private TableColumn colNumeroO;
    @FXML
    private TableColumn colCalleD;
    @FXML
    private TableColumn colColoniaD;
    @FXML
    private TableColumn colNumeroD;
    @FXML
    private TableColumn colConductor;
    @FXML
    private TextField tfBuscarEnvio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        cargarInformacionTabla();
        
        configurarFiltroBusqueda();
    }

    @FXML
    private void OnClickCrearEnvio(ActionEvent event) {
        irFormulario(this, null);
    }
    
    private void configurarTabla() {
        colNoGuia.setCellValueFactory(new PropertyValueFactory("numeroGuia"));
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        colCosto.setCellValueFactory(new PropertyValueFactory("costo"));
        colCalleO.setCellValueFactory(new PropertyValueFactory("calleOrigen"));
        colColoniaO.setCellValueFactory(new PropertyValueFactory("coloniaOrigen"));
        colNumeroO.setCellValueFactory(new PropertyValueFactory("numeroOrigen"));
        colCalleD.setCellValueFactory(new PropertyValueFactory("calleDestino"));
        colColoniaD.setCellValueFactory(new PropertyValueFactory("coloniaDestino"));
        colNumeroD.setCellValueFactory(new PropertyValueFactory("numeroDestino"));
        colConductor.setCellValueFactory(new PropertyValueFactory("colaborador"));
    }
    
    private void cargarInformacionTabla() {
        envios = FXCollections.observableArrayList();
        List<Envio> listaWS = EnvioDAO.obtenerEnvios();
        if (listaWS != null) {
            envios.addAll(listaWS);
            tblEnvios.setItems(envios);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Por el momento no se puede cargar la informacion de los Envios. Intentalo más tarde.", Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(NotificadorOperacion observador, Envio envio) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioEnvios.fxml"));
            Parent root = loader.load();
            //--
            FXMLFormularioEnviosController controlador = loader.getController();
            controlador.inicializarValores(observador, envio);
            //--
            Stage escenarioForm = new Stage();
            Scene escenaFormulario = new Scene(root);
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Envios");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el menu", Alert.AlertType.ERROR);
            System.out.println(ex);
        }
    }
    

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        System.out.println("Tipo operacion: " + tipo);
        System.out.println("Nombre colaborador: " + nombre);
        cargarInformacionTabla();
    }

    @FXML
    private void OnClickEditarEnvio(ActionEvent event) {
        Envio envio = tblEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            irFormulario(this, envio);
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar envio", "Para editar debes seleccioar un envio de la tabla", Alert.AlertType.WARNING);
        }
    }
    
    private void configurarFiltroBusqueda() {
        FilteredList<Envio> filteredData = new FilteredList<>(envios, p -> true);

        tfBuscarEnvio.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate((Envio envio) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (envio.getNumeroGuia().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } 
                
                return false; 
            });
        });
        
        SortedList<Envio> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblEnvios.comparatorProperty());
        tblEnvios.setItems(sortedData);
    }
}
