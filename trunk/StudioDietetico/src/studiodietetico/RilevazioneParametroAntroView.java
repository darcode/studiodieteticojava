package studiodietetico;

import hibernate.Misurazione;
import hibernate.Parametroantropometrico;
import hibernate.Paziente;
import hibernate.Prenotazione;
import hibernate.Rilevamento;

import javax.swing.JComponent;

import java.util.ArrayList;
import java.util.Date;

import command.MisurazioneDAO;
import command.ParametroAntropometricoDAO;
import command.PazienteDAO;
import command.RilevamentoDAO;
import command.VisitaDAO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Combo;

import service.Utils;

public class RilevazioneParametroAntroView extends ViewPart {

	public static final String ID = "studiodietetico.RilevazioneParametroAntroView"; // TODO
																						// Needs
																						// to
																						// be
																						// whatever
																						// is
																						// mentioned
																						// in
																						// plugin.xml
	private Composite top = null;
	private Table tableParametroAntropometrico = null;
	private Button btnRegistra = null;
	private Button buttonSelezionaData = null;
	public TableViewer tableViewer = null;
	private JComponent[] components;
	private ArrayList<Paziente> paz; // @jve:decl-index=0:
	ParametroAntropometricoDAO par = new ParametroAntropometricoDAO(); // @jve:decl-index=0:
	private Shell ShellCalendario = null;
	private Combo cmbParametro = null;
	private Label lblParametro = null;
	private Date dn = null;
	private Text txtValore = null;
	private Text textAreaPrenotazioniOdierne = null;
	private Label lblValore = null;
	private Label lblOsservazione = null;
	private Text txtOsservazione = null;
	private Button btnAggiungi = null;
	private Label lblData = null;
	private Label labelPaziente = null;
	private Text textPaziente = null;
	private static Paziente pazSelHome;

