package vistes.club.C;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import vistes.carrera.M.Carrera_Model;
import vistes.club.M.Club_Model;
import vistes.corredor.M.Corredor_Model;
import vistes.home.C.HomeController;

public class ItemClubController {
	@FXML
	private Label nif;
	@FXML
	private Label nom;
	@FXML
	private Label president;
	@FXML
	private Label localitat;
	@FXML
	private Button editB;
	@FXML
	private Button delB;
	HomeController GestorVentanas = null;
	Club_Model club;

	// gestor de finestres
	public void setGestorVentanas(HomeController homeController, Club_Model club) {
		this.GestorVentanas = homeController;
		this.club = club;
		this.load();
	}

	private void load() {

		this.nom.setText(club.getNom());
		this.localitat.setText(club.getLocalitat());
		this.nif.setText(club.getNif());
		this.president.setText(club.getPresident());

	}

	@FXML
	public void initialize() {

	}

	@FXML
	public void CorredorsClub() {

		Corredor_Model model = new Corredor_Model();
		this.GestorVentanas.corredors = model.load();

		List<Corredor_Model> temp = new ArrayList<Corredor_Model>();
		for (Corredor_Model corredor : this.GestorVentanas.corredors) {
			if (corredor.getClub() != null)
				if (corredor.getClub().getNif().equals(this.club.getNif()))
					temp.add(corredor);
		}
		this.GestorVentanas.corredors = temp;
		this.GestorVentanas.showCorredor();
	}

	// Event Listener on Button[#editB].onAction
	@FXML
	public void edit(ActionEvent event) {
		// TODO Autogenerated
		this.GestorVentanas.modClub(this.club);

	}

	// Event Listener on Button[#delB].onAction
	@FXML
	public void del(ActionEvent event) {
		// TODO Autogenerated
		club.del(this.club);
		this.GestorVentanas.loadClub();
	}

}
