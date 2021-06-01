package vistes.usuarios.C;

import java.net.URL;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXComboBox;


import application.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import vistes.usuarios.M.Usuarios_Model;

public class registerController {

	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private JFXComboBox<String> tipus;
	Main GestorVentanas = null;

	// gestor de finestres
	public void setGestorVentanas(Main GestorVentana) {
		this.GestorVentanas = GestorVentana;
		this.tipus.getItems().add("corredor");
		this.tipus.getItems().add("administrador");
		this.tipus.setValue("corredor");
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	private void register() {

		Usuarios_Model modeloLogin = new Usuarios_Model();

		modeloLogin.setUser(user.getText());
		modeloLogin.setPassword(password.getText());
		modeloLogin.setTipus(this.tipus.getValue());
		boolean b = modeloLogin.checkUser(modeloLogin);

		if (!b) {
			this.alerta(null, null, "Usuari registrat correctament");

			if (this.tipus.getValue().equals("corredor")) {
				this.GestorVentanas.showRegistreCorredor();
				this.GestorVentanas.usuariRegistre = modeloLogin;

			} else {
				this.GestorVentanas.logOut();
				modeloLogin.registrar(modeloLogin);
			}

		} else {
			this.alerta("Error registrar", null, "Error eixe usuari  ja existeix");
		}
	}

	public void alerta(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@FXML
	private void cancel() {
		this.GestorVentanas.logOut();
	}
}
