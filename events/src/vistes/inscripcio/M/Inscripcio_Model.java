package vistes.inscripcio.M;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import dao.DaoInscripcio;

import vistes.carrera.M.Carrera_Model;
import vistes.club.M.Club_Model;
import vistes.corredor.M.Corredor_Model;

@Entity(name = "inscripcio")
@Table(name = "inscripcio")
public class Inscripcio_Model implements Comparable<Inscripcio_Model> {
	@Id
	public int id;
	public int dorsal;
	public LocalDate dataInscripcio;
	public Integer posicio;
	@JoinColumn(name = "nomcarrera")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Carrera_Model carrera;

	@JoinColumn(name = "dnicorredor")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Corredor_Model corredor;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "clubnif")
	private Club_Model nif;

	public Inscripcio_Model() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Inscripcio_Model(int id,int dorsal, LocalDate dataInscripcio, Integer posicio) {
		super();
		this.id = id;
		this.dorsal = dorsal;
		this.dataInscripcio = dataInscripcio;
		this.posicio = posicio;
	}

	public Integer getPosicio() {
		return posicio;
	}

	public void setPosicio(Integer posicio) {
		this.posicio = posicio;
	}

	public Carrera_Model getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera_Model carrera) {
		this.carrera = carrera;
	}

	public Corredor_Model getCorredor() {
		return corredor;
	}

	public void setCorredor(Corredor_Model corredor) {
		this.corredor = corredor;
	}

	public Club_Model getNif() {
		return nif;
	}

	public void setNif(Club_Model nif) {
		this.nif = nif;
	}

	public int getDorsal() {
		return dorsal;
	}

	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}

	public LocalDate getDataInscripcio() {
		return dataInscripcio;
	}

	public void setDataInscripcio(LocalDate dataInscripcio) {
		this.dataInscripcio = dataInscripcio;
	}

	public  void Add(Inscripcio_Model c)  {

		 DaoInscripcio.add(c);
		
	}

	public List<Inscripcio_Model> load() {
		return DaoInscripcio.getAll();
	}

	public void del(Inscripcio_Model inscripcio) {
		// TODO Auto-generated method stub

		DaoInscripcio.del(inscripcio);
	}

	public List<Inscripcio_Model> getByCarrera(String carrera ) {
		return DaoInscripcio.getByCarrera(carrera);

	}
	public List<Inscripcio_Model> getByCorredor(String corredor ) {
		return DaoInscripcio.getByCorredor(corredor);

	}
	public void setAll(Inscripcio_Model ins) {
		// TODO Auto-generated method stub
		this.setCarrera(ins.getCarrera());
		this.setCorredor(ins.getCorredor());
		this.setDataInscripcio(ins.getDataInscripcio());
		this.setDorsal(ins.getDorsal());
		this.setNif(ins.getNif());
		this.setPosicio(ins.getPosicio());
		
	}

	@Override
	public int compareTo(Inscripcio_Model o) {
		// TODO Auto-generated method stub
		return posicio.compareTo(o.getPosicio());
	}

	public int getLastDorsal() {
		return DaoInscripcio.getLastDorsal();
	}
	public void guardar(Inscripcio_Model inscripcio) {
		DaoInscripcio.add(inscripcio);
	}
}
