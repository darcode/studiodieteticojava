package studiodietetico;

import hibernate.Attivitafisica;
import hibernate.Intervento;
import hibernate.Intolleranzaallergia;
import hibernate.Paziente;
import hibernate.Tipologiaintervento;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Spinner;

import command.AnamnesiDAO;
import command.PazienteDAO;

import common.GenericBean;

import org.eclipse.swt.layout.GridLayout;

import service.RegistraIntervento;
import org.eclipse.swt.widgets.Table;

public class AnamnesiShell {

	//INTERVENTI	
	private Label labelNomeInt = null;
	private Text textNomeInt = null;
	private Label labelDescrInt = null;
	private Text textAreaDescrInt = null;
	private Label labelLocalizzazione = null;
	private Text textAreaLocalizzazione = null;
	private Button buttonInsertNewInt = null;
	private Tipologiaintervento interventoSelez = null;  
	private Shell sShellInserimentoInterventi = null;  //  @jve:decl-index=0:visual-constraint="-376,-86"
	private Group groupInserimentoInt = null;
	private boolean attivaModifica = false;
	private Shell sShellDettagliInterventi;  //  @jve:decl-index=0:visual-constraint="-373,748"
	private Label labelNomeIntVis = null;
	private Label labelDescrIntVis = null;
	private Text textNomeIntVis = null;
	private Text textAreaDescrIntVis = null;
	private Label labelLocalizzazioneVis;
	private Text textAreaLocalizzazioneVis;
	private Label labelDataIntVis;
	private Label labelNumIntVis;
	private Button buttonModificaInterventi = null;
	private Button buttonAppyModInterventi;
	private Button buttonChiudi;
	private Spinner spinnerNumVis = null;
	private DateTime calendar;
	private Table tableTipoInt;
	private Button buttonInsertTipoIntervento = null;
	private Button buttonModificaTipoIntervento = null;
	private Button buttonEliminaTipoIntervento = null;
	private Label labelDataIntervento = null;
	private DateTime calendarInserimento = null;
	private Label labelNumInterventi = null;
	private Spinner spinnerNumInterventi = null;
	private Button buttonAnnullaNewInt;
	private Button buttonInserIntervento = null;
	private Label labelElencoTipoInt = null;
	private Button buttonAnnullaIntervento = null;
	private Text textDataIntVis;
	
	
	//ALLERGIE
	private Shell sShellDettagliAllergie = null;
	private Group groupAllergie = null;
	private Label labelIntAll = null;
	private Button radioButtonInt = null;
	private Button radioButtonAll = null;
	private Label labelSostanza = null;
	private Text textSost = null;
	private Label labelAlPrinc = null;
	private Text textAlPrinc = null;
	private Label labelDerivati = null;
	private Text textAreaDerivati = null;
	private Label labelGrado = null;
	private Label labelEffColl = null;
	private Text textAreaEffColl = null;
	private Button buttonConfermaAll = null;
	private Text textGrado = null;
	
	//ATTIVITA' FISICA
	private Group groupAttFisica = null;
	private Label labelAttFisSel = null;
	private List textAreaAttFis = null;
	private Button buttonVaiAttFis = null;
	private List textAreaAttFisSel = null;
	private Label labelNomeAttFis = null;
	private Text textNomeAttFis = null;
	private Label labelDescAttFis = null;
	private Text textAreaDescAttFis = null;
	private Button buttonAggiornaListaAttFis = null;
	private Group groupInsAttFis = null;
	private Button buttonInsNewAttFis = null;
	private Button buttonConfermaAttFis = null;
	private Shell sShellInsAttFis = null; 
	private Group groupVisualizzazioneSport = null;
	private Shell sShellProva = null;  //  @jve:decl-index=0:visual-constraint="-364,1197"
	//public static final String VIEW_ID = "StudioDietetico.anamnesi";
	
	//MESSAGE BOX
	private Shell sShellMessElimina = null;  //  @jve:decl-index=0:visual-constraint="-347,665"
	private Label labelSostanzaAllVis;
	private Text textSostanzaAllVis;
	private Label labelFlagAllVis;
	private static TableItem itemInserimento = null;
	private static TableItem itemModifica = null;
	private Button radioButtonIntolleranza = null;
	private Button radioButtonAllergia = null;
	private Label labelAlimentoVis;
	private Text textAlimentoVis;
	private Label labelDerivatiVis;
	private Text textDerivatiVis;
	private Label labelGradoVis;
	private Text textGradoVis;
	private Label labelEffettiCollVis;
	private Text textEffettiCollVis;
	private Button buttonModificaAllergie;
	private Button buttonAppyModAllergie;
	private Button buttonChiudiAll;
	
	
	public AnamnesiShell() {}

