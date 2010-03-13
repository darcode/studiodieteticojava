package studiodietetico;

import hibernate.Medico;
import hibernate.Prenotazione;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import command.MedicoDAO;
import command.VisitaDAO;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;

import service.Utils;

public class RegistraVisitaView extends ViewPart {

	private Composite top = null;
	private Label labelSelezPrenotaz = null;
	private Combo comboPrenotazOdierne = null;
	private Text textAreaInfoPrenotazione = null;
	private Label labelInfoPrenotazione = null;
	private Label labelSelezMedico = null;
	private Combo comboMedicoVisita = null;
	private Label labelOraInizioVisita = null;
	private Label labelOraFineVisita = null;
	private Label labelMotivazioni = null;
	private Text textAreaMotivazioni = null;
	private Label labelNote = null;
	private Text textAreaNote = null;
	private Button buttonRegistraVisita = null;
	private ArrayList<Medico> medici;  //  @jve:decl-index=0:
	private ArrayList<Prenotazione> prenotazioni;  //  @jve:decl-index=0:
	private Group groupPrenotazione = null;
	private Group groupDataVisita = null;
	private Label labelFatturaAssociata = null;
	final ArrayList<Prenotazione> prenotazioniOdierne = new ArrayList<Prenotazione>();  //  @jve:decl-index=0:
	
	public RegistraVisitaView() {
	}

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
        labelSelezPrenotaz = new Label(top, SWT.WRAP);
        labelSelezPrenotaz.setBounds(new Rectangle(10, 15, 141, 32));
        labelSelezPrenotaz.setText("* Seleziona la prenotazione per la data odierna:");
        createComboPrenotazOdierne();
        textAreaInfoPrenotazione = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaInfoPrenotazione.setBounds(new Rectangle(161, 50, 308, 82));
        textAreaInfoPrenotazione.setEditable(false);
        labelInfoPrenotazione = new Label(top, SWT.WRAP);
        labelInfoPrenotazione.setBounds(new Rectangle(10, 50, 143, 39));
        labelInfoPrenotazione.setText("Info sulla prenotazione selezionata:");
        labelSelezMedico = new Label(top, SWT.WRAP);
        labelSelezMedico.setBounds(new Rectangle(10, 142, 144, 32));
        labelSelezMedico.setText("* Seleziona il medico che ha effettuato la visita:");
        createComboMedicoVisita();
        labelOraInizioVisita = new Label(top, SWT.NONE);
        labelOraInizioVisita.setBounds(new Rectangle(10, 200, 117, 20));
        labelOraInizioVisita.setText("* Ora inizio visita:");
        final DateTime timeInizioVisita = new DateTime(top, SWT.TIME | SWT.SHORT);
        timeInizioVisita.setBounds(130, 200, 70, 20);
        labelOraFineVisita = new Label(top, SWT.NONE);
        labelOraFineVisita.setBounds(new Rectangle(10, 230, 117, 20));
        labelOraFineVisita.setText("* Ora fine visita:");
        final DateTime timeFineVisita = new DateTime(top, SWT.TIME | SWT.SHORT);
        timeFineVisita.setBounds(130, 230, 70, 20);
        labelMotivazioni = new Label(top, SWT.NONE);
        labelMotivazioni.setBounds(new Rectangle(10, 275, 129, 22));
        labelMotivazioni.setText("Motivazioni della visita:");
        textAreaMotivazioni = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaMotivazioni.setBounds(new Rectangle(157, 274, 344, 55));
        labelNote = new Label(top, SWT.NONE);
        labelNote.setBounds(new Rectangle(12, 341, 41, 17));
        labelNote.setText("Note:");
        textAreaNote = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        textAreaNote.setBounds(new Rectangle(60, 342, 398, 115));
        buttonRegistraVisita = new Button(top, SWT.NONE);
        buttonRegistraVisita.setBounds(new Rectangle(354, 467, 147, 25));
        buttonRegistraVisita.setText("Registra visita");
        createGroupPrenotazione();
        createGroupDataVisita();
        labelFatturaAssociata = new Label(top, SWT.NONE);
        labelFatturaAssociata.setBounds(new Rectangle(11, 467, 332, 24));
        labelFatturaAssociata.setText("Visita correttamente registrata");
        labelFatturaAssociata.setVisible(false);
        buttonRegistraVisita
        		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        				String formato = "yyyy-MM-dd HH:mm:ss";
        				Date now = new Date();
        				String dataInizioString =  Integer.toString(now.getYear()+1900)+"-"+((now.getMonth()+1) < 10 ? "0" : "") + (now.getMonth()+1)+"-"+Integer.toString(now.getDate())+" "+timeInizioVisita.getHours()+":"+(timeInizioVisita.getMinutes () < 10 ? "0" : "") + timeInizioVisita.getMinutes ()+":00";
        				String dataFineString =  Integer.toString(now.getYear()+1900)+"-"+((now.getMonth()+1) < 10 ? "0" : "") + (now.getMonth()+1)+"-"+Integer.toString(now.getDate())+" "+timeFineVisita.getHours()+":"+(timeFineVisita.getMinutes () < 10 ? "0" : "") + timeFineVisita.getMinutes ()+":00";
        				System.out.println(dataInizioString+"____"+dataFineString);
        				Date dataInizioVisita = Utils.convertStringToDate(dataInizioString, formato);   
        				Date dataFineVisita = Utils.convertStringToDate(dataFineString, formato);   
        				Prenotazione pren = prenotazioniOdierne.get(comboPrenotazOdierne.getSelectionIndex());
        				int idPren = pren.getIdPrenotazione();
        				Prenotazione prenotazione = VisitaDAO.getPrenotazioneByID(idPren);
        				Medico medico = medici.get(comboMedicoVisita.getSelectionIndex());
        				VisitaDAO visita = new VisitaDAO();

