package studiodietetico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;

import service.Utils;

import command.PazienteDAO;

public class RegistraPazienteView extends ViewPart {

	private Composite top = null;
	private Label labelCognPaz = null;
	private Text textCognPaz = null;
	private Label labelNomePaz = null;
	private Text textNomePaz = null;
	private Label labelSessoPaz = null;
	private Button radioButtonM = null;
	private Button radioButtonF = null;
	private Label labelDataNascPaz = null;
	private Combo comboGiorno = null;
	private ComboViewer comboViewer = null;
	private Combo comboMese = null;
	private ComboViewer comboViewer1 = null;
	private Combo comboAnno = null;
	private ComboViewer comboViewer2 = null;
	private Label labelCodFiscPaz = null;
	private Text textCodFiscPaz = null;
	private Label labelIndirizzoPaz = null;
	private Text textIndirizzoPaz = null;
	private Label labelCittPaz = null;
	private Text textCittPaz = null;
	private Label labelCAP = null;
	private Text textCAP = null;
	private Label labelProvincia = null;
	private Text textProvincia = null;
	private Label labelProfessPaz = null;
	private Text textProfessPaz = null;
	private Label labelTel1Paz = null;
	private Text textTel1Paz = null;
	private Label labelTel2Paz = null;
	private Text textTel2Paz = null;
	private Label labelEmailPaz = null;
	private Text textEmailPaz = null;
	private Label labelNumTessPaz = null;
	private Text textNumTessPaz = null;
	private Label labelNotePaz = null;
	private Text textAreaNotePaz = null;
	private Button buttonRegistraPaz = null;

	//public static final String VIEW_ID = "StudioDietetico.registrapaziente";
	public static final String VIEW_ID = "StudioDietetico.RegistraPazienteView";
	
