package studiodietetico;

import forms.HomePazienteForm;
import hibernate.Dieta;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import command.DietaDAO;
import service.GiornoDieta;
import service.StrutAlimento;
import service.StrutPasto;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.custom.StyledText;

public class InserisciDietaView extends ViewPart {
	
	private Label labelPaziente = null;  //  @jve:decl-index=0:visual-constraint="9,-39"
	private Text textPaziente = null;

	private Composite top = null;
	private Group groupDieta = null;
	private Label lDataInizio = null;
	private Label lNote = null;
	private Text textNote = null;
	private MessageBox messageBox = null;  //  @jve:decl-index=0:
	private Spinner spinCicli = null;
	private Label lNCicli = null;

	private DietaDAO dietaDao = new DietaDAO();  //  @jve:decl-index=0:
	//private ArrayList<StrutAlimento> alimentiDB = null;
	private Shell shellMsg = null;
	
	private Button bCreaSchema = null;
	private Table tableSchemiDiete = null;
	private Label lSelezSchema = null;
	private StyledText textVisSchema = null;


	private DateTime calendar = null;

	private Button buttonInserisci = null;
	public static final String VIEW_ID = "StudioDietetico.InserisciDietaView";
	private Button radioButtonStandard = null;
	private Button radioButtonTutti = null;


	
	public InserisciDietaView() {
		//alimentiDB = dieta.getAlimentiObj();
	}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		
		//Visualizzazione del paziente selezionato
		labelPaziente = new Label(top, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 10, 82, 24));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(top, SWT.BORDER);
		textPaziente.setBounds(new Rectangle(106, 10, 263, 31));
		textPaziente.setEnabled(false);
		textPaziente.setText(HomePazienteForm.getPazienteSelezionato().getCognome()+"   "+
				HomePazienteForm.getPazienteSelezionato().getNome()+"   "+
				HomePazienteForm.getPazienteSelezionato().getDataNascita());

		createGroupDieta();
	}

	
	/**
	 * This method initializes groupDieta	
	 *
	 */
	private void createGroupDieta() {
		
		groupDieta = new Group(top, SWT.NONE);
		groupDieta.setLayout(null);
		groupDieta.setText("Prescrizione dieta");
		groupDieta.setBounds(new Rectangle(11, 50, 816, 677));
		lDataInizio = new Label(groupDieta, SWT.NONE);
		lDataInizio.setBounds(new Rectangle(15, 176, 73, 13));
		lDataInizio.setText("*Data inizio");
		calendar = new DateTime (groupDieta, SWT.NONE | SWT.CALENDAR | SWT.BORDER);
		calendar.setBounds(new Rectangle(17, 196, 165, 144));
		lNote = new Label(groupDieta, SWT.NONE);
		lNote.setBounds(new Rectangle(223, 86, 60, 13));
		lNote.setText("Note");
		textNote = new Text(groupDieta, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		textNote.setBounds(new Rectangle(225, 102, 438, 57));
		spinCicli = new Spinner(groupDieta, SWT.NONE);
		spinCicli.setMaximum(50);
		spinCicli.setMinimum(1);
		Font fontSpin = new Font(Display.getCurrent(),"Arial",14,SWT.BOLD);
		spinCicli.setFont(fontSpin);
		spinCicli.setBounds(new Rectangle(21, 112, 51, 24));
		lNCicli = new Label(groupDieta, SWT.NONE);
		lNCicli.setBounds(new Rectangle(18, 88, 108, 13));
		lNCicli.setText("*Numero cicli");
		bCreaSchema = new Button(groupDieta, SWT.NONE);
		bCreaSchema.setBounds(new Rectangle(669, 376, 81, 23));
		bCreaSchema.setText("Crea nuovo");
		tableSchemiDiete = new Table(groupDieta, SWT.NONE | SWT.FULL_SELECTION | SWT.V_SCROLL);
		tableSchemiDiete.setHeaderVisible(true);
		tableSchemiDiete.setLinesVisible(true);
		tableSchemiDiete.setBounds(new Rectangle(19, 372, 207, 149));
	
		final TableColumn columnNomeSchema = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNomeSchema.setText("Nome");
		final TableColumn columnNumGiorni = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNumGiorni.setText("Durata Ciclo");
		final TableColumn columnNoteSchema = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnNoteSchema.setText("Note");
		final TableColumn columnId = new TableColumn(tableSchemiDiete, SWT.NONE);
		columnId.setText("Id Dieta");
		
		final TableColumn [] columns = tableSchemiDiete.getColumns ();
		aggiornaDiete(true);
		for (int i=0; i<columns.length; i++) columns[i].pack();

		
		
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
					}
				});
		
		
		
		
		lSelezSchema = new Label(groupDieta, SWT.NONE);
		lSelezSchema.setBounds(new Rectangle(16, 350, 155, 13));
		lSelezSchema.setText("*Seleziona schema dietetico");
		textVisSchema = new StyledText(groupDieta, SWT.NONE | SWT.V_SCROLL | SWT.READ_ONLY);
		textVisSchema.setBounds(new Rectangle(229, 373, 438, 149));
		textVisSchema.setEditable(false);
		buttonInserisci = new Button(groupDieta, SWT.NONE);
		buttonInserisci.setBounds(new Rectangle(694, 555, 116, 32));
		buttonInserisci.setText("Inserisci");
		radioButtonStandard = new Button(groupDieta, SWT.RADIO);
		radioButtonStandard.setBounds(new Rectangle(17, 523, 102, 16));
		radioButtonStandard.setText("Schemi standard");
		radioButtonStandard
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						aggiornaDiete(true);
					}
				});
		radioButtonTutti = new Button(groupDieta, SWT.RADIO);
		radioButtonTutti.setBounds(new Rectangle(19, 538, 86, 16));
		radioButtonTutti.setText("Tutti");
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
					int idDieta = Integer.parseInt(tableSchemiDiete.getSelection()[0].getText(2));
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
					dietaDao.inserisciPrescrizione(inizioDieta, Integer.parseInt(spinCicli.getText()), textNote.getText(), HomePazienteForm.getPazienteSelezionato(), dieta);
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
	
	}



	private void aggiornaDiete(boolean standard) {
		TableItem itemSchema = null;
		//TODO
		ArrayList<Dieta> diete = new ArrayList<Dieta>();
		diete = dietaDao.getDiete();
		if (standard) {
			for (Dieta dieta : diete) {
				if (dieta.getDietaStandard()) {
					itemSchema = new TableItem(tableSchemiDiete, SWT.NULL);
					itemSchema.setText(0, dieta.getNome());
					itemSchema.setText(1, ""+dieta.getDurataCiclo());
					itemSchema.setText(2, dieta.getNote());
					itemSchema.setText(3, ""+dieta.getIdDieta());
				}
			}
			}
			else{
				for (Dieta dieta : diete) {
					itemSchema = new TableItem(tableSchemiDiete, SWT.NULL);
					itemSchema.setText(0, dieta.getNome());
					itemSchema.setText(1, ""+dieta.getDurataCiclo());
					itemSchema.setText(2, dieta.getNote());
					itemSchema.setText(3, ""+dieta.getIdDieta());
			}
			}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}




}  //  @jve:decl-index=0:visual-constraint="2,10,859,740"
