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
import clientetimefastjavafx.pojo.UsuarioSesion;
import clientetimefastjavafx.utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Base64;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

    List<Unidad> unidades = UnidadDAO.obtenerUnidades();

    //List<Unidad> unidades = UnidadDAO.obtenerUnidadesDisp();
    ObservableList<Unidad> unidadesDisponibles = FXCollections.observableArrayList(unidades);

    @FXML
    private ImageView igFotoCol;

    private boolean modoEdicion = false;

    private boolean modoValidarDatosConductor = false;
    @FXML
    private Button btnFoto;
    @FXML
    private AnchorPane paneFoto;

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
                modoValidarDatosConductor = true;
            } else {
                mostrarCamposConductor(false);
                modoValidarDatosConductor = false;
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

        int maxLengthLetra = 50;

        tfNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > maxLengthLetra) {
                tfNombre.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });

        tfApellidoPaterno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > maxLengthLetra) {
                tfApellidoPaterno.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });

        tfApellidoMaterno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZnÑ ]*") || newValue.length() > maxLengthLetra) {
                tfApellidoMaterno.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });

        tfNoPersonal.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9]*") || newValue.length() > 7) {
                tfNoPersonal.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });

        tfCurp.textProperty().addListener((observable, oldValue, newValue) -> {
            // Convierte a mayúsculas y valida el formato
            String upperCaseValue = newValue.toUpperCase();
            if (!upperCaseValue.matches("[A-Z0-9]*") || upperCaseValue.length() > 18) {
                tfCurp.setText(oldValue); // Restaura el valor anterior si no es válido
            } else {
                tfCurp.setText(upperCaseValue); // Asegura que el texto sea en mayúsculas
            }
        });

        tfCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir caracteres válidos en un correo electrónico
            if (!newValue.matches("[a-zA-Z0-9@._-]*") || newValue.length() > 50) {
                tfCorreo.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });

        tfContrasenia.textProperty().addListener((observable, oldValue, newValue) -> {
            // Permitir letras, números y caracteres especiales específicos, excluyendo espacios en blanco
            if (!newValue.matches("[a-zA-Z0-9@#$%&*?!._-]*") || newValue.length() > 20) {
                tfContrasenia.setText(oldValue); // Restaura el valor anterior si no es válido
            }
        });

        textLicencia.textProperty().addListener((observable, oldValue, newValue) -> {
            // Convierte a mayúsculas y valida el formato
            String upperCaseValue = newValue.toUpperCase();
            if (!upperCaseValue.matches("[A-ZÑ0-9]*") || upperCaseValue.length() > 18) {
                textLicencia.setText(oldValue); // Restaura el valor anterior si no es válido
            } else {
                textLicencia.setText(upperCaseValue); // Asegura que el texto sea en mayúsculas
            }
        });

    }

    public void inicializarValores(NotificadorOperacion observador, Colaborador colaboradorEdicion) {
        this.observador = observador;
        this.colaboradorEdicion = colaboradorEdicion;
        btnFoto.setVisible(false);

        if (colaboradorEdicion != null) {
            modoEdicion = true;
            btnFoto.setVisible(true);
            cargarDatosEdicion();
            obtenerFoto();
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
        List<Unidad> unidadesDisponiblesBase = UnidadDAO.obtenerUnidadesDisp();

        if (modoEdicion && colaboradorEdicion.getIdUnidad() != null) {
            Unidad unidadAsignada = unidades.stream()
                    .filter(unidad -> unidad.getId() == colaboradorEdicion.getIdUnidad())
                    .findFirst()
                    .orElse(null);

            if (unidadAsignada != null && !unidadesDisponiblesBase.contains(unidadAsignada)) {
                unidadesDisponiblesBase.add(unidadAsignada);
            }
        }

        unidadesDisponibles = FXCollections.observableArrayList(unidadesDisponiblesBase);

        if (!unidadesDisponibles.isEmpty()) {
            comboBoxUnidad.setItems(unidadesDisponibles);
        } else {
            System.out.println("No se pudieron cargar las unidades disponibles.");
        }
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

        if (!validarCampos()) {
            return;
        }

        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String numeroPersonal = tfNoPersonal.getText();
        String curp = tfCurp.getText();
        String correo = tfCorreo.getText();
        String password = tfContrasenia.getText();
        String numLicencia = textLicencia.getText();

        Integer idRol = (comboBoxRol.getSelectionModel().getSelectedItem() != null)
                ? comboBoxRol.getSelectionModel().getSelectedItem().getId() : null;

        Integer idUnidadNueva = (comboBoxUnidad.getSelectionModel().getSelectedItem() != null)
                ? comboBoxUnidad.getSelectionModel().getSelectedItem().getId() : null;

        String nombreRol = (idRol != null && comboBoxRol.getSelectionModel().getSelectedItem() != null)
                ? comboBoxRol.getSelectionModel().getSelectedItem().getNombre()
                : "";

        if (!"Conductor".equalsIgnoreCase(nombreRol)) {
            numLicencia = null;
            idUnidadNueva = null;
        }

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
        colaborador.setIdUnidad(idUnidadNueva);

        if (!modoEdicion) {
            guardarDatosColaborador(colaborador);
            if ("Conductor".equals(comboBoxRol.getSelectionModel().getSelectedItem().getNombre())) {
                System.out.println("aaa" + idUnidadNueva);
                editarEstadoUnidad(idUnidadNueva, "Asignada");
            }
        } else {
            if ("Conductor".equals(comboBoxRol.getSelectionModel().getSelectedItem().getNombre())) {
                Integer idUnidadAntigua = colaboradorEdicion.getIdUnidad();
                actualizarUnidades(idUnidadAntigua, idUnidadNueva);
            }
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

    @FXML
    private void OnClickCancelar(ActionEvent event) {
        cerrarVentana();
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

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        Stage stage = (Stage) igFotoCol.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                igFotoCol.setImage(image);

                byte[] foto = Files.readAllBytes(file.toPath());

                Integer idColaborador = colaboradorEdicion.getId();
                Mensaje mensaje = ColaboradorDAO.subirFoto(idColaborador, foto);

                if (!mensaje.isError()) {
                    Utilidades.mostrarAlertaSimple("Éxito", "Foto subida correctamente", Alert.AlertType.INFORMATION);
                } else {
                    Utilidades.mostrarAlertaSimple("ERROR", mensaje.getMensaje(), Alert.AlertType.ERROR);
                    System.err.println("Error al subir foto: " + mensaje.getMensaje());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Utilidades.mostrarAlertaSimple("ERROR", "El formato elegido nos es válido\n" + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void obtenerFoto() {
        Colaborador colaborador = ColaboradorDAO.obtenerFotoColaborador(colaboradorEdicion.getId());
        if (colaborador.getFoto() != null) {
            String cleanBase64 = colaborador.getFoto().replaceAll("[\\n\\r]", "");
            byte[] foto = Base64.getDecoder().decode(cleanBase64);
            if (foto != null && foto.length > 0) {
                InputStream inputStream = new ByteArrayInputStream(foto);
                Image image = new Image(inputStream);
                igFotoCol.setImage(image);
            } else {
                igFotoCol.setImage(new Image("/recursos/logo.png"));
            }
        }
    }

    private void cargarDatosEdicion() {

        if (this.colaboradorEdicion == null) {
            System.out.println("colaboradorEdicion es null");
            return;
        }

        cargarUnidades();

        tfNombre.setText(this.colaboradorEdicion.getNombre());
        tfApellidoPaterno.setText(this.colaboradorEdicion.getApellidoPaterno());
        tfApellidoMaterno.setText(this.colaboradorEdicion.getApellidoMaterno());
        tfNoPersonal.setText(this.colaboradorEdicion.getNumeroPersonal());
        tfCurp.setText(this.colaboradorEdicion.getCurp());
        tfCorreo.setText(this.colaboradorEdicion.getCorreo());
        tfContrasenia.setText(this.colaboradorEdicion.getPassword());
        if (colaboradorEdicion.getRol().equals("Conductor")) {
            textLicencia.setText(this.colaboradorEdicion.getNumLicencia());
        }

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

        comboBoxRol.setDisable(true);

        tfNoPersonal.setEditable(false);

        tfNoPersonal.setStyle("-fx-background-color: #d3d3d3;");

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
            Unidad unidad = unidadesDisponibles.get(i);
            System.out.println("Unidad ID: " + unidad.getId() + ", Comparando con: " + idUnidad);
            if (unidad.getId() == idUnidad) {
                return i;
            }
        }
        return 0;
    }

    private void editarDatosColaborador(Colaborador colaborador) {
        Mensaje msj = ColaboradorDAO.editarColaborador(colaborador);
        String numPersonal = UsuarioSesion.getInstancia().getNumeroPersonal();
        if (!msj.isError()) {
            Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "La información del Colaborador " + colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + ", fue actualizada de manera correcta", Alert.AlertType.INFORMATION);
            cerrarVentana();
            observador.notificarOperacion("Registro actualizado", colaborador.getNombre());

            if (colaborador.getNumeroPersonal().equals(numPersonal)) {
                UsuarioSesion.getInstancia().setNombreCompleto(colaborador.getNombre() + " " + colaborador.getApellidoPaterno() + " " + colaborador.getApellidoMaterno());
            }
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarEstadoUnidad(int id, String estado) {
        Mensaje msj = UnidadDAO.editarEstadoUnidad(id, estado);

        if (!msj.isError()) {
            //Utilidades.mostrarAlertaSimple("Actualizacion exitosa", "Exito", Alert.AlertType.INFORMATION);
            System.out.println("Funciona correctamente");
        } else {
            Utilidades.mostrarAlertaSimple("Error al actualizar", msj.getMensaje(), Alert.AlertType.ERROR);
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

    String obtenerNombreRolPorId(Integer idRol) {
        for (Rol rol : tiposRoles) {
            if (rol.getId().equals(idRol)) {
                return rol.getNombre();
            }
        }
        return "";
    }

    private void actualizarUnidades(Integer idUnidadAntigua, Integer idUnidadNueva) {
        if (idUnidadAntigua != null) {
            editarEstadoUnidad(idUnidadAntigua, "Disponible");
        }

        if (idUnidadNueva != null) {
            editarEstadoUnidad(idUnidadNueva, "Asignada");
        }
    }

    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfApellidoMaterno.getText().isEmpty() || tfApellidoPaterno.getText().isEmpty() || tfCurp.getText().isEmpty()
                || tfNoPersonal.getText().isEmpty() || tfContrasenia.getText().isEmpty() || tfCorreo.getText().isEmpty() || comboBoxRol.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor, completa todos los campos requeridos.", Alert.AlertType.WARNING);
            return false;
        }

        if (modoValidarDatosConductor == true) {
            if (textLicencia.getText().isEmpty() || comboBoxUnidad.getValue() == null) {
                Utilidades.mostrarAlertaSimple("Campos vacíos", "Por favor, completa todos los campos requeridos para el conductor.", Alert.AlertType.WARNING);
                return false;
            }
        }

        if (!esTextoValido(tfNombre.getText()) || !esTextoValido(tfApellidoPaterno.getText()) || !esTextoValido(tfApellidoMaterno.getText())) {
            Utilidades.mostrarAlertaSimple("Formato inválido", "Los campos de nombre y apellidos no deben contener números ni caracteres especiales.", Alert.AlertType.WARNING);
            return false;
        }

        if (!esNumerico(tfNoPersonal.getText())) {
            if (modoEdicion == false) {
                Utilidades.mostrarAlertaSimple("Formato inválido", "El número personal debe contener solo números.", Alert.AlertType.WARNING);
                return false;
            }
        }

        if (!esTextoAlfanumerico(tfCurp.getText()) || !esTextoAlfanumerico(tfNoPersonal.getText())) {
            Utilidades.mostrarAlertaSimple("Formato inválido", "Los campos de CURP y Numero Personal no deben contener caracteres especiales.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfNombre.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El nombre es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfNoPersonal.getText().length() > 5) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El umero personal no debe ser mayor a 5 digitos .", Alert.AlertType.WARNING);
            return false;
        }

        if (tfApellidoPaterno.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El apellido paterno es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfApellidoMaterno.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El apellido materno es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfNoPersonal.getText().length() > 30) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "El numero de personal es demasiado largo.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfCurp.getText().length() != 18) {
            Utilidades.mostrarAlertaSimple("Formato", "El CURP es invalido, puede que le falten caracteres o este sobrepasando los 18 caracteres.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfContrasenia.getText().length() < 8) {
            Utilidades.mostrarAlertaSimple("Contraseña segura", "La contraseña debe ser igual o mayor a 8 caracteres.", Alert.AlertType.WARNING);
            return false;
        }

        if (tfContrasenia.getText().length() > 99) {
            Utilidades.mostrarAlertaSimple("Limite de caracteres permitidos excedido", "La contraseña es demasiada larga.", Alert.AlertType.WARNING);
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

    private boolean esTextoAlfanumerico(String texto) {

        String patronTexto = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ]+$";
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
