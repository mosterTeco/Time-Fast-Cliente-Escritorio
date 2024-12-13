/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetimefastjavafx;

import clientetimefastjavafx.modelo.dao.ColaboradorDAO;
import clientetimefastjavafx.modelo.dao.RolDAO;
import clientetimefastjavafx.modelo.dao.UnidadDAO;
import clientetimefastjavafx.observador.NotificadorOperacion;
import clientetimefastjavafx.pojo.Colaborador;
import clientetimefastjavafx.pojo.Mensaje;
import clientetimefastjavafx.pojo.Rol;
import clientetimefastjavafx.pojo.Unidad;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DellAIO
 */
public class FXMLFormularioColaboradoresController implements Initializable {

    @FXML
    private ComboBox<Rol> comboBoxRol;
    @FXML
    private Label labelLicencia;
    @FXML
    private TextField textLicencia;
    @FXML
    private Label labelUnidad;

    List<Rol> roles = RolDAO.obtenerRoles();

    ObservableList<Rol> tiposRoles = FXCollections.observableArrayList(roles);

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfNoPersonal;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfContrasenia;

    private NotificadorOperacion observador;

    private Colaborador colaboradorEdicion;
    @FXML
    private ComboBox<Unidad> comboBoxUnidad;

    List<Unidad> unidades = UnidadDAO.obtenerUnidadesDisp(1);

    ObservableList<Unidad> unidadesDisponibles = FXCollections.observableArrayList(unidades);
    @FXML
    private ImageView igFotoCol;

    private boolean modoEdicion = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();
        cargarUnidades();

        comboBoxRol.setOnAction(event -> {
            Rol selectedRole = comboBoxRol.getValue();
            if (selectedRole != null && "Conductor".equals(selectedRole.getNombre())) {
                mostrarCamposConductor(true);
            } else {
                mostrarCamposConductor(false);
            }
        });

