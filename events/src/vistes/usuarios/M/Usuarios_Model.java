package vistes.usuarios.M;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dao.DaoUsers;
import vistes.corredor.M.Corredor_Model;

@Entity(name = "users")
@Table(name = "users")
public class Usuarios_Model {
	@Id
	private int id;
	private String user;
	private String password;
	public String tipus;
	@JoinColumn(name = "dnicorredor")
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Corredor_Model corredor;

	public Usuarios_Model() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Corredor_Model getCorredor() {
		return corredor;
	}

	public void setCorredor(Corredor_Model corredor) {
		this.corredor = corredor;
	}

	public Usuarios_Model(String user, String password, String tipus) {
		super();
		this.user = user;
		this.password = password;
		this.tipus = tipus;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTipus() {
		return tipus;
	}

	public void setTipus(String tipus) {
		this.tipus = tipus;
	}

	public Usuarios_Model getCredentials(Usuarios_Model model) {
		DaoUsers dao = new DaoUsers();

		
		return  dao.getUser(model);

	}

	public boolean checkUser(Usuarios_Model model) {
		DaoUsers dao = new DaoUsers();
		boolean b = dao.checkNom(model);
		return b;
	}

	public void registrar(Usuarios_Model model) {
		DaoUsers dao = new DaoUsers();
		dao.registrar(model);
	}
}
