/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ClienteDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.utilidades.Utilidades;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioClientesController implements Initializable {
    
    private NotificadorOperacion observador;

    private Cliente clienteEdicion;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoP;
    @FXML
    private TextField tfApellidoM;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCp;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    
    private boolean modoEdicion = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void inicializarValores(NotificadorOperacion observador, Cliente clienteEdicion) {
        this.observador = observador;
        this.clienteEdicion = clienteEdicion;

        if (clienteEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {

        }
    }

    @FXML
    private void onClickGuardarClente(ActionEvent event) {
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoP.getText();
        String apellidoMaterno = tfApellidoM.getText();
        String calle = tfCalle.getText();
        String numero = tfNumero.getText();
        String colonia = tfColonia.getText();
        String cp = tfCp.getText();
        String telefono = tfTelefono.getText();
        String correo = tfCorreo.getText();
        
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellidoPaterno(apellidoPaterno);
        cliente.setApellidoMaterno(apellidoMaterno);
        cliente.setCalle(calle);
        cliente.setNumero(numero);
        cliente.setColonia(colonia);
        cliente.setTelefono(telefono);
        cliente.setCorreo(correo);
        cliente.setCp(cp);
        
        if (!modoEdicion) {
            guardarDatosCliente(cliente);
        } else {
            cliente.setId(this.clienteEdicion.getId());
            editarDatosCliente(cliente);
        }
      
    }
    
    private void guardarDatosCliente(Cliente cliente) {
        Mensaje msj = ClienteDAO.registrarCliente(cliente);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", "La información del Colaborador " + cliente.getNombre() + " " + cliente.getApellidoPaterno() + ", fue registrada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Nuevo registro", cliente.getNombre());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarDatosEdicion(){
        tfNombre.setText(this.clienteEdicion.getNombre());
        tfApellidoP.setText(this.clienteEdicion.getApellidoPaterno());
        tfApellidoM.setText(this.clienteEdicion.getApellidoMaterno());
        tfCalle.setText(this.clienteEdicion.getCalle());
        tfColonia.setText(this.clienteEdicion.getColonia());
        tfCp.setText(this.clienteEdicion.getCp());
        tfCorreo.setText(this.clienteEdicion.getCorreo());
        tfTelefono.setText(this.clienteEdicion.getTelefono());
        tfNumero.setText(this.clienteEdicion.getNumero());
    }
    
    private void editarDatosCliente(Cliente cliente) {
        Mensaje msj = ClienteDAO.editarCliente(cliente);
        
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La información del Cliente " + cliente.getNombre() + " " + cliente.getApellidoPaterno() + ", fue actualizada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", cliente.getNombre());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void onClickCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
     private void cerrarVentana() {
        Stage base = (Stage) tfNombre.getScene().getWindow();
        base.close();
    }
}
