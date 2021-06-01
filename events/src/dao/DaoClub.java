package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import conexioHibernate.UtilesHibernate;
import vistes.carrera.M.Carrera_Model;
import vistes.club.M.Club_Model;
import vistes.corredor.M.Corredor_Model;

public class DaoClub {
	public boolean add(Club_Model club) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
			
		Club_Model c = sesion.get(Club_Model.class, club.getNif());

		try {
			if (c != null) {
				c.setAll(club);;
				sesion.update(c);
			} else {
				sesion.save(club);
			}

			sesion.getTransaction().commit();
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	public static List<Club_Model> getAll() {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM club e");
		List<Club_Model> llista = (List<Club_Model>) q.list();
		sesion.getTransaction().commit();
		return llista;

	}
	public static void del(Club_Model club) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(club);
		sesion.getTransaction().commit();

	}
	public static Club_Model get(String dni) {
		// TODO Auto-generated method stub
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM club e where nif like '"+dni+"'");
		List<Club_Model> llista = (List<Club_Model>) q.list();
		sesion.getTransaction().commit();
		return llista.get(0);

	}
	
}
