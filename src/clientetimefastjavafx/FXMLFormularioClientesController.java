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
import java.util.regex.Pattern;
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
        
        int maxLengthLetra = 50;

        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > maxLengthLetra) {
                tfNombre.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfApellidoP.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > maxLengthLetra) {
                tfApellidoP.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfApellidoM.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > maxLengthLetra) {
                tfApellidoM.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfCalle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > 30) {
                tfCalle.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.length() > 4) {
                tfNumero.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfCp.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.length() > 5) {
                tfCp.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfColonia.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > 30) {
                tfColonia.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir caracteres válidos en un correo electrónico
            if (!newValue.matches("[a-zA-Z0-9@._-]*") || newValue.length() > 50) {
                tfCorreo.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
        
        tfTelefono.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.length() > 10) {
                tfTelefono.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });
    }

    @FXML
    private void onClickGuardarClente(ActionEvent event) {
        
        if (!validarCampos()) {
            return;
        }
        
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
     
     private boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfApellidoM.getText().isEmpty() || tfApellidoP.getText().isEmpty() || tfCalle.getText().isEmpty() || 
            tfNumero.getText().isEmpty() || tfColonia.getText().isEmpty() || tfCp.getText().isEmpty() || 
            tfTelefono.getText().isEmpty() || tfCorreo.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor, completa todos los campos requeridos.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (!esTextoValido(tfNombre.getText()) || !esTextoValido(tfApellidoP.getText()) || !esTextoValido(tfApellidoM.getText()) || !esTextoValido(tfCalle.getText()) || !esTextoValido(tfColonia.getText())) {
            Utilidades.mostrarAlertaSimple("Formato inválido", "Los campos de nombre, apellidos, calle y colonia no deben contener números ni caracteres especiales.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfNombre.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El nombre es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfApellidoP.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El apellido paterno es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfApellidoM.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El apellido materno es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfCalle.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El nombre de la calle es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfColonia.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El nombre de la colonia es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }

        if (!esNumerico(tfNumero.getText()) || !esNumerico(tfCp.getText()) || !esNumerico(tfTelefono.getText())) {
            Utilidades.mostrarAlertaSimple("Formato inválido", "El número de calle, el código postal y el teléfono deben contener solo números.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfNumero.getText().length() > 4) {
            Utilidades.mostrarAlertaSimple("Numero de calle invalido", "El número de calle ser debe ser menor o igual a 4 dígitos.", Alert.AlertType.WARNING);
            return false;
        }
        
        if (tfTelefono.getText().length() != 10) {
            Utilidades.mostrarAlertaSimple("Teléfono inválido", "El número de teléfono debe tener exactamente 10 dígitos.", Alert.AlertType.WARNING);
            return false;
        }

        if (!esCorreoValido(tfCorreo.getText())) {
            Utilidades.mostrarAlertaSimple("Correo inválido", "Por favor, ingresa un correo electrónico válido.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }
     
    private boolean esTextoValido(String texto) {
        String patronTexto = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";
        return Pattern.matches(patronTexto, texto);
    }

    private boolean esNumerico(String texto) {
        return texto.matches("\\d+");
    }

    private boolean esCorreoValido(String correo) {
        String patronCorreo = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(patronCorreo, correo);
    }

}
