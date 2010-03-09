package studiodietetico;

import hibernate.Attivitafisica;
import hibernate.Farmacoassunto;
import hibernate.Intervento;
import hibernate.Intolleranzaallergia;
import hibernate.Malattia;
import hibernate.Paziente;
import hibernate.Tipologiadietaspeciale;
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
import org.eclipse.swt.widgets.Control;
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
import common.Utils;

import org.eclipse.swt.layout.GridLayout;

//import service.RegistraIntervento;
import org.eclipse.swt.widgets.Table;

public class AnamnesiShell {

	//MESSAGE BOX
	private Shell sShellMessElimina = null;  
	
	//INTERVENTI	
	private Label labelNomeInt = null;
	private Text textNomeInt = null;
	private Label labelDescrInt = null;
	private Text textAreaDescrInt = null;
	private Label labelLocalizzazione = null;
	private Text textAreaLocalizzazione = null;
	private Button buttonInsertNewInt = null;
	private Tipologiaintervento interventoSelez = null;  
	private Shell sShellInserimentoInterventi = null;  
	private Group groupInserimentoInt = null;
	private boolean attivaModifica = false;
	private Shell sShellDettagliInterventi;  
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
	private Label labelSostanzaAllVis;
	private Text textSostanzaAllVis;
	private Label labelFlagAllVis;
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
	private Shell sShellInserimentoAllergie;
	private Label labelFlagAllIns;
	private Button radioButtonIntolleranzaIns;
	private Button radioButtonAllergiaIns;
	private Label labelSostanzaAllIns;
	private Text textSostanzaAllIns;
	private Label labelAlimentoIns;
	private Text textAlimentoIns;
	private Label labelDerivatiIns;
	private Text textDerivatiIns;
	private Label labelGradoIns;
	private Text textGradoIns;
	private Label labelEffettiCollIns;
	private Text textEffettiCollIns;
	private Button buttonInserisciAllergie;
	private Button buttonChiudiAllIns;
		
	//ATTIVITA' FISICA
	private Label labelNomeAttFis = null;
	private Text textNomeAttFis = null;
	private Label labelDescAttFis = null;
	private Text textAreaDescAttFis = null;
	private Shell sShellInserimentoSport = null;  
	private Label labelDurataAttFis;
	private Text textDurataAttFis;
	private Label labelFreqSettAttFis;
	private Button buttonChiudiSport;
	private Button buttonInserisciSport;
	private Spinner spinnerNumAttFis;
	private Shell sShellDettagliSport;
	private Label labelNomeAttFisVis;
	private Text textNomeAttFisVis;
	private Label labelDescAttFisVis;
	private Text textAreaDescAttFisVis;
	private Label labelDurataAttFisVis;
	private Text textDurataAttFisVis;
	private Label labelFreqSettAttFisVis;
	private Spinner spinnerNumAttFisVis;
	private Button buttonChiudiSportVis;
	private Button buttonModificaSportVis;
	private Button buttonAppyModSportVis;

	//FARMACO ASSUNTO
	private Shell sShellInserimentoFarmaco;  
	private Label labelNomeFarmaco;
	private Text textNomeFarmaco;
	private Label labelDescFarmaco;
	private Text textAreaDescFarmaco;
	private Label labelDoseFarmaco;  
	private Text textDoseFarmaco;
	private Label labelFrequenzaFarmaco;
	private Text textFrequenzaFarmaco;
	private Label labelPrincipiFarmaco;
	private Text textPrincipiFarmaco;
	private Button buttonInserisciFarmaco;
	private Button buttonChiudiFarmaco;
	private Shell sShellDettagliFarmaco;
	private Label labelNomeFarmacoVis;
	private Text textNomeFarmacoVis;
	private Label labelDescFarmacoVis;
	private Text textAreaDescFarmacoVis;
	private Label labelDoseFarmacoVis;
	private Text textDoseFarmacoVis;
	private Label labelFrequenzaFarmacoVis;
	private Text textFrequenzaFarmacoVis;
	private Label labelPrincipiFarmacoVis;
	private Text textPrincipiFarmacoVis;
	private Button buttonModificaFarmacoVis;
	private Button buttonAppyModFarmacoVis;
	private Button buttonChiudiFarmacoVis;
	
	//MALATTIA
	private Shell sShellInserimentoMalattie;  //  @jve:decl-index=0:visual-constraint="14,1286"
	private Label labelElencoMalattie;
	private Table tableMalattie;
	private Button buttonAnnullaMalattia;
	private Button buttonInserMalattia;
	private Malattia malattiaSelez;
	private Tipologiadietaspeciale tipoDietaSpecSelez;
	private Table tableTipoDietaSpeciale;
	private Label labelElencoTipoDieta = null;
	private Button buttonInsertPatologia;
	private Button buttonEliminaPatologia;
	private Label labelNomePatIns;
	private Text textNomePatIns;
	private Label labelNomeTipDietaIns;
	private Text textNomeTipDietaIns;
	private Label labelDescTipDietaIns;
	private Text textDescTipDietaIns;
	private Button checkBoxMalattiaEreditaria = null;
	private Button buttonInsertTipoDieta;
	private Button buttonInsertNewTipoDieta;
	private Button buttonInsertNewPatologia;
	private Button buttonAnnullaNewPatologia;
	private Button buttonAnnullaNewTipoDieta;
	private Shell sShellInserimentoDietaSpeciale;  //  @jve:decl-index=0:visual-constraint="771,898"
	private Group gruppoInserimentoNewMalattia;
	private Shell sShellDettagliMalattie;  //  @jve:decl-index=0:visual-constraint="10,4403"
	private Label labelNomeMalattiaVis;
	private Text textNomeMalattiaVis;
	private Button checkBoxMalattiaEreditariaVis;
	private Text textNomeDietaSpecVis;
	private Text textDescrDietaSpecVis;
	private Button buttonModificaMalattie;
	private Button buttonAppyModMalattie;
	private Label labelNomeDietaVis;
	private Label labelDescDietaVis;
	private Table tableTipoDietaSpecialeVis;
	private Label labelElencoDieteVis;
	private Tipologiadietaspeciale tipoDietaSpecSelezVis;
	
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
						
