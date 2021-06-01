package vistes.inscripcio.C;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import application.Main;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import vistes.carrera.M.Carrera_Model;
import vistes.home.C.HomeController;
import vistes.inscripcio.M.Inscripcio_Model;

public class ItemInscripcioController {
	@FXML
	private Label dorsal;
	@FXML
	private Label carrera;
	@FXML
	private Label club;
	@FXML
	private Label corredor;
	@FXML
	private Label dia;
	@FXML
	private Label mes;
	@FXML
	private Label any;
	@FXML
	private Label posicio;
	@FXML
	private Button editB;
	@FXML
	private Button delB;
	HomeController GestorVentanas = null;
	Inscripcio_Model inscripcio;

	// gestor de finestres
	public void setGestorVentanas(HomeController homeController, Inscripcio_Model inscripcio) {		
		this.GestorVentanas = homeController;
		
			
		this.inscripcio = inscripcio;
		this.load();
	}

	private void load() {

		this.dorsal.setText("" + inscripcio.getDorsal());
		this.carrera.setText(inscripcio.getCarrera().getNom());
		this.corredor.setText(inscripcio.getCorredor().getNomcomplet());
		String clubNom;
		if (inscripcio.getNif() != null) {
			clubNom = inscripcio.getNif().getNom();
		} else {
			clubNom = "independent";
		}
		this.club.setText(clubNom);
		if (inscripcio.getPosicio() != 0)
			this.posicio.setText("" + this.inscripcio.getPosicio());
		else
			this.posicio.setText("sense posici�");

		String dia = inscripcio.getDataInscripcio().format(DateTimeFormatter.ofPattern("dd"));
		String mes = inscripcio.getDataInscripcio().format(DateTimeFormatter.ofPattern("LLLL"));
		String any = inscripcio.getDataInscripcio().format(DateTimeFormatter.ofPattern("yyyy"));

		this.dia.setText(dia);
		this.mes.setText(mes);
		this.any.setText(any);
	}

	@FXML
	public void initialize() {

	}
	@FXML
	public void edit(ActionEvent event) {
		this.GestorVentanas.modInscripcio(this.inscripcio);
	}

	// Event Listener on Button[#editB].onAction
	@FXML
	public void addPosicio(ActionEvent event) {
		// TODO Autogenerated

		TextInputDialog dialog = new TextInputDialog("posici�");
		dialog.setTitle("Selecci� de posici�");
		dialog.setHeaderText(null);
		dialog.setContentText("Introdueix la posici�:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			try {
				int i = Integer.parseInt(result.get());
				inscripcio.posicio = i;

			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		boolean flag = false;
		List<Inscripcio_Model> llista = inscripcio.getByCarrera("" + inscripcio.getCarrera().getId());
		for (Inscripcio_Model ins : llista)
			if (ins.getPosicio() == inscripcio.getPosicio()) {
				this.alert("Error al posar la posici�", null, "Ja existeix a ixa carrera un corredor amb eixa posici�");
				flag = true;
			}
		if (!flag)
			inscripcio.Add(inscripcio);
		// The Java 8 way to get the response value (with lambda expression).

	}

	// Event Listener on Button[#delB].onAction
	@FXML
	public void del(ActionEvent event) {
		// TODO Autogenerated
		Carrera_Model carrera = this.inscripcio.getCarrera();
		carrera.NParticipants -= 1;
		carrera.Add(carrera);
		inscripcio.del(this.inscripcio);
		this.GestorVentanas.loadInscripcio();
	}

	public void alert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
