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
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Colaboradores");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el menu", Alert.AlertType.ERROR);
        }
    }

}