						//Aggiornare la tabella degli interventi in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(0);
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
		sShellInserimentoInterventi = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM | SWT.MODELESS | SWT.PRIMARY_MODAL | SWT.SYSTEM_MODAL);
		sShellInserimentoInterventi.setText("Inserimento Nuovo Intervento Effettuato");
		sShellInserimentoInterventi.setSize(new Point(725, 739));
		
		final AnamnesiDAO an = new AnamnesiDAO(); 
		ArrayList<Object> listTipoInt = an.getListTipoInterventi();
		
		labelElencoTipoInt = new Label(sShellInserimentoInterventi, SWT.NONE);
		labelElencoTipoInt.setBounds(new Rectangle(23, 207, 341, 17));
		labelElencoTipoInt.setText("Selezionare la tipologia dell'intervento di interesse");
		
		tableTipoInt = new Table(sShellInserimentoInterventi, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableTipoInt.setHeaderVisible(true);
		tableTipoInt.setLinesVisible(true);
		tableTipoInt.setBounds(new Rectangle(22, 226, 639, 203));
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
							createMessConfermaCanc(tableTipoInt.getSelectionIndex(), "tableTipoInt");
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
							if(an.getInterventiListByPazIdTipoInt(interventoSelez.getIdTipologiaIntervento(), pazSelHome.getIdPaziente())==null) {
								an.registraIntervento(pazSelHome, interventoSelez, dataI, spinnerNumInterventi.getSelection());	
								sShellInserimentoInterventi.close();
							} else
								createMessElemPresente();
						}
						
						
						//Aggiorna la tabella in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(0);
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
		groupInserimentoInt.setText("Inserimento nuova tipologia di intervento");
		groupInserimentoInt.setBounds(new Rectangle(18, 514, 689, 180));
		groupInserimentoInt.setEnabled(false);
		
		labelNomeInt = new Label(groupInserimentoInt, SWT.NONE);
		labelNomeInt.setBounds(new Rectangle(10, 23, 190, 20));
		labelNomeInt.setText("* Nome della tipologia di intervento");
		textNomeInt = new Text(groupInserimentoInt, SWT.NONE);
		textNomeInt.setBounds(new Rectangle(209, 23, 450, 20));
		
		labelDescrInt = new Label(groupInserimentoInt, SWT.NONE);
		labelDescrInt.setBounds(new Rectangle(10, 50, 190, 20));
		labelDescrInt.setText("Descrizione");
		textAreaDescrInt = new Text(groupInserimentoInt, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescrInt.setBounds(new Rectangle(209, 50, 470, 40));
		
		labelLocalizzazione = new Label(groupInserimentoInt, SWT.NONE);
		labelLocalizzazione.setBounds(new Rectangle(10, 100, 190, 20));
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
						String flagAI = "";
						if(radioButtonAllergia.getSelection())
							flagAI = "all";
						else 
							flagAI = "int";
						AnamnesiDAO an = new AnamnesiDAO();
						an.modificaAllergie(intAll, flagAI, textSostanzaAllVis.getText(), textAlimentoVis.getText(), textDerivatiVis.getText(), textGradoVis.getText(), textEffettiCollVis.getText());
						
						sShellDettagliAllergie.close();
						
						//Aggiornare la tabella delle allergie in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(1);
					}
				});
	
		buttonChiudiAllIns = new Button(sShellDettagliAllergie, SWT.NONE);
		buttonChiudiAllIns.setBounds(new Rectangle(600, 350, 70, 25));
		buttonChiudiAllIns.setText("Chiudi");
		buttonChiudiAllIns.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliAllergie.close();
					}
				});
		sShellDettagliAllergie.open();
		
	}
	
	public void createSShellInserimentoAllergie() {
		sShellInserimentoAllergie = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellInserimentoAllergie.setText("Inserimento Intolleranza/Allergia Rilevata");
		sShellInserimentoAllergie.setSize(new Point(712, 434));
		
		labelFlagAllIns = new Label(sShellInserimentoAllergie, SWT.NONE);
		labelFlagAllIns.setBounds(new Rectangle(20, 20, 130, 20));
		labelFlagAllIns.setText("* Intolleranza / Allergia");
		radioButtonIntolleranzaIns = new Button(sShellInserimentoAllergie, SWT.RADIO);
		radioButtonIntolleranzaIns.setBounds(new Rectangle(170, 20, 100, 20));
		radioButtonIntolleranzaIns.setText("Intolleranza");
		radioButtonAllergiaIns = new Button(sShellInserimentoAllergie, SWT.RADIO);
		radioButtonAllergiaIns.setBounds(new Rectangle(280, 20, 100, 20));
		radioButtonAllergiaIns.setText("Allergia");
		
		labelSostanzaAllIns = new Label(sShellInserimentoAllergie, SWT.NONE);
		labelSostanzaAllIns.setBounds(new Rectangle(20, 60, 130, 20));
		labelSostanzaAllIns.setText("* Sostanza");
		textSostanzaAllIns = new Text(sShellInserimentoAllergie, SWT.NONE);
		textSostanzaAllIns.setBounds(new Rectangle(170, 60, 500, 20));
		
		labelAlimentoIns = new Label(sShellInserimentoAllergie, SWT.NONE);
		labelAlimentoIns.setBounds(new Rectangle(20, 100, 130, 20));
		labelAlimentoIns.setText("* Alimento principale");
		textAlimentoIns = new Text(sShellInserimentoAllergie, SWT.NONE);
		textAlimentoIns.setBounds(new Rectangle(170, 100, 500, 20));
		
		labelDerivatiIns = new Label(sShellInserimentoAllergie, SWT.NONE);
		labelDerivatiIns.setBounds(new Rectangle(20, 140, 130, 20));
		labelDerivatiIns.setText("Derivati");
		textDerivatiIns = new Text(sShellInserimentoAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textDerivatiIns.setBounds(new Rectangle(170, 140, 500, 60));
		
		labelGradoIns = new Label(sShellInserimentoAllergie, SWT.NONE);
		labelGradoIns.setBounds(new Rectangle(20, 220, 130, 20));
		labelGradoIns.setText("Grado");
		textGradoIns = new Text(sShellInserimentoAllergie, SWT.NONE);
		textGradoIns.setBounds(new Rectangle(170, 220, 500, 20));
		
		labelEffettiCollIns = new Label(sShellInserimentoAllergie, SWT.NONE);
		labelEffettiCollIns.setBounds(new Rectangle(20, 260, 130, 20));
		labelEffettiCollIns.setText("Effetti Collaterali");
		textEffettiCollIns = new Text(sShellInserimentoAllergie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textEffettiCollIns.setBounds(new Rectangle(170, 260, 500, 60));
		
		buttonInserisciAllergie = new Button(sShellInserimentoAllergie, SWT.NONE);
		buttonInserisciAllergie.setBounds(new Rectangle(520, 350, 70, 25));
		buttonInserisciAllergie.setText("Inserisci");
		buttonInserisciAllergie.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Paziente pazSel = AnamnesiTTableView.getPazienteSel();
						String flag = "";
						if (radioButtonIntolleranzaIns.getSelection())
							flag = "int";
						else
							flag = "all";
						AnamnesiDAO an = new AnamnesiDAO();
						an.registraAllergie(flag, textSostanzaAllIns.getText(), textAlimentoIns.getText(), 
								textDerivatiIns.getText(), textGradoIns.getText(), textEffettiCollIns.getText(), pazSel);
						
						sShellInserimentoAllergie.close();
						
						//Aggiornare la table di AnamnesiTableView con il nuovo intervento
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");	
						AnamnesiTTableView.selectTab(1);
					}
				});
		
		buttonChiudiAll = new Button(sShellInserimentoAllergie, SWT.NONE);
		buttonChiudiAll.setBounds(new Rectangle(600, 350, 70, 25));
		buttonChiudiAll.setText("Chiudi");
		buttonChiudiAll.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellInserimentoAllergie.close();
					}
				});
		sShellInserimentoAllergie.open();
		
	}
	
	
	
