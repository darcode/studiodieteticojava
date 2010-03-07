package studiodietetico;

import hibernate.Dieta;
import hibernate.Specifichedieta;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import command.DietaDAO;

import service.GiornoDieta;
import service.StrutAlimento;
import service.StrutPasto;
import org.eclipse.swt.layout.GridLayout;

public class InserisciPrescrizioneShell {

	private Shell shellInserisciPrescrizione = null;  //  @jve:decl-index=0:visual-constraint="67,30"

	private Label labelPaziente = null;  //  @jve:decl-index=0:visual-constraint="9,-39"
	private Text textPaziente = null;

	private Group groupDieta = null;
	private Label lDataInizio = null;
	private Label lNote = null;
	private Text textNote = null;
	private MessageBox messageBox = null;  //  @jve:decl-index=0:
	private Spinner spinCicli = null;
	private Label lNCicli = null;

	private static DietaDAO dietaDao = new DietaDAO();  //  @jve:decl-index=0:
	//private ArrayList<StrutAlimento> alimentiDB = null;
	private Shell shellMsg = null;
	
	private Button bCreaSchema = null;
	private static Table tableSchemiDiete = null;
	private Label lSelezSchema = null;
	private StyledText textVisSchema = null;


	private DateTime calendar = null;

	private Button buttonInserisci = null;
	public static final String VIEW_ID = "StudioDietetico.InserisciDietaView";
	private Button radioButtonStandard = null;
	private Button radioButtonTutti = null;

	private Button buttonModificaDieta = null;