        comboBoxRol.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxRol.setStyle("");
            }
        });

        comboBoxUnidad.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBoxUnidad.setStyle("");
            }
        });

    }

    public void inicializarValores(NotificadorOperacion observador, Colaborador colaboradorEdicion) {
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;

        if (colaboradorEdicion != null) {
            modoEdicion = true;
            cargarDatosEdicion();
        } else {

        }
    }

    private void cargarRoles() {
        if (roles != null && !roles.isEmpty()) {
            System.out.println("Roles cargados: " + roles.size());
            comboBoxRol.setItems(tiposRoles);
        } else {
            System.out.println("No se pudieron cargar los tipos");
        }
    }

    private void cargarUnidades() {
        if (unidades != null && !unidades.isEmpty()) {
            System.out.println("Unidades cargadas: " + roles.size());
            comboBoxUnidad.setItems(unidadesDisponibles);
        } else {
            System.out.println("No se pudieron cargar los tipos");
        }
    }

    private void mostrarCamposConductor(boolean mostrar) {
        labelLicencia.setVisible(mostrar);
        labelLicencia.setManaged(mostrar);
        textLicencia.setVisible(mostrar);
        textLicencia.setManaged(mostrar);
        labelUnidad.setVisible(mostrar);
        labelUnidad.setManaged(mostrar);
        comboBoxUnidad.setVisible(mostrar);
        comboBoxUnidad.setManaged(mostrar);
    }

    private void OnClickSeleccionarUnidad(ActionEvent event) {
        try {
            Stage escenarioSeleccionar = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLUnidadesDisponibles.fxml"));
            Scene scene = new Scene(root);
            escenarioSeleccionar.setScene(scene);
            escenarioSeleccionar.setTitle("Seleccionar unidad");
            escenarioSeleccionar.initModality(Modality.APPLICATION_MODAL);
            escenarioSeleccionar.showAndWait();
        } catch (IOException e) {

        }
    }

    @FXML
    private void OnClickAgregarColaborador(ActionEvent event) {
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String numeroPersonal = tfNoPersonal.getText();
        String curp = tfCurp.getText();
        String correo = tfCorreo.getText();
        String password = tfContrasenia.getText();
        String numLicencia = textLicencia.getText();

        Integer idRol = ((comboBoxRol.getSelectionModel().getSelectedItem() != null)
                ? comboBoxRol.getSelectionModel().getSelectedItem().getId() : null);

        Integer idUnidad = ((comboBoxUnidad.getSelectionModel().getSelectedItem() != null)
                ? comboBoxUnidad.getSelectionModel().getSelectedItem().getId() : null);

        Colaborador colaborador = new Colaborador();
        colaborador.setNombre(nombre);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setNumeroPersonal(numeroPersonal);
        colaborador.setCurp(curp);
        colaborador.setCorreo(correo);
        colaborador.setPassword(password);
        colaborador.setIdRol(idRol);
        colaborador.setNumLicencia(numLicencia);
        colaborador.setIdUnidad(idUnidad);

        if (!modoEdicion) {
            guardarDatosColaborador(colaborador);
        } else {
            colaborador.setNumeroPersonal(this.colaboradorEdicion.getNumeroPersonal());
            editarDatosColaborador(colaborador);
        }

    }

    private void guardarDatosColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.registrarColaborador(colaborador);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Registro exitoso", "La información del Colaborador " + colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + ", fue registrada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Nuevo registro", colaborador.getNombre());
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", msj.getMensaje(), Alert.AlertType.ERROR);
        }

    }

    private void cerrarVentana() {
        Stage base = (Stage) tfNombre.getScene().getWindow();
        base.close();
    }

    private byte[] convertImageToBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buf)) != -1) {
                bos.write(buf, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }

    @FXML
    private void OnClickAgregarFotoColaborador(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        Stage stage = (Stage) igFotoCol.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Image image = new Image(file.toURI().toString());

            igFotoCol.setImage(image);
        }
    }

    private void cargarDatosEdicion() {

        if (this.colaboradorEdicion == null) {
            System.out.println("colaboradorEdicion es null");
            return;
        }

        tfNombre.setText(this.colaboradorEdicion.getNombre());
        tfApellidoPaterno.setText(this.colaboradorEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(this.colaboradorEdicion.getApellidoMaterno());
        tfNoPersonal.setText(this.colaboradorEdicion.getNumeroPersonal());
        tfCurp.setText(this.colaboradorEdicion.getCurp());
        tfCorreo.setText(this.colaboradorEdicion.getCorreo());
        tfContrasenia.setText(this.colaboradorEdicion.getPassword());
        textLicencia.setText(this.colaboradorEdicion.getNumLicencia());

        int posicion = buscarIdRol(this.colaboradorEdicion.getIdRol());
        comboBoxRol.getSelectionModel().select(posicion);

        Rol selectedRole = comboBoxRol.getSelectionModel().getSelectedItem();

        if (selectedRole != null && "Conductor".equals(selectedRole.getNombre())) {
            mostrarCamposConductor(true);
        } else {
            mostrarCamposConductor(false);
        }

        if (this.colaboradorEdicion.getIdUnidad() != null) {
            int posicionUn = buscarUnidad(this.colaboradorEdicion.getIdUnidad());
            comboBoxUnidad.getSelectionModel().select(posicionUn);
        } else {
            comboBoxUnidad.getSelectionModel().clearSelection(); 
        }

        tfNoPersonal.setEditable(false);
    }

    private int buscarIdRol(int idRol) {
        for (int i = 0; i < tiposRoles.size(); i++) {
            if (tiposRoles.get(i).getId() == idRol) {
                return i;
            }
        }
        return 0;
    }

    private int buscarUnidad(int idUnidad) {
        for (int i = 0; i < unidadesDisponibles.size(); i++) {
            if (unidadesDisponibles.get(i).getId() == idUnidad) {
                return i;
            }
        }
        return 0;
    }

    private void editarDatosColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.editarColaborador(colaborador);
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La información del Colaborador " + colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + ", fue actualizada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", colaborador.getNombre());
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

}
