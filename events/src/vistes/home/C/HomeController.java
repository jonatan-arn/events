/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistes.home.C;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;
import vistes.carrera.C.ItemCarreraController;
import vistes.carrera.M.Carrera_Model;
import vistes.club.C.ItemClubController;
import vistes.club.M.Club_Model;
import vistes.corredor.C.ItemCorredorController;
import vistes.corredor.C.ItemCorredorPerPosicioController;
import vistes.corredor.M.Corredor_Model;
import vistes.inscripcio.C.ItemInscripcioController;
import vistes.inscripcio.M.Inscripcio_Model;
import vistes.usuarios.M.Usuarios_Model;

public class HomeController implements Initializable {
	@FXML
	private Label nom;
	@FXML
	private Label tipus;
	@FXML
	private Label label;
	@FXML
	private Button BCarrera;
	@FXML
	private Button BCorredor;
	@FXML
	private VBox pnl_scroll;
	@FXML
	private VBox adminVBox;
	@FXML
	private TextField filterFieldCarrera;
	@FXML
	private TextField filterFieldCorredor;
	@FXML
	private TextField filterFieldClub;
	@FXML
	private TextField filterFieldInscripcio;
	@FXML
	private JFXCheckBox checkboxCarreresPosicio;
	@FXML
	private ButtonBar adminBar;
	@FXML
	public Label titol;
	@FXML
	private JFXButton modClub;
	public List<Carrera_Model> carreres;
	public List<Inscripcio_Model> inscripcions;
	public List<Corredor_Model> corredors;
	private List<Club_Model> clubs;
	public Usuarios_Model modeloLogin;

	private ChoiceDialog<String> dialog;
	private List<String> dialogData;
	Main GestorVentanas = null;

	private final ObservableList<Carrera_Model> dataListCarrera = FXCollections.observableArrayList();
	private final ObservableList<Corredor_Model> dataListCorredor = FXCollections.observableArrayList();
	private final ObservableList<Club_Model> dataListClub = FXCollections.observableArrayList();
	private final ObservableList<Inscripcio_Model> dataListInscripcio = FXCollections.observableArrayList();

	// gestor de finestres
	public HomeController setGestorVentanas(Main GestorVentana, Usuarios_Model modeloLogin) {
		this.modClub.setVisible(false);
		System.out.println(modeloLogin);
		this.modeloLogin = modeloLogin;
		this.nom.setText(modeloLogin.getUser());
		this.tipus.setText(modeloLogin.getTipus());
		if (modeloLogin.getTipus().equals("corredor")) {
			this.BCorredor.setVisible(false);
			this.BCarrera.setVisible(false);
			this.adminVBox.setVisible(false);
			this.modClub.setVisible(true);
		}
		if (modeloLogin.getTipus().equals("corredor")) {
			Inscripcio_Model modelInscripcio = new Inscripcio_Model();
			this.inscripcions = modelInscripcio.getByCorredor(this.modeloLogin.getCorredor().getDni());
		} else {
			Inscripcio_Model modelInscripcio = new Inscripcio_Model();
			this.inscripcions = modelInscripcio.load();
		}
		this.loadCarrera();

		this.filtre();
		this.GestorVentanas = GestorVentana;
		return this;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
		Corredor_Model modelCorredor = new Corredor_Model();
		this.corredors = modelCorredor.load();
		Club_Model modelClub = new Club_Model();
		this.clubs = modelClub.load();

	}

	@FXML
	public void modClub() {
		List<String> llistaNoms = new ArrayList<>();
		for (Club_Model club : this.clubs) {
			llistaNoms.add(club.getNom());
		}
		dialogData = llistaNoms;
		dialog = new ChoiceDialog<String>(dialogData.get(0), dialogData);
		dialog.setTitle("Clubs");
		dialog.setHeaderText("Seleccion un club");

		Optional<String> result = dialog.showAndWait();
		String selected = "cancelled.";

		if (result.isPresent()) {
			selected = result.get();
		}
		if (selected != "cancelled") {
			for (Club_Model c : this.clubs) {
				if (c.getNom().equals(selected)) {
					Corredor_Model corredor = this.modeloLogin.getCorredor();
					corredor.setClub(c);
					corredor.Add(corredor);
				}
			}
		}
	}

	

	@FXML
	public void loadCarrera() {
		this.titol.setText("CARRERES");
		Carrera_Model modelCarrera = new Carrera_Model();
		this.carreres = modelCarrera.load();
		this.showCarrera();
	}

	@FXML
	public void loadCorredor() {
		this.titol.setText("CORREDORS");
		Corredor_Model modelCorredor = new Corredor_Model();
		this.corredors = modelCorredor.load();

		this.showCorredor();
	}