	private Button buttonEliminaDieta = null;
	
	
	public void createShellInserisciPrescrizione() {
		shellInserisciPrescrizione = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		shellInserisciPrescrizione.setSize(new Point(838, 572));
		shellInserisciPrescrizione.setText("Inserisci nuova prescrizione");
		labelPaziente = new Label(shellInserisciPrescrizione, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 10, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(shellInserisciPrescrizione, SWT.BORDER);
		textPaziente.setBounds(new Rectangle(106, 10, 263, 31));
		textPaziente.setEnabled(false);
		
		textPaziente.setText(PazienteTableView.getPazienteSelezionato().getCognome()+"   "+
				PazienteTableView.getPazienteSelezionato().getNome()+"   "+
				PazienteTableView.getPazienteSelezionato().getDataNascita());

		createGroupDieta();
		shellInserisciPrescrizione.open();
	}

	

	/**
	 * This method initializes groupDieta	
	 *
	 */
	private void createGroupDieta() {
		
		groupDieta = new Group(shellInserisciPrescrizione, SWT.NONE);
		groupDieta.setLayout(null);
		groupDieta.setText("Prescrizione dieta");
		groupDieta.setBounds(new Rectangle(11, 50, 816, 470));
		lDataInizio = new Label(groupDieta, SWT.NONE);
		lDataInizio.setBounds(new Rectangle(11, 26, 73, 13));
		lDataInizio.setText("*Data inizio");
		calendar = new DateTime (groupDieta, SWT.NONE | SWT.CALENDAR | SWT.BORDER);
		calendar.setBounds(new Rectangle(13, 46, 165, 144));
		lNote = new Label(groupDieta, SWT.NONE);
		lNote.setBounds(new Rectangle(225, 31, 60, 13));
		lNote.setText("Note");
		textNote = new Text(groupDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textNote.setBounds(new Rectangle(226, 46, 565, 57));
		spinCicli = new Spinner(groupDieta, SWT.NONE);
		spinCicli.setMaximum(50);
		spinCicli.setMinimum(1);
		Font fontSpin = new Font(Display.getCurrent(),"Arial",14,SWT.BOLD);
		spinCicli.setFont(fontSpin);
		spinCicli.setBounds(new Rectangle(227, 144, 51, 24));
		lNCicli = new Label(groupDieta, SWT.NONE);
		lNCicli.setBounds(new Rectangle(224, 120, 108, 13));
		lNCicli.setText("*Numero cicli");
		bCreaSchema = new Button(groupDieta, SWT.NONE);
		bCreaSchema.setBounds(new Rectangle(713, 232, 81, 23));
		bCreaSchema.setText("Crea nuovo");
		tableSchemiDiete = new Table(groupDieta, SWT.NONE | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableSchemiDiete.setHeaderVisible(true);
		tableSchemiDiete.setLinesVisible(true);
		tableSchemiDiete.setBounds(new Rectangle(14, 228, 207, 149));
		final TableColumn columnId = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnId.setText("Id Dieta");
		columnId.setWidth(0);
		columnId.setResizable(false);
		final TableColumn columnNomeSchema = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNomeSchema.setText("Nome");
		final TableColumn columnNumGiorni = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNumGiorni.setText("Durata Ciclo");
		final TableColumn columnNoteSchema = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNoteSchema.setText("Note");
		final TableColumn columnStandard = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnStandard.setText("Standard");
		
		

		
		
		tableSchemiDiete
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						TableItem itemSel = tableSchemiDiete.getSelection()[0];
						int id = Integer.parseInt(itemSel.getText(0));
						ArrayList<GiornoDieta> arrGior = dietaDao.getSchemiDieta(id);
						String schema = "";
						for (GiornoDieta giorno : arrGior) {
							schema = schema.concat(giorno.getGiorno()+"\n");
							for (StrutPasto pasto : giorno.getPasti()) {
								schema = schema.concat(pasto.getNomePasto()+"\n");
								for (StrutAlimento alimento : pasto.getAlimenti()) {
									schema = schema.concat(alimento.getQuantita()+"\t"+alimento.getNomeAlimento()+"\t"+alimento.getTipologia()+"\n");
								}
							}
							schema = schema.concat("\n");
						}
						
						textVisSchema.setText(schema);
						buttonModificaDieta.setEnabled(true);
						buttonEliminaDieta.setEnabled(true);
					}
				});
		
		final TableColumn [] columns = tableSchemiDiete.getColumns ();
		aggiornaDiete(false);
		for (int i=0; i<columns.length; i++) columns[i].pack();
		
		
		lSelezSchema = new Label(groupDieta, SWT.NONE);
		lSelezSchema.setBounds(new Rectangle(11, 206, 155, 13));
		lSelezSchema.setText("*Seleziona dieta");
		textVisSchema = new StyledText(groupDieta, SWT.NONE | SWT.V_SCROLL | SWT.READ_ONLY);
		textVisSchema.setBounds(new Rectangle(230, 229, 479, 149));
		textVisSchema.setEditable(false);
		buttonInserisci = new Button(groupDieta, SWT.NONE);
		buttonInserisci.setBounds(new Rectangle(689, 411, 116, 32));
		buttonInserisci.setText("Inserisci");
		radioButtonStandard = new Button(groupDieta, SWT.RADIO);
		radioButtonStandard.setBounds(new Rectangle(92, 379, 102, 16));
		radioButtonStandard.setText("Schemi standard");
		radioButtonStandard
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						aggiornaDiete(true);
					}
				});
		radioButtonTutti = new Button(groupDieta, SWT.RADIO);
		radioButtonTutti.setBounds(new Rectangle(12, 380, 86, 16));
		radioButtonTutti.setText("Tutti");
		radioButtonTutti.setSelection(true);
		buttonModificaDieta = new Button(groupDieta, SWT.NONE);
		buttonModificaDieta.setBounds(new Rectangle(715, 258, 78, 23));
		buttonModificaDieta.setText("Modifica");
		buttonModificaDieta
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						TableItem itemSel = tableSchemiDiete.getSelection()[0];
						int id = Integer.parseInt(itemSel.getText(0));
						ArrayList<GiornoDieta> arrGior = dietaDao.getSchemiDieta(id);
						boolean stand = false;
						if (itemSel.getText(4).equals("si")) {
							stand = true;
						}
						InserisciDietaShell dietaShell = new InserisciDietaShell(arrGior, itemSel.getText(1), itemSel.getText(3), stand);
						shellMsg = new Shell();
						boolean inserisci = true;
						if (Integer.parseInt(spinCicli.getText())==0) {
							inserisci = false;
						}

						if (inserisci) {
							dietaShell.createShellInsSchemaDietetico();
						}else{
							messageBox = new MessageBox(shellMsg,
									SWT.OK |
									SWT.ICON_ERROR);
							messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
							messageBox.open();		

						}
							
					
					}
				});
		buttonEliminaDieta = new Button(groupDieta, SWT.NONE);
		buttonEliminaDieta.setBounds(new Rectangle(717, 285, 75, 23));
		buttonEliminaDieta.setText("Cancella");
		radioButtonTutti
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						aggiornaDiete(false);
					}
				});
		buttonInserisci
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				shellMsg = new Shell();
				boolean inserisci = true;
				if (Integer.parseInt(spinCicli.getText())==0 | tableSchemiDiete.getSelectionCount()==0) {
					inserisci = false;
				}

				if (inserisci) {
					Dieta dieta = new Dieta();
					int idDieta = Integer.parseInt(tableSchemiDiete.getSelection()[0].getText(0));
				for (Dieta d : dietaDao.getDiete()) {
					if(d.getIdDieta()==idDieta){
						dieta = d;
						break;
					}
				}
					String data = calendar.getYear()+"-"+(calendar.getMonth()+1)+"-"+calendar.getDay();
					String formato = "yyyy-MM-dd";
					Date inizioDieta = new Date();
					inizioDieta = service.Utils.convertStringToDate(data, formato);
					//ArrayList<GiornoDieta> arrGiorni = dieta.getSchemiDieta(Integer.parseInt(tableSchemiDiete.getSelection()[0].getText(0)));
					dietaDao.inserisciPrescrizione(inizioDieta, Integer.parseInt(spinCicli.getText()), textNote.getText(), PazienteTableView.getPazienteSelezionato(), dieta);
					//dieta.inserisciDieta(arrGiorni, textNomeDieta.getText(), textNote.getText(), checkBoxStandard.getSelection(), specifichedieta);
				}else{
					messageBox = new MessageBox(shellMsg,
							SWT.OK |
							SWT.ICON_ERROR);
					messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
					messageBox.open();		

				}


			}

		});

		
		
		
		
		bCreaSchema.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				InserisciDietaShell dietaShell = new InserisciDietaShell();
				shellMsg = new Shell();
				boolean inserisci = true;
				if (Integer.parseInt(spinCicli.getText())==0) {
					inserisci = false;
				}

				if (inserisci) {
					dietaShell.createShellInsSchemaDietetico();
				}else{
					messageBox = new MessageBox(shellMsg,
							SWT.OK |
							SWT.ICON_ERROR);
					messageBox.setMessage("Attenzione completare tutti i campi obbligatori (*)");	
					messageBox.open();		

				}
					
			}
		});
		buttonModificaDieta.setEnabled(false);
		buttonEliminaDieta.setEnabled(false);
		buttonEliminaDieta
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						dietaDao.cancellaDieta(tableSchemiDiete.getSelection()[0]);
						aggiornaDiete(false);
					}
				});
	}

	public static void aggiornaDiete(boolean standard) {
		tableSchemiDiete.removeAll();
		TableItem itemSchema = null;
		ArrayList<Dieta> diete = dietaDao.getDiete();
		if (standard) {
			for (Dieta dieta : diete) {
				if (dieta.getDietaStandard()) {
					itemSchema = new TableItem(tableSchemiDiete, SWT.NULL);
					itemSchema.setText(0, ""+dieta.getIdDieta());
					itemSchema.setText(1, dieta.getNome());
					itemSchema.setText(2, ""+dieta.getDurataCiclo());
					itemSchema.setText(3, dieta.getNote());
					itemSchema.setText(4, "si");
				}
			}
			}
			else{
				for (Dieta dieta : diete) {
					itemSchema = new TableItem(tableSchemiDiete, SWT.NULL);
					itemSchema.setText(0, ""+dieta.getIdDieta());
					itemSchema.setText(1, dieta.getNome());
					itemSchema.setText(2, ""+dieta.getDurataCiclo());
					itemSchema.setText(3, dieta.getNote());
					if (dieta.getDietaStandard()) 
						itemSchema.setText(4, "si");
					else
						itemSchema.setText(4, "no");
					
			}
			}
	}
	
	
	
	


}
