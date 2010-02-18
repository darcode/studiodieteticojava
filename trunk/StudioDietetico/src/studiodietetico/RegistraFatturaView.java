package studiodietetico;

import hibernate.Fattura;
import hibernate.Prenotazione;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;

import command.FatturaDAO;

public class RegistraFatturaView extends ViewPart {

	private Composite top = null;
	private Label labelFattura = null;
	private Button buttonCreaNuovaFattura = null;
	private Button buttonAssociaFattura = null;
	private List listFatture = null;
	private ListViewer listViewer = null;
	private Label labelFatturaAssociata = null;
	private Button buttonOk = null;
	private Shell sShellNuovaFattura = null;  //  @jve:decl-index=0:visual-constraint="17,722"
	private Group groupPrenotazione = null;
	private Label labelNumFattura = null;
	private Text textNumeroFattura = null;
	private Label labelImportoFatt = null;
	private Text textImportoFatt = null;
	private Label labelImportoPagato = null;
	private Button radioButtonImportoSi = null;
	private Button radioButtonImportoNo = null;
	private Label labelAcconto = null;
	private Text textImportoAcconto = null;
	private Group groupImportoAcconto = null;
	private Label labelSconto = null;
	private Button radioButtonScontoSi = null;
	private Button radioButtonScontoNo = null;
	private Label labelImportoSconto = null;
	private Text textImportoSconto = null;
	private Group groupScontoFattura = null;
	private Label labelNoteFattura = null;
	private Text textAreaNoteFattura = null;
	private Button buttonRegistraFattura = null;
	private Button buttonAnnullaFattura = null;
	private Composite compositeAcconto = null;
	private Label labelDescrizioneFattura = null;
	private Text textDescrizioneFattura = null;
	private Group groupDataVisita = null;
	private ArrayList<Fattura> fatt;
	private int idFatt;
	private Button buttonAggiornaFattura = null;
	private Fattura fatturaSelezionata = null;
	final ArrayList<Prenotazione> prenotazioniOdierne = new ArrayList<Prenotazione>();  //  @jve:decl-index=0:
	
	
	public RegistraFatturaView() {
	}