	public RegistraPazienteView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        labelCognPaz = new Label(top, SWT.NONE);
        labelCognPaz.setBounds(new Rectangle(6, 7, 67, 20));
        labelCognPaz.setText("* Cognome");
        textCognPaz = new Text(top, SWT.BORDER);
        textCognPaz.setBounds(new Rectangle(75, 6, 158, 21));
        labelNomePaz = new Label(top, SWT.NONE);
        labelNomePaz.setBounds(new Rectangle(7, 32, 59, 16));
        labelNomePaz.setText("* Nome");
        textNomePaz = new Text(top, SWT.BORDER);
        textNomePaz.setBounds(new Rectangle(76, 30, 156, 21));
        labelSessoPaz = new Label(top, SWT.NONE);
        labelSessoPaz.setBounds(new Rectangle(7, 56, 60, 18));
        labelSessoPaz.setText("* Sesso");
        radioButtonM = new Button(top, SWT.RADIO);
        radioButtonM.setBounds(new Rectangle(76, 55, 32, 16));
        radioButtonM.setText("M");
        radioButtonF = new Button(top, SWT.RADIO);
        radioButtonF.setBounds(new Rectangle(113, 55, 33, 16));
        radioButtonF.setText("F");
        labelDataNascPaz = new Label(top, SWT.NONE);
        labelDataNascPaz.setBounds(new Rectangle(9, 79, 94, 21));
        labelDataNascPaz.setText("* Data di nascita");
        createComboGiorno();
        createComboMese();
        createComboAnno();
        labelCodFiscPaz = new Label(top, SWT.NONE);
        labelCodFiscPaz.setBounds(new Rectangle(10, 105, 92, 21));
        labelCodFiscPaz.setText("* Codice Fiscale");
        textCodFiscPaz = new Text(top, SWT.BORDER);
        textCodFiscPaz.setBounds(new Rectangle(112, 106, 147, 21));
        textCodFiscPaz.setTextLimit(16);
        labelIndirizzoPaz = new Label(top, SWT.NONE);
        labelIndirizzoPaz.setBounds(new Rectangle(11, 133, 55, 20));
        labelIndirizzoPaz.setText("Indirizzo");
        textIndirizzoPaz = new Text(top, SWT.BORDER);
        textIndirizzoPaz.setBounds(new Rectangle(75, 133, 168, 21));
        labelCittPaz = new Label(top, SWT.NONE);
        labelCittPaz.setBounds(new Rectangle(12, 158, 50, 17));
        labelCittPaz.setText("Città");
        textCittPaz = new Text(top, SWT.BORDER);
        textCittPaz.setBounds(new Rectangle(74, 157, 170, 20));
        labelCAP = new Label(top, SWT.NONE);
        labelCAP.setBounds(new Rectangle(265, 157, 42, 19));
        labelCAP.setText("CAP");
        textCAP = new Text(top, SWT.BORDER);
        textCAP.setBounds(new Rectangle(312, 156, 76, 21));
        textCAP.setTextLimit(5);
        labelProvincia = new Label(top, SWT.NONE);
        labelProvincia.setBounds(new Rectangle(10, 179, 51, 21));
        labelProvincia.setText("Provincia");
        textProvincia = new Text(top, SWT.BORDER);
        textProvincia.setBounds(new Rectangle(73, 180, 72, 18));
        textProvincia.setTextLimit(2);
        labelProfessPaz = new Label(top, SWT.NONE);
        labelProfessPaz.setBounds(new Rectangle(9, 204, 67, 21));
        labelProfessPaz.setText("Professione");
        textProfessPaz = new Text(top, SWT.BORDER);
        textProfessPaz.setBounds(new Rectangle(81, 205, 97, 16));
        labelTel1Paz = new Label(top, SWT.NONE);
        labelTel1Paz.setBounds(new Rectangle(9, 228, 68, 21));
        labelTel1Paz.setText("Telefono 1");
        textTel1Paz = new Text(top, SWT.BORDER);
        textTel1Paz.setBounds(new Rectangle(82, 228, 98, 17));
        labelTel2Paz = new Label(top, SWT.NONE);
        labelTel2Paz.setBounds(new Rectangle(220, 227, 57, 18));
        labelTel2Paz.setText("Telefono 2");
        textTel2Paz = new Text(top, SWT.BORDER);
        textTel2Paz.setBounds(new Rectangle(290, 228, 98, 17));
        labelEmailPaz = new Label(top, SWT.NONE);
        labelEmailPaz.setBounds(new Rectangle(9, 255, 52, 15));
        labelEmailPaz.setText("E-mail");
        textEmailPaz = new Text(top, SWT.BORDER);
        textEmailPaz.setBounds(new Rectangle(74, 254, 109, 22));
        labelNumTessPaz = new Label(top, SWT.NONE);
        labelNumTessPaz.setBounds(new Rectangle(10, 280, 123, 17));
        labelNumTessPaz.setText("Num Tessera sanitaria");
        textNumTessPaz = new Text(top, SWT.BORDER);
        textNumTessPaz.setBounds(new Rectangle(143, 278, 121, 20));
        labelNotePaz = new Label(top, SWT.NONE);
        labelNotePaz.setBounds(new Rectangle(8, 303, 38, 15));
        labelNotePaz.setText("Note");
        textAreaNotePaz = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNotePaz.setBounds(new Rectangle(61, 301, 255, 61));
        buttonRegistraPaz = new Button(top, SWT.NONE);
        buttonRegistraPaz.setBounds(new Rectangle(321, 364, 102, 25));
        buttonRegistraPaz.setText("Registra paziente");
        buttonRegistraPaz
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				String dateString = comboAnno.getText()+"-"+comboMese.getText()+"-"+comboGiorno.getText();
        				String formato = "yyyy-MM-dd";
        				Date dn = Utils.convertStringToDate(dateString, formato);
/*        				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        				Date convertedDate = null;
						try {
							convertedDate = dateFormat.parse(dateString);
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} */
        				PazienteDAO p = new PazienteDAO();
        				if (radioButtonM.getSelection()) {
        				p.registraPaziente(textCognPaz.getText(), textNomePaz.getText(), radioButtonM.getText(), dn, textCodFiscPaz.getText(),textIndirizzoPaz.getText(), textCittPaz.getText(), textCAP.getText(), textProvincia.getText(),
        						textProfessPaz.getText(), textTel1Paz.getText(), textTel2Paz.getText(), textEmailPaz.getText(), textNumTessPaz.getText(), textAreaNotePaz.getText());
        				}
        				else if (radioButtonF.getSelection()){
        					p.registraPaziente(textCognPaz.getText(), textNomePaz.getText(), radioButtonF.getText(),dn, textCodFiscPaz.getText(),textIndirizzoPaz.getText(), textCittPaz.getText(), textCAP.getText(), textProvincia.getText(),
            						textProfessPaz.getText(), textTel1Paz.getText(), textTel2Paz.getText(), textEmailPaz.getText(), textNumTessPaz.getText(), textAreaNotePaz.getText());	
        				}
        				System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
        			}
        		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes comboGiorno	
	 *
	 */
	private void createComboGiorno() {
		comboGiorno = new Combo(top, SWT.READ_ONLY);
		comboGiorno.setBounds(new Rectangle(109, 78, 60, 23));
		comboViewer = new ComboViewer(comboGiorno);
		for (int i = 1; i < 32; i++) {
			comboGiorno.add(""+i);
		}
		//comboGiorno.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		comboGiorno.setText(comboGiorno.getItem(0));

	}

	/**
	 * This method initializes comboMese	
	 *
	 */
	private void createComboMese() {
		comboMese = new Combo(top, SWT.READ_ONLY);
		comboMese.setBounds(new Rectangle(180, 77, 59, 23));
		comboViewer1 = new ComboViewer(comboMese);
		for (int i = 1; i < 13; i++) {
			comboMese.add(""+i);
		}
		//comboMese.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12"});
		comboMese.setText(comboMese.getItem(0));
	}

	/**
	 * This method initializes comboAnno	
	 *
	 */
	private void createComboAnno() {
		comboAnno = new Combo(top, SWT.READ_ONLY);
		comboAnno.setBounds(new Rectangle(251, 77, 61, 23));
		comboViewer2 = new ComboViewer(comboAnno);
		Date now = new Date();
		//System.out.println(now.getYear()+1900);
		for (int i = 1900; i < (now.getYear()+1901); i++) {
			comboAnno.add(""+i);
		}
/*		comboAnno.setItems(new String [] {"1910","1911","1912","1913","1914","1915","1916","1917","1918","1919",
				"1920","1921","1922","1923","1924","1925","1926","1927","1928","1929",
				"1930","1931","1932","1933","1934","1935","1936","1937","1938","1939",
				"1940","1941","1942","1943","1944","1945","1946","1947","1948","1949",
				"1950","1951","1952","1953","1954","1955","1956","1957","1958","1959",
				"1960","1961","1962","1963","1964","1965","1966","1967","1968","1969",
				"1970","1971","1972","1973","1974","1975","1976","1977","1978","1979",
				"1980","1981","1982","1983","1984","1985","1986","1987","1988","1989",
				"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
				"2000","2001","2002","2003","2004","2005","2006","2007","2008","2009"});*/
		comboAnno.setText(comboAnno.getItem(0));
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,431,392"
