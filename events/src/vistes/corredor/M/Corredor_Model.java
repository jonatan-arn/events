package vistes.corredor.M;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import dao.DaoCorredor;


import vistes.club.M.Club_Model;
import vistes.inscripcio.M.Inscripcio_Model;

@Entity(name="corredor")
@Table(name="corredor")
public class Corredor_Model  {
	@Id
	public String dni;
	String nomicognoms,localitat,email;
	public LocalDate datanaixement;
	public int telefon;
	
	@JoinColumn(name = "clubnif")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Club_Model club;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "corredor")
	private List<Inscripcio_Model> Inscripcions = new ArrayList<Inscripcio_Model>();
	
	public Corredor_Model() {
		
	}

	public Corredor_Model(String dni, String nomicognoms, LocalDate datanaixement, String localitat, String email,
			int telefon) {
		super();
		this.dni = dni;
		this.nomicognoms = nomicognoms;
		this.datanaixement = datanaixement;
		this.localitat = localitat;
		this.email = email;
		this.telefon = telefon;
	}

	public Club_Model getClub() {
		return club;
	}

	public void setClub(Club_Model club) {
		this.club = club;
	}

	public List<Inscripcio_Model> getInscripcions() {
		return Inscripcions;
	}

	public void setInscripcions(List<Inscripcio_Model> inscripcions) {
		Inscripcions = inscripcions;
	}

	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNomcomplet() {
		return nomicognoms;
	}
	public void setNomcomplet(String nomicognoms) {
		this.nomicognoms = nomicognoms;
	}
	public LocalDate getDatanaixement() {
		return datanaixement;
	}
	public void setDatanaixement(LocalDate datanaixement) {
		this.datanaixement = datanaixement;
	}
	public String getLocalitat() {
		return localitat;
	}
	public void setLocalitat(String localitat) {
		this.localitat = localitat;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTelefon() {
		return telefon;
	}
	public void setTelefon(int telefon) {
		this.telefon = telefon;
	}
	public void setAll(Corredor_Model corredor) {
		this.setClub(corredor.getClub());
		this.setDatanaixement(corredor.getDatanaixement());
		this.setEmail(corredor.getEmail());
		this.setInscripcions(corredor.getInscripcions());
		this.setLocalitat(corredor.getLocalitat());
		this.setNomcomplet(corredor.getNomcomplet());
		this.setTelefon(corredor.getTelefon());
	}
	
	public boolean Add(Corredor_Model c)  {
		DaoCorredor dao = new DaoCorredor();
		boolean b = dao.add(c);
		return b;
	}
	public List<Corredor_Model> load() {
		return DaoCorredor.getAll();
	}
	public void del(Corredor_Model corredor) {
		// TODO Auto-generated method stub
		
		DaoCorredor.del(corredor);
	}
	public Corredor_Model get(String dni) {
		return DaoCorredor.get(dni);
	}
	
}
