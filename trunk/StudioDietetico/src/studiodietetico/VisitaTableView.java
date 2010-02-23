package studiodietetico;

import hibernate.Fattura;
import hibernate.Visita;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import command.FatturaDAO;
import command.VisitaDAO;
import common.Utils;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;

public class VisitaTableView extends ViewPart {
	private Composite top = null;
	private ProvaTableForm classVis;
	private ArrayList<Object> visite;  //  @jve:decl-index=0:
	private Shell sShellNuovaFattura = null;  //  @jve:decl-index=0:visual-constraint="10,425"
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
	private Button buttonAggiornaFattura = null;
	private int idFatt;
	private Shell sShellMessElimina;
	private Shell sShellFatture = null;  //  @jve:decl-index=0:visual-constraint="7,808"
	private Table tableConti = null;
	private ArrayList<Fattura> fatture;  //  @jve:decl-index=0:
	private Composite compositeShell = null;
	private Fattura fatturaSelezionata = null;
	private Visita visitaSel = null;
	
	public VisitaTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		visite = VisitaDAO.getVisite();
		//TODO aggiungere parametri
		classVis = new ProvaTableForm(top, SWT.BORDER, visite, "","","","VisitaTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		
		classVis.nascondiColonne(new int[] {0,1,2,3,6,7,8});

		aggiungiColonne(classVis, visite);
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 9);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 10);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 11);
		
		Button buttonCreaAssociaFattura = new Button(classVis.top, SWT.NONE);
		buttonCreaAssociaFattura.setBounds(260, 284, 150, 25);
		buttonCreaAssociaFattura.setText("Crea e associa una fattura");
		buttonCreaAssociaFattura.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(classVis.getTableVisualizzazione().getSelectionCount()>0) {
					createSShellNuovaFattura(); 
				} else {
					createMessSelElemCanc();
				}
			}
		});
		
		Button buttonAssociaFattura = new Button(classVis.top, SWT.NONE);
		buttonAssociaFattura.setBounds(420, 284, 150, 25);
		buttonAssociaFattura.setText("Associa ad un conto");
		buttonAssociaFattura.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(classVis.getTableVisualizzazione().getSelectionCount()>0) {
					createSShellFatture();
				} else {
					createMessSelElemCanc();
				}
			}
		});

	}
	
	public static void aggiungiColonne(ProvaTableForm classVis, ArrayList<Object> visite) {
		TableColumn colonna = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna.setText("Medico");
		String nome = "";
		TableItem itemSel = null;
		for (int j = 0; j < visite.size(); j++) {
			nome = ((Visita)visite.get(j)).getMedico().getCognome()+" "+((Visita)visite.get(j)).getMedico().getNome();
			itemSel = classVis.getTableVisualizzazione().getItem(j);
			itemSel.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome);
		} 
		colonna.pack();
		colonna.setResizable(false);
		
		TableColumn colonna2 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna2.setText("Paziente");
		String nome2 = "";
		TableItem itemSel2 = null;
		for (int j = 0; j < visite.size(); j++) {
			nome2 = ((Visita)visite.get(j)).getPrenotazione().getPaziente().getCognome()+" "+((Visita)visite.get(j)).getPrenotazione().getPaziente().getNome();
			itemSel2 = classVis.getTableVisualizzazione().getItem(j);
			itemSel2.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome2);
		} 
		colonna2.pack();
		colonna2.setResizable(false);
		
		TableColumn colonna3 = new TableColumn(classVis.getTableVisualizzazione(), SWT.CENTER);
		colonna3.setText("Fatturata");
		String nome3 = "";
		TableItem itemSel3 = null;
		for (int j = 0; j < visite.size(); j++) {
			if (((Visita)visite.get(j)).getFattura()!=null) {
				nome3="si";
				itemSel3 = classVis.getTableVisualizzazione().getItem(j);
				itemSel3.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome3);
			} else {
				nome3="no";
				itemSel3 = classVis.getTableVisualizzazione().getItem(j);
				itemSel3.setText(classVis.getTableVisualizzazione().getColumnCount()-1, nome3);
			}
		} 
		colonna3.pack();
		colonna3.setResizable(false);
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
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
						
						TableItem itemSel = classVis.getTableVisualizzazione().getItem(classVis.getTableVisualizzazione().getSelectionIndex());
						int idVisitaSel = Integer.parseInt(itemSel.getText(0));
						visitaSel = VisitaDAO.getVisitaByID(idVisitaSel);
						Fattura fatturaCreata = FatturaDAO.getFatturaByID(idFatt);
						FatturaDAO.associaFattura(fatturaCreata, visitaSel);
						itemSel.setText(11, "si"); 
						System.out.println("visita "+visitaSel.getIdVisita()+" fattura "+fatturaCreata.getIdFattura());
						sShellNuovaFattura.close();
						System.out.println("fattura registrata"); 
					}
				});
		buttonAnnullaFattura = new Button(sShellNuovaFattura, SWT.NONE);
		buttonAnnullaFattura.setBounds(new Rectangle(286, 270, 107, 29));
		buttonAnnullaFattura.setText("Annulla");
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
						TableItem itemSel = classVis.getTableVisualizzazione().getItem(classVis.getTableVisualizzazione().getSelectionIndex());
						int idVisitaSel = Integer.parseInt(itemSel.getText(0));
						visitaSel = VisitaDAO.getVisitaByID(idVisitaSel);
						FatturaDAO.associaFattura(fatturaSelezionata, visitaSel);
						itemSel.setText(11, "si"); 
						System.out.println("visita "+visitaSel.getIdVisita()+" fattura "+fatturaSelezionata.getIdFattura());
						sShellNuovaFattura.close();
						sShellFatture.close();
						System.out.println("conto aggiornato e associato"); 
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

	/**
	 * This method initializes sShellFatture	
	 *
	 */
	private void createSShellFatture() {
		sShellFatture = new Shell();
		compositeShell = new Composite(sShellFatture, SWT.NONE);
		compositeShell.setBounds(new Rectangle(3, 2, 590, 331));
		sShellFatture.setText("Selezionare un conto dalla tabella");		
		sShellFatture.setSize(new Point(611, 371));
		fatture = FatturaDAO.getFatture();
		tableConti = new Table(compositeShell, SWT.FILL | SWT.BORDER | SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL);
		tableConti.setHeaderVisible(true);
		tableConti.setLinesVisible(true);
		tableConti.setSize(new Point(589, 328));
		tableConti.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				createSShellNuovaFattura();
        		sShellNuovaFattura.setText("Aggiorna e associa un conto");
        		labelDescrizioneFattura.setText("Descrizione conto:");
        		labelImportoFatt.setText("Importo conto (euro):");
        		labelImportoPagato.setText("L'importo è stato pagato totalmente?");
        		groupImportoAcconto.setText("Stato pagamento");
        		groupScontoFattura.setText("Sconto");
        		buttonAggiornaFattura.setText("Aggiorna/Associa conto");
        		TableItem[] itemSelez = tableConti.getSelection();
        		TableItem item = itemSelez[0];
        		int idFattSelez = Integer.parseInt(item.getText(0));
        		fatturaSelezionata = FatturaDAO.getFatturaByID(idFattSelez);
        		textDescrizioneFattura.setText(fatturaSelezionata.getDescrizione());
        		textImportoFatt.setText(""+fatturaSelezionata.getImporto());
        		textImportoAcconto.setText(""+fatturaSelezionata.getAcconto());
        		textImportoSconto.setText(""+fatturaSelezionata.getImportoSconto());
        		textAreaNoteFattura.setText(fatturaSelezionata.getNote());
        		buttonRegistraFattura.setVisible(false);
        		buttonAggiornaFattura.setVisible(true);
			}
		});
		TableColumn colonnaId = new TableColumn(tableConti, SWT.CENTER);	
		colonnaId.setText("id");
		colonnaId.setWidth(0);
		colonnaId.setResizable(false);
		TableColumn colonnaData = new TableColumn(tableConti, SWT.CENTER);	
		colonnaData.setText("Data ultima modifica");
		TableColumn colonnaDescrizione = new TableColumn(tableConti, SWT.CENTER);	
		colonnaDescrizione.setText("Descrizione");
		TableColumn colonnaNote = new TableColumn(tableConti, SWT.CENTER);	
		colonnaNote.setText("Note");
		for (int i = 0; i < fatture.size(); i++) {
			TableItem item = new TableItem(tableConti, SWT.NONE);
			item.setText(0, ""+fatture.get(i).getIdFattura());
			item.setText(1, ""+fatture.get(i).getData());
			item.setText(2, fatture.get(i).getDescrizione());
			item.setText(3, fatture.get(i).getNote());
		}
		//elimina i secondi e i millisecondi dagli items
		for (TableItem item : tableConti.getItems()) {
			int i = 1;
			String testoitem = item.getText(i);
			int lunghezzaTestoItem = item.getText(i).length();
			item.setText(i, testoitem.substring(0, lunghezzaTestoItem-5));
		}
		for (int i = 1; i < tableConti.getColumnCount(); i++) {
			tableConti.getColumn(i).pack();
			tableConti.getColumn(i).setResizable(false);
		}
		
		classVis.ordinamentoData(tableConti, 1);
		classVis.ordinamentoStringhe(tableConti, 2);
		classVis.ordinamentoStringhe(tableConti, 3);
		
		sShellFatture.open();
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,310,351"
