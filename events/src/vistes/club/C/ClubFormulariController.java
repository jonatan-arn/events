package vistes.club.C;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXTextField;

import application.Main;
import dao.DaoClub;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vistes.club.M.Club_Model;

public class ClubFormulariController {
	@FXML
	private JFXTextField Nom;
	@FXML
	private JFXTextField Localitat;
	@FXML
	private JFXTextField Nif;
	@FXML
	private JFXTextField President;
	Main GestorVentanas = null;
	private Club_Model model = null;

	// gestor de finestres
	public void setGestorVentanas(Main GestorVentana, Club_Model model) {
		this.GestorVentanas = GestorVentana;

		if (model != null) {

			this.model = model;
			this.Nom.setText(this.model.getNom());
			this.Nif.setText(this.model.getNif());
			this.Localitat.setText(this.model.getLocalitat());
			this.President.setText(this.model.getPresident());
		}
	}

	@FXML
	private void Cancelar() {
		GestorVentanas.closeForm("club");

	}

	@FXML
	private void Guardar() {
		if (this.model == null) {
			if (!this.isNifNie(this.Nif.getText())) {
				this.alert("Error NIF", null, "Ese nif no existeix");
			} else if (DaoClub.get(this.Nif.getText()) != null) {
				this.alert("Error NIF", null, "Ja existeix un club amb aquest nif");
			}
		} else {
			Club_Model model = new Club_Model(this.Nif.getText(), this.Nom.getText(), this.Localitat.getText(),
					this.President.getText());
			boolean b = model.Add(model);
			if (b) {

				this.alert(null, "Informació guardad correctament", null);
				this.GestorVentanas.closeForm("club");

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error club formulari");
				alert.setHeaderText(null);
				alert.setContentText("Error al guarda la informació");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void initialize(Club_Model model) {
		if (model != null) {

			this.Nom.setText(model.getNom());
			this.Nif.setText(model.getNif());
			this.Localitat.setText(model.getLocalitat());
			this.President.setText(model.getPresident());
		}
	}

	public void alert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static boolean isNifNie(String nif) {

		// si es NIE, eliminar la x,y,z inicial para tratarlo como nif
		if (nif.toUpperCase().startsWith("X") || nif.toUpperCase().startsWith("Y") || nif.toUpperCase().startsWith("Z"))
			nif = nif.substring(1);

		Pattern nifPattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
		Matcher m = nifPattern.matcher(nif);
		if (m.matches()) {
			String letra = m.group(2);
			// Extraer letra del NIF
			String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
			int dni = Integer.parseInt(m.group(1));
			dni = dni % 23;
			String reference = letras.substring(dni, dni + 1);

			if (reference.equalsIgnoreCase(letra)) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}
}
