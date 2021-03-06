package vistes.corredor.C;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dao.DaoCarrera;
import dao.DaoInscripcio;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import vistes.carrera.M.Carrera_Model;
import vistes.corredor.M.Corredor_Model;
import vistes.home.C.HomeController;
import vistes.inscripcio.M.Inscripcio_Model;

public class ItemCorredorPerPosicioController {
	@FXML
	private Label nomCarrera;
	@FXML
	private Label dni;
	@FXML
	private Label nom;
	@FXML
	private Label posicio;
	@FXML
	private Button editB;
	@FXML
	private Button delB;

	HomeController GestorVentanas = null;
	Corredor_Model corredor;
	private ChoiceDialog<String> dialog;
	private List<String> dialogData;
	private List<Carrera_Model> lCarrera;
	private List<Inscripcio_Model> lInscripcions;

	public void setGestorVentanas(HomeController homeController, Corredor_Model corredor,
			List<Inscripcio_Model> inscripcions) {
		this.GestorVentanas = homeController;
		this.corredor = corredor;
		this.load(inscripcions);
	}

	private void load(List<Inscripcio_Model> inscripcions) {
		this.dni.setText(corredor.getDni());
		for (Inscripcio_Model ins : inscripcions) {
			if (ins.getCorredor().getDni().equals(this.corredor.dni)) {
				if (ins.getPosicio() == 0)
					this.posicio.setText("Sense posici?");
				else
					this.posicio.setText("" + ins.getPosicio());
				this.nomCarrera.setText(ins.getCarrera().getNom());
			}
		}

		this.nom.setText(corredor.getNomcomplet());

	}

	@FXML
	public void initialize() {

	}

	// Event Listener on Button[#editB].onAction
	@FXML
	public void edit(ActionEvent event) {
		// TODO Autogenerated
		this.GestorVentanas.modCorredor(this.corredor);

	}

	// Event Listener on Button[#delB].onAction
	@FXML
	public void del(ActionEvent event) {
		// TODO Autogenerated
		corredor.del(this.corredor);
		this.GestorVentanas.loadCorredor();
	}

	// Event Listener on Button.onAction
	@FXML
	public void inscriure(ActionEvent event) {
		// TODO Autogenerated
		List<String> llistaNoms = new ArrayList<>();
		this.lCarrera = DaoCarrera.getAll();
		this.lInscripcions = DaoInscripcio.getByCorredor("" + this.corredor.getDni());
		boolean esta = false;

		for (Carrera_Model c : this.lCarrera) {
			for (Inscripcio_Model ins : this.lInscripcions) {
				if (ins.getCarrera().getId() == (c.getId())) {
					esta = true;
					if (!ins.getCorredor().getDni().equals(this.corredor.getDni())) {
						llistaNoms.add(c.getNom());
					}
				}

			}
			if (!esta) {
				llistaNoms.add(c.getNom());

			}
			esta = false;
		}
		if (llistaNoms.size() == 0) {
			this.alert("Error inscripci?", null, "Aquest corredor ja est? inscrit a totes les carreres");

		} else {
			dialogData = llistaNoms;
			dialog = new ChoiceDialog<String>(dialogData.get(0), dialogData);
			dialog.setTitle("Carreres");
			dialog.setHeaderText("Selecciona una carrera");

			Optional<String> result = dialog.showAndWait();
			String selected = "cancelled.";

			if (result.isPresent()) {
				selected = result.get();
			}
			if (selected != "cancelled") {
				for (Carrera_Model c : this.lCarrera) {
					if (c.getNom().equals(selected)) {
						Inscripcio_Model inscripcio = new Inscripcio_Model();
						inscripcio.setCarrera(c);
						inscripcio.setCorredor(corredor);
						inscripcio.setDataInscripcio(LocalDate.now());
						inscripcio = this.addDorsal(inscripcio);
						if (corredor.getClub() != null)
							inscripcio.setNif(corredor.getClub());
						inscripcio.Add(inscripcio);
					}
				}

			}
		}

	}

	public Inscripcio_Model addDorsal(Inscripcio_Model inscripcio) {
		Inscripcio_Model model = new Inscripcio_Model();
		inscripcio.setDorsal(model.getLastDorsal() + 1);
		return inscripcio;
		// The Java 8 way to get the response value (with lambda expression).

	}

	public void alert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