	@FXML
	public void loadClub() {
		this.titol.setText("CLUBS");
		Club_Model modelClub = new Club_Model();
		this.clubs = modelClub.load();
		this.showClub();

	}

	@FXML
	public void loadInscripcio() {
		this.titol.setText("INSCRIPCIONS");
		if (modeloLogin.getTipus().equals("corredor")) {
			Inscripcio_Model modelInscripcio = new Inscripcio_Model();
			this.inscripcions = modelInscripcio.getByCorredor(this.modeloLogin.getCorredor().getDni());
		} else {
			Inscripcio_Model modelInscripcio = new Inscripcio_Model();
			this.inscripcions = modelInscripcio.load();
		}

		this.showInscripcio();
	}

	@FXML
	public void showCarrera() {

		this.filterFieldCarrera.setVisible(true);
		this.filterFieldCorredor.setVisible(false);
		this.filterFieldClub.setVisible(false);
		this.filterFieldInscripcio.setVisible(false);

		pnl_scroll.getChildren().clear();

		Node[] nodes = new Node[this.carreres.size()];

		for (int i = 0; i < this.carreres.size(); i++) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistes/carrera/V/ItemCarrera.fxml"));
				Carrera_Model carrera = this.carreres.get(i);
				nodes[i] = (Node) loader.load();
				ItemCarreraController controller = loader.getController();
				controller.setGestorVentanas(this, carrera);

				pnl_scroll.getChildren().add(nodes[i]);

			} catch (IOException ex) {
				Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	@FXML
	public void showInscripcio() {

		this.filterFieldCarrera.setVisible(false);
		this.filterFieldCorredor.setVisible(false);
		this.filterFieldClub.setVisible(false);
		this.filterFieldInscripcio.setVisible(true);

		pnl_scroll.getChildren().clear();

		Node[] nodes = new Node[this.inscripcions.size()];

		for (int i = 0; i < this.inscripcions.size(); i++) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistes/inscripcio/V/ItemInscripcio.fxml"));
				Inscripcio_Model inscripcio = this.inscripcions.get(i);
				nodes[i] = (Node) loader.load();
				ItemInscripcioController controller = loader.getController();
				controller.setGestorVentanas(this, inscripcio);

				pnl_scroll.getChildren().add(nodes[i]);

			} catch (IOException ex) {
				Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	@FXML
	public void showCorredor() {

		this.filterFieldCarrera.setVisible(false);
		this.filterFieldCorredor.setVisible(true);
		this.filterFieldClub.setVisible(false);
		this.filterFieldInscripcio.setVisible(false);

		pnl_scroll.getChildren().clear();

		Node[] nodes = new Node[this.corredors.size()];

		for (int i = 0; i < this.corredors.size(); i++) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistes/corredor/V/ItemCorredor.fxml"));
				Corredor_Model corredor = this.corredors.get(i);

				nodes[i] = (Node) loader.load();
				ItemCorredorController controller = loader.getController();
				controller.setGestorVentanas(this, corredor);

				pnl_scroll.getChildren().add(nodes[i]);

			} catch (IOException ex) {
				Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	@FXML
	public void showClub() {

		this.filterFieldCarrera.setVisible(false);
		this.filterFieldCorredor.setVisible(false);
		this.filterFieldClub.setVisible(true);
		this.filterFieldInscripcio.setVisible(false);

		pnl_scroll.getChildren().clear();

		Node[] nodes = new Node[this.clubs.size()];

		for (int i = 0; i < this.clubs.size(); i++) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/vistes/club/V/ItemClub.fxml"));
				Club_Model club = this.clubs.get(i);
				nodes[i] = (Node) loader.load();
				ItemClubController controller = loader.getController();
				controller.setGestorVentanas(this, club);

				pnl_scroll.getChildren().add(nodes[i]);

			} catch (IOException ex) {
				Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}
	}

	@FXML
	public void ordenarPerParticipants() {
		this.titol.setText("CARRERES ORDENADES PER PARTICIPANTS");
		if (this.checkboxCarreresPosicio.isSelected()) {
			Carrera_Model model = new Carrera_Model();
			this.carreres = model.load();
			Collections.sort(this.carreres);
			this.showCarrera();
		} else {
			this.loadCarrera();
		}
	}

	@FXML
	public void showCorredorInscritsCarrera() {

		this.filterFieldCarrera.setVisible(false);
		this.filterFieldCorredor.setVisible(true);
		this.filterFieldClub.setVisible(false);
		this.filterFieldInscripcio.setVisible(false);

		pnl_scroll.getChildren().clear();

		Node[] nodes = new Node[this.corredors.size()];

		for (int i = 0; i < this.corredors.size(); i++) {
			try {
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/vistes/corredor/V/ItemCorredorPerPosicio.fxml"));
				Corredor_Model corredor = this.corredors.get(i);
				nodes[i] = (Node) loader.load();
				ItemCorredorPerPosicioController controller = loader.getController();
				controller.setGestorVentanas(this, corredor, this.inscripcions);

				pnl_scroll.getChildren().add(nodes[i]);

			} catch (IOException ex) {
				Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}

	// Event Listener on JFXButton.onAction
	@FXML
	public void exit(ActionEvent event) {
		// TODO Autogenerated
		this.GestorVentanas.logOut();
	}

	@FXML
	public void addCarrera(ActionEvent event) {
		// TODO Autogenerated
		Object obj = null;
		this.GestorVentanas.showForm("/vistes/carrera/V/CarreraFormulari.fxml", obj);
	}

	@FXML
	public void addCorredor(ActionEvent event) {
		// TODO Autogenerated
		Object obj = null;
		this.GestorVentanas.showForm("/vistes/corredor/V/CorredorFormulari.fxml", obj);

	}

	@FXML
	public void addClub(ActionEvent event) {
		// TODO Autogenerated
		Object obj = null;
		this.GestorVentanas.showForm("/vistes/club/V/ClubFormulari.fxml", obj);

	}

	public void modCarrera(Object obj) {
		// TODO Autogenerated
		this.GestorVentanas.showForm("/vistes/carrera/V/CarreraFormulari.fxml", obj);

	}

	public void modCorredor(Object obj) {
		// TODO Autogenerated
		this.GestorVentanas.showForm("/vistes/corredor/V/CorredorFormulari.fxml", obj);

	}

	public void modClub(Object obj) {
		// TODO Autogenerated
		this.GestorVentanas.showForm("/vistes/club/V/ClubFormulari.fxml", obj);

	}

	public void modInscripcio(Object obj) {
		// TODO Autogenerated
		this.GestorVentanas.showForm("/vistes/inscripcio/V/InscripcioFormulari.fxml", obj);

	}

	public void filtre() {

		for (Carrera_Model carrera : this.carreres)
			this.dataListCarrera.add(carrera);

		FilteredList<Carrera_Model> filteredDataCarrera = new FilteredList<>(dataListCarrera, b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterFieldCarrera.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataCarrera.setPredicate(carrera -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (carrera.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (carrera.getLocalitat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (String.valueOf(carrera.getPreu()).indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (String.valueOf(carrera.getNParticipants()).indexOf(lowerCaseFilter) != -1)
					return true;

				else
					return false; // Does not match.
			});
			this.carreres = filteredDataCarrera;
			this.showCarrera();
		});

		for (Club_Model club : this.clubs)
			this.dataListClub.add(club);

		FilteredList<Club_Model> filteredDataClub = new FilteredList<>(dataListClub, b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterFieldClub.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataClub.setPredicate(club -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (club.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (club.getLocalitat().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (club.getNif().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (club.getPresident().toLowerCase().indexOf(lowerCaseFilter) != -1)
					return true;

				else
					return false; // Does not match.
			});
			this.clubs = filteredDataClub;
			this.showClub();
		});

		for (Corredor_Model corredor : this.corredors)
			this.dataListCorredor.add(corredor);

		FilteredList<Corredor_Model> filteredDataCorredor = new FilteredList<>(dataListCorredor, b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterFieldCorredor.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataCorredor.setPredicate(corredor -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				String independent = "independent";
				if (corredor.getNomcomplet().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (corredor.getClub() != null
						&& corredor.getClub().getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (corredor.getClub() == null && independent.indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (corredor.getLocalitat().indexOf(lowerCaseFilter) != -1)
					return true;

				else
					return false; // Does not match.
			});
			this.corredors = filteredDataCorredor;
			this.showCorredor();
		});

		for (Inscripcio_Model inscripcio : this.inscripcions)
			this.dataListInscripcio.add(inscripcio);

		FilteredList<Inscripcio_Model> filteredDataInscripcio = new FilteredList<>(dataListInscripcio, b -> true);

		// 2. Set the filter Predicate whenever the filter changes.
		filterFieldInscripcio.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredDataInscripcio.setPredicate(inscripcio -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				String independent = "independent";

				if (String.valueOf(inscripcio.getDorsal()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (inscripcio.getCarrera().getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				} else if (inscripcio.getCorredor().getNomcomplet().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (inscripcio.getNif() != null && inscripcio.getNif().getNom().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (inscripcio.getNif() == null && independent.indexOf(lowerCaseFilter) != -1)
					return true;
				else
					return false; // Does not match.
			});
			this.inscripcions = filteredDataInscripcio;
			this.showInscripcio();
		});

	}
}
