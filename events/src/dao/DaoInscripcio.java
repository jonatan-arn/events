package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import conexioHibernate.UtilesHibernate;
import vistes.inscripcio.M.Inscripcio_Model;

public class DaoInscripcio {
	public static void add(Inscripcio_Model ins) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();

		sesion.beginTransaction();

		Inscripcio_Model i = sesion.get(Inscripcio_Model.class, ins.getId());
		System.out.println(i);
		if (i != null) {
			i.setAll(ins);
			sesion.update(i);

		} else {
			sesion.save(ins);
		}
		sesion.getTransaction().commit();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Inscripcio_Model> getAll() {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM inscripcio e");
		List<Inscripcio_Model> llista = (List<Inscripcio_Model>) q.list();
		sesion.getTransaction().commit();
		return llista;

	}

	public static void del(Inscripcio_Model inscripcio) {
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		sesion.delete(inscripcio);
		sesion.getTransaction().commit();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Inscripcio_Model> getByCarrera(String carrera) {
		// TODO Auto-generated method stub
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM inscripcio e where nomcarrera = " + carrera);
		List<Inscripcio_Model> llista = (List<Inscripcio_Model>) q.list();
		sesion.getTransaction().commit();
		return llista;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Inscripcio_Model> getByCorredor(String corredor) {
		// TODO Auto-generated method stub
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM inscripcio e where dnicorredor like '" + corredor + "'");
		List<Inscripcio_Model> llista = (List<Inscripcio_Model>) q.list();
		sesion.getTransaction().commit();
		return llista;
	}

	public static int getLastDorsal() {

		// TODO Auto-generated method stub
		SessionFactory factory = UtilesHibernate.getSessionFactory();
		Session sesion = factory.getCurrentSession();
		sesion.beginTransaction();
		Query q = sesion.createQuery("SELECT e FROM inscripcio e order by id DESC  ");
		q.setMaxResults(1);
		Inscripcio_Model ultima = (Inscripcio_Model) q.uniqueResult();
		sesion.getTransaction().commit();

		if (ultima == null)
			return 1;
		else
			return ultima.dorsal;
	}
}
