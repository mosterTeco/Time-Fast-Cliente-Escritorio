/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ClienteDAO;
import clientetimefastjavafx.modelo.dao.ColaboradorDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLMenuClientesController implements Initializable, NotificadorOperacion {
    
    private ObservableList<Cliente> clientes;

    @FXML
    private TableView<Cliente> tblClientes;
    @FXML
    private TableColumn colNombreCliente;
    @FXML
    private TableColumn colApellidoP;
    @FXML
    private TableColumn colApellidoM;
    @FXML
    private TableColumn colCorreo;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private TableColumn colCalle;
    @FXML
    private TableColumn colColonia;
    @FXML
    private TableColumn colCp;
    @FXML
    private TableColumn colNumero;

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
    private void OnClickAgregarCliente(ActionEvent event) {
        irFormulario(this, null);
    }
    
    private void configurarTabla() {
        colNombreCliente.setCellValueFactory(new PropertyValueFactory("nombre"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        colApellidoM.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        colCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        colColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        colCp.setCellValueFactory(new PropertyValueFactory("cp"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numero"));
    }
    
    private void cargarInformacionTabla() {
        clientes = FXCollections.observableArrayList();
        List<Cliente> listaWS = ClienteDAO.obtenerClientes();
        
        if (listaWS != null) {
            clientes.addAll(listaWS);
            tblClientes.setItems(clientes);
        } else {
            Utilidades.mostrarAlertaSimple("Error al cargar", "Por el momento no se puede cargar la informacion de los Clientes. Intentalo más tarde.", Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(NotificadorOperacion observador, Cliente cliente) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLFormularioClientes.fxml"));
            Parent root = loader.load();
            //--
            FXMLFormularioClientesController controlador = loader.getController();
            controlador.inicializarValores(observador, cliente);
            //--
            Stage escenarioForm = new Stage();
            Scene escenaFormulario = new Scene(root);
            escenarioForm.setScene(escenaFormulario);
            escenarioForm.setTitle("Clientes");
            escenarioForm.initModality(Modality.APPLICATION_MODAL);
            escenarioForm.showAndWait();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error", "Lo sentimos, paso algo y no se puede mostrar el formulario", Alert.AlertType.ERROR);
        }
    }
    
    

    @Override
    public void notificarOperacion(String tipo, String nombre) {
        System.out.println("Tipo operacion: " + tipo);
        System.out.println("Nombre colaborador: " + nombre);
        cargarInformacionTabla();
    }
    
}