	@Override
	public void createPartControl(Composite parent) {
        top = new Composite(parent, SWT.NONE);
        labelFattura = new Label(top, SWT.WRAP);
        labelFattura.setBounds(new Rectangle(10, 9, 155, 48));
        labelFattura.setText("Si desidera creare una nuova fattura o associare una fattura esistente?");
        buttonCreaNuovaFattura = new Button(top, SWT.MULTI);
        buttonCreaNuovaFattura.setBounds(new Rectangle(174, 17, 180, 28));
        buttonCreaNuovaFattura.setText("Crea e associa una nuova fattura");
        buttonCreaNuovaFattura
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				//test.sShellNuovaFattura.open();
				listFatture.setEnabled(false);
				createSShellNuovaFattura();  				
				System.out.println("widgetSelected()"); 
			}
		});
        buttonAssociaFattura = new Button(top, SWT.NONE);
        buttonAssociaFattura.setBounds(new Rectangle(366, 17, 180, 28));
        buttonAssociaFattura.setText("Associa una fattura esistente");
        buttonAssociaFattura
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				listFatture.setEnabled(true);
				System.out.println("widgetSelected()"); 
			}
		});
        //listFatture = new List(top, SWT.V_SCROLL | SWT.H_SCROLL);
        //listFatture.setBounds(new Rectangle(10, 62, 501, 133));
        //listFatture.setEnabled(false);
        labelFatturaAssociata = new Label(top, SWT.NONE);
        labelFatturaAssociata.setBounds(new Rectangle(12, 205, 332, 29));
        labelFatturaAssociata.setText("Fattura correttamente creata e associata alla visita");
        labelFatturaAssociata.setVisible(false);
        buttonOk = new Button(top, SWT.NONE);
        buttonOk.setBounds(new Rectangle(359, 206, 150, 26));
        buttonOk.setText("Ok");
        buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		
        		System.out.println("widgetSelected()"); 
        	}
        });
        
        fatt = FatturaDAO.getFatture();
		ArrayList<String> f = new ArrayList<String>();
		for (Fattura fattura : fatt) {
			f.add("Data fattura: "+fattura.getData().getDate()+"/"+(fattura.getData().getMonth()+1)+"/"+(fattura.getData().getYear()+1900)+"  -- Descrizione: "+fattura.getDescrizione()+"  -- Note: "+fattura.getNote());
		}
		String[] FattureArray = (String[]) f.toArray((new String[0]));
        listFatture.setItems(FattureArray);
        listFatture.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
        	public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
        		createSShellNuovaFattura();
        		sShellNuovaFattura.setText("Associa una fattura");
        		FatturaDAO fatturaSel = new FatturaDAO();
        		int fatturaselez = listFatture.getSelectionIndex();
        		Fattura fatturasel = fatt.get(fatturaselez);
        		int idFatturasel = fatturasel.getIdFattura();
        		fatturaSelezionata = fatturaSel.getFatturaByID(idFatturasel);
        		textDescrizioneFattura.setText(fatturaSelezionata.getDescrizione());
        		textImportoFatt.setText(""+fatturaSelezionata.getImporto());
        		textImportoAcconto.setText(""+fatturaSelezionata.getAcconto());
        		textImportoSconto.setText(""+fatturaSelezionata.getImportoSconto());
        		textAreaNoteFattura.setText(fatturaSelezionata.getNote());
        		buttonRegistraFattura.setVisible(false);
        		buttonAggiornaFattura.setVisible(true);
        		
        		System.out.println("widgetSelected()"); 
        	}
        });
        listViewer = new ListViewer(listFatture);
	}
	
	private void createSShellNuovaFattura() {
		sShellNuovaFattura = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		//sShellNuovaFattura.setLayout(new GridLayout());
		sShellNuovaFattura.setSize(new Point(527, 366));
		sShellNuovaFattura.setText("Crea una nuova fattura");
		labelImportoFatt = new Label(sShellNuovaFattura, SWT.WRAP);
		labelImportoFatt.setBounds(new Rectangle(301, 15, 98, 33));
		labelImportoFatt.setText("Importo fattura (euro):");
		textImportoFatt = new Text(sShellNuovaFattura, SWT.BORDER);
		textImportoFatt.setBounds(new Rectangle(407, 15, 92, 27));		
		
		labelSconto = new Label(sShellNuovaFattura, SWT.WRAP);
		labelSconto.setBounds(new Rectangle(265, 73, 136, 21));
		labelSconto.setText("E' applicato uno sconto?");
		radioButtonScontoSi = new Button(sShellNuovaFattura, SWT.RADIO);
		radioButtonScontoSi.setBounds(new Rectangle(412, 75, 32, 16));
		radioButtonScontoSi.setText("Si");
		radioButtonScontoSi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				labelImportoSconto.setVisible(true);
				textImportoSconto.setVisible(true);
			System.out.println("widgetSelected()"); 
		}
	});
		radioButtonScontoNo = new Button(sShellNuovaFattura, SWT.RADIO);
		radioButtonScontoNo.setBounds(new Rectangle(447, 75, 39, 16));
		radioButtonScontoNo.setText("No");
		radioButtonScontoNo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				labelImportoSconto.setVisible(false);
				textImportoSconto.setVisible(false);
			System.out.println("widgetSelected()"); 
		}
	});
		labelImportoSconto = new Label(sShellNuovaFattura, SWT.NONE);
		labelImportoSconto.setBounds(new Rectangle(265, 113, 134, 21));
		labelImportoSconto.setText("Importo sconto (euro):");
		labelImportoSconto.setVisible(false);
		textImportoSconto = new Text(sShellNuovaFattura, SWT.BORDER);
		textImportoSconto.setBounds(new Rectangle(406, 113, 83, 21));
		textImportoSconto.setVisible(false);
		createGroupScontoFattura();
		labelNoteFattura = new Label(sShellNuovaFattura, SWT.NONE);
		labelNoteFattura.setBounds(new Rectangle(15, 172, 97, 18));
		labelNoteFattura.setText("Note (eventuali):");
		textAreaNoteFattura = new Text(sShellNuovaFattura, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaNoteFattura.setBounds(new Rectangle(124, 172, 276, 84));
		buttonRegistraFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonRegistraFattura.setBounds(new Rectangle(135, 270, 142, 29));
		buttonRegistraFattura.setText("Registra/Associa fattura");
		buttonRegistraFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FatturaDAO fattura = new FatturaDAO();
						double importoFattura = Double.parseDouble(textImportoFatt.getText());						
						if (radioButtonImportoSi.getSelection() && radioButtonScontoSi.getSelection()) {
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, 0, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoSi.getSelection() && radioButtonScontoNo.getSelection()){
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, 0, 0, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoSi.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, importoAcconto, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoNo.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							idFatt = fattura.registraFattura(textDescrizioneFattura.getText(), importoFattura, importoAcconto, 0, textAreaNoteFattura.getText());
						}
						// TODO associare la fattura alla visita
						labelFatturaAssociata.setVisible(true);
						listFatture.setEnabled(false);
						buttonCreaNuovaFattura.setEnabled(false);
						buttonAssociaFattura.setEnabled(false);
						sShellNuovaFattura.close();
						System.out.println("fattura registrata"); 
					}
				});
		buttonAnnullaFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonAnnullaFattura.setBounds(new Rectangle(286, 270, 107, 29));
		buttonAnnullaFattura.setText("Chiudi");
		createCompositeAcconto();
		labelDescrizioneFattura = new Label(sShellNuovaFattura, SWT.WRAP);
		labelDescrizioneFattura.setBounds(new Rectangle(8, 6, 62, 35));
		labelDescrizioneFattura.setText("Descrizione fattura:");
		textDescrizioneFattura = new Text(sShellNuovaFattura, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		textDescrizioneFattura.setBounds(new Rectangle(76, 6, 189, 37));
		buttonAggiornaFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonAggiornaFattura.setBounds(new Rectangle(135, 270, 142, 29));
		buttonAggiornaFattura.setText("Aggiorna/Associa fattura");
		buttonAggiornaFattura.setVisible(false);
		buttonAggiornaFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						FatturaDAO fattura = new FatturaDAO(); 
						//fattura.aggiornaFattura(fatturaSelezionata, textDescrizioneFattura.getText(), textImportoFatt.getText(), textImportoAcconto.getText(), textImportoSconto.getText(), textAreaNoteFattura.getText());
						double importoFattura = Double.parseDouble(textImportoFatt.getText());						
						if (radioButtonImportoSi.getSelection() && radioButtonScontoSi.getSelection()) {
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, 0, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoSi.getSelection() && radioButtonScontoNo.getSelection()){
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, 0, 0, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoSi.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							double importoSconto = Double.parseDouble(textImportoSconto.getText());
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, importoAcconto, importoSconto, textAreaNoteFattura.getText());
						} else if (radioButtonImportoNo.getSelection() && radioButtonScontoNo.getSelection()){
							double importoAcconto = Double.parseDouble(textImportoAcconto.getText());
							fattura.aggiornaFattura(fatturaSelezionata,textDescrizioneFattura.getText(), importoFattura, importoAcconto, 0, textAreaNoteFattura.getText());
						}
						// TODO associare la fattura alla visita
						labelFatturaAssociata.setVisible(true);
						labelFatturaAssociata.setText("Fattura correttamente modificata e associata alla visita");
						listFatture.setEnabled(false);
						buttonCreaNuovaFattura.setEnabled(false);
						buttonAssociaFattura.setEnabled(false);
						sShellNuovaFattura.close();
						System.out.println("fattura aggiornata/associata"); 
					}
				});
		buttonAnnullaFattura
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShellNuovaFattura.close();
						System.out.println("widgetSelected()"); 
					}
				});
		
		sShellNuovaFattura.open();
	}

	private void createCompositeAcconto() {
		compositeAcconto = new Composite(sShellNuovaFattura, SWT.NONE);
		//compositeAcconto.setLayout(new GridLayout());
		compositeAcconto.setBounds(new Rectangle(7, 49, 246, 117));
		labelImportoPagato = new Label(compositeAcconto, SWT.WRAP);
		labelImportoPagato.setBounds(new Rectangle(10, 15, 115, 45));
		labelImportoPagato.setText("L'importo della fattura è stato pagato totalmente?");
		radioButtonImportoSi = new Button(compositeAcconto, SWT.RADIO);
		radioButtonImportoSi.setBounds(new Rectangle(140, 20, 38, 20));
		radioButtonImportoSi.setText("Si");
		radioButtonImportoSi.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					labelAcconto.setVisible(false);
					textImportoAcconto.setVisible(false);
				System.out.println("widgetSelected()"); 
			}
		});
		radioButtonImportoNo = new Button(compositeAcconto, SWT.RADIO);
		radioButtonImportoNo.setBounds(new Rectangle(185, 20, 39, 20));
		radioButtonImportoNo.setText("No");
		radioButtonImportoNo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					labelAcconto.setVisible(true);
					textImportoAcconto.setVisible(true);
				System.out.println("widgetSelected()");
			}
		});
		labelAcconto = new Label(compositeAcconto, SWT.WRAP);
		labelAcconto.setBounds(new Rectangle(10, 70, 118, 32));
		labelAcconto.setText("Importo versato (euro):");
		labelAcconto.setVisible(false);
		textImportoAcconto = new Text(compositeAcconto, SWT.BORDER);
		textImportoAcconto.setBounds(new Rectangle(130, 72, 83, 21));
		textImportoAcconto.setVisible(false);
		createGroupImportoAcconto();
	}

	private void createGroupImportoAcconto() {
		groupImportoAcconto = new Group(compositeAcconto, SWT.NONE);
		//groupImportoAcconto.setLayout(new GridLayout());
		groupImportoAcconto.setText("Stato pagamento fattura");
		groupImportoAcconto.setBounds(new Rectangle(3, 0, 240, 110));
	}
	
	private void createGroupScontoFattura() {
		groupScontoFattura = new Group(sShellNuovaFattura, SWT.NONE);
		groupScontoFattura.setLayout(new GridLayout());
		groupScontoFattura.setText("Sconto sulla fattura");
		groupScontoFattura.setBounds(new Rectangle(255, 52, 247, 105));
	}
	
	@Override
	public void setFocus() {
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,571,328"
