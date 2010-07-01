package statistics;

import grafici.StatisticheBarChart;
import grafici.FatturePieChart;
import grafici.FattureTimeSeriesChart;
import grafici.MediciBarChart;
import grafici.MediciPieChart;
import grafici.MediciTimeSeriesChart;
import grafici.PazientiBarChart;
import grafici.PazientiPieChart;
import grafici.PazientiTimeSeriesChart;
import grafici.PrenotazioneTimeSeriesChart;
import grafici.PrenotazioniBarChart;
import grafici.PrenotazioniPieChart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import common.Utils;
import common.ui.ListComposite;

public class GraficiMainForm extends ListComposite {

	private static final int INDEX_PRENOTAZ_TEMPO = 0;
	private static final int INDEX_PRENOTAZ_VISITA = 1;
	private Combo comboFunzioni;
	private Composite compGrafico;
	private Button pazienteButton;
	private Button fattureButton;
	private Button prenotazioniButton;

	public GraficiMainForm(Composite parent, int style) {
		super(parent, style);

		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.widthHint = 700;
		// gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(2, true);
		this.setLayout(glForm);
		Color white = Utils.getStandardWhiteColor();
		this.setBackground(white);

		GridData gdLbl = new GridData(SWT.BORDER);
		gdLbl.grabExcessHorizontalSpace = true;
		gdLbl.horizontalAlignment = SWT.FILL;
		gdLbl.verticalIndent = 100;

		Label lbl2 = new Label(this, SWT.NONE | SWT.BOLD);
		lbl2.setText("SELEZIONE AREA D'INTERESSE:");
		lbl2.setBackground(white);
		lbl2.setLayoutData(gdLbl);
		lbl2.setFont(Utils.getFont("Arial", 13, SWT.BOLD));
		Label lbl = new Label(this, SWT.NONE | SWT.BOLD);
		lbl.setText("SELEZIONE AREA FUNZIONALE:");
		lbl.setBackground(white);
		lbl.setLayoutData(gdLbl);
		lbl.setFont(Utils.getFont("Arial", 13, SWT.BOLD));
		// Label lbl1 = new Label(this, SWT.NONE | SWT.BOLD);
		// lbl1.setText("SELEZIONE IL TIPO DI GRAFICO:");
		// lbl1.setBackground(white);
		// lbl1.setLayoutData(gdLbl);
		// lbl1.setFont(Utils.getFont("Arial", 13, SWT.BOLD));

		Composite cmp1 = new Composite(this, SWT.BORDER);
		GridData gdCmp1 = new GridData();
		gdCmp1.grabExcessHorizontalSpace = true;
		gdCmp1.horizontalAlignment = SWT.FILL;
		gdCmp1.verticalIndent = 50;
		cmp1.setLayoutData(gdCmp1);
		cmp1.setLayout(new GridLayout(1, false));
		cmp1.setBackground(white);
		GridData gdButton = new GridData();
		gdButton.grabExcessHorizontalSpace = true;
		gdButton.horizontalAlignment = SWT.FILL;
		gdButton.verticalIndent = 30;
		fattureButton = new Button(cmp1, SWT.RADIO);
		fattureButton.setText("Fatture");
		fattureButton.setBackground(white);
		fattureButton.setFont(Utils.getFont("Arial", 12, SWT.BOLD));
		fattureButton.setLayoutData(gdButton);
		prenotazioniButton = new Button(cmp1, SWT.RADIO);
		prenotazioniButton.setText("Prenotazione");
		prenotazioniButton.setBackground(white);
		prenotazioniButton.setFont(Utils.getFont("Arial", 12, SWT.BOLD));
		prenotazioniButton.setLayoutData(gdButton);
		pazienteButton = new Button(cmp1, SWT.RADIO);
		pazienteButton.setText("Paziente");
		pazienteButton.setBackground(white);
		pazienteButton.setLayoutData(gdButton);
		pazienteButton.setFont(Utils.getFont("Arial", 12, SWT.BOLD));
		Button medicoButton = new Button(cmp1, SWT.RADIO);
		medicoButton.setText("Medico");
		medicoButton.setBackground(white);
		medicoButton.setFont(Utils.getFont("Arial", 12, SWT.BOLD));
		medicoButton.setLayoutData(gdButton);
		comboFunzioni = new Combo(this, SWT.NONE);
		comboFunzioni.setEnabled(false);
		comboFunzioni.setLayoutData(gdCmp1);
		comboFunzioni.setFont(Utils.getFont("Arial", 12, SWT.BOLD));
		fattureButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				comboFunzioni.removeAll();
				comboFunzioni.add("N° fatture per mese (BARRE)");
				comboFunzioni.add("Fatturati nel mese (TORTA)");
				comboFunzioni.add("Fatturato nel tempo (SERIE TEMPORALE)");
				comboFunzioni.setEnabled(true);
				comboFunzioni.select(0);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		prenotazioniButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				comboFunzioni.removeAll();
				comboFunzioni.add("N° prenotazioni per tipo visita nell'anno (BARRE)");
				comboFunzioni.add("N° prenotazioni per tipo visita (TORTA)");
				comboFunzioni.add("Distribuzione prenotazioni nell'anno (SERIE TEMPORALE)");
				comboFunzioni.setEnabled(true);
				comboFunzioni.select(0);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		pazienteButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				comboFunzioni.removeAll();
				comboFunzioni
						.add("Tipo attività fisica pazienti, per età e per sesso");
				comboFunzioni
						.add("Durata attività fisica pazienti, per età e per sesso");
				comboFunzioni
						.add("Farmaci assunti dai pazienti, per età e per sesso");
				comboFunzioni.add("Interventi pazienti, per età e per sesso");
				comboFunzioni.add("Allergie pazienti, per età e per sesso");
				comboFunzioni.setEnabled(true);
				comboFunzioni.select(0);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		medicoButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				comboFunzioni.removeAll();
				comboFunzioni.add("Prestazioni medico per mese (BARRE)");
				comboFunzioni.add("Prestazioni medici per tipo visita (TORTA)");
				comboFunzioni.add("Prestazioni medici anno in corso (SERIE TEMPORALE)");
				comboFunzioni.select(0);
				comboFunzioni.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		// AREE FUNZIONALI - ENTITA'
		Button disegna = new Button(this, SWT.NONE);
		disegna.setText("DISEGNA GRAFICO");
		GridData gdDis = new GridData();
		gdDis.horizontalSpan = 2;
		gdDis.horizontalAlignment = SWT.CENTER;
		disegna.setLayoutData(gdDis);
		disegna.setFont(Utils.getFont("Arial", 14, SWT.BOLD));
		disegna.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fattureButton.getSelection())
					disegnaGrafico(0);
				else if (pazienteButton.getSelection())
					disegnaGrafico(1);
				else if (prenotazioniButton.getSelection())
					disegnaGrafico(2);
				else
					disegnaGrafico(3);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}

	private void disegnaGrafico(int areaFunzionale) {
		// compGrafico.getChildren()[0].dispose();
		Shell shell = new Shell();
		shell.setText(comboFunzioni.getText());
		shell.setLayout(new GridLayout(1, false));
		shell.setLayoutData(new GridData(SWT.FILL));
		shell.setSize(1000, 550);
		// fatture
		if (areaFunzionale == 0) {
			if (comboFunzioni.getSelectionIndex() == 0) {
//				StatisticheBarChart chart = new StatisticheBarChart(comboFunzioni
//						.getText(), shell, SWT.BORDER, 0);
			} else if (comboFunzioni.getSelectionIndex() == 1) {
				FatturePieChart chart = new FatturePieChart(comboFunzioni
						.getText(), shell, SWT.BORDER, 0);
			} else if (comboFunzioni.getSelectionIndex() == 2) {
				System.out.println("s");
				FattureTimeSeriesChart chart = new FattureTimeSeriesChart(
						comboFunzioni.getText(), shell, SWT.BORDER, 0);
			}
		}
		// pazienti
		else if (areaFunzionale == 1) {
			if (comboFunzioni.getSelectionIndex() == 0) {
				PazientiBarChart chart = new PazientiBarChart(comboFunzioni
						.getText(), shell, SWT.BORDER, 0);
			} else if (comboFunzioni.getSelectionIndex() == 1) {
				PazientiPieChart chart = new PazientiPieChart(comboFunzioni
						.getText(), shell, SWT.BORDER, 0);
			} else if (comboFunzioni.getSelectionIndex() == 2) {
				PazientiTimeSeriesChart chart = new PazientiTimeSeriesChart(
						comboFunzioni.getText(), shell, SWT.BORDER, 0);
			}
		}
		// PRENOTAZIONI
		else if (areaFunzionale == 2) {
			if (comboFunzioni.getSelectionIndex() == 0) {
				PrenotazioniBarChart chart = new PrenotazioniBarChart(
						comboFunzioni.getText(), shell, SWT.BORDER, 0);
			} else if (comboFunzioni.getSelectionIndex() == 1) {
				PrenotazioniPieChart chart = new PrenotazioniPieChart(
						comboFunzioni.getText(), shell, SWT.BORDER, 1);
			} else if (comboFunzioni.getSelectionIndex() == 2) {
				PrenotazioneTimeSeriesChart chart = new PrenotazioneTimeSeriesChart(
						comboFunzioni.getText(), shell, SWT.BORDER, 2);
			}
		}
		// /medici
		else {
			if (comboFunzioni.getSelectionIndex() == 0) {
				MediciBarChart chart = new MediciBarChart(comboFunzioni
						.getText(), shell, SWT.BORDER, 1);
			} else if (comboFunzioni.getSelectionIndex() == 1) {
				MediciPieChart chart = new MediciPieChart(comboFunzioni
						.getText(), shell, SWT.BORDER, 1);
			} else if (comboFunzioni.getSelectionIndex() == 2) {
				MediciTimeSeriesChart chart = new MediciTimeSeriesChart(
						comboFunzioni.getText(), shell, SWT.BORDER, 2);
			}

		}
		System.out.println("shell open");
		shell.open();
	}

}