	//-----------------------------------------INTERVENTI-------------------------------------------------------------
	public void createSShellDettagliInterventi(final TableItem rigaTableClick) {
		String idTipoInt = rigaTableClick.getText(2);
		AnamnesiDAO an = new AnamnesiDAO();
		final Tipologiaintervento tipoInt = an.getTipoInterventiById(idTipoInt);
		final String nome = rigaTableClick.getText(5);
		final String descrizione = tipoInt.getDescrizione();
		final String localizzazione = tipoInt.getLocalizzazione();
		String[] dataSplit = rigaTableClick.getText(3).split("-");
		int gg = Integer.parseInt(dataSplit[2]),
			mm = Integer.parseInt(dataSplit[1]),
			yy = Integer.parseInt(dataSplit[0]),
			num = Integer.parseInt(rigaTableClick.getText(4));
		
		sShellDettagliInterventi = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellDettagliInterventi.setText("Dettagli Intervento Selezionato");
		sShellDettagliInterventi.setSize(new Point(712, 434));
		
		labelNomeIntVis = new Label(sShellDettagliInterventi, SWT.NONE);
		labelNomeIntVis.setBounds(new Rectangle(20, 20, 130, 20));
		labelNomeIntVis.setText("Nome dell'intervento");
		textNomeIntVis = new Text(sShellDettagliInterventi, SWT.NONE);
		textNomeIntVis.setBounds(new Rectangle(170, 20, 480, 20));
		textNomeIntVis.setEnabled(false);
		textNomeIntVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textNomeIntVis.setText(nome);
		
		labelDescrIntVis = new Label(sShellDettagliInterventi, SWT.NONE);
		labelDescrIntVis.setBounds(new Rectangle(20, 60, 130, 20));
		labelDescrIntVis.setText("Descrizione");
		textAreaDescrIntVis = new Text(sShellDettagliInterventi, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescrIntVis.setBounds(new Rectangle(170, 60, 500, 45));
		textAreaDescrIntVis.setEnabled(false);
		textAreaDescrIntVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textAreaDescrIntVis.setText(descrizione);
		
		labelLocalizzazioneVis = new Label(sShellDettagliInterventi, SWT.NONE);
		labelLocalizzazioneVis.setBounds(new Rectangle(20, 125, 130, 20));
		labelLocalizzazioneVis.setText("Zona interessata");
		textAreaLocalizzazioneVis = new Text(sShellDettagliInterventi, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaLocalizzazioneVis.setBounds(new Rectangle(170, 125, 500, 45));
		textAreaLocalizzazioneVis.setEnabled(false);
		textAreaLocalizzazioneVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textAreaLocalizzazioneVis.setText(localizzazione);
		
		labelDataIntVis = new Label(sShellDettagliInterventi, SWT.NONE);
		labelDataIntVis.setBounds(new Rectangle(20, 190, 130, 20));
		labelDataIntVis.setText("Data ultimo intervento");
		calendar = new DateTime (sShellDettagliInterventi, SWT.NONE | SWT.CALENDAR | SWT.BORDER);
		calendar.setBounds(new Rectangle(170, 190, 225, 145));
		calendar.setVisible(false);
		textDataIntVis = new Text(sShellDettagliInterventi, SWT.NONE);
		textDataIntVis.setBounds(new Rectangle(170, 190, 225, 20));
		textDataIntVis.setEnabled(false);
		textDataIntVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textDataIntVis.setText(gg+"/"+mm+"/"+yy);
		
		labelNumIntVis = new Label(sShellDettagliInterventi, SWT.NONE);
		labelNumIntVis.setBounds(new Rectangle(430, 190, 120, 20));
		labelNumIntVis.setText("Numero di interventi");
		spinnerNumVis = new Spinner(sShellDettagliInterventi, SWT.READ_ONLY);
		spinnerNumVis.setBounds(new Rectangle(570, 190, 45, 20));
		spinnerNumVis.setEnabled(false);
		spinnerNumVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		spinnerNumVis.setMinimum(num);
		
		buttonModificaInterventi = new Button(sShellDettagliInterventi, SWT.NONE);
		buttonModificaInterventi.setBounds(new Rectangle(400, 350, 70, 25));
		buttonModificaInterventi.setText("Modifica");
		buttonModificaInterventi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textDataIntVis.setVisible(false);
						calendar.setVisible(true);
						spinnerNumVis.setEnabled(true);
						spinnerNumVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						
						buttonAppyModInterventi.setEnabled(true);
						buttonModificaInterventi.setEnabled(false);
					}
				});
		
		buttonAppyModInterventi = new Button(sShellDettagliInterventi, SWT.NONE);
		buttonAppyModInterventi.setBounds(new Rectangle(480, 350, 110, 25));
		buttonAppyModInterventi.setText("Applica Modifiche");
		buttonAppyModInterventi.setEnabled(false);
		buttonAppyModInterventi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Paziente pazSel = AnamnesiTTableView.getPazienteSel();
						String data = calendar.getYear()+"-"+(calendar.getMonth()+1)+"-"+calendar.getDay();
						String formato = "yyyy-MM-dd";
						Date dataInt = service.Utils.convertStringToDate(data, formato);
						int num = spinnerNumVis.getSelection();
						AnamnesiDAO an = new AnamnesiDAO();
						an.modificaIntervento(pazSel, tipoInt, dataInt, num);
						
						sShellDettagliInterventi.close();
						
