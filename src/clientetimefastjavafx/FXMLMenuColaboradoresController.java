/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ColaboradorDAO;
import clientetimefastjavafx.modelo.dao.UnidadDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.UsuarioSesion;
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
public class FXMLMenuColaboradoresController implements Initializable, NotificadorOperacion {

    private ObservableList<Colaborador> colaboradores;

    @FXML
    private TextField txfBuscarCol;
    @FXML
    private TableView<Colaborador> tblColaboradores;
    @FXML
    private TableColumn colNoPersonal;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colApellidoM;
    @FXML
    private TableColumn colCurp;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colRol;

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
    private void OnClickAgregarColaborador(ActionEvent event) {
        irFormulario(this, null);

    }

    private void configurarTabla() {
        colNoPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCurp.setCellValueFactory(new PropertyValueFactory("curp"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory("rol"));
    }

    private void cargarInformacionTabla() {
        colaboradores = FXCollections.observableArrayList();
        List<Colaborador> listaWS = ColaboradorDAO.obtenerColaboradores();
        if (listaWS != null) {
            colaboradores.addAll(listaWS);
            tblColaboradores.setItems(colaboradores);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Por el momento no se puede cargar la informacion de los Colaboradores. Intentalo más tarde.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        System.out.println("Tipo operacion: " + tipo);
        System.out.println("Nombre colaborador: " + nombre);
        cargarInformacionTabla();
    }

    @FXML
    private void OnClickEditarColaborador(ActionEvent event) {
        Colaborador colaborador = tblColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador != null) {
            irFormulario(this, colaborador);
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar colaborador", "Para editar debes seleccioar un colaborador de la tabla", Alert.AlertType.WARNING);
        }
    }

    private void irFormulario(NotificadorOperacion observador, Colaborador colaborador) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioColaboradores.fxml"));
            Parent root = loader.load();
            //--
            FXMLFormularioColaboradoresController controlador = loader.getController();
            controlador.inicializarValores(observador, colaborador);
            //--
            Stage escenarioForm = new Stage();
            Scene escenaFormulario = new Scene(root);
            escenarioForm.setResizable(false);
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Colaboradores");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el menu", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void OnClickEliminarColaborador(ActionEvent event) {
        Colaborador colaborador = tblColaboradores.getSelectionModel().getSelectedItem();
        String noPersonalActual = UsuarioSesion.getInstancia().getNumeroPersonal();
        
        if (colaborador != null) {
            
            if (colaborador.getNumeroPersonal().equals(noPersonalActual)) {
                Utilidades.mostrarAlertaSimple("Acción no permitida", "No puedes eliminar tu propia cuenta", Alert.AlertType.ERROR);
                return;
            }
            
            editarEstadoUnidad(colaborador.getIdUnidad(), "Disponible");
            eliminarColaborador(colaborador.getNumeroPersonal());
        } else {
            Utilidades.mostrarAlertaSimple("Seleccionar colaborador", "Para eliminar debes seleccioar un colaborador de la tabla", Alert.AlertType.WARNING);
        }

    }

    private void eliminarColaborador(String numeroPersonal) {
        Mensaje msj = ColaboradorDAO.eliminarColaborador(numeroPersonal);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Eliminacion exitosa", "El colaborador con No. Personal: " + numeroPersonal + ", fue eliminado de manera correcta", Alert.AlertType.INFORMATION);
            cargarInformacionTabla();
        } else {
            Utilidades.mostrarAlertaSimple("Error al eliminar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void configurarFiltroBusqueda() {
        FilteredList<Colaborador> filteredData = new FilteredList<>(colaboradores, p -> true);

        txfBuscarCol.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(colaborador -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (colaborador.getNumeroPersonal().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (colaborador.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (colaborador.getRol().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; 
            });
        });

        SortedList<Colaborador> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblColaboradores.comparatorProperty());
        tblColaboradores.setItems(sortedData);
    }
    
    private void editarEstadoUnidad(int id, String estado) {
        Mensaje msj = UnidadDAO.editarEstadoUnidad(id, estado);

        if (!msj.isError()) {
            System.out.println("Funciona correctamente");
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

}
