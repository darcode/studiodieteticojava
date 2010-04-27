package common;

import org.hibernate.proxy.HibernateProxy;

public class HibernateUtils {

	public static <T> T getProxiedObject(T proxy){
	      if ( proxy instanceof HibernateProxy ) {
	         return (T) ( ( HibernateProxy ) proxy ).getHibernateLazyInitializer().getImplementation();
	      }
	      return proxy;
	   }
}