						//TODO Aggiornare la tabella degli interventi in AnamnesiTableView
					}
				});
	
		buttonChiudi = new Button(sShellDettagliInterventi, SWT.NONE);
		buttonChiudi.setBounds(new Rectangle(600, 350, 70, 25));
		buttonChiudi.setText("Chiudi");
		buttonChiudi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliInterventi.close();
					}
				});
		sShellDettagliInterventi.open();
	}
	
	public void createSShellInserimentoInterventi() {
		sShellInserimentoInterventi = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellInserimentoInterventi.setText("Inserimento Nuovo Intervento");
		sShellInserimentoInterventi.setSize(new Point(725, 739));
		
		final AnamnesiDAO an = new AnamnesiDAO(); 
		ArrayList<Object> listTipoInt = an.getListTipoInterventi();
		
		for (int i = 0; i < listTipoInt.size(); i++) {
			System.out.println("ListaTipoInt("+i+"): "+listTipoInt.get(i));
		}
				
		labelElencoTipoInt = new Label(sShellInserimentoInterventi, SWT.NONE);
		labelElencoTipoInt.setBounds(new Rectangle(23, 207, 341, 17));
		labelElencoTipoInt.setText("Selezionare la tipologia dell'intervento di interesse");
		
		tableTipoInt = new Table(sShellInserimentoInterventi, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableTipoInt.setHeaderVisible(true);
		tableTipoInt.setLinesVisible(true);
		tableTipoInt.setBounds(new Rectangle(22, 226, 483, 203));
		tableTipoInt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						buttonEliminaTipoIntervento.setEnabled(true);
						buttonModificaTipoIntervento.setEnabled(true);
						interventoSelez = an.getTipoInterventiById(tableTipoInt.getSelection()[0].getText(0));
						
						textAreaDescrInt.setText(interventoSelez.getDescrizione());
						textNomeInt.setText(interventoSelez.getNome());
						textAreaLocalizzazione.setText(interventoSelez.getLocalizzazione());
					}
				});
		
		//Adatta la tabella, nasconde la prima colonna e applica l'ordinamento alle colonne
		riempiTabellaTipoIntervento(tableTipoInt, listTipoInt);
		for (TableColumn colonna : tableTipoInt.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		tableTipoInt.getColumn(0).setWidth(0);
		TableForm.ordinamentoStringhe(tableTipoInt, 1);
		TableForm.ordinamentoStringhe(tableTipoInt, 2);
		TableForm.ordinamentoStringhe(tableTipoInt, 3);
		
		createGroupInserimentoInt();
		
		buttonInsertTipoIntervento = new Button(sShellInserimentoInterventi, SWT.NONE);
		buttonInsertTipoIntervento.setBounds(new Rectangle(22, 435, 90, 25));
		buttonInsertTipoIntervento.setText("Inserisci Nuovo");
		buttonInsertTipoIntervento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						groupInserimentoInt.setEnabled(true);
						
						calendarInserimento.setEnabled(false);
						spinnerNumInterventi.setEnabled(false);
						tableTipoInt.setEnabled(false);
						buttonInsertTipoIntervento.setEnabled(false);
						buttonModificaTipoIntervento.setEnabled(false);
						buttonEliminaTipoIntervento.setEnabled(false);
						buttonInserIntervento.setEnabled(false);
						buttonAnnullaIntervento.setEnabled(false);
					}
				});
		
		buttonModificaTipoIntervento = new Button(sShellInserimentoInterventi, SWT.NONE);
		buttonModificaTipoIntervento.setBounds(new Rectangle(119, 435, 60, 25));
		buttonModificaTipoIntervento.setText("Modifica");
		buttonModificaTipoIntervento.setEnabled(false);
		buttonModificaTipoIntervento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(tableTipoInt.getSelectionCount()>0) {
							attivaModifica = true;
							groupInserimentoInt.setEnabled(true);
						
							calendarInserimento.setEnabled(false);
							spinnerNumInterventi.setEnabled(false);
							tableTipoInt.setEnabled(false);
							buttonInsertTipoIntervento.setEnabled(false);
							buttonModificaTipoIntervento.setEnabled(false);
							buttonEliminaTipoIntervento.setEnabled(false);
							buttonInserIntervento.setEnabled(false);
							buttonAnnullaIntervento.setEnabled(false);
						} else {
							createMessNotSelElem();
						}
					}
				});
		
		buttonEliminaTipoIntervento = new Button(sShellInserimentoInterventi, SWT.NONE);
		buttonEliminaTipoIntervento.setBounds(new Rectangle(184, 435, 60, 25));
		buttonEliminaTipoIntervento.setText("Elimina");
		buttonEliminaTipoIntervento.setEnabled(false);
		buttonEliminaTipoIntervento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(tableTipoInt.getSelectionCount()>0)
							createMessConfermaCanc(tableTipoInt.getSelectionIndex());
						else
							createMessNotSelElem();
					}
				});
		
		labelDataIntervento = new Label(sShellInserimentoInterventi, SWT.NONE);
		labelDataIntervento.setBounds(new Rectangle(25, 34, 130, 20));
		labelDataIntervento.setText("Data ultimo intervento");
		calendarInserimento = new DateTime(sShellInserimentoInterventi, SWT.NONE | SWT.CALENDAR | SWT.BORDER);
		calendarInserimento.setBackground(new Color(Display.getCurrent(),245,245,245));
		calendarInserimento.setBounds(new Rectangle(163, 34, 227, 141));
		
		labelNumInterventi = new Label(sShellInserimentoInterventi, SWT.NONE);
		labelNumInterventi.setBounds(new Rectangle(437, 37, 115, 20));
		labelNumInterventi.setText("Numero di interventi");
		spinnerNumInterventi = new Spinner(sShellInserimentoInterventi, SWT.NONE);
		spinnerNumInterventi.setBounds(new Rectangle(563, 37, 68, 20));
		spinnerNumInterventi.setMinimum(1);
		buttonInserIntervento = new Button(sShellInserimentoInterventi, SWT.NONE);
		buttonInserIntervento.setBounds(new Rectangle(520, 466, 89, 28));
		buttonInserIntervento.setText("Inserisci");
		buttonInserIntervento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						AnamnesiDAO an = new AnamnesiDAO();
						Paziente pazSelHome = AnamnesiTTableView.getPazienteSel();
						//Paziente pazSelHome = PazienteDAO.getPazienti().get(3);
						String data = calendarInserimento.getYear()+"-"+(calendarInserimento.getMonth()+1)+"-"+calendarInserimento.getDay();
						String formato = "yyyy-MM-dd";
						Date dataI = service.Utils.convertStringToDate(data, formato);
						
						if(interventoSelez == null) {
							createMessNotSelElem();
						} else {
							if(an.getInterventiListByPazIdTipoInt(interventoSelez.getIdTipologiaIntervento(), pazSelHome.getIdPaziente())==null)
								an.registraIntervento(pazSelHome, interventoSelez, dataI, spinnerNumInterventi.getSelection());	
							else
								createMessElemPresente();
						}
						//Aggiornare la table di AnamnesiTableView con il nuovo intervento
						
					}
				});
		
		buttonAnnullaIntervento = new Button(sShellInserimentoInterventi, SWT.NONE);
		buttonAnnullaIntervento.setBounds(new Rectangle(617, 468, 84, 26));
		buttonAnnullaIntervento.setText("Annulla");
		buttonAnnullaIntervento.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellInserimentoInterventi.close();
					}
				});
		
		sShellInserimentoInterventi.open();
	}
	
	private void createGroupInserimentoInt() {
		groupInserimentoInt = new Group(sShellInserimentoInterventi, SWT.NONE);
		groupInserimentoInt.setText("Inserimento nuovo intervento");
		groupInserimentoInt.setBounds(new Rectangle(18, 514, 689, 180));
		groupInserimentoInt.setEnabled(false);
		
		labelNomeInt = new Label(groupInserimentoInt, SWT.NONE);
		labelNomeInt.setBounds(new Rectangle(10, 23, 170, 20));
		labelNomeInt.setText("* Nome dell'intervento");
		textNomeInt = new Text(groupInserimentoInt, SWT.NONE);
		textNomeInt.setBounds(new Rectangle(209, 23, 450, 20));
		
		labelDescrInt = new Label(groupInserimentoInt, SWT.NONE);
		labelDescrInt.setBounds(new Rectangle(10, 50, 170, 20));
		labelDescrInt.setText("Descrizione");
		textAreaDescrInt = new Text(groupInserimentoInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescrInt.setBounds(new Rectangle(209, 50, 470, 40));
		
		labelLocalizzazione = new Label(groupInserimentoInt, SWT.NONE);
		labelLocalizzazione.setBounds(new Rectangle(10, 100, 170, 20));
		labelLocalizzazione.setText("* Zona interessata");
		textAreaLocalizzazione = new Text(groupInserimentoInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaLocalizzazione.setBounds(new Rectangle(209, 100, 470, 40));
		
		buttonInsertNewInt = new Button(groupInserimentoInt, SWT.NONE);
		buttonInsertNewInt.setBounds(new Rectangle(505, 150, 74, 28));
		buttonInsertNewInt.setText("Conferma");
		buttonInsertNewInt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AnamnesiDAO interv = new AnamnesiDAO();
				if (attivaModifica) { //fa la modifica
					interv.modificaTipoIntervento(interventoSelez, textNomeInt.getText(), textAreaDescrInt.getText(), textAreaLocalizzazione.getText());
				} else { //fa l'inserimento
					interv.registraTipoIntervento(textNomeInt.getText(), textAreaDescrInt.getText(), textAreaLocalizzazione.getText());
				}
				
				textNomeInt.setText("");
				textAreaDescrInt.setText("");
				textAreaLocalizzazione.setText("");
				groupInserimentoInt.setEnabled(false);
				
				
				//Aggiorna la tabella delle tipologie di Interventi
				tableTipoInt.removeAll();
				int k = 0;
				while (k<tableTipoInt.getColumnCount()) {
					tableTipoInt.getColumn(k).dispose();
				}
				
				ArrayList<Object> listaTipoInt = interv.getListTipoInterventi();
				riempiTabellaTipoIntervento(tableTipoInt, listaTipoInt);
				//TableUtil.riempiTabellaEntita(tableTipoInt, listaTipoInt);
				
				//Adatto la tabella
				for (TableColumn colonna : tableTipoInt.getColumns()) {
					colonna.pack();
					colonna.setResizable(false);
				}
				tableTipoInt.getColumn(0).setWidth(0);
				TableForm.ordinamentoStringhe(tableTipoInt, 1);
				TableForm.ordinamentoStringhe(tableTipoInt, 2);
				TableForm.ordinamentoStringhe(tableTipoInt, 3);
		
				calendarInserimento.setEnabled(true);
				spinnerNumInterventi.setEnabled(true);
				tableTipoInt.setEnabled(true);
				tableTipoInt.deselectAll();
				buttonInsertTipoIntervento.setEnabled(true);
				buttonInserIntervento.setEnabled(true);
				buttonAnnullaIntervento.setEnabled(true);
				attivaModifica = false;
			}
		});
		
		buttonAnnullaNewInt = new Button(groupInserimentoInt, SWT.NONE);
		buttonAnnullaNewInt.setBounds(new Rectangle(585, 150, 74, 28));
		buttonAnnullaNewInt.setText("Annulla");
		buttonAnnullaNewInt.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textNomeInt.setText("");
						textAreaDescrInt.setText("");
						textAreaLocalizzazione.setText("");
						attivaModifica = false;
						interventoSelez = null;
						
						groupInserimentoInt.setEnabled(false);
						calendarInserimento.setEnabled(true);
						spinnerNumInterventi.setEnabled(true);
						tableTipoInt.setEnabled(true);
						tableTipoInt.deselectAll();
						buttonInsertTipoIntervento.setEnabled(true);
						buttonInserIntervento.setEnabled(true);
						buttonAnnullaIntervento.setEnabled(true);
					}
				});
	
	}
	
	
	
	//-----------------------------------------ALLERGIE/INTOLLERANZE---------------------------------------------------
	public void createSShellDettagliAllergie(final TableItem rigaTableClick) {
		String idAllInt = rigaTableClick.getText(0);
		AnamnesiDAO an = new AnamnesiDAO();
		final Intolleranzaallergia intAll = an.getAllergiaById(idAllInt);
		
		final String flag = rigaTableClick.getText(2);
		final String sostanza = rigaTableClick.getText(3);
		final String alimento = rigaTableClick.getText(4);
		final String derivati = rigaTableClick.getText(5);
		final String grado = rigaTableClick.getText(6);
		final String effettiCollaterali = rigaTableClick.getText(7);
		
		sShellDettagliAllergie = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellDettagliAllergie.setText("Dettagli Intolleranza/Allergia Selezionata");
		sShellDettagliAllergie.setSize(new Point(712, 434));
		
		labelFlagAllVis = new Label(sShellDettagliAllergie, SWT.NONE);
		labelFlagAllVis.setBounds(new Rectangle(20, 20, 130, 20));
		labelFlagAllVis.setText("Intolleranza / Allergia");
		radioButtonIntolleranza = new Button(sShellDettagliAllergie, SWT.RADIO);
		radioButtonIntolleranza.setBounds(new Rectangle(170, 20, 100, 20));
		radioButtonIntolleranza.setText("Intolleranza");
		radioButtonIntolleranza.setEnabled(false);
		radioButtonAllergia = new Button(sShellDettagliAllergie, SWT.RADIO);
		radioButtonAllergia.setBounds(new Rectangle(280, 20, 100, 20));
		radioButtonAllergia.setText("Allergia");
		radioButtonAllergia.setEnabled(false);
		if (flag.equals("int")) {
			radioButtonIntolleranza.setSelection(true);
			radioButtonAllergia.setSelection(false);
		} else if (flag.equals("all")) {
			radioButtonAllergia.setSelection(true);
			radioButtonIntolleranza.setSelection(false);
		}

		labelSostanzaAllVis = new Label(sShellDettagliAllergie, SWT.NONE);
		labelSostanzaAllVis.setBounds(new Rectangle(20, 60, 130, 20));
		labelSostanzaAllVis.setText("Sostanza");
		textSostanzaAllVis = new Text(sShellDettagliAllergie, SWT.NONE);
		textSostanzaAllVis.setBounds(new Rectangle(170, 60, 500, 20));
		textSostanzaAllVis.setEnabled(false);
		textSostanzaAllVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textSostanzaAllVis.setText(sostanza);
		
		labelAlimentoVis = new Label(sShellDettagliAllergie, SWT.NONE);
		labelAlimentoVis.setBounds(new Rectangle(20, 100, 130, 20));
		labelAlimentoVis.setText("Alimento principale");
		textAlimentoVis = new Text(sShellDettagliAllergie, SWT.NONE);
		textAlimentoVis.setBounds(new Rectangle(170, 100, 500, 20));
		textAlimentoVis.setEnabled(false);
		textAlimentoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textAlimentoVis.setText(alimento);
		
		labelDerivatiVis = new Label(sShellDettagliAllergie, SWT.NONE);
		labelDerivatiVis.setBounds(new Rectangle(20, 140, 130, 20));
		labelDerivatiVis.setText("Derivati");
		textDerivatiVis = new Text(sShellDettagliAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textDerivatiVis.setBounds(new Rectangle(170, 140, 500, 60));
		textDerivatiVis.setEnabled(false);
		textDerivatiVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textDerivatiVis.setText(derivati);
		
		labelGradoVis = new Label(sShellDettagliAllergie, SWT.NONE);
		labelGradoVis.setBounds(new Rectangle(20, 220, 130, 20));
		labelGradoVis.setText("Grado");
		textGradoVis = new Text(sShellDettagliAllergie, SWT.NONE);
		textGradoVis.setBounds(new Rectangle(170, 220, 500, 20));
		textGradoVis.setEnabled(false);
		textGradoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textGradoVis.setText(grado);
		
		labelEffettiCollVis = new Label(sShellDettagliAllergie, SWT.NONE);
		labelEffettiCollVis.setBounds(new Rectangle(20, 260, 130, 20));
		labelEffettiCollVis.setText("Effetti Collaterali");
		textEffettiCollVis = new Text(sShellDettagliAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textEffettiCollVis.setBounds(new Rectangle(170, 260, 500, 60));
		textEffettiCollVis.setEnabled(false);
		textEffettiCollVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textEffettiCollVis.setText(effettiCollaterali);
		
		
		buttonModificaAllergie = new Button(sShellDettagliAllergie, SWT.NONE);
		buttonModificaAllergie.setBounds(new Rectangle(400, 350, 70, 25));
		buttonModificaAllergie.setText("Modifica");
		buttonModificaAllergie.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						radioButtonIntolleranza.setEnabled(true);
						radioButtonAllergia.setEnabled(true);
						textSostanzaAllVis.setEnabled(true);
						textSostanzaAllVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textAlimentoVis.setEnabled(true);
						textAlimentoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textDerivatiVis.setEnabled(true);
						textDerivatiVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textGradoVis.setEnabled(true);
						textGradoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textEffettiCollVis.setEnabled(true);
						textEffettiCollVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						
						buttonAppyModAllergie.setEnabled(true);
						buttonModificaAllergie.setEnabled(false);
					}
				});
		
		buttonAppyModAllergie = new Button(sShellDettagliAllergie, SWT.NONE);
		buttonAppyModAllergie.setBounds(new Rectangle(480, 350, 110, 25));
		buttonAppyModAllergie.setText("Applica Modifiche");
		buttonAppyModAllergie.setEnabled(false);
		buttonAppyModAllergie.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Paziente pazSel = AnamnesiTTableView.getPazienteSel();
						String flagAI = "";
						if(radioButtonAllergia.getSelection())
							flagAI = "all";
						else 
							flagAI = "int";
						AnamnesiDAO an = new AnamnesiDAO();
						an.modificaAllergie(intAll, flagAI, textSostanzaAllVis.getText(), textAlimentoVis.getText(), textDerivatiVis.getText(), textGradoVis.getText(), textEffettiCollVis.getText());
						
						sShellDettagliAllergie.close();
						
						//TODO Aggiornare la tabella delle allergie in AnamnesiTableView
					}
				});
	
		buttonChiudiAll = new Button(sShellDettagliAllergie, SWT.NONE);
		buttonChiudiAll.setBounds(new Rectangle(600, 350, 70, 25));
		buttonChiudiAll.setText("Chiudi");
		buttonChiudiAll.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliAllergie.close();
					}
				});
		sShellDettagliAllergie.open();
		
	}
	
	
	
	/*private void createGroupAll(Composite comp) {
		groupAllergie = new Group(comp, SWT.NONE);
		groupAllergie.setText("Inserimento eventuali intolleranze o allergie");
		groupAllergie.setBounds(new Rectangle(20, 63, 1000, 400));
		labelIntAll = new Label(groupAllergie, SWT.NONE);
		labelIntAll.setText("Indicare se si tratta di intolleranza o allergia");
		labelIntAll.setBounds(new Rectangle(20, 40, 250, 20));
		radioButtonInt = new Button(groupAllergie, SWT.RADIO);
		radioButtonInt.setBounds(new Rectangle(280, 40, 85, 20));
		radioButtonInt.setText("Intolleranza");
		radioButtonAll = new Button(groupAllergie, SWT.RADIO);
		radioButtonAll.setBounds(new Rectangle(390, 40, 71, 20));
		radioButtonAll.setText("Allergia");
		
		labelGrado = new Label(groupAllergie, SWT.NONE);
		labelGrado.setBounds(new Rectangle(20, 80, 210, 20));
		labelGrado.setText("Indicare il grado di intolleranza/allergia");
		textGrado = new Text(groupAllergie, SWT.BORDER);
		textGrado.setBounds(new Rectangle(250, 80, 300, 20));
		
		labelSostanza = new Label(groupAllergie, SWT.NONE);
		labelSostanza.setBounds(new Rectangle(20, 110, 61, 20));
		labelSostanza.setText("*Sostanza");
		textSost = new Text(groupAllergie, SWT.BORDER);
		textSost.setBounds(new Rectangle(80, 110, 400, 40));
		
		labelAlPrinc = new Label(groupAllergie, SWT.NONE);
		labelAlPrinc.setBounds(new Rectangle(20, 160, 120, 20));
		labelAlPrinc.setText("*Alimento principale");
		textAlPrinc = new Text(groupAllergie, SWT.BORDER);
		textAlPrinc.setBounds(new Rectangle(140, 160, 400, 40));
		
		labelDerivati = new Label(groupAllergie, SWT.NONE);
		labelDerivati.setBounds(new Rectangle(20, 210, 50, 20));
		labelDerivati.setText("Derivati");
		textAreaDerivati = new Text(groupAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL );
		textAreaDerivati.setBounds(new Rectangle(80, 210, 400, 60));
		
		labelEffColl = new Label(groupAllergie, SWT.NONE);
		labelEffColl.setBounds(new Rectangle(20, 290, 120, 20));
		labelEffColl.setText("Effetti collaterali");
		textAreaEffColl = new Text(groupAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaEffColl.setBounds(new Rectangle(150, 290, 400, 60));
		
		buttonConfermaAll = new Button(groupAllergie, SWT.NONE);
		buttonConfermaAll.setBounds(new Rectangle(600, 370, 80, 25));
		buttonConfermaAll.setText("Conferma");
		
		buttonConfermaAll.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				AnamnesiDAO an = new AnamnesiDAO();
				String flag = "";
				if (radioButtonInt.getSelection())
					flag = "int";
				else flag = "all";
				Paziente nomePaziente = new Paziente();
				
				//Sostituire con il paziente selezionato in HomePazienteView(togliere i commenti)
				nomePaziente = PazienteDAO.getPazienti().get(3);
				
				//HomePazienteView homeP = new HomePazienteView();
				//an.registraAllergie(flag, textSost.getText(), textAlPrinc.getText(), textAreaDerivati.getText(), textGrado.getText(), textAreaEffColl.getText(), homeP.getPazienteSelezionato());
				an.registraAllergie(flag, textSost.getText(), textAlPrinc.getText(), textAreaDerivati.getText(), textGrado.getText(), textAreaEffColl.getText(), nomePaziente);
		}
	});
	}*/

	
	
