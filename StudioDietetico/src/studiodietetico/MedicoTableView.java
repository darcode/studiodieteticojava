package studiodietetico;

import hibernate.Medico;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.MedicoDAO;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;

import service.Utils;

public class MedicoTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> medici;
	private Shell sShellInserisciMedico = null;  //  @jve:decl-index=0:visual-constraint="326,9"
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
	private Button buttonAggiornaMed = null;
	private Button buttonAnnulla = null;
	public MedicoTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		top.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		top.setLayout(glForm);
		medici = MedicoDAO.getMediciObject();
		MedicoDAO md = new MedicoDAO();
		classVis = new TableForm(top, SWT.BORDER, medici, "createShellDettagliMedico","createSShellInserisciMedico",MedicoTableView.this, md, "MedicoTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		classVis.nascondiColonne(new int[] {0,1,4,5,8,9,13});
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 12);

	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}


	public void createSShellInserisciMedico() {
		sShellInserisciMedico = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellInserisciMedico.setLayout(new GridLayout());
		sShellInserisciMedico.setSize(new Point(413, 352));
		sShellInserisciMedico.setText("Registra medico");
		
		labelCognMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelCognMed.setBounds(new Rectangle(6, 7, 67, 20));
        labelCognMed.setText("* Cognome");
        textCognMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textCognMed.setBounds(new Rectangle(75, 6, 158, 21));
        labelNomeMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelNomeMed.setBounds(new Rectangle(7, 32, 59, 16));
        labelNomeMed.setText("* Nome");
        textNomeMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textNomeMed.setBounds(new Rectangle(76, 30, 156, 21));
        labelSessoMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelSessoMed.setBounds(new Rectangle(9, 108, 60, 18));
        labelSessoMed.setText("* Sesso");
        radioButtonM = new Button(sShellInserisciMedico, SWT.RADIO);
        radioButtonM.setBounds(new Rectangle(76, 109, 32, 16));
        radioButtonM.setText("M");
        radioButtonF = new Button(sShellInserisciMedico, SWT.RADIO);
        radioButtonF.setBounds(new Rectangle(115, 110, 33, 16));
        radioButtonF.setText("F");
        labelDataNascMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelDataNascMed.setBounds(new Rectangle(9, 79, 94, 21));
        labelDataNascMed.setText("* Data di nascita");
        createComboGiorno();
        createComboMese();
        createComboAnno();
        labelCodFiscMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelCodFiscMed.setBounds(new Rectangle(6, 53, 92, 21));
        labelCodFiscMed.setText("* Codice Fiscale");
        textCodFiscMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textCodFiscMed.setBounds(new Rectangle(107, 53, 194, 21));
        textCodFiscMed.setTextLimit(16);
        labelIndirizzoMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelIndirizzoMed.setBounds(new Rectangle(11, 133, 55, 20));
        labelIndirizzoMed.setText("Indirizzo");
        textIndirizzoMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textIndirizzoMed.setBounds(new Rectangle(75, 133, 168, 21));
        labelCittMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelCittMed.setBounds(new Rectangle(12, 158, 50, 17));
        labelCittMed.setText("Città");
        textCittMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textCittMed.setBounds(new Rectangle(74, 157, 170, 20));
        labelCAPMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelCAPMed.setBounds(new Rectangle(180, 182, 42, 19));
        labelCAPMed.setText("CAP");
        textCAPMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textCAPMed.setBounds(new Rectangle(233, 182, 76, 21));
        textCAPMed.setTextLimit(5);
        labelProvMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelProvMed.setBounds(new Rectangle(10, 179, 51, 21));
        labelProvMed.setText("Provincia");
        textProvMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textProvMed.setBounds(new Rectangle(73, 180, 72, 18));
        textProvMed.setTextLimit(2);
        labelSpecMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelSpecMed.setBounds(new Rectangle(9, 204, 93, 21));
        labelSpecMed.setText("Specializzazione");
        textSpecMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textSpecMed.setBounds(new Rectangle(108, 205, 191, 20));
        labelTel1Med = new Label(sShellInserisciMedico, SWT.NONE);
        labelTel1Med.setBounds(new Rectangle(9, 228, 68, 21));
        labelTel1Med.setText("Telefono 1");
        textTel1Med = new Text(sShellInserisciMedico, SWT.BORDER);
        textTel1Med.setBounds(new Rectangle(82, 228, 98, 17));
        labelTel2Med = new Label(sShellInserisciMedico, SWT.NONE);
        labelTel2Med.setBounds(new Rectangle(220, 227, 57, 18));
        labelTel2Med.setText("Telefono 2");
        textTel2Med = new Text(sShellInserisciMedico, SWT.BORDER);
        textTel2Med.setBounds(new Rectangle(290, 228, 98, 17));
        labelMailMed = new Label(sShellInserisciMedico, SWT.NONE);
        labelMailMed.setBounds(new Rectangle(9, 255, 52, 15));
        labelMailMed.setText("E-mail");
        textMailMed = new Text(sShellInserisciMedico, SWT.BORDER);
        textMailMed.setBounds(new Rectangle(74, 254, 109, 22));
        buttonRegistraMed = new Button(sShellInserisciMedico, SWT.NONE);
        buttonRegistraMed.setBounds(new Rectangle(279, 288, 102, 25));
        buttonRegistraMed.setText("Registra medico");
        buttonAnnulla = new Button(sShellInserisciMedico, SWT.NONE);
        buttonAnnulla.setBounds(new Rectangle(173, 288, 102, 25));
        buttonAnnulla.setText("Chiudi");
        buttonAnnulla
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				sShellInserisciMedico.close();
        			}
        		});
        buttonRegistraMed
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				String dateString = comboAnno.getText()+"-"+comboMese.getText()+"-"+comboGiorno.getText();
        				String formato = "yyyy-MM-dd";
        				Date dn = Utils.convertStringToDate(dateString, formato);
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
        				classVis.getTableVisualizzazione().removeAll(); //rimuove le righe
        				//rimuove le colonne
        				int k = 0;
        				while (k<classVis.getTableVisualizzazione().getColumnCount()) {
        					classVis.getTableVisualizzazione().getColumn(k).dispose();
        				}
        				medici = MedicoDAO.getMediciObject();
        				classVis.riempiTabella(medici, "MedicoTableView");
        				classVis.nascondiColonne(new int[] {0,1,4,5,8,9,13});
        				classVis.aggiornaCombo();
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 12);

        				sShellInserisciMedico.close();
        			}
        		});
		
		sShellInserisciMedico.open();
	}
	
	
	private void createComboGiorno() {
		comboGiorno = new Combo(sShellInserisciMedico, SWT.READ_ONLY);
		comboGiorno.setBounds(new Rectangle(109, 78, 60, 23));
		comboGiorno.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"});
		comboGiorno.setText(comboGiorno.getItem(0));

	}

	private void createComboMese() {
		comboMese = new Combo(sShellInserisciMedico, SWT.READ_ONLY);
		comboMese.setBounds(new Rectangle(180, 77, 59, 23));
		comboMese.setItems(new String [] {"1","2","3","4","5","6","7","8","9","10","11","12"});
		comboMese.setText(comboMese.getItem(0));
	}

	private void createComboAnno() {
		comboAnno = new Combo(sShellInserisciMedico, SWT.READ_ONLY);
		comboAnno.setBounds(new Rectangle(251, 77, 61, 23));
		Date now = new Date();
		for (int i = 1900; i < (now.getYear()+1901); i++) {
			comboAnno.add(""+i);
		}
		comboAnno.setText(comboAnno.getItem(0));
	}

	public void createShellDettagliMedico(final TableItem rigaTableClick){
		createSShellInserisciMedico();
		int idMed = Integer.parseInt(rigaTableClick.getText(0));
		final Medico med = MedicoDAO.getMedicoByID(idMed);
		sShellInserisciMedico.setText("Dettagli medico");
		buttonRegistraMed.setEnabled(false);
		buttonRegistraMed.setVisible(false);
        buttonAggiornaMed = new Button(sShellInserisciMedico, SWT.NONE);
        buttonAggiornaMed.setBounds(new Rectangle(279, 288, 102, 25));
        buttonAggiornaMed.setText("Aggiorna dati");
        buttonAggiornaMed
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				String dateString = comboAnno.getText()+"-"+comboMese.getText()+"-"+comboGiorno.getText();
				String formato = "yyyy-MM-dd";
				Date dn = Utils.convertStringToDate(dateString, formato);
				MedicoDAO m = new MedicoDAO();
				if (radioButtonM.getSelection()) {
					m.aggiornaMedico(med, textCognMed.getText(), textNomeMed.getText(), radioButtonM.getText(), dn, textCodFiscMed.getText(),
							textIndirizzoMed.getText(), textCittMed.getText(), textCAPMed.getText(), textProvMed.getText(),
						textSpecMed.getText(), textTel1Med.getText(), textTel2Med.getText(), textMailMed.getText());
				}
				else if (radioButtonF.getSelection()){
					m.aggiornaMedico(med, textCognMed.getText(), textNomeMed.getText(), radioButtonF.getText(),dn, textCodFiscMed.getText(),
							textIndirizzoMed.getText(), textCittMed.getText(), textCAPMed.getText(), textProvMed.getText(),
    						textSpecMed.getText(), textTel1Med.getText(), textTel2Med.getText(), textMailMed.getText());	
				}
				classVis.getTableVisualizzazione().removeAll(); //rimuove le righe
				//rimuove le colonne
				int k = 0;
				while (k<classVis.getTableVisualizzazione().getColumnCount()) {
					classVis.getTableVisualizzazione().getColumn(k).dispose();
				}
				medici = MedicoDAO.getMediciObject();
				classVis.riempiTabella(medici, "MedicoTableView");
				classVis.nascondiColonne(new int[] {0,1,4,5,8,9,13});
				classVis.aggiornaCombo();
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 12);
				
				sShellInserisciMedico.close();
			}
		});
        textCognMed.setText(med.getCognome());
        textNomeMed.setText(med.getNome());
        textCodFiscMed.setText(med.getCodiceFiscale());
        comboGiorno.select(med.getDataNascita().getDate()-1);
		comboMese.select(med.getDataNascita().getMonth());
		comboAnno.select(med.getDataNascita().getYear());
		if (med.getSesso()=='M') {
			radioButtonM.setSelection(true);
		} else {
			radioButtonF.setSelection(true);
		}
        textIndirizzoMed.setText(med.getIndirizzo());
        textCittMed.setText(med.getCitta());
        textProvMed.setText(med.getProvincia());
        textCAPMed.setText(med.getCap());
        textSpecMed.setText(med.getSpecializzazione());
        textTel1Med.setText(med.getTelefono1());
        textTel2Med.setText(med.getTelefono2());
        textMailMed.setText(med.getEmail());
	}
}
