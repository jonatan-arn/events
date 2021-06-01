package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import conexioHibernate.UtilesHibernate;
import vistes.carrera.M.Carrera_Model;
import vistes.club.M.Club_Model;
import vistes.usuarios.M.Usuarios_Model;

public class DaoCarrera {
	public boolean add(Carrera_Model carrera) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();

		Carrera_Model c = sesion.get(Carrera_Model.class, carrera.getId());

		try {
			if (c != null) {
				c.setAll(carrera);

				sesion.update(c);
			} else {
				sesion.save(carrera);
			}

			sesion.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public static List<Carrera_Model> getAll() {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM carrera e");
		List<Carrera_Model> llista = (List<Carrera_Model>) q.list();
		sesion.getTransaction().commit();
		return llista;

	}

	public static void del(Carrera_Model carrera) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(carrera);
		sesion.getTransaction().commit();

	}

}
