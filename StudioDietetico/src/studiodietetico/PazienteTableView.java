package studiodietetico;

import hibernate.Paziente;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.PazienteDAO;
import org.eclipse.swt.widgets.Button;

import service.Utils;

public class PazienteTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> pazienti;
	private Button buttonPrenotaVisita = null;
	private Shell sShellMessElimina;
	public static Paziente pazienteSel = null;
	private Shell sShellInserisciPaziente = null;  //  @jve:decl-index=0:visual-constraint="18,376"

	private Composite top1 = null;
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
	private Button buttonAggiornaPaz = null;
	private Button buttonAnnulla = null;
	
	public PazienteTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		pazienti = PazienteDAO.getPazientiObject();
		PazienteDAO pd = new PazienteDAO();
		//TODO aggiungere parametri
		classVis = new TableForm(top, SWT.BORDER, pazienti, "createShellDettagliPaziente","createSShellInserisciPaziente",PazienteTableView.this, pd, "PazienteTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		buttonPrenotaVisita = new Button(classVis.top, SWT.NONE);
		buttonPrenotaVisita.setBounds(new Rectangle(260, 284, 110, 25));
		buttonPrenotaVisita.setText("PrenotaVisita");
		buttonPrenotaVisita.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(classVis.getTableVisualizzazione().getSelectionCount()>0) {
					TableItem itemSel = classVis.getTableVisualizzazione().getItem(classVis.getTableVisualizzazione().getSelectionIndex());
					int idPazienteSel = Integer.parseInt(itemSel.getText(0));
					pazienteSel = PazienteDAO.getPazienteByID(idPazienteSel);
					Utils.showView("StudioDietetico.prenotavisita"); 
				} else {
					createMessSelElemCanc();
				}
			}
		});
		
		classVis.nascondiColonne(new int[] {0,1,8,9,10,11,12,13,14,15});
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
	
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
	}
	
	private void createMessSelElemCanc() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_ERROR);
		messageBox.setMessage("Selezionare un elemento dalla tabella");
		messageBox.setText("Errore: elemento non selezionato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	private void createSShellMessElimina() {
		sShellMessElimina = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setSize(new Point(377, 72));
	}


	public void createSShellInserisciPaziente() {
		sShellInserisciPaziente = new Shell();
		//sShellInserisciPaziente.setLayout(new GridLayout());
		sShellInserisciPaziente.setSize(new Point(443, 430));
		sShellInserisciPaziente.setText("Registra un nuovo paziente");
		
        labelCognPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelCognPaz.setBounds(new Rectangle(6, 7, 67, 20));
        labelCognPaz.setText("* Cognome");
        textCognPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textCognPaz.setBounds(new Rectangle(75, 6, 158, 21));
        labelNomePaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelNomePaz.setBounds(new Rectangle(7, 32, 59, 16));
        labelNomePaz.setText("* Nome");
        textNomePaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textNomePaz.setBounds(new Rectangle(76, 30, 156, 21));
        labelSessoPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelSessoPaz.setBounds(new Rectangle(7, 56, 60, 18));
        labelSessoPaz.setText("* Sesso");
        radioButtonM = new Button(sShellInserisciPaziente, SWT.RADIO);
        radioButtonM.setBounds(new Rectangle(76, 55, 32, 16));
        radioButtonM.setText("M");
        radioButtonF = new Button(sShellInserisciPaziente, SWT.RADIO);
        radioButtonF.setBounds(new Rectangle(113, 55, 33, 16));
        radioButtonF.setText("F");
        labelDataNascPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelDataNascPaz.setBounds(new Rectangle(9, 79, 94, 21));
        labelDataNascPaz.setText("* Data di nascita");
        createComboGiorno();
        createComboMese();
        createComboAnno();
        labelCodFiscPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelCodFiscPaz.setBounds(new Rectangle(10, 105, 92, 21));
        labelCodFiscPaz.setText("* Codice Fiscale");
        textCodFiscPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textCodFiscPaz.setBounds(new Rectangle(112, 106, 147, 21));
        textCodFiscPaz.setTextLimit(16);
        labelIndirizzoPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelIndirizzoPaz.setBounds(new Rectangle(11, 133, 55, 20));
        labelIndirizzoPaz.setText("Indirizzo");
        textIndirizzoPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textIndirizzoPaz.setBounds(new Rectangle(75, 133, 168, 21));
        labelCittPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelCittPaz.setBounds(new Rectangle(12, 158, 50, 17));
        labelCittPaz.setText("Città");
        textCittPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textCittPaz.setBounds(new Rectangle(74, 157, 170, 20));
        labelCAP = new Label(sShellInserisciPaziente, SWT.NONE);
        labelCAP.setBounds(new Rectangle(265, 157, 42, 19));
        labelCAP.setText("CAP");
        textCAP = new Text(sShellInserisciPaziente, SWT.BORDER);
        textCAP.setBounds(new Rectangle(312, 156, 76, 21));
        textCAP.setTextLimit(5);
        labelProvincia = new Label(sShellInserisciPaziente, SWT.NONE);
        labelProvincia.setBounds(new Rectangle(10, 179, 51, 21));
        labelProvincia.setText("Provincia");
        textProvincia = new Text(sShellInserisciPaziente, SWT.BORDER);
        textProvincia.setBounds(new Rectangle(73, 180, 72, 18));
        textProvincia.setTextLimit(2);
        labelProfessPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelProfessPaz.setBounds(new Rectangle(9, 204, 67, 21));
        labelProfessPaz.setText("Professione");
        textProfessPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textProfessPaz.setBounds(new Rectangle(81, 205, 97, 16));
        labelTel1Paz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelTel1Paz.setBounds(new Rectangle(9, 228, 68, 21));
        labelTel1Paz.setText("Telefono 1");
        textTel1Paz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textTel1Paz.setBounds(new Rectangle(82, 228, 104, 18));
        labelTel2Paz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelTel2Paz.setBounds(new Rectangle(220, 227, 57, 18));
        labelTel2Paz.setText("Telefono 2");
        textTel2Paz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textTel2Paz.setBounds(new Rectangle(290, 228, 98, 17));
        labelEmailPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelEmailPaz.setBounds(new Rectangle(9, 255, 52, 15));
        labelEmailPaz.setText("E-mail");
        textEmailPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textEmailPaz.setBounds(new Rectangle(74, 254, 109, 22));
        labelNumTessPaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelNumTessPaz.setBounds(new Rectangle(10, 280, 123, 17));
        labelNumTessPaz.setText("Num Tessera sanitaria");
        textNumTessPaz = new Text(sShellInserisciPaziente, SWT.BORDER);
        textNumTessPaz.setBounds(new Rectangle(143, 278, 121, 20));
        labelNotePaz = new Label(sShellInserisciPaziente, SWT.NONE);
        labelNotePaz.setBounds(new Rectangle(8, 303, 38, 15));
        labelNotePaz.setText("Note");
        textAreaNotePaz = new Text(sShellInserisciPaziente, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNotePaz.setBounds(new Rectangle(61, 301, 255, 61));
        buttonRegistraPaz = new Button(sShellInserisciPaziente, SWT.NONE);
        buttonRegistraPaz.setBounds(new Rectangle(321, 364, 102, 25));
        buttonRegistraPaz.setText("Registra paziente");
        buttonAnnulla = new Button(sShellInserisciPaziente, SWT.NONE);
        buttonAnnulla.setBounds(new Rectangle(212, 364, 102, 25));
        buttonAnnulla.setText("Chiudi");
        buttonAnnulla
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				sShellInserisciPaziente.close();
        			}
        		});
        buttonRegistraPaz
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				String dateString = comboAnno.getText()+"-"+comboMese.getText()+"-"+comboGiorno.getText();
        				String formato = "yyyy-MM-dd";
        				Date dn = Utils.convertStringToDate(dateString, formato);
        				PazienteDAO p = new PazienteDAO();
        				if (radioButtonM.getSelection()) {
        				p.registraPaziente(textCognPaz.getText(), textNomePaz.getText(), radioButtonM.getText(), dn, textCodFiscPaz.getText(),textIndirizzoPaz.getText(), textCittPaz.getText(), textCAP.getText(), textProvincia.getText(),
        						textProfessPaz.getText(), textTel1Paz.getText(), textTel2Paz.getText(), textEmailPaz.getText(), textNumTessPaz.getText(), textAreaNotePaz.getText());
        				}
        				else if (radioButtonF.getSelection()){
        					p.registraPaziente(textCognPaz.getText(), textNomePaz.getText(), radioButtonF.getText(),dn, textCodFiscPaz.getText(),textIndirizzoPaz.getText(), textCittPaz.getText(), textCAP.getText(), textProvincia.getText(),
            						textProfessPaz.getText(), textTel1Paz.getText(), textTel2Paz.getText(), textEmailPaz.getText(), textNumTessPaz.getText(), textAreaNotePaz.getText());
        					
        				}
        				
        				classVis.getTableVisualizzazione().removeAll(); //rimuove le righe
        				//rimuove le colonne
        				int k = 0;
        				while (k<classVis.getTableVisualizzazione().getColumnCount()) {
        					classVis.getTableVisualizzazione().getColumn(k).dispose();
        				}
        				pazienti = PazienteDAO.getPazientiObject();
        				classVis.riempiTabella(pazienti, "PazienteTableView");
        				classVis.nascondiColonne(new int[] {0,1,8,9,10,11,12,13,14,15});
        				classVis.aggiornaCombo();
        				classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
        				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
        				
        				sShellInserisciPaziente.close();
        			}
        		});
        
		sShellInserisciPaziente.open();
	}
	
	/**
	 * This method initializes comboGiorno	
	 *
	 */
	private void createComboGiorno() {
		comboGiorno = new Combo(sShellInserisciPaziente, SWT.READ_ONLY);
		comboGiorno.setBounds(new Rectangle(109, 78, 60, 23));
		comboViewer = new ComboViewer(comboGiorno);
		for (int i = 1; i < 32; i++) {
			comboGiorno.add(""+i);
		}
		comboGiorno.setText(comboGiorno.getItem(0));

	}

	/**
	 * This method initializes comboMese	
	 *
	 */
	private void createComboMese() {
		comboMese = new Combo(sShellInserisciPaziente, SWT.READ_ONLY);
		comboMese.setBounds(new Rectangle(180, 77, 59, 23));
		comboViewer1 = new ComboViewer(comboMese);
		for (int i = 1; i < 13; i++) {
			comboMese.add(""+i);
		}
		comboMese.setText(comboMese.getItem(0));
	}

	/**
	 * This method initializes comboAnno	
	 *
	 */
	private void createComboAnno() {
		comboAnno = new Combo(sShellInserisciPaziente, SWT.READ_ONLY);
		comboAnno.setBounds(new Rectangle(251, 77, 61, 23));
		comboViewer2 = new ComboViewer(comboAnno);
		Date now = new Date();
		//System.out.println(now.getYear()+1900);
		for (int i = 1900; i < (now.getYear()+1901); i++) {
			comboAnno.add(""+i);
		}
		comboAnno.setText(comboAnno.getItem(0));
	}
	
	public void createShellDettagliPaziente(final TableItem rigaTableClick) {
		createSShellInserisciPaziente();
		int idPaz = Integer.parseInt(rigaTableClick.getText(0));
		final Paziente paz = PazienteDAO.getPazienteByID(idPaz);
		sShellInserisciPaziente.setText("Dettagli paziente");
		buttonRegistraPaz.setEnabled(false);
		buttonRegistraPaz.setVisible(false);
		buttonAggiornaPaz = new Button(sShellInserisciPaziente, SWT.NONE);
        buttonAggiornaPaz.setBounds(new Rectangle(321, 364, 102, 25));
        buttonAggiornaPaz.setText("Aggiorna paziente");
        buttonAggiornaPaz
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				String dateString = comboAnno.getText()+"-"+comboMese.getText()+"-"+comboGiorno.getText();
				String formato = "yyyy-MM-dd";
				Date dn = Utils.convertStringToDate(dateString, formato);
				PazienteDAO p = new PazienteDAO();
				if (radioButtonM.getSelection()) {
				p.aggiornaPaziente(paz, textCognPaz.getText(), textNomePaz.getText(), radioButtonM.getText(), dn, textCodFiscPaz.getText(),textIndirizzoPaz.getText(), textCittPaz.getText(), textCAP.getText(), textProvincia.getText(),
						textProfessPaz.getText(), textTel1Paz.getText(), textTel2Paz.getText(), textEmailPaz.getText(), textNumTessPaz.getText(), textAreaNotePaz.getText());
				}
				else if (radioButtonF.getSelection()){
					p.aggiornaPaziente(paz, textCognPaz.getText(), textNomePaz.getText(), radioButtonF.getText(),dn, textCodFiscPaz.getText(),textIndirizzoPaz.getText(), textCittPaz.getText(), textCAP.getText(), textProvincia.getText(),
    						textProfessPaz.getText(), textTel1Paz.getText(), textTel2Paz.getText(), textEmailPaz.getText(), textNumTessPaz.getText(), textAreaNotePaz.getText());
					
				}
				
				classVis.getTableVisualizzazione().removeAll(); //rimuove le righe
				//rimuove le colonne
				int k = 0;
				while (k<classVis.getTableVisualizzazione().getColumnCount()) {
					classVis.getTableVisualizzazione().getColumn(k).dispose();
				}
				pazienti = PazienteDAO.getPazientiObject();
				classVis.riempiTabella(pazienti, "PazienteTableView");
				classVis.nascondiColonne(new int[] {0,1,8,9,10,11,12,13,14,15});
				classVis.aggiornaCombo();
				classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
				classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
				
				sShellInserisciPaziente.close();
			}
			
		});
		textCognPaz.setText(paz.getCognome());
		textNomePaz.setText(paz.getNome());
		if (paz.getSesso()=='M') {
			radioButtonM.setSelection(true);
		} else {
			radioButtonF.setSelection(true);
		}
		comboGiorno.select(paz.getDataNascita().getDate()-1);
		comboMese.select(paz.getDataNascita().getMonth());
		comboAnno.select(paz.getDataNascita().getYear());
		textCodFiscPaz.setText(paz.getCodiceFiscale());
		textIndirizzoPaz.setText(paz.getIndirizzo());
		textCittPaz.setText(paz.getCitta());
		textCAP.setText(paz.getCap());
		textProvincia.setText(paz.getProvincia());
		textProfessPaz.setText(paz.getProfessione());
		textTel1Paz.setText(paz.getTelefono1());
		textTel2Paz.setText(paz.getTelefono2());
		textEmailPaz.setText(paz.getEmail());
		textNumTessPaz.setText(paz.getNumTesseraSanitaria());
		textAreaNotePaz.setText(paz.getNote());
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,431,253"
