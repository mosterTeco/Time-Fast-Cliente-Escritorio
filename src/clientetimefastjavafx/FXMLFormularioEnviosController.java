/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ClienteDAO;
import clientetimefastjavafx.modelo.dao.EstadoDAO;
import clientetimefastjavafx.modelo.dao.RolDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Envio;
import clientetimefastjavafx.pojo.Estado;
import clientetimefastjavafx.pojo.Rol;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioEnviosController implements Initializable {

    
    private NotificadorOperacion observador;

    private Envio envioEdicion;
    @FXML
    private TextField tfCalleO;
    @FXML
    private TextField tfNumeroO;
    @FXML
    private TextField tfCpO;
    @FXML
    private TextField tfCiudadO;
    @FXML
    private ComboBox<Estado> comboBoxEstado;
    @FXML
    private TextField tfDestino;
    @FXML
    private TextField tfNumeroGuia;
    @FXML
    private TextField tfCostoEnvio;
    @FXML
    private ComboBox<Estado> comboBoxEstatus;
    @FXML
    private TextField tfColoniaO;
    @FXML
    private ComboBox<Cliente> comboBoxClientes;
    
    List<Estado> estados = EstadoDAO.obtenerEstados();
    
    ObservableList<Estado> tiposEstados = FXCollections.observableArrayList(estados);
    
    List<Cliente> clientes = ClienteDAO.obtenerClientes();
    
    ObservableList<Cliente> tiposClientes = FXCollections.observableArrayList(clientes);
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        cargarClientes();
        // TODO
       
        tfDestino.setEditable(false);
        
        comboBoxClientes.valueProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            String direccion = newValue.getCalle() + " " +
                               newValue.getColonia() + " " +
                               newValue.getCp() + " " +
                               newValue.getNumero();
            tfDestino.setText(direccion);
        }
    });
        
    }
    
    public void inicializarValores(NotificadorOperacion observador, Envio envioEdicion) {
        this.observador = observador;
        this.envioEdicion = envioEdicion;

        if (envioEdicion != null) {
       
        } else {

        }
    }

    @FXML
    private void OnClickSeleccionarConductor(ActionEvent event) {
        try {
            Stage escenarioSelec = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLConductoresDisponibles.fxml"));
            Scene scene = new Scene(root);
            escenarioSelec.setScene(scene);
            escenarioSelec.setTitle("Seleccionar conudctor");
            escenarioSelec.initModality(Modality.APPLICATION_MODAL);
            escenarioSelec.showAndWait();
        } catch (IOException e) {

        }
    }
    
    private void cargarEstados() {
        if (estados != null && !estados.isEmpty()) {
            System.out.println("Roles cargados: " + estados.size());
            comboBoxEstado.setItems(tiposEstados);
        } else {
            System.out.println("No se pudieron cargar los estados");
        }
    }
    
    private void cargarClientes() {
        if (clientes != null && !clientes.isEmpty()) {
            System.out.println("Clientes cargados: " + clientes.size());
            comboBoxClientes.setItems(tiposClientes);
        } else {
            System.out.println("No se pudieron cargar los estados");
        }
    }

    
    
     private int buscarIdEstado(int idEstado) {
        for (int i = 0; i < tiposEstados.size(); i++) {
            if (tiposEstados.get(i).getId() == idEstado) {
                return i;
            }
        }
        return 0;
    }
    
}
