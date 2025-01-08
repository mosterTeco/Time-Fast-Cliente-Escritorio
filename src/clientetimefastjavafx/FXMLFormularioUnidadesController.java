/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ColaboradorDAO;
import clientetimefastjavafx.modelo.dao.TipoDAO;
import clientetimefastjavafx.modelo.dao.UnidadDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Tipo;
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioUnidadesController implements Initializable {

    private NotificadorOperacion observador;

    private Unidad unidadEdicion;

    private boolean modoEdicion = false;

    @FXML
    private ComboBox<Tipo> comboBoxTipo;
    @FXML
    private Button buttonSeleccionarUnidad;

    List<Tipo> tipos = TipoDAO.obtenerTipos();

    ObservableList<Tipo> tiposUnidades = FXCollections.observableArrayList(tipos);
    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfVin;
    @FXML
    private Label lbQuitarCond;
    @FXML
    private RadioButton rbQuitarConductor;
    @FXML
    private Label lblNombreConductor;
    @FXML
    private Label lblConductor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarTiposUsuario();

        comboBoxTipo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxTipo.setStyle("");
            }
        });

        tfMarca.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÃ‘ ]*") || newValue.length() > 20) {
                tfMarca.setText(oldValue); 
            }
        });

        tfModelo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZñÑ0-9 ]*") || newValue.length() > 50) {
                tfModelo.setText(oldValue); 
            }
        });

        tfAnio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*") || newValue.length() > 4) {
                tfAnio.setText(oldValue); 
            }
        });

        tfVin.textProperty().addListener((observable, oldValue, newValue) -> {
            String upperCaseValue = newValue.toUpperCase();
            if (!upperCaseValue.matches("[A-Z0-9]*") || upperCaseValue.length() > 17) {
                tfVin.setText(oldValue); 
            } else {
                tfVin.setText(upperCaseValue); 
            }
        });
    }

    public void inicializarValores(NotificadorOperacion observador, Unidad unidadEdicion) {
        this.observador = observador;
        this.unidadEdicion = unidadEdicion;

        if (unidadEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {

        }
    }

    private void cargarTiposUsuario() {

        if (tipos != null && !tipos.isEmpty()) {
            comboBoxTipo.setItems(tiposUnidades);
        } else {
            System.out.println("No se pudieron cargar los tipos");
        }
    }

    private void cargarDatosEdicion() {
        tfMarca.setText(this.unidadEdicion.getMarca());
        tfModelo.setText(this.unidadEdicion.getModelo());
        tfAnio.setText(this.unidadEdicion.getAnio());
        tfVin.setText(this.unidadEdicion.getVin());

        int posicion = buscarTipo(this.unidadEdicion.getIdTipo());
        comboBoxTipo.getSelectionModel().select(posicion);

        tfVin.setEditable(false);

        tfVin.setStyle("-fx-background-color: #d3d3d3;");

        if (this.unidadEdicion.getNombreColaborador() != null && !this.unidadEdicion.getNombreColaborador().isEmpty()) {
            mostrarCamposUnidad(true);
            lblConductor.setText(this.unidadEdicion.getNombreColaborador());
            //lblConductor.setText(this.unidadEdicion.getIdColaborador().toString());
        } else {
            mostrarCamposUnidad(false);
        }
    }

    @FXML
    private void OnClickAgregarUnidad(ActionEvent event) {

        if (!validarCampos()) {
            return;
        }

        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String anio = tfAnio.getText();
        String vin = tfVin.getText();

        if (vin == null || vin.length() < 4) {
            System.out.println("El VIN debe tener al menos 4 caracteres.");
            return;
        }

        String nii = anio + vin.substring(0, 4);

        Integer idTipo = (comboBoxTipo.getSelectionModel().getSelectedItem() != null)
                ? comboBoxTipo.getSelectionModel().getSelectedItem().getId() : null;

        String estado = "Disponible";

        if (rbQuitarConductor.isSelected()) {
            int idConductor = this.unidadEdicion.getIdColaborador();
            editarUnidadColaborador(idConductor, null);
            editarEstadoUnidad(this.unidadEdicion.getId(), "Disponible");
            estado = "Disponible";
        }

        Unidad unidad = new Unidad();
        unidad.setMarca(marca);
        unidad.setModelo(modelo);
        unidad.setAnio(anio);
        unidad.setVin(vin);
        unidad.setNii(nii);
        unidad.setEstado(estado);
        unidad.setIdTipo(idTipo);

        if (!modoEdicion) {
            guardarDatosUnidad(unidad);
        } else {
            unidad.setId(this.unidadEdicion.getId());
            editarDatosUnidad(unidad);
        }
    }

    private void guardarDatosUnidad(Unidad unidad) {
        Mensaje msj = UnidadDAO.registrarUnidad(unidad);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", "La unidad con NII: " + unidad.getNii() + ", fue registrada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Nuevo registro", unidad.getNii());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void OnClickCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage base = (Stage) tfMarca.getScene().getWindow();
        base.close();
    }

    private int buscarTipo(int idTipo) {
        for (int i = 0; i < tiposUnidades.size(); i++) {
            if (tiposUnidades.get(i).getId() == idTipo) {
                return i;
            }
        }
        return 0;
    }

    private void editarDatosUnidad(Unidad unidad) {
        Mensaje msj = UnidadDAO.editarUnidad(unidad);

        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La unidad con NII: " + unidad.getNii() + ", fue actualizada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", unidad.getNii());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarUnidadColaborador(int id, Integer idUnidad) {
        Mensaje msj = ColaboradorDAO.editarUnidadColaborador(id, idUnidad);

        if (!msj.isError()) {
            //Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La información del Colaborador,fue actualizada de manera correcta", Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarEstadoUnidad(int id, String estado) {
        Mensaje msj = UnidadDAO.editarEstadoUnidad(id, estado);

        if (!msj.isError()) {
            //Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "Exito", Alert.AlertType.INFORMATION);
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarCamposUnidad(boolean mostrar) {
        lbQuitarCond.setVisible(mostrar);
        lbQuitarCond.setManaged(mostrar);
        rbQuitarConductor.setVisible(mostrar);
        rbQuitarConductor.setManaged(mostrar);
        lblNombreConductor.setVisible(mostrar);
        lblNombreConductor.setManaged(mostrar);
        lblConductor.setVisible(mostrar);
        lblConductor.setManaged(mostrar);
    }

    private boolean validarCampos() {
        if (tfMarca.getText().isEmpty() || tfModelo.getText().isEmpty() || comboBoxTipo.getValue() == null || tfAnio.getText().isEmpty()
                || tfVin.getText().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campos vacios", "Por favor, completa todos los campos requeridos.", Alert.AlertType.WARNING);
            return false;
        }

        if (!esTextoValido(tfMarca.getText()) || !esTextoValido(tfModelo.getText())) {
            Utilidades.mostrarAlertaSimple("Formato invalido", "Los campos marca y modelo no deben contener numeros ni caracteres especiales.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfMarca.getText().length() > 50) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El nombre de la marca es demasiada largo, no debe superar los 50 caracteres.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfModelo.getText().length() > 50) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El nombre de la modelo es demasiada largo, no debe superar los 50 caracteres.", Alert.AlertType.WARNING);
            return false;
        }

        if (!esNumerico(tfAnio.getText())) {
            Utilidades.mostrarAlertaSimple("Formato invalido", "El campo anio no debe contener caracteres especiales ni letras.", Alert.AlertType.WARNING);
            return false;
        }

        try {
            int anio = Integer.parseInt(tfAnio.getText());

            int anioActual = java.time.Year.now().getValue(); 
            if (anio <= 2015 || anio >= anioActual) {
                Utilidades.mostrarAlertaSimple("Anio fuera de rango", "El anio debe ser mayor a 2015 y menor al anio actual.", Alert.AlertType.WARNING);
                return false;
            }
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple("Formato invalido", "El aÃ±o debe ser un numero valido.", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private boolean esTextoValido(String texto) {
        String patronTexto = "^[a-zA-ZÃ¡Ã©Ã­Ã³ÃºÃ�Ã‰Ã�Ã“ÃšÃ±Ã‘\\s]+$";
        return Pattern.matches(patronTexto, texto);
    }

    private boolean esNumerico(String texto) {
        return texto.matches("\\d+");
    }

}
