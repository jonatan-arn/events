package vistes.usuarios.C;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import vistes.corredor.C.CorredorFormulariController;
import vistes.usuarios.M.Usuarios_Model;

public class LoginViewController {
	
	@FXML
	private TextField user;
	@FXML
	private TextField password;
	@FXML
	private Button loginB;
	@FXML
	private Button exitB;
	Main GestorVentanas = null;
	//gestor de finestres 
	public void setGestorVentanas(Main GestorVentana) {
		this.GestorVentanas = GestorVentana;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@FXML
	private void exit() {
		Platform.exit();
	}
	@FXML
	private void registrar() {
		this.GestorVentanas.register();
	}
	@FXML
	private void login() {
		Usuarios_Model modeloLogin = new Usuarios_Model();
		Usuarios_Model b;
		modeloLogin.setUser(user.getText());
		modeloLogin.setPassword(password.getText());
		b = modeloLogin.getCredentials(modeloLogin);	
		String pwd = new String(password.getText());
		
		if (b.getPassword().equals(pwd) ) {
			
			modeloLogin.setTipus(b.getTipus());
			modeloLogin.setCorredor(b.getCorredor());
			this.GestorVentanas.tipus = b.getTipus();
			this.GestorVentanas.showHome(modeloLogin);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error inici sesio");
			alert.setHeaderText(null);
			alert.setContentText("Usuaio o contranya erronis");
			alert.showAndWait();
		}
	}
}
