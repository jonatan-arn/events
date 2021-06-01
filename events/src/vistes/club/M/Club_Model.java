package vistes.club.M;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dao.DaoCarrera;
import dao.DaoClub;
import dao.DaoInscripcio;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import vistes.carrera.M.Carrera_Model;
import vistes.inscripcio.M.Inscripcio_Model;

@Entity(name="club")
@Table(name="club")
public class Club_Model {
	@Id
	private String nif;
	private String localitat,president,nom;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "nif")
	private List<Inscripcio_Model> Inscripcions = new ArrayList<Inscripcio_Model>();
	public Club_Model() {
		
	}
	public Club_Model(String nif,String nom,String localitat, String president) {
		super();
		this.nif = nif;
		this.nom = nom;
		this.localitat = localitat;
		this.president = president;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getLocalitat() {
		return localitat;
	}
	public void setLocalitat(String localitat) {
		this.localitat = localitat;
	}
	public String getPresident() {
		return president;
	}
	public void setPresident(String president) {
		this.president = president;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public List<Inscripcio_Model> getInscripcions() {
		return Inscripcions;
	}
	public void setInscripcions(List<Inscripcio_Model> inscripcions) {
		Inscripcions = inscripcions;
	}
	@FXML
	public boolean Add(Club_Model club)  {
		DaoClub dao = new DaoClub();
		boolean b = dao.add(club);
		return b;
	}
	public List<Club_Model> load() {
		DaoClub dao = new DaoClub();
		return dao.getAll();
	}
	public void del(Club_Model club) {
		// TODO Auto-generated method stub
		
		DaoClub dao = new DaoClub();
		dao.del(club);
	}
	public void setAll(Club_Model club) {
		// TODO Auto-generated method stub
		this.setInscripcions(club.getInscripcions());
		this.setLocalitat(club.getLocalitat());
		this.setNom(club.getNom());
		this.setPresident(club.getPresident());
	}
}
