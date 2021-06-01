package vistes.corredor.C;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import application.Main;
import dao.DaoClub;
import dao.DaoCorredor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vistes.club.M.Club_Model;
import vistes.corredor.M.Corredor_Model;

public class CorredorFormulariController {
	@FXML
	private JFXTextField Nom;
	@FXML
	private JFXTextField Localitat;
	@FXML
	private JFXTextField Dni;
	@FXML
	private JFXDatePicker Data;
	@FXML
	private JFXTextField Email;
	@FXML
	private JFXTextField Telefon;
	@FXML
	private JFXComboBox<String> Club;

	private List<Club_Model> lClub;
	Main GestorVentanas = null;
	Corredor_Model model;
	boolean isRegistre;

	// gestor de finestres
	public Main setGestorVentanas(Main GestorVentana, Corredor_Model model, boolean isRegistre) {
		this.GestorVentanas = GestorVentana;
		this.isRegistre = isRegistre;
		if (model != null) {
			this.model = model;
			this.Nom.setText(this.model.getNomcomplet());
			this.Localitat.setText(this.model.getLocalitat());
			this.Dni.setText(this.model.getDni());
			this.Data.setValue(this.model.getDatanaixement());
			this.Email.setText(this.model.getEmail());
			this.Telefon.setText("" + this.model.getTelefon());
			this.Club.setValue(this.model.getClub().getNom());
		}
		this.carrgarDatos();

		return this.GestorVentanas;

	}

	public void carrgarDatos() {
		this.lClub = DaoClub.getAll();
		for (Club_Model c : lClub) {
			Club.getItems().add(c.getNom());
		}
		Club.getItems().add("Independent");
	}

	@FXML
	public void initialize() {
	}

	@FXML
	private void Cancelar() {
		GestorVentanas.closeForm("corredor");
	}

	@FXML
	private void Guardar() {
		LocalDate ahora = LocalDate.now();
		LocalDate selected = this.Data.getValue();
		Period periodo = Period.between(selected, ahora);

		if (!this.validar(this.Dni.getText())) {
			this.alert("Error dni", null, "El dni introduit  no existeix");
		} else if (DaoCorredor.get(this.Dni.getText()) != null) {
			this.alert("Error dni", null, "Ja existeix un corredor amb aquest dni");
		} else if (periodo.getYears() < 18) {
			this.alert("Error data de naixement", null, "El corredor te que ser major d'edat");
		} else if (this.Telefon.getText().length() < 9) {
			this.alert("Error telefon", null, "El numero de telefon te que tindre minim 9 digits");
		} else {
			 this.model = new Corredor_Model(this.Dni.getText(), this.Nom.getText(), this.Data.getValue(),
					this.Localitat.getText(), this.Email.getText(), Integer.parseInt(this.Telefon.getText()));
			String Cname = Club.getValue();
			if (this.model.getClub() != null)
				for (Club_Model c : lClub) {
					if (Cname.equals("Independent")) {
						this.model.setClub(null);
					}
					if (Cname.equals(c.getNom()))
						this.model.setClub(c);
				}
			boolean b = this.model.Add(this.model);
			if (b) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(null);
				alert.setHeaderText(null);
				alert.setContentText("Informació guardada correctament");
				alert.showAndWait();
				if (this.isRegistre) {
					this.GestorVentanas.logOut();
					this.GestorVentanas.usuariRegistre.setCorredor(this.model);
					this.GestorVentanas.usuariRegistre.registrar(this.GestorVentanas.usuariRegistre);
				} else
					this.GestorVentanas.closeForm("corredor");

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error corredor formulari");
				alert.setHeaderText(null);
				alert.setContentText("Error al guarda la informació");
				alert.showAndWait();
			}
		}
	}



	public void alert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public boolean validar(String dni) {

		String letraMayuscula = ""; // Guardaremos la letra introducida en formato mayúscula

		// Aquí excluimos cadenas distintas a 9 caracteres que debe tener un dni y
		// también si el último caracter no es una letra
		if (dni.length() != 9 || Character.isLetter(dni.charAt(8)) == false) {
			return false;
		}

		// Al superar la primera restricción, la letra la pasamos a mayúscula
		letraMayuscula = (dni.substring(8)).toUpperCase();

		// Por último validamos que sólo tengo 8 dígitos entre los 8 primeros caracteres
		// y que la letra introducida es igual a la de la ecuación
		// Llamamos a los métodos privados de la clase soloNumeros() y letraDNI()
		if (soloNumeros(dni) == true && letraDNI(dni).equals(letraMayuscula)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean soloNumeros(String dni) {

		int i, j = 0;
		String numero = ""; // Es el número que se comprueba uno a uno por si hay alguna letra entre los 8
							// primeros dígitos
		String miDNI = ""; // Guardamos en una cadena los números para después calcular la letra
		String[] unoNueve = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

		for (i = 0; i < dni.length() - 1; i++) {
			numero = dni.substring(i, i + 1);

			for (j = 0; j < unoNueve.length; j++) {
				if (numero.equals(unoNueve[j])) {
					miDNI += unoNueve[j];
				}
			}
		}

		if (miDNI.length() != 8) {
			return false;
		} else {
			return true;
		}
	}

	private String letraDNI(String dni) {
		// El método es privado porque lo voy a usar internamente en esta clase, no se
		// necesita fuera de ella

		// pasar miNumero a integer
		int miDNI = Integer.parseInt(dni.substring(0, 8));
		int resto = 0;
		String miLetra = "";
		String[] asignacionLetra = { "T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
				"Q", "V", "H", "L", "C", "K", "E" };

		resto = miDNI % 23;

		miLetra = asignacionLetra[resto];

		return miLetra;
	}

}
