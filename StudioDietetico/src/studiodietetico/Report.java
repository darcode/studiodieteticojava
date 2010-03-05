package studiodietetico;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.graphics.Color;

import service.GiornoDieta;
import service.StrutAlimento;
import service.StrutDieta;
import service.StrutPasto;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.*;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;



import command.DietaDAO;
import command.PazienteDAO;
import common.GenericBean;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;



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




	public DynamicReport buildReport(String F) throws Exception {
		Style detailStyle = new Style();
		detailStyle.setBorder(Border.THIN);detailStyle.setStretchWithOverflow(false);
		//			detailStyle.setTransparency(Transparency.OPAQUE);
		Style headerStyle = new Style(); headerStyle.setTransparency(Transparency.OPAQUE);

		Style titleStyle = new Style();
		Style subtitleStyle = new Style();
		Style amountStyle = new Style(); amountStyle.setHorizontalAlign(HorizontalAlign.RIGHT);

		/***
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setTitle("November 2006 sales report")					//defines the title of the report
		.setSubtitle("The items in this report correspond "
				+"to the main products: DVDs, Books, Foods and Magazines")
				.setDetailHeight(17)						//defines the height for each record of the report
				.setMargins(30, 20, 30, 15)							//define the margin space for each side (top, bottom, left and right)
				.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, detailStyle)
				.setColumnsPerPage(1);						//defines columns per page (like in the telephone guide)

		/***
		 * Note that we still didn't call the build() method
		 */

		/***
		 * Column definitions. We use a new ColumnBuilder instance for each
		 * column, the ColumnBuilder.getNew() method returns a new instance
		 * of the builder
		 */
		AbstractColumn columnState = ColumnBuilder.getNew()		//creates a new instance of a ColumnBuilder
		.setColumnProperty("state", String.class.getName())			//defines the field of the data source that this column will show, also its type
		.setTitle("State")											//the title for the column
		.setWidth(85)												//the width of the column
		.build();													//builds and return a new AbstractColumn

		//Create more columns
		AbstractColumn columnBranch = ColumnBuilder.getNew()
		.setColumnProperty("branch", String.class.getName())
		.setTitle("Branch").setWidth(85).setTruncateSuffix("...")
		.build();

		AbstractColumn columnaProductLine = ColumnBuilder.getNew()
		.setColumnProperty("productLine", String.class.getName())
		.setTitle("Product Line").setWidth(250).setFixedWidth(true)
		.build();

		AbstractColumn columnaItem = ColumnBuilder.getNew()
		.setColumnProperty("item", String.class.getName())
		.setTitle("Item").setWidth(85)
		.build();

		AbstractColumn columnCode = ColumnBuilder.getNew()
		.setColumnProperty("id", Long.class.getName())
		.setTitle("ID").setWidth(40)
		.build();

		AbstractColumn columnaCantidad = ColumnBuilder.getNew()
		.setColumnProperty("quantity", Long.class.getName())
		.setTitle("Quantity").setWidth(80)
		.build();

		AbstractColumn columnAmount = ColumnBuilder.getNew()
		.setColumnProperty("amount", Float.class.getName())
		.setTitle("Amount").setWidth(90)
		.setFixedWidth(true) // <--- FIXED COLUMN WIDTH
		.setPattern("$ 0.00")
		//defines a pattern to apply to the values swhown (uses TextFormat)
		.build();

		/***
		 * We add the columns to the report (through the builder) in the order
		 * we want them to appear
		 */
		drb.addColumn(columnState);
		drb.addColumn(columnBranch);
		drb.addColumn(columnaProductLine);
		drb.addColumn(columnaItem);
		drb.addColumn(columnCode);
		drb.addColumn(columnaCantidad);
		drb.addColumn(columnAmount);

		/***
		 * add some more options to the report (through the builder)
		 */
		drb.setUseFullPageWidth(true);	//we tell the report to use the full width of the page. this rezises
		//the columns width proportionally to meat the page width.


		DynamicReport dr = drb.build();	//Finally build the report!

		//JRProperties.setProperty(JRTextElement.PROPERTY_TRUNCATE_SUFFIX, "...");

		return dr;
	}


