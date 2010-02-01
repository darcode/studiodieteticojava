package command;


import hibernate.Alimento;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class BaseDAO {

	public BaseDAO() {
	}

	public static Session getSession() {
		Session session = (Session) BaseDAO.session.get();
		if (session == null) {
			try {
				
			session = sessionFactory.openSession();
			}
			catch (HibernateException ex){
				
				String msg = "Method = getSession; Calling sessionFactory.openSession(); Thrown: ";
				
			}
			BaseDAO.session.set(session);
		}
		return session;
	}

	protected static void begin() {
		
		try {
			getSession().beginTransaction();
		}
		catch (HibernateException ex){
			String msg = "Method = begin(); Calling getSession().beginTransaction(); Thrown: ";
		}
	}

	protected static void commit() {
		Transaction tx = null;
		try {
			tx = getSession().getTransaction();
			tx.commit();
		}
		catch (HibernateException ex){
			String msg = "Method = commit(); Calling getSession().getTransaction().commit(); Thrown: ";
		} 	
	}

	protected void rollback() {
		Transaction tx=getSession().getTransaction();
		try {
			tx.rollback();
		} catch (HibernateException e) {
			System.out.println("Cannot rollback Hibernate transaction" + e.getMessage());
		}
		try {
			getSession().close();
		} catch (HibernateException e) {
			System.out.println("Cannot close Hibernate session:" + e.getMessage());
		}
		BaseDAO.session.set(null);
	}

	public static void close() {
		getSession().close();
		BaseDAO.session.set(null);
	}
	

	private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
}