package service;


import javax.servlet.ServletException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import command.AlimentoDAO;



public class Main {

	public static void main(String[] args) {
		
		AlimentoDAO a = new AlimentoDAO();
		a.insAlimento("nome", "tipologia");
		//System.out.println("ok");
//		String s = Utils.ConvertDateToString(new Date());
//		System.out.println(s);
		
//		System.out.println("Prova Hibernate...");
//		
		
		//System.out.println(ApplicationUtils.criptWord("admin"));
		
		
//		Configuration cfg = new Configuration().configure();
//		
//		HibernatePlugIn hibernate = new HibernatePlugIn();
//		try {
//			hibernate.init();
//			System.out.println("Connessione con Hibernate avvenuta...");
//		} catch (ServletException e) {
//			System.out.println("Errore durante il tentarivo di connessione con Hibernate...");
//			e.printStackTrace();
//		}
//		
//		Session session = null;
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//	       session =sessionFactory.openSession();
//		 Transaction tx = session.beginTransaction();
//	       
//		  User user = new User();
//	       user.setNomeUtente("supp");
//	       user.setPassword(ApplicationUtils.criptWord("supp"));
//	       user.setRuolo(ApplicationConstants.User_Level_Supervisore);
//	       user.setStato(ApplicationConstants.User_Stato_Perfezionato);
//		 
//			session.saveOrUpdate(user);
//			tx.commit();
//		
//		Session session = null;
//
//	    try{
//	      // This step will read hibernate.cfg.xml and prepare hibernate for use
//	      SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//	       session =sessionFactory.openSession();
//	       
//	       User user = new User();
//	       user.setNomeUtente("sav");
//	       user.setPassword("sav");
//	       user.setRuolo(ApplicationConstants.User_Level_Administrator);
//	       user.setStato(ApplicationConstants.User_Stato_Perfezionato);
//	       
//	        
//	       String pwdCod = null;
//	       byte[] defaultBytes = user.getPassword().getBytes();
//	       try{
//	       	MessageDigest algorithm = MessageDigest.getInstance("MD5");
//	       	algorithm.reset();
//	       	algorithm.update(defaultBytes);
//	       	byte messageDigest[] = algorithm.digest();
//	                   
//	       	StringBuffer hexString = new StringBuffer();
//	       	for (int i=0;i<messageDigest.length;i++) {
//	       		hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//	       	}
////	       	String foo = messageDigest.toString();
//	       	System.out.println("sessionid "+user.getPassword()+" md5 version is "+hexString.toString());
//	       	pwdCod=hexString+"";
//	       }catch(NoSuchAlgorithmException nsae){
//	                   
//	       }
//	       user.setPassword(pwdCod);
//	       
////	       session.createCriteria(
////			"it.exhicon.bionetwork.model.User").add(
////			Example.create(user)).list();
////	       
//	       Transaction tx = session.beginTransaction();
//	       
//			session.saveOrUpdate(user);
//			tx.commit();
//	       
//	        //Create new instance of Contact and set values in it by reading them from form object
//	         System.out.println("Inserting Record");
//	         
////	         UserHome hh = new UserHome();
//	         
////	        Contact contact = new Contact();
////	        contact.setId(3);
////	        contact.setFirstName("Deepak");
////	        contact.setLastName("Kumar");
////	        contact.setEmail("deepak_38@yahoo.com");
////	        session.save(contact);
////	        System.out.println("Done");
//	    }catch(Exception e){
//	      System.out.println(e.getMessage());
//	    }finally{
//	      // Actual contact insertion will happen at this step
//	      session.flush();
//	      session.close();
//
//	      }
//	    
	  }
	}
