package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vistes.carrera.C.CarreraFormulariController;
import vistes.carrera.M.Carrera_Model;
import vistes.club.C.ClubFormulariController;
import vistes.club.M.Club_Model;
import vistes.corredor.C.CorredorFormulariController;
import vistes.corredor.M.Corredor_Model;
import vistes.home.C.HomeController;
import vistes.inscripcio.C.InscripcioFormulariController;
import vistes.inscripcio.M.Inscripcio_Model;
import vistes.usuarios.C.LoginViewController;
import vistes.usuarios.C.registerController;
import vistes.usuarios.M.Usuarios_Model;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	private Stage PrincipalStage;
	private Stage Register;
	private Stage formulari;
	private Stage formulari2;
	private Stage login;
	
	public String tipus;
	private HomeController HomeController;
	public Corredor_Model corredorRegistre;
	public Usuarios_Model usuariRegistre;
	public Club_Model club;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.login = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/vistes/usuarios/V/Login.fxml"));
			AnchorPane oBorder = loader.load();

			Scene scene = new Scene(oBorder);

			this.login.setTitle("Login");
			this.login.setScene(scene);

			LoginViewController Controller = loader.getController();
			Controller.setGestorVentanas(this);
			this.login.setResizable(false);
			this.login.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void register() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/vistes/usuarios/V/register.fxml"));
			AnchorPane oBorder = loader.load();

			Scene scene = new Scene(oBorder);

			this.login.setTitle("Registre");
			this.login.setScene(scene);
			this.login.setResizable(false);
			registerController Controller = loader.getController();
			Controller.setGestorVentanas(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logOut() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/vistes/usuarios/V/Login.fxml"));
			AnchorPane oBorder = loader.load();

			Scene scene = new Scene(oBorder);

			this.login.setTitle("Login");
			this.login.setScene(scene);
			this.login.setResizable(false);
			LoginViewController Controller = loader.getController();
			Controller.setGestorVentanas(this);
			try {
				this.PrincipalStage.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			this.login.show();
			try {
				formulari.hide();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showHome(Usuarios_Model modeloLogin) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/vistes/home/V/Home.fxml"));
			AnchorPane oBorder = loader.load();

			PrincipalStage = new Stage();
			PrincipalStage.setTitle("Vista Principal");

			// PrincipalStage.initOwner(primaryStage);
			Scene scene = new Scene(oBorder);
			PrincipalStage.setScene(scene);

			// Give the controller access to the main app.
			HomeController Controller = loader.getController();
			System.out.println(modeloLogin);
			this.HomeController = Controller.setGestorVentanas(this, modeloLogin);

			// LoginStage.hide(); Hide = Close = Destroy = Dispose
			this.login.close();
			PrincipalStage.show();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public void closeForm(String vista) {
		if (vista.equals("carrera"))
			this.HomeController.showCarrera();
		else if (vista.equals("club"))
			this.HomeController.showClub();
		else if (vista.equals("corredor"))
			this.HomeController.showCorredor();
		else if (vista.equals("inscripcio"))
			this.HomeController.showInscripcio();
		formulari.hide();
	}
	public void closeClubForm()
	{
		formulari2.hide();
	}
	public void showForm(String form, Object obj) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(form));
			BorderPane oBorder = loader.load();

			formulari = new Stage();

			// PrincipalStage.initOwner(primaryStage);
			Scene scene = new Scene(oBorder);
			formulari.setScene(scene);
			this.formulari.setResizable(false);
			// Give the controller access to the main app.
			if (form.equals("/vistes/club/V/ClubFormulari.fxml")) {
				formulari.setTitle("Formulari club");
				ClubFormulariController Controller = loader.getController();
				Controller.setGestorVentanas(this, (Club_Model) obj);
				this.formulari.show();
			} else if (form.equals("/vistes/carrera/V/CarreraFormulari.fxml")) {
				formulari.setTitle("Formulari carrera");
				CarreraFormulariController Controller = loader.getController();
				Controller.setGestorVentanas(this, (Carrera_Model) obj);
				this.formulari.show();
			} else if (form.equals("/vistes/corredor/V/CorredorFormulari.fxml")) {
				formulari.setTitle("Formulari corredor");
				CorredorFormulariController Controller = loader.getController();
				Controller.setGestorVentanas(this, (Corredor_Model) obj, false);
				this.formulari.show();
			} else if (form.equals("/vistes/inscripcio/V/InscripcioFormulari.fxml")) {
				formulari.setTitle("Formulari inscripcio");
				InscripcioFormulariController Controller = loader.getController();
				Controller.setGestorVentanas(this, (Inscripcio_Model) obj);
				this.formulari.show();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void showRegistreCorredor() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/vistes/corredor/V/CorredorFormulari.fxml"));
			BorderPane oBorder = loader.load();

			formulari = new Stage();

			// PrincipalStage.initOwner(primaryStage);
			Scene scene = new Scene(oBorder);
			formulari.setScene(scene);

			// Give the controller access to the main app.

			formulari.setTitle("Formulari corredor");
			CorredorFormulariController Controller = loader.getController();
			Controller.setGestorVentanas(this, null, true);
			this.formulari.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
