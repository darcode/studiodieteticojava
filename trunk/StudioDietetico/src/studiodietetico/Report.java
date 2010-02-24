package studiodietetico;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import command.DietaDAO;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class Report implements JRDataSource{
	

		  private String[] fields; 
		  private Iterator iterator; 
		  private Object currentValue; 

		  public Report(List list, String[] fields) { 
		    this.fields = fields; 
		    this.iterator = list.iterator(); 
		  } 

		  public Object getFieldValue(JRField field) throws JRException { 
		    Object value = null; 
		    int index = getFieldIndex(field.getName()); 
		    if (index > -1) { 
		      Object[] values = (Object[])currentValue; 
		      value = values[index]; 
		    } 
		    return value; 
		  } 

		  public boolean next() throws JRException { 
		    currentValue = iterator.hasNext() ? iterator.next() : null; 
		    return (currentValue != null); 
		  } 

		  private int getFieldIndex(String field) { 
		    int index = -1; 
		    for (int i = 0; i < fields.length; i++) { 
		      if (fields[i].equals(field)) { 
		        index = i; 
		        break; 
		      } 
		    } 
		    return index; 
		  } 

		
public static void creaReport(List listaDatiDB, Map parametri, String fileJRXML,  String[] campi) {

	try {
	  JasperDesign jasperDesign = JRXmlLoader.load("Report/"+fileJRXML+".jrxml");
	 
	  JasperCompileManager.compileReportToFile(jasperDesign, "Report/"+fileJRXML+".jasper");

	  Report ds = new Report(listaDatiDB, campi);
	  
	  JasperPrint jp;
	
		jp = JasperFillManager.fillReport("Report/"+fileJRXML+".jasper", parametri, ds);

	 JasperExportManager.exportReportToPdfFile(jp, "Report/Output/report"+fileJRXML+".pdf");
	} catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void main(String[] args) throws Exception
{
	  DietaDAO dieta = new DietaDAO();
	  List alimenti = dieta.getAlimentiList();  
	  Map parameters = new HashMap();
	  parameters.put("titolo", "Alimenti");
	  String[] fields = new String[] {"nome", "tipologia", "calorie"};
	  Report.creaReport(alimenti, parameters, "alimento", fields);
	
}
}

