package vistes.carrera.C;

import java.awt.TextField;

import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.Main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vistes.carrera.M.Carrera_Model;

public class CarreraFormulariController {
	@FXML
	private JFXTextField Nom;
	@FXML
	private JFXTextField Localitat;
	@FXML
	private JFXTextField Distancia;
	@FXML
	private JFXDatePicker Data;
	@FXML
	private JFXTextField Hora;
	@FXML
	private JFXTextField Preu;
	@FXML
	private int NPart;
	@FXML
	private TextField a;
	Main GestorVentanas = null;
	Carrera_Model model;

	// gestor de finestres
	public void setGestorVentanas(Main GestorVentana, Carrera_Model model) {
		this.GestorVentanas = GestorVentana;
		if (model != null) {
			this.model = model;
			this.Nom.setText(this.model.getNom());
			this.Localitat.setText(this.model.getLocalitat());
			this.Distancia.setText("" + this.model.getDistancia());
			this.Hora.setText("" + this.model.getHora());
			this.Preu.setText("" + this.model.getPreu());
			this.NPart = this.model.getNParticipants();
			this.Data.setValue(this.model.getData());

		} else {
			this.NPart = 0;
		}
	}

	@FXML
	public void initialize() {

	}

	@FXML
	private void Cancelar() {
		GestorVentanas.closeForm("carrera");

	}

	@FXML
	private void Guardar() {

		float distancia = 0, preu = 0;
		int i = this.Data.getValue().compareTo(LocalDate.now());
		this.NPart = this.NPart + 1;
		try {
			preu = Integer.parseInt(this.Preu.getText());
			distancia = Integer.parseInt(this.Distancia.getText());
			if (i <= 0)
				alert("error en la data", null, "la data de la carrera  no pot ser anterior a hui.");
			else if (distancia <= 0) {
				alert("error en la distancia", null, "la la distancia te que ser major de 0.");
			} else if (preu < 0.00) {
				alert("error en el preu", null, "El preu no pot ser negatiu.");
			} else {
				Carrera_Model model_R;
				if (this.model == null) {
					model_R = new Carrera_Model(this.Nom.getText(), this.Localitat.getText(), this.Data.getValue(),
							this.Hora.getText(), NPart, distancia, preu);
				} else {
					model_R = new Carrera_Model(this.Nom.getText(), this.Localitat.getText(), this.Data.getValue(),
							this.Hora.getText(), NPart, distancia, preu);
					model_R.setId(this.model.getId());
				}
				System.out.println(preu);
				System.out.println(model_R.getPreu());
				boolean b = model.Add(model_R);
				if (b) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(null);
					alert.setHeaderText(null);
					alert.setContentText("Informació guardada correctament");
					alert.showAndWait();
					this.GestorVentanas.closeForm("carrera");
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error carrera formulari");
					alert.setHeaderText(null);
					alert.setContentText("Error al guarda la informació");
					alert.showAndWait();
				}
			}
		} catch (NumberFormatException e) {
			alert("error ", null, "Error al guardar la carrera");
		}

	}

	public void alert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