public static void creaReportDinamico(List listaDatiDB, String[][] campi, String titolo, String fileJRXML) throws Exception{
/*	String[] campi = GenericBean.getFieldsNames(listaDatiDB.get(0));
	ArrayList<String> aux = new ArrayList<String>();
	for (int i = 0; i < campi.length; i++) {
		if ((!(campi[i].substring(campi[i].length()-1)).equals("s"))&&
				(!(campi[i].contains("id")))) {
			aux.add(campi[i]);
		}
	}
	campi = (aux.toArray(new String[aux.size()]));
*/
	
String[] campiAux = new String[campi.length];
	for (int i = 0; i < campi.length; i++) {
		campiAux[i] = campi[i][0];
	}
	//String[] campi = GenericBean.getFieldsNames(listaDatiDB.get(0));
	//Field[] campiHyb = listaDatiDB.get(0).getClass().getFields();
	Report ds = new Report(listaDatiDB, campiAux);
	
	FastReportBuilder drb = new FastReportBuilder();
	drb.setTitle(titolo)
	.setPrintBackgroundOnOddRows(true)                      
    .setUseFullPageWidth(true);  
	for (int i = 0; i < campi.length; i++) {
	
		drb.addColumn(campi[i][0], campi[i][0], campi[i][1], 20);
		
	}
	DynamicReport dr = drb.build();
  /*  DynamicReport dr = drb.addColumn("Nome", "nome", String.class.getName(),30)
                    .addColumn("Tipologia", "tipologia", String.class.getName(),30)
                    .addColumn("Calorie", "calorie", Integer.class.getName(),50)
                    .setTitle("Alimenti")
                    .setPrintBackgroundOnOddRows(true)                      
                    .setUseFullPageWidth(true)
                   
                    
            .build();       
    */
     
    JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), ds);
    JasperViewer.viewReport(jp);    //finally display the report report             
	
	
}
	

	public static void creaReport(List listaDatiDB, Map parametri, String fileJRXML) {
		String[] campi = GenericBean.getFieldsNames(listaDatiDB.get(0));
		ArrayList<String> aux = new ArrayList<String>();
		for (int i = 0; i < campi.length; i++) {
			if ((!(campi[i].substring(campi[i].length()-1)).equals("s"))&&
					(!(campi[i].contains("id")))) {
				aux.add(campi[i]);
			}
		}
		campi = (aux.toArray(new String[aux.size()]));

		try {
			JasperDesign jasperDesign = JRXmlLoader.load("Report/"+fileJRXML+".jrxml");

			JasperCompileManager.compileReportToFile(jasperDesign, "Report/"+fileJRXML+".jasper");

			Report ds = new Report(listaDatiDB, campi);

			JasperPrint jp;

			jp = JasperFillManager.fillReport("Report/"+fileJRXML+".jasper", parametri, ds);
			 JasperViewer.viewReport(jp);   
			JasperExportManager.exportReportToPdfFile(jp, "Report/Output/report"+fileJRXML+".pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void creaReportDieta(ArrayList<GiornoDieta> giorniDieta, String fileJRXML) {
		String[] campi = {"giorno", "pasto", "alimento", "quantita"};
		ArrayList<StrutDieta> dieta = new ArrayList<StrutDieta>();
		ArrayList<Object> dietaObj = new ArrayList<Object>();
		Object[] arr;
		StrutDieta item;
		for (GiornoDieta giorno : giorniDieta) {
			
			for (StrutPasto pasto : giorno.getPasti()) {
			
				for (StrutAlimento alimento : pasto.getAlimenti()) {				
					item = new StrutDieta();
					if (item.giorno.equals("")) 
						item.giorno = giorno.getGiorno();
					else
						item.giorno = "";
					if (item.pasto.equals("")) 
						item.pasto = pasto.getNomePasto();
					else
						item.pasto = "";
					item.alimento = alimento.getNomeAlimento();
					item.quantita = 200;
					dieta.add(item);
					dietaObj.add(item);
				}
			}
			
		}
	
		try {
			JasperDesign jasperDesign = JRXmlLoader.load("Report/"+fileJRXML+".jrxml");

			JasperCompileManager.compileReportToFile(jasperDesign, "Report/"+fileJRXML+".jasper");
			 
			Report ds = new Report(dietaObj, campi);

			JasperPrint jp;
		
			jp = JasperFillManager.fillReport("Report/"+fileJRXML+".jasper", null, ds);
			 JasperViewer.viewReport(jp);   
			//JasperExportManager.exportReportToPdfFile(jp, "Report/Output/report"+fileJRXML+".pdf");
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	public static void main(String[] args) throws Exception
	{
		/*DietaDAO dieta = new DietaDAO();
		List alimenti = dieta.getAlimentiList();  
		Map parameters = new HashMap();
		parameters.put("titolo", "Alimenti");
		//String[][] fields = new String[][] {{"nome", String.class.getName()}, {"tipologia", String.class.getName()}, {"calorie", Integer.class.getName()}};
		//Report.creaReportDinamico(alimenti, fields, "Alimenti", "alimento");
		List pazienti = PazienteDAO.getPazienti();
		String[][] fields = new String[][] {{"nome", String.class.getName()}, {"tipologia", String.class.getName()}, {"calorie", Integer.class.getName()}};
		Report.creaReportDinamico(alimenti, fields, "Alimenti", "alimento");*/
		
		
		ArrayList<GiornoDieta> dieta = new ArrayList<GiornoDieta>();
		GiornoDieta g = new GiornoDieta();
		g.setGiorno("Lunedi");
		ArrayList<StrutPasto> p = new ArrayList<StrutPasto>();
		StrutPasto pasto = new StrutPasto("primo");
		pasto.addAlimento(new StrutAlimento("pasto1", "tipologia1", 100, 4));
		pasto.addAlimento(new StrutAlimento("pasto2", "tipologia2", 200, 5));
		p.add(pasto);
		g.setPasti(p);
		dieta.add(g);
		creaReportDieta(dieta, "dieta");
	}
}

