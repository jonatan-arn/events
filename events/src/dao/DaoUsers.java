package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import conexioHibernate.UtilesHibernate;
import vistes.usuarios.M.Usuarios_Model;

public class DaoUsers {
	public Usuarios_Model getUser(Usuarios_Model user) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM users e");
		List<Usuarios_Model> qResult = q.list();
		for (Usuarios_Model us : qResult) {
			if (us.getUser().equals(user.getUser())) {
				sesion.getTransaction().commit();
				return us;
			}
		}
		sesion.getTransaction().commit();

		return qResult.get(0);
	}

	public boolean checkNom(Usuarios_Model user) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM users e where user like '" + user.getUser() + "'");
		List<Usuarios_Model> qResult = q.list();
		if (qResult.size() > 0) {
			sesion.getTransaction().commit();
			return true;
		} else {
			sesion.getTransaction().commit();
			return false;
		}

	}

	public void registrar(Usuarios_Model user) {
		Usuarios_Model u = user;
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction(); 
		sesion.saveOrUpdate(u);
		sesion.getTransaction().commit();
	}
}