	@Override
	public void createPartControl(Composite parent) {

		top = new Composite(parent, SWT.NONE);
/*		GridData gdForm = new GridData(SWT.NONE);
		gdForm.grabExcessVerticalSpace = true;
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		gdForm.verticalAlignment = SWT.FILL;
		top.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, false);
		top.setLayout(glForm);*/
		// Visualizzazione del paziente selezionato
		labelPaziente = new Label(top, SWT.NONE);
		labelPaziente.setBounds(new Rectangle(14, 31, 46, 13));
		labelPaziente.setText("Paziente");
		textPaziente = new Text(top, SWT.BORDER | SWT.BOLD);
		textPaziente.setBounds(new Rectangle(76, 16, 263, 31));
		textPaziente.setEnabled(false);
		// pazSelHome = PazienteDAO.getPazienti().get(2);
		pazSelHome = PazienteTableView.getPazienteSelezionato();
		String dataNascPazSel = pazSelHome.getDataNascita().getDay() + "/"
				+ pazSelHome.getDataNascita().getMonth() + "/"
				+ pazSelHome.getDataNascita().getYear();
		textPaziente.setText(pazSelHome.getCognome() + "   "
				+ pazSelHome.getNome() + "   " + dataNascPazSel);
		tableParametroAntropometrico = new Table(top, SWT.NONE);
		tableParametroAntropometrico.setHeaderVisible(true);
		tableParametroAntropometrico.setLinesVisible(true);
		tableParametroAntropometrico
				.setBounds(new Rectangle(14, 183, 421, 111));
		TableColumn tableColumn = new TableColumn(tableParametroAntropometrico,
				SWT.NONE);
		tableColumn.setWidth(80);
		tableColumn.setText("IdParametro");
		TableColumn tableColumn1 = new TableColumn(
				tableParametroAntropometrico, SWT.NONE);
		tableColumn1.setWidth(80);
		tableColumn1.setText("Parametro");
		TableColumn tableColumn11 = new TableColumn(
				tableParametroAntropometrico, SWT.NONE);
		tableColumn11.setWidth(80);
		tableColumn11.setText("Misurazione");
		TableColumn tableColumn12 = new TableColumn(
				tableParametroAntropometrico, SWT.NONE);
		tableColumn12.setWidth(180);
		tableColumn12.setText("Osservazione");
		tableViewer = new TableViewer(tableParametroAntropometrico);

		btnRegistra = new Button(top, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(319, 300, 117, 23));
		btnRegistra.setText("Registra Rilevazioni");
		createCmbParametro();
		buttonSelezionaData = new Button(top, SWT.NONE);
		buttonSelezionaData.setBounds(new Rectangle(13, 102, 189, 27));
		buttonSelezionaData.setText("Seleziona la data della rilevazione");
		buttonSelezionaData
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						createShellCalendario();
						ShellCalendario.open();
						cmbParametro.setEnabled(true);
						txtValore.setEnabled(true);
						txtOsservazione.setEnabled(true);
						btnAggiungi.setEnabled(true);
						btnRegistra.setEnabled(true);
					}
				});
		lblParametro = new Label(top, SWT.NONE);
		lblParametro.setBounds(new Rectangle(14, 138, 61, 13));
		lblParametro.setText("Parametro");
		txtValore = new Text(top, SWT.BORDER);
		txtValore.setBounds(new Rectangle(140, 157, 76, 19));
		txtValore.setEnabled(false);
		lblValore = new Label(top, SWT.NONE);
		lblValore.setBounds(new Rectangle(140, 139, 43, 13));
		lblValore.setText("Valore");
		lblOsservazione = new Label(top, SWT.NONE);
		lblOsservazione.setBounds(new Rectangle(232, 139, 66, 13));
		lblOsservazione.setText("Osservazioni");
		txtOsservazione = new Text(top, SWT.BORDER);
		txtOsservazione.setBounds(new Rectangle(232, 158, 155, 19));
		txtOsservazione.setEnabled(false);
		btnAggiungi = new Button(top, SWT.NONE);
		btnAggiungi.setBounds(new Rectangle(404, 156, 30, 23));
		btnAggiungi.setText("+");
		btnAggiungi.setEnabled(false);
		lblData = new Label(top, SWT.NONE);
		lblData.setBounds(new Rectangle(215, 107, 172, 17));
		lblData.setText("");
		btnAggiungi
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						Parametroantropometrico parametro = par
								.getParametro(cmbParametro.getText());
						TableItem item = new TableItem(
								tableParametroAntropometrico, SWT.NONE);
						item
								.setText(new String[] {
										parametro
												.getIdParametroAntropometrico()
												.toString(),
										parametro.getNome(),
										txtValore.getText(),
										txtOsservazione.getText() });
						txtValore.setText("");
						txtOsservazione.setText("");
					}
				});
		btnRegistra.setEnabled(false);
		btnRegistra
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						Paziente paziente = pazSelHome;
						for (int i = 0; i <= tableParametroAntropometrico
								.getItemCount() - 1; ++i) {
							TableItem item = tableParametroAntropometrico
									.getItem(i);
							Parametroantropometrico parametro = par
									.getParametro(Integer.valueOf(item
											.getText(0)));
							RilevamentoDAO ril = new RilevamentoDAO();
							MisurazioneDAO p = new MisurazioneDAO();
							Rilevamento r = new Rilevamento();
							Misurazione misurazione = new Misurazione();
							misurazione = p.registraMisurazione(parametro,
									paziente, item.getText(3));
							Date data = dn;

							ril.registraRilevamento(data, misurazione, item
									.getText(2));
							// p.registraMisurazione(parametro, paziente,
							// setril, item.getText(3));

						}
						tableParametroAntropometrico.clearAll();
						tableParametroAntropometrico.removeAll();
						cmbParametro.setEnabled(true);
						txtValore.setEnabled(true);
						txtOsservazione.setEnabled(true);
						btnAggiungi.setEnabled(true);
						btnRegistra.setEnabled(true);
						lblData.setText("");
					}
				});
		paz = PazienteDAO.getPazienti();
		ArrayList<String> p = new ArrayList<String>();
		for (Paziente paziente : paz) {
			p.add(paziente.getCognome() + " " + paziente.getNome());
		}
		String[] pazientiArray = (String[]) p.toArray((new String[0]));
		caricacmbParametri();
	}

	public void caricacmbParametri() {
		ArrayList<Parametroantropometrico> list = ParametroAntropometricoDAO
				.getParametri();
		for (int i = 0; i <= list.size() - 1; ++i) {
			cmbParametro.add(list.get(i).getNome());
		}
		cmbParametro.select(0);
	}

	private Date createShellCalendario() {
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.heightHint = 32;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		ShellCalendario = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		ShellCalendario.setText("Seleziona data e ora");
		ShellCalendario.setLayout(new GridLayout());
		ShellCalendario.setSize(new Point(270, 300));
		final DateTime calendar = new DateTime(ShellCalendario, SWT.CALENDAR
				| SWT.BORDER);
		// final DateTime date = new DateTime (ShellCalendario, SWT.DATE |
		// SWT.SHORT);
		calendar
				.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						ArrayList<Prenotazione> prenotGiorno = VisitaDAO
								.getPrenotazioniGiorno(calendar.getYear(),
										(calendar.getMonth() + 1), calendar
												.getDay());
						textAreaPrenotazioniOdierne
								.setText("N. di prenotazioni per il "
										+ calendar.getDay() + "/"
										+ (calendar.getMonth() + 1) + "/"
										+ calendar.getYear() + " = "
										+ prenotGiorno.size() + "\n");
						for (Prenotazione prenotazione : prenotGiorno) {
							textAreaPrenotazioniOdierne
									.append("ore: "
											+ prenotazione.getDataOra()
													.getHours()
											+ ":"
											+ (prenotazione.getDataOra()
													.getMinutes() < 10 ? "0"
													: "")
											+ prenotazione.getDataOra()
													.getMinutes()
											+ "  -paziente: "
											+ prenotazione.getPaziente()
													.getCognome()
											+ " "
											+ prenotazione.getPaziente()
													.getNome() + "\n");
						}
						textAreaPrenotazioniOdierne.setTopIndex(0);
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						// TODO Auto-generated method stub

					}
				});
		// final DateTime time = new DateTime (ShellCalendario, SWT.TIME |
		// SWT.SHORT);
		textAreaPrenotazioniOdierne = new Text(ShellCalendario, SWT.MULTI
				| SWT.V_SCROLL | SWT.BORDER);
		textAreaPrenotazioniOdierne.setEditable(false);
		textAreaPrenotazioniOdierne.setLayoutData(gridData);
		// new Label (ShellCalendario, SWT.NONE);
		// new Label (ShellCalendario, SWT.NONE);
		Button ok = new Button(ShellCalendario, SWT.PUSH);
		ok.setText("OK");
		ok.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// System.out.println ("Calendar date selected (MM/DD/YYYY) = "
				// + (calendar.getMonth () + 1) + "/" + calendar.getDay () + "/"
				// + calendar.getYear ());
				// System.out.println ("Date selected (MM/YYYY) = " +
				// (date.getMonth () + 1) + "/" + date.getYear ());
				// System.out.println ("Time selected (HH:MM) = " +
				// time.getHours () + ":" + (time.getMinutes () < 10 ? "0" : "")
				// + time.getMinutes ());
				// String dateString = calendar.getYear
				// ()+"-"+(calendar.getMonth () + 1)+"-"+calendar.getDay
				// ()+" "+time.getHours () +":"+(time.getMinutes () < 10 ? "0" :
				// "") + time.getMinutes ()+":00";
				String dateString = calendar.getYear() + "-"
						+ (calendar.getMonth() + 1) + "-" + calendar.getDay()
						+ " 00:00:00";
				String formato = "yyyy-MM-dd HH:mm:ss";
				dn = Utils.convertStringToDate(dateString, formato);
				lblData.setText(dn.toString());
				ShellCalendario.close();
			}
		});

		// ShellCalendario.open();

		return dn;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes cmbParametro
	 * 
	 */
	private void createCmbParametro() {
		cmbParametro = new Combo(top, SWT.NONE);
		cmbParametro.setBounds(new Rectangle(15, 157, 103, 21));
		cmbParametro.setEnabled(false);
	}

} // @jve:decl-index=0:visual-constraint="10,10,440,328"