//-----------------------------------------ATTIVITA' FISICA-------------------------------------------------------------	
	public void createSShellInserimentoSport() {
		sShellInserimentoSport = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellInserimentoSport.setText("Inserimento Nuovo Sport Praticato");
		sShellInserimentoSport.setSize(new Point(727, 313));
		
		labelNomeAttFis = new Label(sShellInserimentoSport, SWT.NONE);
		labelNomeAttFis.setBounds(new Rectangle(20, 20, 130, 20));
		labelNomeAttFis.setText("* Nome");
		textNomeAttFis = new Text(sShellInserimentoSport, SWT.BORDER);
		textNomeAttFis.setBounds(new Rectangle(170, 20, 500, 20));
		
		labelDescAttFis = new Label(sShellInserimentoSport, SWT.NONE);
		labelDescAttFis.setBounds(new Rectangle(20, 60, 130, 20));
		labelDescAttFis.setText("Descrizione");
		textAreaDescAttFis = new Text(sShellInserimentoSport, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescAttFis.setBounds(new Rectangle(170, 60, 500, 60));
		
		labelDurataAttFis = new Label(sShellInserimentoSport, SWT.NONE);
		labelDurataAttFis.setBounds(new Rectangle(20, 140, 130, 20));
		labelDurataAttFis.setText("Durata");
		textDurataAttFis = new Text(sShellInserimentoSport, SWT.BORDER);
		textDurataAttFis.setBounds(new Rectangle(170, 140, 500, 20));
		
		labelFreqSettAttFis = new Label(sShellInserimentoSport, SWT.NONE);
		labelFreqSettAttFis.setBounds(new Rectangle(20, 180, 130, 20));
		labelFreqSettAttFis.setText("* Frequenza Settimanale");
		spinnerNumAttFis = new Spinner(sShellInserimentoSport, SWT.NONE);
		spinnerNumAttFis.setBounds(new Rectangle(170, 180, 68, 20));
		spinnerNumAttFis.setMinimum(1);
		
		buttonInserisciSport = new Button(sShellInserimentoSport, SWT.NONE);
		buttonInserisciSport.setBounds(new Rectangle(520, 230, 70, 25));
		buttonInserisciSport.setText("Inserisci");
		buttonInserisciSport.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Paziente pazSel = AnamnesiTTableView.getPazienteSel();
						AnamnesiDAO an = new AnamnesiDAO();
						an.registraSport(textNomeAttFis.getText(), textAreaDescAttFis.getText(), textDurataAttFis.getText(), spinnerNumAttFis.getSelection(), pazSel);
						
						sShellInserimentoSport.close();
						
						//Aggiornare la table di AnamnesiTableView con il nuovo sport
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(2);
					}
				});
		
		buttonChiudiSport = new Button(sShellInserimentoSport, SWT.NONE);
		buttonChiudiSport.setBounds(new Rectangle(600, 230, 70, 25));
		buttonChiudiSport.setText("Chiudi");
		buttonChiudiSport.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellInserimentoSport.close();
					}
				});
		
		sShellInserimentoSport.open();		
	}
	
	public void createSShellDettagliSport(final TableItem rigaTableClick) {
		String idSport = rigaTableClick.getText(0);
		AnamnesiDAO an = new AnamnesiDAO();
		final Attivitafisica sport = an.getSportById(idSport);
		
		final String nome = rigaTableClick.getText(2);
		final String descrizione = rigaTableClick.getText(3);
		final String durata = rigaTableClick.getText(4);
		final int freqSett = Integer.parseInt(rigaTableClick.getText(5));
		
		sShellDettagliSport = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellDettagliSport.setText("Dettagli Sport Selezionato");
		sShellDettagliSport.setSize(new Point(727, 313));
		
		labelNomeAttFisVis = new Label(sShellDettagliSport, SWT.NONE);
		labelNomeAttFisVis.setBounds(new Rectangle(20, 20, 130, 20));
		labelNomeAttFisVis.setText("* Nome");
		textNomeAttFisVis = new Text(sShellDettagliSport, SWT.BORDER);
		textNomeAttFisVis.setBounds(new Rectangle(170, 20, 500, 20));
		textNomeAttFisVis.setEnabled(false);
		textNomeAttFisVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textNomeAttFisVis.setText(nome);
		
		labelDescAttFisVis = new Label(sShellDettagliSport, SWT.NONE);
		labelDescAttFisVis.setBounds(new Rectangle(20, 60, 130, 20));
		labelDescAttFisVis.setText("Descrizione");
		textAreaDescAttFisVis = new Text(sShellDettagliSport, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescAttFisVis.setBounds(new Rectangle(170, 60, 500, 60));
		textAreaDescAttFisVis.setEnabled(false);
		textAreaDescAttFisVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textAreaDescAttFisVis.setText(descrizione);
		
		labelDurataAttFisVis = new Label(sShellDettagliSport, SWT.NONE);
		labelDurataAttFisVis.setBounds(new Rectangle(20, 140, 130, 20));
		labelDurataAttFisVis.setText("Durata");
		textDurataAttFisVis = new Text(sShellDettagliSport, SWT.BORDER);
		textDurataAttFisVis.setBounds(new Rectangle(170, 140, 500, 20));
		textDurataAttFisVis.setEnabled(false);
		textDurataAttFisVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textDurataAttFisVis.setText(durata);
		
		labelFreqSettAttFisVis = new Label(sShellDettagliSport, SWT.NONE);
		labelFreqSettAttFisVis.setBounds(new Rectangle(20, 180, 130, 20));
		labelFreqSettAttFisVis.setText("* Frequenza Settimanale");
		spinnerNumAttFisVis = new Spinner(sShellDettagliSport, SWT.NONE);
		spinnerNumAttFisVis.setBounds(new Rectangle(170, 180, 68, 20));
		spinnerNumAttFisVis.setEnabled(false);
		spinnerNumAttFisVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		spinnerNumAttFisVis.setSelection(freqSett);
		
		buttonModificaSportVis = new Button(sShellDettagliSport, SWT.NONE);
		buttonModificaSportVis.setBounds(new Rectangle(400, 230, 70, 25));
		buttonModificaSportVis.setText("Modifica");
		buttonModificaSportVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textNomeAttFisVis.setEnabled(true);
						textNomeAttFisVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textAreaDescAttFisVis.setEnabled(true);
						textAreaDescAttFisVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textDurataAttFisVis.setEnabled(true);
						textDurataAttFisVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						spinnerNumAttFisVis.setEnabled(true);
						spinnerNumAttFisVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						
						buttonAppyModSportVis.setEnabled(true);
						buttonModificaSportVis.setEnabled(false);
					}
				});
		
		buttonAppyModSportVis = new Button(sShellDettagliSport, SWT.NONE);
		buttonAppyModSportVis.setBounds(new Rectangle(480, 230, 110, 25));
		buttonAppyModSportVis.setText("Applica Modifiche");
		buttonAppyModSportVis.setEnabled(false);
		buttonAppyModSportVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						AnamnesiDAO an = new AnamnesiDAO();
						an.modificaSport(sport, textNomeAttFisVis.getText(), textAreaDescAttFisVis.getText(), textDurataAttFisVis.getText(), spinnerNumAttFisVis.getSelection());
						
						sShellDettagliSport.close();
						
						//Aggiornare la tabella delle allergie in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(2);
					}
				});		
		
		buttonChiudiSportVis = new Button(sShellDettagliSport, SWT.NONE);
		buttonChiudiSportVis.setBounds(new Rectangle(600, 230, 70, 25));
		buttonChiudiSportVis.setText("Chiudi");
		buttonChiudiSportVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliSport.close();
					}
				});
		
		sShellDettagliSport.open();
	}
	
	
	
