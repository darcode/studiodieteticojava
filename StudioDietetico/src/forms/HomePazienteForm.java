package forms;

import hibernate.Paziente;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import service.Utils;
import studiodietetico.AnamnesiView;
import studiodietetico.PrenotaVisitaView;
import studiodietetico.RegistraPazienteView;

import command.PazienteDAO;
import common.ui.ListComposite;

public class HomePazienteForm extends ListComposite {

	public HomePazienteForm(Composite parent, int style) {
		super(parent, style);
		initialize(parent);
	}

	private Composite top = null;
	private Table listElencoPazienti = null;
	private Label labelSelPaz = null;
	private Button buttonInsNewPaz = null;
	private Button buttonPrenota = null;
	private Button buttonAnagrafica = null;
	private Button buttonVisualizzaDieta = null;
	private Button buttonAnamnesi = null;
	private ArrayList<Object> paz; // @jve:decl-index=0:

	private void initialize(Composite parent) {
		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, true);
		this.setLayout(glForm);
		this.setBackground(common.Utils.getStandardWhiteColor());

		GridData gdFiller = new GridData(SWT.FILL);
		gdFiller.grabExcessHorizontalSpace = true;
		gdFiller.horizontalAlignment = SWT.FILL;
		gdFiller.horizontalSpan = 4;

		GridData gdTbl = new GridData(SWT.FILL);
		gdTbl.grabExcessHorizontalSpace = true;
		gdTbl.horizontalAlignment = SWT.FILL;
		gdTbl.verticalAlignment = SWT.FILL;
		gdTbl.grabExcessVerticalSpace = true;
		gdTbl.horizontalSpan = 4;
		
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.grabExcessVerticalSpace = true;

		top = new Composite(this, SWT.BORDER);
		top.setLayout(new GridLayout(4, true));
		top.setLayoutData(gdTop);
		labelSelPaz = new Label(top, SWT.NONE);
		labelSelPaz
				.setText("Selezonare il paziente dalla lista o inserirne uno nuovo");

		labelSelPaz.setLayoutData(gdFiller);
		
		listElencoPazienti = new Table(top, SWT.FILL | SWT.BORDER
				| SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI);
		listElencoPazienti.setHeaderVisible(true);
		listElencoPazienti.setLinesVisible(true);
		listElencoPazienti.setLayout(new GridLayout(1, true));
		listElencoPazienti.setLayoutData(gdTbl);

		buttonInsNewPaz = new Button(top, SWT.NONE);
		buttonInsNewPaz.setText("Inserisci nuovo paziente");
		buttonInsNewPaz.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				Utils.showView(RegistraPazienteView.VIEW_ID);
			}
		});
		buttonPrenota = new Button(top, SWT.NONE);
		buttonPrenota.setText("Prenotazione visita");
		buttonPrenota.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				Utils.showView(PrenotaVisitaView.VIEW_ID);
			}
		});
		buttonAnagrafica = new Button(top, SWT.NONE);
		buttonAnagrafica.setText("Visualizza Anagrafica");
		buttonVisualizzaDieta = new Button(top, SWT.NONE);
		buttonVisualizzaDieta.setText("Visualizza Dieta");
		buttonAnamnesi = new Button(top, SWT.NONE);
		buttonAnamnesi.setText("Gestione Anamnesi");
		buttonAnamnesi
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						Utils.showView(AnamnesiView.VIEW_ID);
					}
				});

		// PazienteDAO paz = new PazienteDAO();

		/*
		 * paz = new ArrayList<Paziente>(); paz = PazienteDAO.getPazienti();
		 * ArrayList<String> p = new ArrayList<String>(); for (Paziente paziente
		 * : paz) {
		 * p.add(paziente.getCognome()+"\t"+paziente.getNome()+"\t"+paziente
		 * .getDataNascita()); } String[] pazientiArray = (String[])
		 * p.toArray((new String[0]));
		 * listElencoPazienti.setItems(pazientiArray);
		 */

		paz = PazienteDAO.getPazientiPerLista();
		riempiTabellaEntità(listElencoPazienti, paz);
		listElencoPazienti.getColumn(0).setWidth(0);
		// ArrayList<String> p = new ArrayList<String>();
		// for (Object paziente : paz) {
		// p.add(((Paziente)paziente).getCognome() + " " +
		// ((Paziente)paziente).getNome() + "  ("
		// + ((Paziente)paziente).getDataNascita() + ")");
		// }
		// String[] pazientiArray = (String[]) p.toArray((new String[0]));

	}

	public Paziente getPazienteSelezionato() {
		Paziente paziente = (Paziente) paz.get(listElencoPazienti
				.getSelectionIndex());
		return paziente;
	}
}
