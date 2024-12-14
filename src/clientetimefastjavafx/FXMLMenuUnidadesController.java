/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.UnidadDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
public class FXMLMenuUnidadesController implements Initializable, NotificadorOperacion {

    private ObservableList<Unidad> unidades;

    @FXML
    private TableView<Unidad> tblUnidades;
    @FXML
    private TableColumn colNii;
    @FXML
    private TableColumn colModelo;
    @FXML
    private TableColumn colAnio;
    @FXML
    private TableColumn colVin;
    @FXML
    private TableColumn colTipoUnidad;
    @FXML
    private TableColumn colConductor;
    @FXML
    private TableColumn colMarca;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TextField tfBuscar;

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
    private void OnClickAgregarUnidad(ActionEvent event) {
        irFormulario(this, null);
    }

    @FXML
    private void OnClickEliminarUnidad(ActionEvent event) {
        Unidad unidad = tblUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            if (unidad.getNombreColaborador() != null && !unidad.getNombreColaborador().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Unidad no válida", "La unidad seleccionada tiene un conductor asignado y no puede ser dada de baja.", Alert.AlertType.WARNING);
                return;
            }
            if ("Baja".equalsIgnoreCase(unidad.getEstado())) {
                Utilidades.mostrarAlertaSimple("Unidad no válida", "La unidad seleccionada ya está en estado 'Baja'", Alert.AlertType.WARNING);
                return;
            }
            irFormularioDarBaja(this, unidad);
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar unidad", "Para eliminar debes seleccionar una unidad de la tabla.", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(NotificadorOperacion observador, Unidad unidad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioUnidades.fxml"));
            Parent root = loader.load();
            //--
            FXMLFormularioUnidadesController controlador = loader.getController();
            controlador.inicializarValores(observador, unidad);
            //--
            Stage escenarioForm = new Stage();
            Scene escenaFormulario = new Scene(root);
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Unidades");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el formulario", Alert.AlertType.ERROR);
        }
    }

    private void configurarTabla() {
        colNii.setCellValueFactory(new PropertyValueFactory("nii"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        colVin.setCellValueFactory(new PropertyValueFactory("vin"));
        colTipoUnidad.setCellValueFactory(new PropertyValueFactory("nombre"));
        colConductor.setCellValueFactory(new PropertyValueFactory("nombreColaborador"));
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }

    private void cargarInformacionTabla() {
        unidades = FXCollections.observableArrayList();
        List<Unidad> listaWS = UnidadDAO.obtenerUnidades();
        if (listaWS != null) {
            unidades.addAll(listaWS);
            tblUnidades.setItems(unidades);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Por el momento no se puede cargar la informacion de las Unidades. Intentalo más tarde.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        System.out.println("Tipo operacion: " + tipo);
        System.out.println("Nombre colaborador: " + nombre);
        cargarInformacionTabla();
    }

    @FXML
    private void OnClickEditarUnidad(ActionEvent event) {
        Unidad unidad = tblUnidades.getSelectionModel().getSelectedItem();
        if (unidad != null) {
            irFormulario(this, unidad);
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar unidad", "Para editar debes seleccioar un colaborador de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irFormularioDarBaja(NotificadorOperacion observador, Unidad unidad) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLEliminarUnidades.fxml"));
            Parent root = loader.load();
            //--
            FXMLEliminarUnidadesController controlador = loader.getController();
            controlador.inicializarValores(observador, unidad);
            //--
            Stage escenarioForm = new Stage();
            Scene escenaFormulario = new Scene(root);
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Unidades");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el formulario", Alert.AlertType.ERROR);
        }
    }
    
     private void configurarFiltroBusqueda() {
        FilteredList<Unidad> filteredData = new FilteredList<>(unidades, p -> true);

        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(unidad -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (unidad.getVin().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (unidad.getMarca().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (unidad.getNii().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; 
            });
        });

        SortedList<Unidad> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblUnidades.comparatorProperty());
        tblUnidades.setItems(sortedData);
    }
    

}