//-----------------------------------------FARMACO ASSUNTO-------------------------------------------------------------	
	public void createSShellInserimentoFarmaco() {
		sShellInserimentoFarmaco = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellInserimentoFarmaco.setText("Inserimento Nuovo Farmaco Assunto");
		sShellInserimentoFarmaco.setSize(new Point(727, 392));
		
		labelNomeFarmaco = new Label(sShellInserimentoFarmaco, SWT.NONE);
		labelNomeFarmaco.setBounds(new Rectangle(20, 20, 130, 20));
		labelNomeFarmaco.setText("* Nome");
		textNomeFarmaco = new Text(sShellInserimentoFarmaco, SWT.BORDER);
		textNomeFarmaco.setBounds(new Rectangle(170, 20, 500, 20));
		
		labelDescFarmaco = new Label(sShellInserimentoFarmaco, SWT.NONE);
		labelDescFarmaco.setBounds(new Rectangle(20, 60, 130, 20));
		labelDescFarmaco.setText("Descrizione");
		textAreaDescFarmaco = new Text(sShellInserimentoFarmaco, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescFarmaco.setBounds(new Rectangle(170, 60, 500, 60));
		
		labelDoseFarmaco = new Label(sShellInserimentoFarmaco, SWT.NONE);
		labelDoseFarmaco.setBounds(new Rectangle(20, 140, 130, 20));
		labelDoseFarmaco.setText("* Dose");
		textDoseFarmaco = new Text(sShellInserimentoFarmaco, SWT.BORDER);
		textDoseFarmaco.setBounds(new Rectangle(170, 140, 500, 20));
		
		labelFrequenzaFarmaco = new Label(sShellInserimentoFarmaco, SWT.NONE);
		labelFrequenzaFarmaco.setBounds(new Rectangle(20, 180, 130, 20));
		labelFrequenzaFarmaco.setText("* Frequenza");
		textFrequenzaFarmaco = new Text(sShellInserimentoFarmaco, SWT.BORDER);
		textFrequenzaFarmaco.setBounds(new Rectangle(170, 180, 500, 20));
		
		labelPrincipiFarmaco = new Label(sShellInserimentoFarmaco, SWT.NONE);
		labelPrincipiFarmaco.setBounds(new Rectangle(20, 220, 130, 20));
		labelPrincipiFarmaco.setText("Principi Attivi");
		textPrincipiFarmaco = new Text(sShellInserimentoFarmaco, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textPrincipiFarmaco.setBounds(new Rectangle(170, 220, 500, 60));
		
		buttonInserisciFarmaco = new Button(sShellInserimentoFarmaco, SWT.NONE);
		buttonInserisciFarmaco.setBounds(new Rectangle(520, 300, 70, 25));
		buttonInserisciFarmaco.setText("Inserisci");
		buttonInserisciFarmaco.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Paziente pazSel = AnamnesiTTableView.getPazienteSel();
						AnamnesiDAO an = new AnamnesiDAO();
						an.registraFarmaco(textNomeFarmaco.getText(), textAreaDescFarmaco.getText(), textDoseFarmaco.getText(), textFrequenzaFarmaco.getText(), textPrincipiFarmaco.getText(), pazSel);
						
						sShellInserimentoFarmaco.close();
						
						//Aggiornare la table di AnamnesiTableView con il nuovo farmaco
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(3);
					}
				});
		
		buttonChiudiFarmaco = new Button(sShellInserimentoFarmaco, SWT.NONE);
		buttonChiudiFarmaco.setBounds(new Rectangle(600, 300, 70, 25));
		buttonChiudiFarmaco.setText("Chiudi");
		buttonChiudiFarmaco.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellInserimentoFarmaco.close();
					}
				});
		
		sShellInserimentoFarmaco.open();		
	}

	public void createSShellDettagliFarmaco(final TableItem rigaTableClick) {
		String idFarmaco = rigaTableClick.getText(0);
		AnamnesiDAO an = new AnamnesiDAO();
		final Farmacoassunto farmaco = an.getFarmacoById(idFarmaco);
		
		final String nome = rigaTableClick.getText(2);
		final String descrizione = rigaTableClick.getText(3);
		final String dose = rigaTableClick.getText(4);
		final String frequenza = rigaTableClick.getText(5);
		final String principi = rigaTableClick.getText(6);
		
		sShellDettagliFarmaco = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellDettagliFarmaco.setText("Dettagli Farmaco Selezionato");
		sShellDettagliFarmaco.setSize(new Point(727, 392));
		
		labelNomeFarmacoVis = new Label(sShellDettagliFarmaco, SWT.NONE);
		labelNomeFarmacoVis.setBounds(new Rectangle(20, 20, 130, 20));
		labelNomeFarmacoVis.setText("* Nome");
		textNomeFarmacoVis = new Text(sShellDettagliFarmaco, SWT.BORDER);
		textNomeFarmacoVis.setBounds(new Rectangle(170, 20, 500, 20));
		textNomeFarmacoVis.setEnabled(false);
		textNomeFarmacoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textNomeFarmacoVis.setText(nome);
		
		labelDescFarmacoVis = new Label(sShellDettagliFarmaco, SWT.NONE);
		labelDescFarmacoVis.setBounds(new Rectangle(20, 60, 130, 20));
		labelDescFarmacoVis.setText("Descrizione");
		textAreaDescFarmacoVis = new Text(sShellDettagliFarmaco, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaDescFarmacoVis.setBounds(new Rectangle(170, 60, 500, 60));
		textAreaDescFarmacoVis.setEnabled(false);
		textAreaDescFarmacoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textAreaDescFarmacoVis.setText(descrizione);
		
		labelDoseFarmacoVis = new Label(sShellDettagliFarmaco, SWT.NONE);
		labelDoseFarmacoVis.setBounds(new Rectangle(20, 140, 130, 20));
		labelDoseFarmacoVis.setText("* Dose");
		textDoseFarmacoVis = new Text(sShellDettagliFarmaco, SWT.BORDER);
		textDoseFarmacoVis.setBounds(new Rectangle(170, 140, 500, 20));
		textDoseFarmacoVis.setEnabled(false);
		textDoseFarmacoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textDoseFarmacoVis.setText(dose);
		
		labelFrequenzaFarmacoVis = new Label(sShellDettagliFarmaco, SWT.NONE);
		labelFrequenzaFarmacoVis.setBounds(new Rectangle(20, 180, 130, 20));
		labelFrequenzaFarmacoVis.setText("* Frequenza");
		textFrequenzaFarmacoVis = new Text(sShellDettagliFarmaco, SWT.BORDER);
		textFrequenzaFarmacoVis.setBounds(new Rectangle(170, 180, 500, 20));
		textFrequenzaFarmacoVis.setEnabled(false);
		textFrequenzaFarmacoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textFrequenzaFarmacoVis.setText(frequenza);
		
		labelPrincipiFarmacoVis = new Label(sShellDettagliFarmaco, SWT.NONE);
		labelPrincipiFarmacoVis.setBounds(new Rectangle(20, 220, 130, 20));
		labelPrincipiFarmacoVis.setText("Principi Attivi");
		textPrincipiFarmacoVis = new Text(sShellDettagliFarmaco, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textPrincipiFarmacoVis.setBounds(new Rectangle(170, 220, 500, 60));
		textPrincipiFarmacoVis.setEnabled(false);
		textPrincipiFarmacoVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textPrincipiFarmacoVis.setText(principi);
		
		buttonModificaFarmacoVis = new Button(sShellDettagliFarmaco, SWT.NONE);
		buttonModificaFarmacoVis.setBounds(new Rectangle(400, 300, 70, 25));
		buttonModificaFarmacoVis.setText("Modifica");
		buttonModificaFarmacoVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textNomeFarmacoVis.setEnabled(true);
						textNomeFarmacoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textAreaDescFarmacoVis.setEnabled(true);
						textAreaDescFarmacoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textDoseFarmacoVis.setEnabled(true);
						textDoseFarmacoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textFrequenzaFarmacoVis.setEnabled(true);
						textFrequenzaFarmacoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						textPrincipiFarmacoVis.setEnabled(true);
						textPrincipiFarmacoVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));

						buttonAppyModFarmacoVis.setEnabled(true);
						buttonModificaFarmacoVis.setEnabled(false);
					}
				});
		
		buttonAppyModFarmacoVis = new Button(sShellDettagliFarmaco, SWT.NONE);
		buttonAppyModFarmacoVis.setBounds(new Rectangle(480, 300, 110, 25));
		buttonAppyModFarmacoVis.setText("Applica Modifiche");
		buttonAppyModFarmacoVis.setEnabled(false);
		buttonAppyModFarmacoVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						AnamnesiDAO an = new AnamnesiDAO();
						an.modificaFarmaco(farmaco, textNomeFarmacoVis.getText(), textAreaDescFarmacoVis.getText(), textDoseFarmacoVis.getText(), textFrequenzaFarmacoVis.getText(), textPrincipiFarmacoVis.getText());
						
						sShellDettagliFarmaco.close();
						
						//Aggiornare la tabella delle allergie in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(3);
					}
				});		
		
		buttonChiudiFarmacoVis = new Button(sShellDettagliFarmaco, SWT.NONE);
		buttonChiudiFarmacoVis.setBounds(new Rectangle(600, 300, 70, 25));
		buttonChiudiFarmacoVis.setText("Chiudi");
		buttonChiudiFarmacoVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliFarmaco.close();
					}
				});
		
		sShellDettagliFarmaco.open();
	}
	
	
	//--------------------------------------MALATTIE-------------------------------------------------------------
	public void createSShellInserimentoMalattia() {
		sShellInserimentoMalattie = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellInserimentoMalattie.setText("Inserimento Nuova Malattia Diagnosticata");
		sShellInserimentoMalattie.setSize(new Point(696, 769));
		
		final AnamnesiDAO an = new AnamnesiDAO(); 
		final ArrayList<Object> listMalattie = an.getListMalattie();
		
		//TABELLA MALATTIE
		labelElencoMalattie = new Label(sShellInserimentoMalattie, SWT.NONE);
		labelElencoMalattie.setBounds(new Rectangle(20, 25, 199, 20));
		labelElencoMalattie.setText("Selezionare la patologia di interesse");
		
		tableMalattie = new Table(sShellInserimentoMalattie, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableMalattie.setHeaderVisible(true);
		tableMalattie.setLinesVisible(true);
		tableMalattie.setBounds(new Rectangle(20, 50, 625, 177));
		tableMalattie.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						buttonEliminaPatologia.setEnabled(true);
						malattiaSelez = an.getMalattiaById(tableMalattie.getSelection()[0].getText(0));
						
					}
				});
		
		//Adatta la tabella, nasconde la prima colonna e applica l'ordinamento alle colonne
		riempiTabellaMalattie(tableMalattie, listMalattie);
		for (TableColumn colonna : tableMalattie.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		tableMalattie.getColumn(0).setWidth(0);
		tableMalattie.getColumn(1).setWidth(0);
		
		//Aggiunge le colonna che visualizzano il nome e la descrizione della tipologia di dieta speciale
		TableColumn colonnaNome = new TableColumn(tableMalattie, SWT.CENTER);
		colonnaNome.setText("Nome Dieta Speciale");
		TableColumn colonnaDesc = new TableColumn(tableMalattie, SWT.CENTER);
		colonnaDesc.setText("Descrizione Dieta Speciale");
		String nome = "", descriz = "";
		TableItem itemSel = null;
		//System.out.println("size list"+listMalattie.size());
		for (int j = 0; j < listMalattie.size(); j++) {
			if (((Malattia)listMalattie.get(j)).getTipologiadietaspeciale()!=null) {
				nome = ((Malattia)listMalattie.get(j)).getTipologiadietaspeciale().getNome();
				descriz = ((Malattia)listMalattie.get(j)).getTipologiadietaspeciale().getDescrizione();
				itemSel = tableMalattie.getItem(j);
				itemSel.setText(tableMalattie.getColumnCount()-2, nome);
				itemSel.setText(tableMalattie.getColumnCount()-1, descriz);
			}
		}
		
		colonnaNome.pack();
		colonnaNome.setResizable(false);
		colonnaDesc.pack();
		colonnaDesc.setResizable(false);
		
		TableForm.ordinamentoStringhe(tableMalattie, 2);
		TableForm.ordinamentoStringhe(tableMalattie, 2);
		TableForm.ordinamentoStringhe(tableMalattie, 4);
		TableForm.ordinamentoStringhe(tableMalattie, 5);
		
		buttonInsertPatologia = new Button(sShellInserimentoMalattie, SWT.NONE);
		buttonInsertPatologia.setBounds(new Rectangle(20, 232, 90, 20));
		buttonInsertPatologia.setText("Inserisci Nuova");
		buttonInsertPatologia.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						gruppoInserimentoNewMalattia.setEnabled(true);
						labelNomePatIns.setEnabled(true);
						textNomePatIns.setEnabled(true);
						checkBoxMalattiaEreditaria.setEnabled(true);
						labelElencoTipoDieta.setEnabled(true);
						tableTipoDietaSpeciale.setEnabled(true);
						buttonInsertTipoDieta.setEnabled(true);
						buttonInsertNewPatologia.setEnabled(true);
						buttonAnnullaNewPatologia.setEnabled(true);
						
						labelElencoMalattie.setEnabled(false);
						tableMalattie.setEnabled(false);
						buttonInsertPatologia.setEnabled(false);
						buttonInserMalattia.setEnabled(false);
						buttonAnnullaMalattia.setEnabled(false);
						buttonEliminaPatologia.setEnabled(false);
						tableMalattie.deselectAll();
						
					}
				});
		
		buttonEliminaPatologia = new Button(sShellInserimentoMalattie, SWT.NONE);
		buttonEliminaPatologia.setBounds(new Rectangle(110, 232, 60, 20));
		buttonEliminaPatologia.setText("Elimina");
		buttonEliminaPatologia.setEnabled(false);
		buttonEliminaPatologia.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if(tableMalattie.getSelectionCount()>0)
							createMessConfermaCanc(tableMalattie.getSelectionIndex(), "tableMalattie");
						else
							createMessNotSelElem();
					}
				});
		
		buttonInserMalattia = new Button(sShellInserimentoMalattie, SWT.NONE);
		buttonInserMalattia.setBounds(new Rectangle(467, 266, 89, 28));
		buttonInserMalattia.setText("Conferma");
		buttonInserMalattia.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						AnamnesiDAO an = new AnamnesiDAO();
						Paziente pazSelHome = AnamnesiTTableView.getPazienteSel();
						System.out.println("Associa-paz: "+pazSelHome.getIdPaziente()+", mal: "+malattiaSelez.getIdMalattia());
						if(malattiaSelez == null) {
							createMessNotSelElem();
						} else {
							if(an.esisteMalattiaPerPaziente(pazSelHome.getIdPaziente(), malattiaSelez.getIdMalattia())) {
								createMessElemPresente();
							} else {
								System.out.println("else associa");
								an.associaMalattiaPaziente(pazSelHome, malattiaSelez);	
								sShellInserimentoMalattie.close();
							}
						}
						
						//Aggiorna la tabella in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(4);
					}
				});
		
		buttonAnnullaMalattia = new Button(sShellInserimentoMalattie, SWT.NONE);
		buttonAnnullaMalattia.setBounds(new Rectangle(564, 268, 84, 26));
		buttonAnnullaMalattia.setText("Annulla");
		buttonAnnullaMalattia.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellInserimentoMalattie.close();
						//Aggiorna la tabella in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(4);
					}
				});
		
		//Inserimento nuova malattia
		gruppoInserimentoNewMalattia = new Group(sShellInserimentoMalattie, SWT.NONE);
		gruppoInserimentoNewMalattia.setBounds(new Rectangle(40, 320, 574, 404));
		gruppoInserimentoNewMalattia.setText("Inserimento nuova patologia");
		gruppoInserimentoNewMalattia.setEnabled(false);
		
		labelNomePatIns = new Label(gruppoInserimentoNewMalattia, SWT.NONE);
		labelNomePatIns.setBounds(new Rectangle(20, 40, 145, 20));
		labelNomePatIns.setText("Nome della patologia");
		labelNomePatIns.setEnabled(false);
		textNomePatIns = new Text(gruppoInserimentoNewMalattia, SWT.NONE);
		textNomePatIns.setBounds(new Rectangle(180, 40, 300, 20));
		//textNomePatIns.setEnabled(false);
		//textNomePatIns.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		
		checkBoxMalattiaEreditaria = new Button(gruppoInserimentoNewMalattia, SWT.CHECK);
		checkBoxMalattiaEreditaria.setBounds(new Rectangle(20, 80, 237, 20));
		checkBoxMalattiaEreditaria.setText(" Indicare se si tratta di malattia ereditaria");
		checkBoxMalattiaEreditaria.setEnabled(false);
		
		//TABELLA TIPOLOGIA DIETA SPECIALE
		labelElencoTipoDieta = new Label(gruppoInserimentoNewMalattia, SWT.NONE);
		labelElencoTipoDieta.setBounds(new Rectangle(20, 120, 406, 20));
		labelElencoTipoDieta.setText("Selezionare l'eventuale tipologia di Dieta speciale da associare alla patologia");
		labelElencoTipoDieta.setEnabled(false);
		
		tableTipoDietaSpeciale = new Table(gruppoInserimentoNewMalattia, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableTipoDietaSpeciale.setHeaderVisible(true);
		tableTipoDietaSpeciale.setLinesVisible(true);
		tableTipoDietaSpeciale.setBounds(new Rectangle(20, 150, 420, 150));
		tableTipoDietaSpeciale.setEnabled(false);
		tableTipoDietaSpeciale.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						tipoDietaSpecSelez = an.getTipoDietaSpecById(tableTipoDietaSpeciale.getSelection()[0].getText(0));
					}
				});
		
		//Adatta la tabella, nasconde la prima colonna e applica l'ordinamento alle colonne
		AnamnesiDAO ad = new AnamnesiDAO(); 
		ArrayList<Object> listTipoDietaSpeciale = ad.getListTipoDietaSpeciale();
		riempiTabellaTipoDietaSpeciale(tableTipoDietaSpeciale, listTipoDietaSpeciale);
		for (TableColumn colonna : tableTipoDietaSpeciale.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		tableTipoDietaSpeciale.getColumn(0).setWidth(0);
		//tableTipoDietaSpeciale.getColumn(1).setWidth(0);
		TableForm.ordinamentoStringhe(tableTipoDietaSpeciale, 1);
		TableForm.ordinamentoStringhe(tableTipoDietaSpeciale, 2);
		
		buttonInsertTipoDieta = new Button(gruppoInserimentoNewMalattia, SWT.NONE);
		buttonInsertTipoDieta.setBounds(new Rectangle(20, 310, 167, 20));
		buttonInsertTipoDieta.setText("Inserisci Nuova Dieta Speciale");
		buttonInsertTipoDieta.setEnabled(false);
		buttonInsertTipoDieta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						createSShellInserimentoDietaSpeciale();
					}
				});
		
		buttonInsertNewPatologia = new Button(gruppoInserimentoNewMalattia, SWT.NONE);
		buttonInsertNewPatologia.setBounds(new Rectangle(290, 360, 90, 28));
		buttonInsertNewPatologia.setText("Conferma");
		buttonInsertNewPatologia.setEnabled(false);
		buttonInsertNewPatologia.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
						if (textNomePatIns.getText()!=null) {
							AnamnesiDAO ad = new AnamnesiDAO();
							ad.registraMalattia(textNomePatIns.getText(), checkBoxMalattiaEreditaria.getSelection(), tipoDietaSpecSelez);
							
							//Aggiorna la tabella delle malattie
							tableMalattie.removeAll();
							int k = 0;
							while (k<tableMalattie.getColumnCount()) {
								tableMalattie.getColumn(k).dispose();
							}
							ArrayList<Object> listMalattie = an.getListMalattie();
							riempiTabellaMalattie(tableMalattie, listMalattie);
							for (TableColumn colonna : tableMalattie.getColumns()) {
								colonna.pack();
								colonna.setResizable(false);
							}
							tableMalattie.getColumn(0).setWidth(0);
							tableMalattie.getColumn(1).setWidth(0);
							
							//Aggiunge le colonna che visualizzano il nome e la descrizione della tipologia di dieta speciale
							TableColumn colonnaNome = new TableColumn(tableMalattie, SWT.CENTER);
							colonnaNome.setText("Nome Dieta Speciale");
							TableColumn colonnaDesc = new TableColumn(tableMalattie, SWT.CENTER);
							colonnaDesc.setText("Descrizione Dieta Speciale");
							String nome = "", descriz = "";
							TableItem itemSel = null;
							System.out.println("size list"+listMalattie.size());
							for (int j = 0; j < listMalattie.size(); j++) {
								if (((Malattia)listMalattie.get(j)).getTipologiadietaspeciale()!=null) {
									nome = ((Malattia)listMalattie.get(j)).getTipologiadietaspeciale().getNome();
									descriz = ((Malattia)listMalattie.get(j)).getTipologiadietaspeciale().getDescrizione();
									itemSel = tableMalattie.getItem(j);
									itemSel.setText(tableMalattie.getColumnCount()-2, nome);
									itemSel.setText(tableMalattie.getColumnCount()-1, descriz);
								}
							}
							
							colonnaNome.pack();
							colonnaNome.setResizable(false);
							colonnaDesc.pack();
							colonnaDesc.setResizable(false);
							
							//TableForm.ordinamentoStringhe(tableMalattie, 2);
							TableForm.ordinamentoStringhe(tableMalattie, 2);
							TableForm.ordinamentoStringhe(tableMalattie, 4);
							TableForm.ordinamentoStringhe(tableMalattie, 5);
						}
						
						gruppoInserimentoNewMalattia.setEnabled(false);
						labelNomePatIns.setEnabled(false);
						textNomePatIns.setEnabled(false);
						checkBoxMalattiaEreditaria.setEnabled(false);
						labelElencoTipoDieta.setEnabled(false);
						tableTipoDietaSpeciale.setEnabled(false);
						buttonInsertTipoDieta.setEnabled(false);
						buttonInsertNewPatologia.setEnabled(false);
						buttonAnnullaNewPatologia.setEnabled(false);
						
						
						labelElencoMalattie.setEnabled(true);
						tableMalattie.setEnabled(true);
						buttonInsertPatologia.setEnabled(true);
						buttonInserMalattia.setEnabled(true);
						buttonAnnullaMalattia.setEnabled(true);
					}
				});
		
		buttonAnnullaNewPatologia = new Button(gruppoInserimentoNewMalattia, SWT.NONE);
		buttonAnnullaNewPatologia.setBounds(new Rectangle(400, 360, 90, 28));
		buttonAnnullaNewPatologia.setText("Annulla");
		buttonAnnullaNewPatologia.setEnabled(false);
		buttonAnnullaNewPatologia.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
						gruppoInserimentoNewMalattia.setEnabled(false);
						labelNomePatIns.setEnabled(false);
						textNomePatIns.setEnabled(false);
						checkBoxMalattiaEreditaria.setEnabled(false);
						labelElencoTipoDieta.setEnabled(false);
						tableTipoDietaSpeciale.setEnabled(false);
						buttonInsertTipoDieta.setEnabled(false);
						buttonInsertNewPatologia.setEnabled(false);
						buttonAnnullaNewPatologia.setEnabled(false);
						
						labelElencoMalattie.setEnabled(true);
						tableMalattie.setEnabled(true);
						buttonInsertPatologia.setEnabled(true);
						buttonInserMalattia.setEnabled(true);
						buttonAnnullaMalattia.setEnabled(true);
					}
				});
				
		
		
		sShellInserimentoMalattie.open();
	}	
	
	private void createSShellInserimentoDietaSpeciale() {
		sShellInserimentoDietaSpeciale = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellInserimentoDietaSpeciale.setLayout(new GridLayout());
		sShellInserimentoDietaSpeciale.setSize(new Point(630, 242));
		sShellInserimentoDietaSpeciale.setText("Inserimento dieta speciale");
		
		labelNomeTipDietaIns = new Label(sShellInserimentoDietaSpeciale, SWT.NONE);
		labelNomeTipDietaIns.setBounds(new Rectangle(20, 20, 145, 20));
		labelNomeTipDietaIns.setText("* Nome dieta speciale");
		textNomeTipDietaIns = new Text(sShellInserimentoDietaSpeciale, SWT.NONE);
		textNomeTipDietaIns.setBounds(new Rectangle(180, 20, 375, 20));
		
		labelDescTipDietaIns = new Label(sShellInserimentoDietaSpeciale, SWT.NONE);
		labelDescTipDietaIns.setBounds(new Rectangle(20, 60, 145, 20));
		labelDescTipDietaIns.setText("Descrizione dieta speciale");
		textDescTipDietaIns = new Text(sShellInserimentoDietaSpeciale, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textDescTipDietaIns.setBounds(new Rectangle(180, 60, 390, 60));
		
		buttonInsertNewTipoDieta = new Button(sShellInserimentoDietaSpeciale, SWT.NONE);
		buttonInsertNewTipoDieta.setBounds(new Rectangle(400, 150, 90, 28));
		buttonInsertNewTipoDieta.setText("Conferma");
		buttonInsertNewTipoDieta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
				if(textNomeTipDietaIns.getText()!="") {
					AnamnesiDAO ad = new AnamnesiDAO();
					ad.registraDietaSpec(textNomeTipDietaIns.getText(),textDescTipDietaIns.getText());

					tableTipoDietaSpeciale.removeAll();
					int k = 0;
					while (k<tableTipoDietaSpeciale.getColumnCount()) {
						tableTipoDietaSpeciale.getColumn(k).dispose();
					}
					//AnamnesiDAO ad = new AnamnesiDAO(); 
					ArrayList<Object> listTipoDietaSpeciale = ad.getListTipoDietaSpeciale();
					riempiTabellaTipoDietaSpeciale(tableTipoDietaSpeciale, listTipoDietaSpeciale);
					for (TableColumn colonna : tableTipoDietaSpeciale.getColumns()) {
						colonna.pack();
						colonna.setResizable(false);
					}
					tableTipoDietaSpeciale.getColumn(0).setWidth(0);
					//tableTipoDietaSpeciale.getColumn(1).setWidth(0);
					TableForm.ordinamentoStringhe(tableTipoDietaSpeciale, 1);
					TableForm.ordinamentoStringhe(tableTipoDietaSpeciale, 2);
				}
				sShellInserimentoDietaSpeciale.close();
			}
		});
		
		buttonAnnullaNewTipoDieta = new Button(sShellInserimentoDietaSpeciale, SWT.NONE);
		buttonAnnullaNewTipoDieta.setBounds(new Rectangle(500, 150, 90, 28));
		buttonAnnullaNewTipoDieta.setText("Annulla");
		buttonAnnullaNewTipoDieta.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
						sShellInserimentoDietaSpeciale.close();
					}
				});
		sShellInserimentoDietaSpeciale.open();
	}
		
	public void createSShellDettagliMalattia(final TableItem rigaTableClick) {
		final int idMalattia = Integer.parseInt(rigaTableClick.getText(0));
		final String idTipoDietaSpec = rigaTableClick.getText(1);
		AnamnesiDAO an = new AnamnesiDAO();
		final String nomeM = rigaTableClick.getText(2);
		final String malattiaEredit = rigaTableClick.getText(3);
		final String nomeDietaSpec = an.getTipoDietaSpecById(idTipoDietaSpec).getNome();
		final String descDietaSpec = an.getTipoDietaSpecById(idTipoDietaSpec).getDescrizione();
		
		sShellDettagliMalattie = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellDettagliMalattie.setText("Dettagli Malattia Selezionata");
		sShellDettagliMalattie.setSize(new Point(712, 345));
		
		labelNomeMalattiaVis = new Label(sShellDettagliMalattie, SWT.NONE);
		labelNomeMalattiaVis.setBounds(new Rectangle(20, 20, 190, 20));
		labelNomeMalattiaVis.setText("Nome della patologia");
		textNomeMalattiaVis = new Text(sShellDettagliMalattie, SWT.NONE);
		textNomeMalattiaVis.setBounds(new Rectangle(220, 20, 435, 20));
		textNomeMalattiaVis.setEnabled(false);
		textNomeMalattiaVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textNomeMalattiaVis.setText(nomeM);
		
		checkBoxMalattiaEreditariaVis = new Button(sShellDettagliMalattie, SWT.CHECK);
		checkBoxMalattiaEreditariaVis.setBounds(new Rectangle(20, 60, 124, 20));
		checkBoxMalattiaEreditariaVis.setText(" Malattia ereditaria");
		checkBoxMalattiaEreditariaVis.setEnabled(false);
		if (malattiaEredit.equals("true")) {
			checkBoxMalattiaEreditariaVis.setSelection(true);
		}
		
		labelNomeDietaVis = new Label(sShellDettagliMalattie, SWT.NONE);
		labelNomeDietaVis.setBounds(new Rectangle(20, 120, 190, 20));
		labelNomeDietaVis.setText("Nome dieta speciale associata");
		textNomeDietaSpecVis = new Text(sShellDettagliMalattie, SWT.NONE);
		textNomeDietaSpecVis.setBounds(new Rectangle(220, 120, 435, 20));
		textNomeDietaSpecVis.setEnabled(false);
		textNomeDietaSpecVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textNomeDietaSpecVis.setText(nomeDietaSpec);
		
		labelDescDietaVis = new Label(sShellDettagliMalattie, SWT.NONE);
		labelDescDietaVis.setBounds(new Rectangle(20, 160, 190, 20));
		labelDescDietaVis.setText("Descrizione dieta speciale associata");
		textDescrDietaSpecVis = new Text(sShellDettagliMalattie, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textDescrDietaSpecVis.setBounds(new Rectangle(220, 160, 450, 45));
		textDescrDietaSpecVis.setEnabled(false);
		textDescrDietaSpecVis.setBackground(new Color(Display.getCurrent(), 245, 245, 245));
		textDescrDietaSpecVis.setText(descDietaSpec);
		
		labelElencoDieteVis = new Label(sShellDettagliMalattie, SWT.NONE);
		labelElencoDieteVis.setBounds(new Rectangle(20, 100, 215, 20));
		labelElencoDieteVis.setText("Selezionare la dieta speciale da associare");
		labelElencoDieteVis.setVisible(false);
		tableTipoDietaSpecialeVis = new Table(sShellDettagliMalattie, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		tableTipoDietaSpecialeVis.setHeaderVisible(true);
		tableTipoDietaSpecialeVis.setLinesVisible(true);
		tableTipoDietaSpecialeVis.setBounds(new Rectangle(250, 100, 400, 150));
		tableTipoDietaSpecialeVis.setVisible(false);
		tableTipoDietaSpecialeVis.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						AnamnesiDAO an = new AnamnesiDAO();
						tipoDietaSpecSelezVis = an.getTipoDietaSpecById(tableTipoDietaSpecialeVis.getSelection()[0].getText(0));
					}
				});
		
		//Adatta la tabella, nasconde la prima colonna e applica l'ordinamento alle colonne
		AnamnesiDAO ad = new AnamnesiDAO(); 
		ArrayList<Object> listTipoDietaSpeciale = ad.getListTipoDietaSpeciale();
		riempiTabellaTipoDietaSpeciale(tableTipoDietaSpecialeVis, listTipoDietaSpeciale);
		for (TableColumn colonna : tableTipoDietaSpecialeVis.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		tableTipoDietaSpecialeVis.getColumn(0).setWidth(0);
		//tableTipoDietaSpeciale.getColumn(1).setWidth(0);
		TableForm.ordinamentoStringhe(tableTipoDietaSpecialeVis, 1);
		TableForm.ordinamentoStringhe(tableTipoDietaSpecialeVis, 2);
		
		buttonModificaMalattie = new Button(sShellDettagliMalattie, SWT.NONE);
		buttonModificaMalattie.setBounds(new Rectangle(400, 270, 70, 25));
		buttonModificaMalattie.setText("Modifica");
		buttonModificaMalattie.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						textNomeMalattiaVis.setEnabled(true);
						textNomeMalattiaVis.setBackground(new Color(Display.getCurrent(), 250, 250, 250));
						checkBoxMalattiaEreditariaVis.setEnabled(true);
						
						labelNomeDietaVis.setVisible(false);
						textNomeDietaSpecVis.setVisible(false);
						labelDescDietaVis.setVisible(false);
						textDescrDietaSpecVis.setVisible(false);
						labelElencoDieteVis.setVisible(true);
						tableTipoDietaSpecialeVis.setVisible(true);
						for (int i = 0; i < tableTipoDietaSpecialeVis.getItemCount(); i++) {
							if(tableTipoDietaSpecialeVis.getItem(i).getText(0).equals(idTipoDietaSpec)) {
								tableTipoDietaSpecialeVis.setSelection(i);
							}
						}
						buttonAppyModMalattie.setEnabled(true);
						buttonModificaMalattie.setEnabled(false);
					}
				});
		
		buttonAppyModMalattie = new Button(sShellDettagliMalattie, SWT.NONE);
		buttonAppyModMalattie.setBounds(new Rectangle(480, 270, 110, 25));
		buttonAppyModMalattie.setText("Applica Modifiche");
		buttonAppyModMalattie.setEnabled(false);
		buttonAppyModMalattie.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						AnamnesiDAO an = new AnamnesiDAO();
						Tipologiadietaspeciale dietaSpec = an.getTipoDietaSpecById(tableTipoDietaSpecialeVis.getSelection()[0].getText(0));
						an.modificaMalattia(idMalattia, textNomeMalattiaVis.getText(), checkBoxMalattiaEreditariaVis.getSelection(), dietaSpec);
						
						sShellDettagliMalattie.close();
						
						//Aggiornare la tabella degli interventi in AnamnesiTableView
						Utils.getActiveView().dispose();
						Utils.showView("StudioDietetico.AnamnesiTableView");
						AnamnesiTTableView.selectTab(4);
					}
				});
	
		buttonChiudi = new Button(sShellDettagliMalattie, SWT.NONE);
		buttonChiudi.setBounds(new Rectangle(600, 270, 70, 25));
		buttonChiudi.setText("Chiudi");
		buttonChiudi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellDettagliMalattie.close();
					}
				});
		sShellDettagliMalattie.open();
	}
	
	
	
	//MESSAGE BOX
	private void createSShellMessElimina() {
		sShellMessElimina = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellMessElimina.setLayout(new GridLayout());
		sShellMessElimina.setSize(new Point(377, 72));
	}
	
	private void createMessConfermaCanc(int indiceItemSel, String tableCanc) {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.CANCEL| SWT.ICON_WARNING);
		messageBox.setMessage("Sei sicuro di voler eliminare questo elemento?");
		messageBox.setText("Conferma cancellazione");
		if (messageBox.open() == SWT.OK) {
			if (tableCanc.equalsIgnoreCase("tableTipoInt")) {
				cancellaTipoInt(indiceItemSel); 
			} 
			else if (tableCanc.equalsIgnoreCase("tableMalattie")) {
				cancellaMalattia(indiceItemSel);
			}
		}
	}
	
	private void cancellaMalattia(int indiceItemSel) {
		//System.out.println("Cancella Malattia entrato");
		AnamnesiDAO an = new AnamnesiDAO();
		int idCanc = Integer.parseInt(tableMalattie.getItem(indiceItemSel).getText());
		int idPaz = AnamnesiTTableView.getPazienteSel().getIdPaziente();
		//System.out.println("idPaz:"+idPaz);
		int numPazienti = an.numPazientiPerMalattia(idPaz, idCanc);
		System.out.println("num Paz:"+numPazienti);
		if(numPazienti<1) {
			an.cancellaMalattia(idCanc);
			tableMalattie.remove(indiceItemSel);
		} else {
			sShellMessElimina.close();
			createMessElemCascade();
		}
		/*sShellInserimentoMalattie.close();
		//Aggiorna la tabella in AnamnesiTableView
		Utils.getActiveView().dispose();
		Utils.showView("StudioDietetico.AnamnesiTableView");
		AnamnesiTTableView.selectTab(4);*/
	}
	
	private void cancellaTipoInt(int indiceItemSel) {
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
	
	private void createMessNotSelElem() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_ERROR);
		messageBox.setMessage("Selezionare un elemento!");
		messageBox.setText("Errore: elemento non selezionato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	private void createMessElemCascade() {
		createSShellMessElimina();
		MessageBox messageBox = new MessageBox(sShellMessElimina, SWT.OK | SWT.ICON_INFORMATION);
		messageBox.setMessage("Non è possibile cancellare questo elemento: è collegato ad altri!");
		messageBox.setText("Elemento referenziato");
		if (messageBox.open() == SWT.OK) {
			sShellMessElimina.close();
		}
	}
	
	private void createMessElemPresente() {
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
					//System.out.println("Val: "+valuesObj[i]);
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
	
	private void riempiTabellaTipoDietaSpeciale(Table table, ArrayList<Object> lista) {
		ArrayList<String> colonne = new ArrayList<String>();
		colonne.add("idTipologiaDietaSpeciale");
		colonne.add("nome");
		colonne.add("descrizione");
		for (String item : colonne) {
			TableColumn colonna = new TableColumn(table, SWT.CENTER);
			colonna.setWidth(item.length() * 15);
			colonna.setText(item);
		}
		table.setHeaderVisible(true);
		
		if (!lista.isEmpty()) {
			for (Object item : lista) {
				TableItem tblItem = new TableItem(table, SWT.NONE);
				Object[] valuesObj = new Object[4];
				String[] values = new String[4];
				int i = 0;
				for (String colonna : colonne) {
					valuesObj[i] = GenericBean.getProperty(colonna, item);
					//System.out.println("Val: "+valuesObj[i]);
					if  (valuesObj[i] instanceof Tipologiadietaspeciale) {  
						valuesObj[i] = ((Tipologiadietaspeciale)valuesObj[i]).getIdTipologiaDietaSpeciale();
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
	
	private void riempiTabellaMalattie(Table table, ArrayList<Object> lista) {
		ArrayList<String> colonne = new ArrayList<String>();
		colonne.add("IdMalattia");
		colonne.add("tipologiadietaspeciale");
		colonne.add("patologia");
		colonne.add("malattiaEreditaria");
		for (String item : colonne) {
			TableColumn colonna = new TableColumn(table, SWT.CENTER);
			colonna.setWidth(item.length() * 15);
			colonna.setText(item);
		}
		table.setHeaderVisible(true);
		
		if (!lista.isEmpty()) {
			for (Object item : lista) {
				TableItem tblItem = new TableItem(table, SWT.NONE);
				Object[] valuesObj = new Object[5];
				String[] values = new String[5];
				int i = 0;
				for (String colonna : colonne) {
					valuesObj[i] = GenericBean.getProperty(colonna, item);
					//System.out.println("Val: "+valuesObj[i]);
					if  (valuesObj[i] instanceof Malattia) {  
						valuesObj[i] = ((Malattia)valuesObj[i]).getIdMalattia();
					}
					if  (valuesObj[i] instanceof Tipologiadietaspeciale) {  
						valuesObj[i] = ((Tipologiadietaspeciale)valuesObj[i]).getIdTipologiaDietaSpeciale();
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
}
