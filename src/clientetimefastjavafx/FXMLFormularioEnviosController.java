/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ClienteDAO;
import clientetimefastjavafx.modelo.dao.ColaboradorDAO;
import clientetimefastjavafx.modelo.dao.EnvioDAO;
import clientetimefastjavafx.modelo.dao.EstadoDAO;
import clientetimefastjavafx.modelo.dao.EstatusDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Cliente;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Envio;
import clientetimefastjavafx.pojo.Estado;
import clientetimefastjavafx.pojo.Estatus;
import clientetimefastjavafx.pojo.Mensaje;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
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
    private ComboBox<Estatus> comboBoxEstatus;
    @FXML
    private TextField tfColoniaO;
    @FXML
    private ComboBox<Cliente> comboBoxClientes;

    List<Estado> estados = EstadoDAO.obtenerEstados();

    ObservableList<Estado> tiposEstados = FXCollections.observableArrayList(estados);

    List<Cliente> clientes = ClienteDAO.obtenerClientes();

    ObservableList<Cliente> tiposClientes = FXCollections.observableArrayList(clientes);

    @FXML
    private ComboBox<Colaborador> comboBoxConductores;

    List<Colaborador> colaboradores = ColaboradorDAO.obtenerColaboradoresDisp();

    ObservableList<Colaborador> tiposColaboradores = FXCollections.observableArrayList(colaboradores);

    List<Estatus> estatus = EstatusDAO.obtenerEstatus();

    ObservableList<Estatus> tiposEstatus = FXCollections.observableArrayList(estatus);

    String calleDestino;

    String coloniaDestino;

    String numeroDestinoTexto;

    String cpDestino;

    private boolean modoEdicion = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstados();
        cargarClientes();
        cargarColaboradores();
        cargarEstatus();
        // TODO

        tfDestino.setEditable(false);

        comboBoxClientes.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String direccion = newValue.getCalle() + " "
                        + newValue.getColonia() + " "
                        + newValue.getCp() + " "
                        + newValue.getNumero();
                tfDestino.setText(direccion);

                calleDestino = newValue.getCalle();
                coloniaDestino = newValue.getColonia();
                numeroDestinoTexto = newValue.getNumero();
                cpDestino = newValue.getCp();
            }
        });

        comboBoxEstado.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxEstado.setStyle("");
            }
        });

        comboBoxClientes.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxClientes.setStyle("");
            }
        });

        comboBoxEstatus.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxEstatus.setStyle("");
            }
        });

        comboBoxConductores.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxConductores.setStyle("");
            }
        });
    }

    public void inicializarValores(NotificadorOperacion observador, Envio envioEdicion) {
        this.observador = observador;
        this.envioEdicion = envioEdicion;

        if (envioEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {

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

    private void cargarColaboradores() {
        if (colaboradores != null && !colaboradores.isEmpty()) {
            System.out.println("Clientes cargados: " + colaboradores.size());
            comboBoxConductores.setItems(tiposColaboradores);
        } else {
            System.out.println("No se pudieron cargar los conductores");
        }
    }

    private void cargarEstatus() {
        if (estatus != null && !estatus.isEmpty()) {
            System.out.println("Estatus cargados: " + estatus.size());
            comboBoxEstatus.setItems(tiposEstatus);
        } else {
            System.out.println("No se pudieron cargar los estatus");
        }
    }

    @FXML
    private void onClickGuardarEnvio(ActionEvent event) {
        String calleOrigen = tfCalleO.getText();
        String coloniaOrigen = tfColoniaO.getText();
        String numeroOrigenTexto = tfNumeroO.getText();
        Integer numeroOrigen = null;

        try {
            if (numeroOrigenTexto != null && !numeroOrigenTexto.isEmpty()) {
                numeroOrigen = Integer.parseInt(numeroOrigenTexto);
            }
        } catch (NumberFormatException e) {
            System.out.println("El número de origen no es válido: " + numeroOrigenTexto);
        }

        String cpOrigen = tfCpO.getText();
        String ciudadOrigen = tfCiudadO.getText();

        Integer idEstadoOrigen = (comboBoxEstado.getSelectionModel().getSelectedItem() != null)
                ? comboBoxEstado.getSelectionModel().getSelectedItem().getId() : null;

        Integer idCliente = (comboBoxClientes.getSelectionModel().getSelectedItem() != null)
                ? comboBoxClientes.getSelectionModel().getSelectedItem().getId() : null;

        String ciudadDestino = "Xalapa";

        String estadoDestino = "Xalapa";

        Integer idEstadoDestino = 1;

        Integer idConductor = (comboBoxConductores.getSelectionModel().getSelectedItem() != null)
                ? comboBoxConductores.getSelectionModel().getSelectedItem().getId() : null;

        String numeroGuia = tfNumeroGuia.getText();

        //String costo = tfCostoEnvio.getText();
        Integer idEstatus = (comboBoxEstatus.getSelectionModel().getSelectedItem() != null)
                ? comboBoxEstatus.getSelectionModel().getSelectedItem().getId() : null;

        Envio envio = new Envio();

        envio.setCalleOrigen(calleOrigen);
        envio.setColoniaOrigen(coloniaOrigen);
        envio.setNumeroOrigen(numeroOrigen);
        envio.setCpOrigen(cpOrigen);
        envio.setCiudadOrigen(ciudadOrigen);
        envio.setIdEstadoOrigen(idEstadoOrigen);
        envio.setIdCliente(idCliente);
        envio.setCalleDestino(calleDestino);
        envio.setColoniaDestino(coloniaDestino);
        Integer numeroDestino = Integer.parseInt(numeroDestinoTexto);
        envio.setNumeroDestino(numeroDestino);
        envio.setCpDestino(cpDestino);
        envio.setEstadoDestino(ciudadDestino);
        envio.setCiudadDestino(estadoDestino);
        envio.setIdEstadoDestino(idEstadoDestino);
        envio.setIdColaborador(idConductor);
        envio.setNumeroGuia(numeroGuia);

        float costo = 0.0f;

        try {
            costo = Float.parseFloat(tfCostoEnvio.getText());
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir el costo: " + e.getMessage());
        }

        envio.setCosto(costo);
        envio.setIdEstatus(idEstatus);

        if (!modoEdicion) {
            guardarDatosEnvio(envio);
        } else {
            envio.setId(this.envioEdicion.getId());
            editarDatosEnvio(envio);
        }
    }

    private void guardarDatosEnvio(Envio envio) {
        Mensaje msj = EnvioDAO.registrarEnvio(envio);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", "La informacion del envio fue guardada con exito", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Nuevo registro", envio.getNumeroGuia());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfCalleO.getScene().getWindow();
        base.close();
    }

    public void cargarDatosEdicion() {
        tfCalleO.setText(this.envioEdicion.getCalleOrigen());
        tfColoniaO.setText(this.envioEdicion.getColoniaOrigen());
        tfNumeroO.setText(this.envioEdicion.getNumeroOrigen().toString());
        tfCpO.setText(this.envioEdicion.getCpOrigen());
        tfCiudadO.setText(this.envioEdicion.getCiudadOrigen());

        int posicion = buscarIdEstado(this.envioEdicion.getIdEstadoOrigen());
        comboBoxEstado.getSelectionModel().select(posicion);

        int posicionCliente = buscarIdCliente(this.envioEdicion.getIdCliente());
        comboBoxClientes.getSelectionModel().select(posicionCliente);

        int posicionConductor = buscarIdColaborador(this.envioEdicion.getIdColaborador());
        comboBoxConductores.getSelectionModel().select(posicionConductor);

        tfNumeroGuia.setText(this.envioEdicion.getNumeroGuia());

        int posicionEstatus = buscarIdEstatus(this.envioEdicion.getIdEstatus());
        comboBoxEstatus.getSelectionModel().select(posicionEstatus);

        tfCostoEnvio.setText(this.envioEdicion.getCosto().toString());

    }

    private int buscarIdEstado(int idEstado) {
        for (int i = 0; i < tiposEstados.size(); i++) {
            if (tiposEstados.get(i).getId() == idEstado) {
                return i;
            }
        }
        return 0;
    }

    private int buscarIdCliente(int id) {
        for (int i = 0; i < tiposClientes.size(); i++) {
            if (tiposClientes.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }

    private int buscarIdColaborador(int id) {
        for (int i = 0; i < tiposColaboradores.size(); i++) {
            if (tiposColaboradores.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }

    private int buscarIdEstatus(int id) {
        for (int i = 0; i < tiposEstatus.size(); i++) {
            if (tiposEstatus.get(i).getId() == id) {
                return i;
            }
        }
        return 0;
    }

    private void editarDatosEnvio(Envio envio) {
        Mensaje msj = EnvioDAO.editarEnvio(envio);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La información del envio: " + envio.getNumeroGuia() + ", fue actualizada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", envio.getNumeroGuia());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

}