        				visita.registraVisita(dataInizioVisita, dataFineVisita, textAreaMotivazioni.getText(), textAreaNote.getText(), medico, prenotazione);
        				labelFatturaAssociata.setVisible(true);
        				System.out.println("visita registrata"); 
        			}
        		});
	}

	@Override
	public void setFocus() {
		
	}


	private void createComboPrenotazOdierne() {
		comboPrenotazOdierne = new Combo(top, SWT.READ_ONLY);
		comboPrenotazOdierne.setBounds(new Rectangle(161, 15, 260, 25));
		Date now = new Date();
		prenotazioni = (ArrayList<Prenotazione>) VisitaDAO.getPrenotazioni();
		ArrayList<String> pren = new ArrayList<String>();
		int index = 0;
		for (Prenotazione prenotazione : prenotazioni) {
			if (prenotazione.getDataOra().getDate()== now.getDate() && 
					prenotazione.getDataOra().getMonth()== now.getMonth() &&
					prenotazione.getDataOra().getYear()== now.getYear()) { 
				prenotazioniOdierne.add(index, prenotazione);
				index++;
				pren.add(prenotazione.getPaziente().getCognome()+" "+prenotazione.getPaziente().getNome()+"  - "+prenotazione.getTipologiavisita().getTipologia());
			}
		}
		String[] prenotazioniArray = (String[]) pren.toArray((new String[0]));
		comboPrenotazOdierne.setItems(prenotazioniArray);
		comboPrenotazOdierne
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						int selez = comboPrenotazOdierne.getSelectionIndex();
						textAreaInfoPrenotazione.setText("Paziente: "+prenotazioniOdierne.get(selez).getPaziente().getCognome()+" "+prenotazioniOdierne.get(selez).getPaziente().getNome()+"\n"+
															"Data di nascita paziente: "+prenotazioniOdierne.get(selez).getPaziente().getDataNascita()+"\n"+
															"Codice fiscale paziente: "+prenotazioniOdierne.get(selez).getPaziente().getCodiceFiscale()+"\n"+
															"Indirizzo paziente: "+prenotazioniOdierne.get(selez).getPaziente().getCitta()+"  "+prenotazioniOdierne.get(selez).getPaziente().getIndirizzo()+"\n"+
															"Tessera sanitaria paziente: "+prenotazioniOdierne.get(selez).getPaziente().getNumTesseraSanitaria()+"\n\n"+
															"Tipologia visita prenotata: "+prenotazioniOdierne.get(selez).getTipologiavisita().getTipologia()+"\n"+
															"Costo visita prenotata: "+prenotazioniOdierne.get(selez).getTipologiavisita().getCostoVisita()+"\n"+
															"Data e ora prenotazione: "+prenotazioniOdierne.get(selez).getDataOra()+"\n"+
															"Eventuali note sulla prenotazione: "+prenotazioniOdierne.get(selez).getNote());	
						System.out.println("widgetSelected()");
					}
					public void widgetDefaultSelected(
							org.eclipse.swt.events.SelectionEvent e) {
					}
				});
		
	}


	private void createComboMedicoVisita() {
		comboMedicoVisita = new Combo(top, SWT.READ_ONLY);
		comboMedicoVisita.setBounds(new Rectangle(165, 147, 209, 22));
		medici = (ArrayList<Medico>) MedicoDAO.getMedici();
		ArrayList<String> med = new ArrayList<String>();
		for (Medico medico : medici) {
			med.add(medico.getCognome()+"_"+medico.getNome());
		}
		String[] mediciArray = (String[]) med.toArray((new String[0]));
		comboMedicoVisita.setItems(mediciArray);
	}

	
	private void createGroupPrenotazione() {
		groupPrenotazione = new Group(top, SWT.NONE);
		groupPrenotazione.setLayout(new GridLayout());
		groupPrenotazione.setBounds(new Rectangle(5, 1, 474, 136));
		groupPrenotazione.setText("Prenotazione");
	}

	
	private void createGroupDataVisita() {
		groupDataVisita = new Group(top, SWT.NONE);
		groupDataVisita.setLayout(new GridLayout());
		groupDataVisita.setText("Data della visita");
		groupDataVisita.setBounds(new Rectangle(7, 176, 300, 85));
	}


}  //  @jve:decl-index=0:visual-constraint="10,10,623,579"
