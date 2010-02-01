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

import service.Utils;

import command.MedicoDAO;

public class RegistraMedicoView extends ViewPart {

	private Composite top = null;
	private Label labelCognMed = null;
	private Text textCognMed = null;
	private Label labelNomeMed = null;
	private Text textNomeMed = null;
	private Label labelSessoMed = null;
	private Button radioButtonM = null;
	private Button radioButtonF = null;
	private Label labelDataNascMed = null;
	private Combo comboGiorno = null;
	private Combo comboMese = null;
	private Combo comboAnno = null;
	private Label labelCodFiscMed = null;
	private Text textCodFiscMed = null;
	private Label labelIndirizzoMed = null;
	private Text textIndirizzoMed = null;
	private Label labelCittMed = null;
	private Text textCittMed = null;
	private Label labelCAPMed = null;
	private Text textCAPMed = null;
	private Label labelProvMed = null;
	private Text textProvMed = null;
	private Label labelSpecMed = null;
	private Text textSpecMed = null;
	private Label labelTel1Med = null;
	private Text textTel1Med = null;
	private Label labelTel2Med = null;
	private Text textTel2Med = null;
	private Label labelMailMed = null;
	private Text textMailMed = null;
	private Button buttonRegistraMed = null;

	public RegistraMedicoView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        labelCognMed = new Label(top, SWT.NONE);
        labelCognMed.setBounds(new Rectangle(6, 7, 67, 20));
        labelCognMed.setText("* Cognome");
        textCognMed = new Text(top, SWT.BORDER);
        textCognMed.setBounds(new Rectangle(75, 6, 158, 21));
        labelNomeMed = new Label(top, SWT.NONE);
        labelNomeMed.setBounds(new Rectangle(7, 32, 59, 16));
        labelNomeMed.setText("* Nome");
        textNomeMed = new Text(top, SWT.BORDER);
        textNomeMed.setBounds(new Rectangle(76, 30, 156, 21));
        labelSessoMed = new Label(top, SWT.NONE);
        labelSessoMed.setBounds(new Rectangle(9, 108, 60, 18));
        labelSessoMed.setText("* Sesso");
        radioButtonM = new Button(top, SWT.RADIO);
        radioButtonM.setBounds(new Rectangle(76, 109, 32, 16));
        radioButtonM.setText("M");
        radioButtonF = new Button(top, SWT.RADIO);
        radioButtonF.setBounds(new Rectangle(115, 110, 33, 16));
        radioButtonF.setText("F");
        labelDataNascMed = new Label(top, SWT.NONE);
        labelDataNascMed.setBounds(new Rectangle(9, 79, 94, 21));
        labelDataNascMed.setText("* Data di nascita");
        createComboGiorno();
        createComboMese();
        createComboAnno();
        labelCodFiscMed = new Label(top, SWT.NONE);
        labelCodFiscMed.setBounds(new Rectangle(6, 53, 92, 21));
        labelCodFiscMed.setText("* Codice Fiscale");
        textCodFiscMed = new Text(top, SWT.BORDER);
        textCodFiscMed.setBounds(new Rectangle(107, 53, 194, 21));
        textCodFiscMed.setTextLimit(16);
        labelIndirizzoMed = new Label(top, SWT.NONE);
        labelIndirizzoMed.setBounds(new Rectangle(11, 133, 55, 20));
        labelIndirizzoMed.setText("Indirizzo");
        textIndirizzoMed = new Text(top, SWT.BORDER);
        textIndirizzoMed.setBounds(new Rectangle(75, 133, 168, 21));
        labelCittMed = new Label(top, SWT.NONE);
        labelCittMed.setBounds(new Rectangle(12, 158, 50, 17));
        labelCittMed.setText("Città");
        textCittMed = new Text(top, SWT.BORDER);
        textCittMed.setBounds(new Rectangle(74, 157, 170, 20));
        labelCAPMed = new Label(top, SWT.NONE);
        labelCAPMed.setBounds(new Rectangle(180, 182, 42, 19));
        labelCAPMed.setText("CAP");
        textCAPMed = new Text(top, SWT.BORDER);
        textCAPMed.setBounds(new Rectangle(233, 182, 76, 21));
        textCAPMed.setTextLimit(5);
        labelProvMed = new Label(top, SWT.NONE);
        labelProvMed.setBounds(new Rectangle(10, 179, 51, 21));
        labelProvMed.setText("Provincia");
        textProvMed = new Text(top, SWT.BORDER);
        textProvMed.setBounds(new Rectangle(73, 180, 72, 18));
        textProvMed.setTextLimit(2);
        labelSpecMed = new Label(top, SWT.NONE);
        labelSpecMed.setBounds(new Rectangle(9, 204, 93, 21));
        labelSpecMed.setText("Specializzazione");
        textSpecMed = new Text(top, SWT.BORDER);
        textSpecMed.setBounds(new Rectangle(108, 205, 191, 20));
        labelTel1Med = new Label(top, SWT.NONE);
        labelTel1Med.setBounds(new Rectangle(9, 228, 68, 21));
        labelTel1Med.setText("Telefono 1");
        textTel1Med = new Text(top, SWT.BORDER);
        textTel1Med.setBounds(new Rectangle(82, 228, 98, 17));
        labelTel2Med = new Label(top, SWT.NONE);
        labelTel2Med.setBounds(new Rectangle(220, 227, 57, 18));
        labelTel2Med.setText("Telefono 2");
        textTel2Med = new Text(top, SWT.BORDER);
        textTel2Med.setBounds(new Rectangle(290, 228, 98, 17));
        labelMailMed = new Label(top, SWT.NONE);
        labelMailMed.setBounds(new Rectangle(9, 255, 52, 15));
        labelMailMed.setText("E-mail");
        textMailMed = new Text(top, SWT.BORDER);
        textMailMed.setBounds(new Rectangle(74, 254, 109, 22));
        buttonRegistraMed = new Button(top, SWT.NONE);
        buttonRegistraMed.setBounds(new Rectangle(282, 317, 102, 25));
        buttonRegistraMed.setText("Registra medico");
        buttonRegistraMed
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
        				MedicoDAO med = new MedicoDAO();
        				if (radioButtonM.getSelection()) {
        					med.registraMedico(textCognMed.getText(), textNomeMed.getText(), radioButtonM.getText(), dn, textCodFiscMed.getText(),
        							textIndirizzoMed.getText(), textCittMed.getText(), textCAPMed.getText(), textProvMed.getText(),
        						textSpecMed.getText(), textTel1Med.getText(), textTel2Med.getText(), textMailMed.getText());
        				}
        				else if (radioButtonF.getSelection()){
        					med.registraMedico(textCognMed.getText(), textNomeMed.getText(), radioButtonF.getText(),dn, textCodFiscMed.getText(),
        							textIndirizzoMed.getText(), textCittMed.getText(), textCAPMed.getText(), textProvMed.getText(),
            						textSpecMed.getText(), textTel1Med.getText(), textTel2Med.getText(), textMailMed.getText());	
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
		comboGiorno.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		comboGiorno.setText(comboGiorno.getItem(0));

	}

