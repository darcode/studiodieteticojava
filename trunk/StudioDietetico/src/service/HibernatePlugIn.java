package service;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernatePlugIn /*implements PlugIn*/ {
   private String _configFilePath = "/hibernate.cfg.xml";

   
    /**
     * the key under which the <code>SessionFactory</code> instance is stored
     * in the <code>ServletContext</code>.
     */
    public static final String SESSION_FACTORY_KEY = SessionFactory.class.getName();

	private SessionFactory _factory = null;

   public SessionFactory get_factory() {
		return _factory;
	}

	public void set_factory(SessionFactory _factory) {
		this._factory = _factory;
	}

public void destroy() {
	   try{
		   _factory.close();
	   }catch(HibernateException e){
			System.out.println("Unable to close Hibernate Session Factory: " + e.getMessage());
	   }
       
   }

   public void init() throws ServletException {
        Configuration configuration = null;
        URL configFileURL = null;
//        ServletContext context = null;

	 try {
		 configFileURL = HibernatePlugIn.class.getResource(_configFilePath);
		 configuration = (new Configuration()).configure(configFileURL);
		 _factory = configuration.buildSessionFactory();
	 } catch(HibernateException e) {
		System.out.println("Error while initializing hibernate: " + e.getMessage());
	 }
	 
   }
   
  /* public void init(ActionServlet servlet, ModuleConfig config) throws ServletException {
	        Configuration configuration = null;
	        URL configFileURL = null;
	        ServletContext context = null;

		 try{
	            configFileURL = HibernatePlugIn.class.getResource(_configFilePath);
	            context = servlet.getServletContext();
	            configuration = (new Configuration()).configure(configFileURL);
	            _factory = configuration.buildSessionFactory();
				//Set the factory into session
				context.setAttribute(SESSION_FACTORY_KEY, _factory);

		 }catch(HibernateException e){
			System.out.println("Error while initializing hibernate: " + e.getMessage());
		 }
		 

	   }*/

    /**
     * Setter for property configFilePath.
     * @param configFilePath New value of property configFilePath.
     */
    public void setConfigFilePath(String configFilePath) {
        if ((configFilePath == null) || (configFilePath.trim().length() == 0)) {
            throw new IllegalArgumentException(
                    "configFilePath cannot be blank or null.");
        }
        
       
        _configFilePath = configFilePath;
    }


/*(SessionFactory) servletContext.getAttribute
(HibernatePlugIn.SESSION_FACTORY_KEY);
*/
	
}