//-----------------------------------------ATTIVITA' FISICA-------------------------------------------------------------	
	/**
	 * sShellProva da inserire nel tab	
	 *
	 */
	private void createSShellProva() {
		sShellProva = new Shell();
		//sShellProva.setLayout(new GridLayout());
		sShellProva.setText("Shell prova");
		createGroupAttFisica();
		createGroupVisualizzazioneSport();
		sShellProva.setSize(new Point(810, 592));

		
	}

	/**
	 * Crea tutto il contenuto del tab in groupAttFisica
	 */
	private void createGroupAttFisica() {
		groupAttFisica = new Group(sShellProva, SWT.NONE);
		//groupAttFisica.setLayout(new GridLayout());
		groupAttFisica.setText("Attività Fisica");
		groupAttFisica.setBounds(new Rectangle(14, 2, 772, 309));
		labelAttFisSel = new Label(groupAttFisica, SWT.NONE);
		labelAttFisSel.setBounds(new Rectangle(15, 31, 200, 27));
		labelAttFisSel.setText("Selezionare lo sport seguito");
		textAreaAttFis = new List(groupAttFisica, SWT.WRAP | SWT.V_SCROLL);
		textAreaAttFis.setBounds(new Rectangle(15, 60, 330, 115));
		textAreaAttFis
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				buttonVaiAttFis.setEnabled(true);
			}
		});
		buttonVaiAttFis = new Button(groupAttFisica, SWT.NONE);
		buttonVaiAttFis.setBounds(new Rectangle(350, 90, 70, 30));
		buttonVaiAttFis.setEnabled(false);
		buttonVaiAttFis.setText(">>");
		buttonVaiAttFis
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						for (String sportSel : textAreaAttFis.getSelection()) {
							textAreaAttFisSel.add(sportSel);
						}
						textAreaAttFis.deselectAll();
						
					}
				});
		textAreaAttFisSel = new List(groupAttFisica, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaAttFisSel.setBounds(new Rectangle(430, 60, 330, 115));
		textAreaAttFisSel
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						System.out.println("widgetSelected()"); // TODO Auto-generated Event stub widgetSelected()
					}
				});
		
				
		buttonInsNewAttFis = new Button(groupAttFisica, SWT.NONE);
		buttonInsNewAttFis.setBounds(new Rectangle(15, 179, 98, 21));
		buttonInsNewAttFis.setText("Inserisci nuovo");
		buttonInsNewAttFis
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						createSShellInsAttFis();
					}
				});
		
		buttonConfermaAttFis = new Button(groupAttFisica, SWT.NONE);
		buttonConfermaAttFis.setBounds(new Rectangle(636, 266, 94, 31));
		buttonConfermaAttFis.setText("Conferma");
		buttonConfermaAttFis
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						
						
					}
				});
		
		
		//aggiorna la lista con gli sport presenti nel db
		AnamnesiDAO am = new AnamnesiDAO();
		ArrayList<Attivitafisica> elencoSport = new ArrayList<Attivitafisica>();
		ArrayList<String> listAF = new ArrayList<String>();
		elencoSport = am.getSport();
		
		for (Attivitafisica sport : elencoSport) {
			listAF.add(sport.getNome()+"    "+sport.getDescrizione());
		}
		String[] listAFString = (String[]) listAF.toArray((new String[0]));
		textAreaAttFis.setItems(listAFString);
	}

	/**
	 * Crea il contenuto per l'inserimento di un nuovo sport in groupInsAttFis	
	 *
	 */
	private void createGroupInsAttFis() {
		groupInsAttFis = new Group(sShellInsAttFis, SWT.NONE);
		groupInsAttFis.setBounds(new Rectangle(4, 8, 774, 167));
		groupInsAttFis.setText("Inserimento nuovo sport");
		labelNomeAttFis = new Label(groupInsAttFis, SWT.NONE);
		labelNomeAttFis.setBounds(new Rectangle(10, 30, 45, 25));
		labelNomeAttFis.setText("* Nome");
		textNomeAttFis = new Text(groupInsAttFis, SWT.BORDER);
		textNomeAttFis.setBounds(new Rectangle(10, 60, 220, 25));
		labelDescAttFis = new Label(groupInsAttFis, SWT.NONE);
		labelDescAttFis.setBounds(new Rectangle(255, 30, 81, 24));
		labelDescAttFis.setText("Descrizione");
		textAreaDescAttFis = new Text(groupInsAttFis, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescAttFis.setBounds(new Rectangle(256, 60, 483, 60));
		buttonAggiornaListaAttFis = new Button(groupInsAttFis, SWT.NONE);
		buttonAggiornaListaAttFis.setBounds(new Rectangle(4, 135, 764, 27));
		buttonAggiornaListaAttFis.setText("Aggiorna elenco attività fisiche");
		buttonAggiornaListaAttFis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				textAreaAttFis.add(textNomeAttFis.getText()+"    "+textAreaDescAttFis.getText());
				sShellInsAttFis.close();
			}
		});
	}

	/**
	 * Crea sShellInsAttFis per l'inserimento di un nuovo sport
	 */
	private void createSShellInsAttFis() {
		sShellInsAttFis = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellInsAttFis.setLayout(new GridLayout());
		sShellInsAttFis.setText("Inserimento Nuovo Sport");
		sShellInsAttFis.setSize(new Point(800, 215));
		createGroupInsAttFis();
		sShellInsAttFis.open();
	}

	/**
	 * This method initializes groupVisualizzazioneSport	
	 */
	private void createGroupVisualizzazioneSport() {
		groupVisualizzazioneSport = new Group(sShellProva, SWT.NONE);
		//groupVisualizzazioneSport.setLayout(new GridLayout());
		groupVisualizzazioneSport.setText("Attività fisica del paziente");
		groupVisualizzazioneSport.setBounds(new Rectangle(15, 324, 770, 226));
		
		
		//tableSport = new AnamnesiViewTableSport(groupVisualizzazioneSport, SWT.BORDER, pazSelHome);
		//tableSport.setLayout(new GridLayout(1, true));
		//tableSport.setBackground(Utils.getStandardWhiteColor());
		
		/*form = new HomePazienteForm(parent, SWT.BORDER);
		form.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		form.setLayout(new GridLayout(1, true));
		form.setBackground(Utils.getStandardWhiteColor());*/
	}	
	
	
	//MESSAGE BOX
	private void createSShellMessElimina() {
		sShellMessElimina = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setSize(new Point(377, 72));
	}
	
	private void createMessConfermaCanc(int indiceItemSel) {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.CANCEL| SWT.ICON_WARNING);
		messageBox.setMessage("Sei sicuro di voler eliminare questo elemento?");
		messageBox.setText("Conferma cancellazione");
		if (messageBox.open() == SWT.OK) {
			AnamnesiDAO an = new AnamnesiDAO();
			int idCanc = Integer.parseInt(tableTipoInt.getItem(indiceItemSel).getText());
			if(an.getInterventiListByIdTipoInt(idCanc).isEmpty()) {
				an.cancellaTipoIntervento(idCanc);
				tableTipoInt.remove(indiceItemSel);
			} else {
				sShellMessElimina.close();
				createMessElemCascade();
			}
		}
	}
	
	private void createMessNotSelElem() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_ERROR);
		messageBox.setMessage("Selezionare un elemento!");
		messageBox.setText("Errore: elemento non selezionato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	public void createMessElemCascade() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setMessage("Non è possibile cancellare questo elemento: è collegato ad altri!");
		messageBox.setText("Elemento referenziato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	public void createMessElemPresente() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setMessage("Non è possibile inserire questo elemento: associazione già presente!");
		messageBox.setText("Associazione presente");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	private void riempiTabellaTipoIntervento(Table table, ArrayList<Object> lista) {
		if (!lista.isEmpty()) {
			ArrayList<String> colonne = new ArrayList<String>();
			colonne.add("IdTipologiaIntervento");
			colonne.add("Nome");
			colonne.add("Descrizione");
			colonne.add("Localizzazione");
			for (String item : colonne) {
				TableColumn colonna = new TableColumn(table, SWT.CENTER);
				colonna.setWidth(item.length() * 15);
				colonna.setText(item);
			}
			table.setHeaderVisible(true);
			for (Object item : lista) {
				TableItem tblItem = new TableItem(table, SWT.NONE);
				Object[] valuesObj = new Object[5];
				String[] values = new String[5];
				int i = 0;
				for (String colonna : colonne) {
					valuesObj[i] = GenericBean.getProperty(colonna, item);
					System.out.println("Val: "+valuesObj[i]);
					if  (valuesObj[i] instanceof Tipologiaintervento) {  
						valuesObj[i] = ((Tipologiaintervento)valuesObj[i]).getIdTipologiaIntervento();
					} 
					i++;
				}
				for (int j = 0; j < valuesObj.length; j++) {
					if (valuesObj[j]!=null)
						values[j]=valuesObj[j].toString();
				}			
				tblItem.setText(values);
			}
		}
	}

	public static TableItem getItemInserimento() {
		return itemInserimento;
	}

	public static TableItem getItemModifica() {
		return itemModifica;
	}

}