	/**
	 * This method initializes comboMese	
	 *
	 */
	private void createComboMese() {
		comboMese = new Combo(top, SWT.READ_ONLY);
		comboMese.setBounds(new Rectangle(180, 77, 59, 23));
		comboMese.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12"});
		comboMese.setText(comboMese.getItem(0));
	}

	/**
	 * This method initializes comboAnno	
	 *
	 */
	private void createComboAnno() {
		comboAnno = new Combo(top, SWT.READ_ONLY);
		comboAnno.setBounds(new Rectangle(251, 77, 61, 23));
		comboAnno.setItems(new String [] {"1910","1911","1912","1913","1914","1915","1916","1917","1918","1919",
				"1920","1921","1922","1923","1924","1925","1926","1927","1928","1929",
				"1930","1931","1932","1933","1934","1935","1936","1937","1938","1939",
				"1940","1941","1942","1943","1944","1945","1946","1947","1948","1949",
				"1950","1951","1952","1953","1954","1955","1956","1957","1958","1959",
				"1960","1961","1962","1963","1964","1965","1966","1967","1968","1969",
				"1970","1971","1972","1973","1974","1975","1976","1977","1978","1979",
				"1980","1981","1982","1983","1984","1985","1986","1987","1988","1989",
				"1990","1991","1992","1993","1994","1995","1996","1997","1998","1999",
				"2000","2001","2002","2003","2004","2005","2006","2007","2008","2009"});
		comboAnno.setText(comboAnno.getItem(0));
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,416,365"
