package vistes.inscripcio.C;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import dao.DaoCarrera;
import dao.DaoClub;
import dao.DaoCorredor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vistes.carrera.M.Carrera_Model;
import vistes.club.M.Club_Model;
import vistes.corredor.M.Corredor_Model;
import vistes.inscripcio.M.Inscripcio_Model;

public class InscripcioFormulariController {

	@FXML
	private JFXDatePicker Data;
	@FXML
	private JFXTextField Posicio;
	@FXML
	private JFXComboBox<String> Club;
	@FXML
	private JFXComboBox<String> Corredor;
	@FXML
	private JFXComboBox<String> Carrera;

	private List<Club_Model> lClub;
	private List<Carrera_Model> lCarr;
	private List<Corredor_Model> lCorr;
	Main GestorVentanas = null;
	Inscripcio_Model model;
	private boolean error = false;

	// gestor de finestres
	public void setGestorVentanas(Main GestorVentana, Inscripcio_Model model) {
		this.GestorVentanas = GestorVentana;
		if (model != null) {
			this.model = model;
			this.CargarDatos();
		}
		if(this.GestorVentanas.tipus.equals("corredor")) {
			this.Corredor.setDisable(true);
			this.Carrera.setDisable(true);
		}
	}

	public void CargarDatos() {
		DaoClub daoClub = new DaoClub();
		this.lClub = daoClub.getAll();
		for (Club_Model c : lClub) {
			Club.getItems().add(c.getNom());
		}
		Club.getItems().add("Independent");

		DaoCarrera daoCarrera = new DaoCarrera();
		this.lCarr = daoCarrera.getAll();
		for (Carrera_Model c : lCarr) {
			Carrera.getItems().add(c.getNom());
		}
		DaoCorredor daoCorredor = new DaoCorredor();
		this.lCorr = daoCorredor.getAll();
		for (Corredor_Model c : lCorr) {
			Corredor.getItems().add(c.getNomcomplet());
		}

		this.Posicio.setText("" + this.model.getPosicio());
		this.Data.setValue(this.model.getDataInscripcio());
		this.Carrera.setValue(this.model.getCarrera().getNom());
		if (this.model.getNif() != null) {
			this.Club.setValue(this.model.getNif().getNom());
		} else {
			this.Club.setValue("Independent");
		}
		this.Corredor.setValue(this.model.getCorredor().getNomcomplet());
	}

	@FXML
	public void initialize() {

	}

	@FXML
	private void Cancelar() {
		this.GestorVentanas.closeForm("incripcio");
	}

	@FXML
	private void Guardar() {
		try {
			Integer.parseInt(this.Posicio.getText());
		} catch (Exception e) {
			// TODO: handle exception
			this.alert("Error Posició", "La posicó té que ser un numero", null);
			this.error = true;
		}

		if (!error) {

			Inscripcio_Model model = new Inscripcio_Model(this.model.getId(),this.model.getDorsal(), this.Data.getValue(),
					Integer.parseInt(this.Posicio.getText()));

			String Clubname = Club.getValue();

			for (Club_Model c : lClub) {
				if (Clubname.equals(c.getNom()))
					model.setNif(c);
			}
			String Corredorname = Corredor.getValue();

			for (Corredor_Model c : lCorr) {
				if (Corredorname.equals(c.getNomcomplet()))
					model.setCorredor(c);
			}

			String Carrerabname = Carrera.getValue();
			for (Carrera_Model c : lCarr) {
				if (Carrerabname.equals(c.getNom()))
					model.setCarrera(c);
			}

			this.model.Add(model);
			this.error = false;

			this.alert(null, "Informació guardad correctament", null);
			this.GestorVentanas.closeForm("inscripcio");
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
