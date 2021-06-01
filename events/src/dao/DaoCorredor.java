package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import conexioHibernate.UtilesHibernate;
import vistes.carrera.M.Carrera_Model;
import vistes.club.M.Club_Model;
import vistes.corredor.M.Corredor_Model;
import vistes.usuarios.M.Usuarios_Model;

public class DaoCorredor {
	public boolean add(Corredor_Model corredor) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();

		Corredor_Model c = sesion.get(Corredor_Model.class, corredor.getDni());

		try {
			if (c != null) {
				
				c.setAll(corredor);
				sesion.update(c);
				System.out.println(c.getClub());
				System.out.println("update");
			} else {
				sesion.save(corredor);
				System.out.println("save");

			}

			sesion.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}

	@SuppressWarnings("rawtypes")
	public static List<Corredor_Model> getAll() {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM corredor e ");
		List<Corredor_Model> llista = (List<Corredor_Model>) q.list();
		sesion.getTransaction().commit();
		return llista;

	}

	public static void del(Corredor_Model corredor) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(corredor);
		sesion.getTransaction().commit();

	}

	public static Corredor_Model get(String dni) {
		// TODO Auto-generated method stub
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM corredor e where dni like '"+dni+"'");
		List<Corredor_Model> llista = (List<Corredor_Model>) q.list();
		sesion.getTransaction().commit();
		return llista.get(0);

	}
}
