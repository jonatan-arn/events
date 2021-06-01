package vistes.carrera.M;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dao.DaoCarrera;
import javafx.fxml.FXML;

import vistes.inscripcio.M.Inscripcio_Model;


@Entity(name="carrera")
@Table(name="carrera")
public class Carrera_Model implements Comparable<Carrera_Model>  {
	@Id
	public int id;
	public String nom;

	public String localitat,horainici;
	public LocalDate data;
	public Integer NParticipants;
	public float distancia,preu;
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "carrera")
	private List<Inscripcio_Model> Inscripcions = new ArrayList<Inscripcio_Model>();
	public Carrera_Model() {
		
	}
	
	
	public Carrera_Model(String nom, String localitat, LocalDate data, String hora, Integer nParticipants, float distancia,
			float preu) {
		super();
		this.nom = nom;
		this.localitat = localitat;
		this.data = data;
		this.horainici = hora;
		NParticipants = nParticipants;
		this.distancia = distancia;
		this.preu = preu;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getHorainici() {
		return horainici;
	}


	public void setHorainici(String horainici) {
		this.horainici = horainici;
	}


	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLocalitat() {
		return localitat;
	}

	public void setLocalitat(String localitat) {
		this.localitat = localitat;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getHora() {
		return horainici;
	}

	public void setHora(String hora) {
		this.horainici = hora;
	}

	public Integer getNParticipants() {
		return NParticipants;
	}

	public void setNParticipants(Integer nParticipants) {
		NParticipants = nParticipants;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

	public float getPreu() {
		return preu;
	}

	public void setPreu(float preu) {
		this.preu = preu;
	}

	public List<Inscripcio_Model> getInscripcions() {
		return Inscripcions;
	}

	public void setInscripcions(List<Inscripcio_Model> inscripcions) {
		Inscripcions = inscripcions;
	}

	@FXML
	public boolean Add(Carrera_Model carrera)  {
		DaoCarrera dao = new DaoCarrera();
		boolean b = dao.add(carrera);
		return b;
	}
	public List<Carrera_Model> load() {
		DaoCarrera dao = new DaoCarrera();
		return dao.getAll();
	}
	public void setAll(Carrera_Model carrera) {
		this.setData(carrera.getData());
		this.setDistancia(carrera.getDistancia());
		this.setHora(carrera.getHora());
		this.setHorainici(carrera.getHorainici());
		this.setInscripcions(carrera.getInscripcions());
		this.setLocalitat(carrera.getLocalitat());
		this.setNom(carrera.getNom());
		this.setNParticipants(carrera.getNParticipants());
		this.setPreu(carrera.getPreu());
	}

	public void del(Carrera_Model carrera) {
		// TODO Auto-generated method stub
		
		DaoCarrera dao = new DaoCarrera();
		dao.del(carrera);
	}


	@Override
	public int compareTo(Carrera_Model o) {
		// TODO Auto-generated method stub
		return NParticipants.compareTo(o.getNParticipants());
	}
}
