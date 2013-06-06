package cn.npnt.tiaps.base.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 工具类，负责生成Hibernate的SessionFactory
 * 
 * @author lijia
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory = null;
	static {
		try {
			sessionFactory = new Configuration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	} 

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}