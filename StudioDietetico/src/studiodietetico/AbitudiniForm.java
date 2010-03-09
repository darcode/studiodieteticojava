package studiodietetico;

import hibernate.Abitudinialimentari;
import hibernate.Assunzionebevande;
import hibernate.Modalitapastofuori;
import hibernate.Paziente;
import hibernate.Tipologiabevanda;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import command.AnamnesiDAO;
import common.GenericBean;
import common.Utils;
import common.ui.ListComposite;

import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.List;

public class AbitudiniForm extends ListComposite{
	private Abitudinialimentari abitudineSel = null;  //  @jve:decl-index=0:
	private Paziente pazienteSel = null;  //  @jve:decl-index=0:
	
	private Composite top = null;
	private Label labelSelItem;
	private Spinner spinnerNumPasti = null;
	private Button checkBoxColazione = null;
	private Label labelIntegratori = null;
	private Text textAreaIntegratori = null;
	private Label CibiNonGraditi;
	private Label labelCibiNonGraditi;
	private Text textAreaCibiNonGraditi;
	private Group gruppoPastiFuori;
	private Label labelLuogo;
	private Text textLuogo;
	private Label labelGiorno;
	private Label labelFrequenza;
	private List listGiornoSettimanale = null;
	private Table tableGiornoSettimanale;
	private Label labelNote;
	private Group gruppoBevande;
	private Button checkBoxPastoFuori;
	private Table tableBevande;
	private Button buttonInsertBevanda = null;
	private Shell sShellInserimentoBevanda;  //  @jve:decl-index=0:
	//BEVANDA
	private Label labelNomeBevanda;
	private Text textNomeBevanda;
	private Label labelDescBevanda;
	private Text textDescBevanda;
	private Button buttonInsertNewBevanda;
	private Button buttonAnnullaNewBevanda;
	private Button buttonInsertAbitudine;
	private Label labelPreferenzaSchema;
	private Text textAreaPreferenzaSchema;
	private Text textAreaNote;
	
	public AbitudiniForm(Composite parent, int style) {
		super(parent, style);
		initialize();
	}

	private void initialize() {       
		AnamnesiDAO ad  = new AnamnesiDAO();
		pazienteSel = AnamnesiTTableView.getPazienteSel();
		abitudineSel = (Abitudinialimentari)ad.getAbitudineByPaziente(pazienteSel);
		int numPastiAb = 1;
		boolean colazioneAb = false, pastiFuoriAb = false;
		String integratoriAb = "", gradimentoCibiAb = "", preferenzaSchemaAb = "", noteAb = "";
		if(abitudineSel != null) {
			numPastiAb = abitudineSel.getNumPastiGiorno();
			colazioneAb = abitudineSel.isPrimaColazione();
			pastiFuoriAb = !(abitudineSel.getModalitapastofuoris().isEmpty());
			integratoriAb = abitudineSel.getIntegratori();
			gradimentoCibiAb = abitudineSel.getCibiNonGraditi();
			preferenzaSchemaAb = abitudineSel.getPreferenzaSchemaDietetico();
			noteAb = abitudineSel.getNote();
		}
			
		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1, true);
		this.setLayout(glForm);
		this.setBackground(common.Utils.getStandardWhiteColor());
		
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		gdTop.grabExcessVerticalSpace = true;
		
		top = new Composite(this, SWT.BORDER);
		//top.setLayout(new GridLayout(2, true));
		//top.setBounds(new Rectangle(7, 7, 600, 500));
		top.setLayoutData(gdTop);
		
		labelSelItem = new Label(top, SWT.NONE);
		labelSelItem.setText("* Numero di pasti consumati in una giornata");
		labelSelItem.setBounds(new Rectangle(20, 10, 236, 20));
		spinnerNumPasti = new Spinner(top, SWT.LEFT);
		spinnerNumPasti.setMinimum(1);
		spinnerNumPasti.setBounds(new Rectangle(270, 10, 50, 20));
		spinnerNumPasti.setSelection(numPastiAb);
		
		checkBoxColazione = new Button(top, SWT.CHECK);
		checkBoxColazione.setText("* Indicare se si consuma abitualmente la prima colazione");
		checkBoxColazione.setBounds(new Rectangle(400, 10, 320, 20));
		checkBoxColazione.setSelection(colazioneAb);
		
		labelIntegratori = new Label(top, SWT.NONE);
		labelIntegratori.setText("Eventuali integratori assunti");
		labelIntegratori.setBounds(new Rectangle(20, 45, 230, 20));
		textAreaIntegratori = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaIntegratori.setBounds(new Rectangle(250, 45, 480, 40));
		textAreaIntegratori.setText(integratoriAb);
		
		labelCibiNonGraditi = new Label(top, SWT.NONE);
		labelCibiNonGraditi.setText("Cibi non graditi");
		labelCibiNonGraditi.setBounds(new Rectangle(20, 100, 230, 20));
		textAreaCibiNonGraditi = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaCibiNonGraditi.setBounds(new Rectangle(250, 100, 480, 40));
		textAreaCibiNonGraditi.setText(gradimentoCibiAb);
		
		labelPreferenzaSchema = new Label(top, SWT.NONE);
		labelPreferenzaSchema.setText("Organizzazione schema dietetico preferita");
		labelPreferenzaSchema.setBounds(new Rectangle(20, 155, 230, 20));
		textAreaPreferenzaSchema = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaPreferenzaSchema.setBounds(new Rectangle(250, 155, 480, 40));
		textAreaPreferenzaSchema.setText(preferenzaSchemaAb);
		
		checkBoxPastoFuori = new Button(top, SWT.CHECK);
		checkBoxPastoFuori.setText("Assunzione pasti fuori casa");
		checkBoxPastoFuori.setBounds(new Rectangle(20, 200, 230, 20));
		checkBoxPastoFuori.setSelection(pastiFuoriAb);
		checkBoxPastoFuori.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if (checkBoxPastoFuori.getSelection()) {
							gruppoPastiFuori.setEnabled(true);
						} else
							gruppoPastiFuori.setEnabled(false);
					}
				});
		
		gruppoPastiFuori = new Group(top, SWT.CHECK);
		gruppoPastiFuori.setBounds(new Rectangle(20, 225, 716, 138));
		gruppoPastiFuori.setText("Modalità pasti fuori");
		gruppoPastiFuori.setEnabled(false);
		
		tableGiornoSettimanale = new Table(gruppoPastiFuori, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tableGiornoSettimanale.setLinesVisible(true);
		tableGiornoSettimanale.setHeaderVisible(true);
		tableGiornoSettimanale.setBounds(new Rectangle(20, 20, 667, 106));
		final TableEditor editorPastoFuori = new TableEditor(tableGiornoSettimanale);
	    editorPastoFuori.horizontalAlignment = SWT.LEFT;
	    editorPastoFuori.grabHorizontal = true;
		tableGiornoSettimanale.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
					public void mouseDown(org.eclipse.swt.events.MouseEvent event) {
						//System.out.println("mouseDown()");
						//inizio table editor
						Rectangle clientArea = tableGiornoSettimanale.getClientArea();
				        Point pt = new Point(event.x, event.y);
				        int index = tableGiornoSettimanale.getTopIndex();
				        while (index < tableGiornoSettimanale.getItemCount()) {
				          boolean visible = false;
				          final TableItem item = tableGiornoSettimanale.getItem(index);
				          for (int i = 0; i < tableGiornoSettimanale.getColumnCount(); i++) {
				            Rectangle rect = item.getBounds(i);
				            if (rect.contains(pt)) {
				              final int column = i;
				              if(item.getChecked()) {
				              if(column ==2) {
				              final Text text = new Text(tableGiornoSettimanale, SWT.NONE);
				              Listener textListener = new Listener() {
				                public void handleEvent(final Event e) {
				                  switch (e.type) {
				                  case SWT.FocusOut:
				                    item.setText(column, text.getText());
				                    text.dispose();
				                    break;
				                  case SWT.Traverse:
				                    switch (e.detail) {
				                    case SWT.TRAVERSE_RETURN:
				                      item.setText(column, text.getText());
				                    //FALL THROUGH
				                    case SWT.TRAVERSE_ESCAPE:
				                      text.dispose();
				                      e.doit = false;
				                    }
				                    break;
				                  }
				                }

				              };
				              text.addListener(SWT.FocusOut, textListener);
				              text.addListener(SWT.Traverse, textListener);
				              editorPastoFuori.setEditor(text, item, column);
				              text.setText(item.getText(2));
				              text.selectAll();
				              text.setFocus();
				              } //endif
				              else if(column ==1) {
				              final Spinner spinnerFreq = new Spinner(tableGiornoSettimanale, SWT.NONE);
				              Listener spinnerListener = new Listener() {
				                public void handleEvent(final Event e) {
				                  switch (e.type) {
				                  case SWT.FocusOut:
				                	item.setText(column, ""+spinnerFreq.getSelection());
				                	spinnerFreq.dispose();
				                    break;
				                  case SWT.Traverse:
				                    switch (e.detail) {
				                    case SWT.TRAVERSE_RETURN:
				                      item.setText(column, ""+spinnerFreq.getSelection());
				                    //FALL THROUGH
				                    case SWT.TRAVERSE_ESCAPE:
				                    	spinnerFreq.dispose();
				                      e.doit = false;
				                    }
				                    break;
				                  }
				                }

				              };
				              spinnerFreq.addListener(SWT.FocusOut, spinnerListener);
				              spinnerFreq.addListener(SWT.Traverse, spinnerListener);
				              editorPastoFuori.setEditor(spinnerFreq, item, column);
				              //spinnerFreq.setSelection(Integer.parseInt(item.getText(1)));
				              spinnerFreq.setFocus();
				              } //endelse
				            }
				              return;
				            }
				            if (!visible && rect.intersects(clientArea)) {
				              visible = true;
				            }
				          } //end for
				        
				          if (!visible)
				            return;
				          index++;
				        }

					}
				});
		
		TableColumn columnGiorni = new TableColumn(tableGiornoSettimanale, SWT.NONE);
		columnGiorni.setText("Giorno settimanale");
		ArrayList<String> giorni = new ArrayList<String>();
		giorni.add("Lunedì"); giorni.add("Martedì"); giorni.add("Mercoledì");
		giorni.add("Giovedì"); giorni.add("Venerdì"); giorni.add("Sabato");
		giorni.add("Domenica");
		for (String gg : giorni) {
			TableItem item = new TableItem (tableGiornoSettimanale, SWT.NONE);
			item.setText(0,gg);
		}
		columnGiorni.pack();
		columnGiorni.setResizable(false);
		
		TableColumn columnFrequenza = new TableColumn(tableGiornoSettimanale, SWT.NONE);
		columnFrequenza.setText("Frequenza giornaliera");
		columnFrequenza.pack();
		columnFrequenza.setResizable(false);
		
		TableColumn columnLuogo = new TableColumn(tableGiornoSettimanale, SWT.NONE);
		columnLuogo.setText("Luogo");
		columnLuogo.setWidth(300);

		ArrayList<Modalitapastofuori> pastiFuori = ad.getListModalitaPastoFuori();
		for (Modalitapastofuori pastofuori : pastiFuori) {
			for (TableItem item : tableGiornoSettimanale.getItems()) {
				if (pastofuori.getSpecificaGiorno().equals(item.getText(0))) {
					item.setChecked(true);
					item.setText(1, pastofuori.getFrequenzaGiornaliera().toString());
					item.setText(2, pastofuori.getLuogo());
				}
			}
			
		}
		
		//ASSUNZIONE BEVANDE
		gruppoBevande = new Group(top, SWT.NONE);
		gruppoBevande.setBounds(new Rectangle(20, 370, 716, 155));
		gruppoBevande.setText("Assunzione bavande");
		
		tableBevande = new Table(gruppoBevande, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tableBevande.setLinesVisible(true);
		tableBevande.setHeaderVisible(true);
		tableBevande.setBounds(new Rectangle(20, 20, 545, 130));
		
		//AnamnesiDAO ad = new AnamnesiDAO(); 
		ArrayList<Object> listBevande = ad.getListTipoBevande();
		riempiTabellaTipoBevande(tableBevande, listBevande);
		for (TableColumn colonna : tableBevande.getColumns()) {
			colonna.pack();
			colonna.setResizable(false);
		}
		tableBevande.getColumn(0).setWidth(0);
		
		//aggiunge la colonna che visualizza la quantità della bevanda
		TableColumn colonnaQuant = new TableColumn(tableBevande, SWT.CENTER);
		colonnaQuant.setText("quantità");
		String quant = "";
		int idAbitudine=0;
		TableItem itemSel = null;
		for (int j = 0; j < listBevande.size(); j++) {
			int idBevanda = ((Tipologiabevanda)listBevande.get(j)).getIdTipologiaBevanda();
			if(abitudineSel!=null) {
				idAbitudine = abitudineSel.getIdAbitudiniAlimentari();
				//System.out.println("Bev ass- tipobev: "+idBevanda+" - abit: "+idAbitudine);
				Object bevandaAssunta = ad.getAssunzioneByAbitudineBevanda(idBevanda, idAbitudine);
				if (bevandaAssunta!=null) {
					quant = ((Assunzionebevande)bevandaAssunta).getQuantita();
					itemSel = tableBevande.getItem(j);
					itemSel.setText(3, quant);
					itemSel.setChecked(true);
				}
			}
		}
		colonnaQuant.pack();
		colonnaQuant.setResizable(false);
		
		TableForm.ordinamentoStringhe(tableBevande, 1);
		TableForm.ordinamentoStringhe(tableBevande, 2);
		TableForm.ordinamentoStringhe(tableBevande, 3);
		
		TableColumn colonnaCheck = new TableColumn(tableBevande, SWT.CENTER,0);
		colonnaCheck.setWidth(30);
		colonnaCheck.setResizable(false); 
		
		final TableEditor editorBevande = new TableEditor(tableBevande);
		editorBevande.horizontalAlignment = SWT.LEFT;
		editorBevande.grabHorizontal = true;
		tableBevande.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDown(org.eclipse.swt.events.MouseEvent event) {
				Rectangle clientArea = tableBevande.getClientArea();
		        Point pt = new Point(event.x, event.y);
		        int index = tableBevande.getTopIndex();
		        while (index < tableBevande.getItemCount()) {
		          boolean visible = false;
		          final TableItem item = tableBevande.getItem(index);
		          for (int i = 0; i < tableBevande.getColumnCount(); i++) {
		            Rectangle rect = item.getBounds(i);
		            if (rect.contains(pt)) {
		              final int column = i;
		              if(column ==4) {
		            	  if(item.getChecked()) {
		              final Text text = new Text(tableBevande, SWT.NONE);
		              Listener textListener = new Listener() {
		                public void handleEvent(final Event e) {
		                  switch (e.type) {
		                  case SWT.FocusOut:
		                    item.setText(column, text.getText());
		                    text.dispose();
		                    break;
		                  case SWT.Traverse:
		                    switch (e.detail) {
		                    case SWT.TRAVERSE_RETURN:
		                      item.setText(column, text.getText());
		                    //FALL THROUGH
		                    case SWT.TRAVERSE_ESCAPE:
		                      text.dispose();
		                      e.doit = false;
		                    }
		                    break;
		                  }
		                }

		              };
		              text.addListener(SWT.FocusOut, textListener);
		              text.addListener(SWT.Traverse, textListener);
		              editorBevande.setEditor(text, item, column);
		              text.setText(item.getText(4));
		              //text.selectAll();
		              text.setFocus();
		              } //end if check
		              } //endif
		            
		              return;
		            }
		            if (!visible && rect.intersects(clientArea)) {
		              visible = true;
		            }
		          } //end for
		          if (!visible)
		            return;
		          index++;
		        }

			}
		});
	    
		buttonInsertBevanda = new Button(gruppoBevande, SWT.NONE);
		buttonInsertBevanda.setBounds(new Rectangle(574, 126, 135, 23));
		buttonInsertBevanda.setText("Inserisci nuova bevanda");
		buttonInsertBevanda.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
				createSShellInserimentoBevanda();
			}
		});
		
	    labelNote = new Label(top, SWT.NONE);
		labelNote.setText("Note");
		labelNote.setBounds(new Rectangle(19, 530, 50, 20));
		textAreaNote = new Text(top, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaNote.setBounds(new Rectangle(20, 550, 571, 30));	
		textAreaNote.setText(noteAb);
		
		buttonInsertAbitudine = new Button(top, SWT.NONE);
		buttonInsertAbitudine.setBounds(new Rectangle(640, 550, 100, 28));
		buttonInsertAbitudine.setText("Conferma");
		buttonInsertAbitudine.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
				AnamnesiDAO ad = new AnamnesiDAO();
				//inserimento e modifica abitudine
				if (abitudineSel==null) 
					ad.registraAbitudine(spinnerNumPasti.getSelection(), checkBoxColazione.getSelection(), textAreaIntegratori.getText(), textAreaCibiNonGraditi.getText(), textAreaPreferenzaSchema.getText(), textAreaNote.getText(), pazienteSel);
				else
					ad.modificaAbitudine(abitudineSel.getIdAbitudiniAlimentari(), spinnerNumPasti.getSelection(), checkBoxColazione.getSelection(), textAreaIntegratori.getText(), textAreaCibiNonGraditi.getText(), textAreaPreferenzaSchema.getText(), textAreaNote.getText());
				
				//inserimento, modifica e cancellazione pasti fuori
				boolean mod = false;
				for (TableItem item : tableGiornoSettimanale.getItems()) {
					if(item.getChecked()) {
						mod = false;
						for (Modalitapastofuori pastiAbitudine : ad.getListPastoFuoriByAbitudine(abitudineSel)) {
							if (pastiAbitudine.getSpecificaGiorno().equals(item.getText(0))) {
								ad.modificaPastoFuori(pastiAbitudine, Integer.parseInt(item.getText(1)), item.getText(2));
								mod = true;
							}
						}
						if(!mod) {
							ad.registraPastoFuori(item.getText(0), Integer.parseInt(item.getText(1)), item.getText(2), abitudineSel);
						}
					} else {
						if(ad.getListPastoFuoriByAbitudine(abitudineSel)!=null) {
							for (Modalitapastofuori pastiAbitudine : ad.getListPastoFuoriByAbitudine(abitudineSel)) {
								if (pastiAbitudine.getSpecificaGiorno().equals(item.getText(0))) {
									ad.cancellaPastoFuori(pastiAbitudine.getIdModalitaPastoFuori());
								}
							}
						}
					}
				}
				
				
				
				//inserimento, modifica e cancellazione assunzione bavande
				for (TableItem itemBev : tableBevande.getItems()) {
					if(itemBev.getChecked()) {
						if(ad.getAssunzioneByAbitudineBevanda(Integer.parseInt(itemBev.getText(1)), abitudineSel.getIdAbitudiniAlimentari())==null) {
							System.out.println("IdBev insert: "+Integer.parseInt(itemBev.getText(1)));
							ad.registraAssunzioneBevande(abitudineSel, ad.getTipoBevandaById(itemBev.getText(1)), itemBev.getText(4));
						} else {
							System.out.println("IdBev modifica: "+Integer.parseInt(itemBev.getText(1)));
							ad.modificaAssunzioneBevande(abitudineSel.getIdAbitudiniAlimentari(), Integer.parseInt(itemBev.getText(1)), itemBev.getText(4));
						}
					} else {
						//se non è checkato cancella la relazione
						if(ad.getAssunzioneByAbitudineBevanda(Integer.parseInt(itemBev.getText(1)), abitudineSel.getIdAbitudiniAlimentari())!=null){
						System.out.println("IdBev canc: "+Integer.parseInt(itemBev.getText(1)));
						ad.cancellaAssunzioneBevande(abitudineSel.getIdAbitudiniAlimentari(), Integer.parseInt(itemBev.getText(1)));
						}
					}
				}
				
				//aggiorna le table in anamnesiTableView
				Utils.getActiveView().dispose();
				Utils.showView("StudioDietetico.AnamnesiTableView");	
				AnamnesiTTableView.selectTab(5);
			}
		});
	
	}
	
	private void createSShellInserimentoBevanda() {
		
		sShellInserimentoBevanda = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
		sShellInserimentoBevanda.setText("Inserimento nuova bevanda");
		sShellInserimentoBevanda.setSize(new Point(630, 240));
		
		labelNomeBevanda = new Label(sShellInserimentoBevanda, SWT.NONE);
		labelNomeBevanda.setBounds(new Rectangle(20, 20, 145, 20));
		labelNomeBevanda.setText("* Nome bevanda");
		textNomeBevanda = new Text(sShellInserimentoBevanda, SWT.NONE);
		textNomeBevanda.setBounds(new Rectangle(180, 20, 375, 20));
		
		labelDescBevanda = new Label(sShellInserimentoBevanda, SWT.NONE);
		labelDescBevanda.setBounds(new Rectangle(20, 60, 145, 20));
		labelDescBevanda.setText("Descrizione bevanda");
		textDescBevanda = new Text(sShellInserimentoBevanda, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textDescBevanda.setBounds(new Rectangle(180, 60, 390, 60));
		
		buttonInsertNewBevanda = new Button(sShellInserimentoBevanda, SWT.NONE);
		buttonInsertNewBevanda.setBounds(new Rectangle(400, 150, 90, 28));
		buttonInsertNewBevanda.setText("Conferma");
		buttonInsertNewBevanda.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
				if(textNomeBevanda.getText()!="") {
					AnamnesiDAO ad = new AnamnesiDAO();
					ad.registraBevanda(textNomeBevanda.getText(),textDescBevanda.getText());
					
					tableBevande.removeAll();
					int k = 0;
					while (k<tableBevande.getColumnCount()) {
						tableBevande.getColumn(k).dispose();
					}
					
					ArrayList<Object> listBevande = ad.getListTipoBevande();
					riempiTabellaTipoBevande(tableBevande, listBevande);
					for (TableColumn colonna : tableBevande.getColumns()) {
						colonna.pack();
						colonna.setResizable(false);
					}
					tableBevande.getColumn(0).setWidth(0);
					
					//aggiunge la colonna che visualizza la quantità della bevanda
					TableColumn colonnaQuant = new TableColumn(tableBevande, SWT.CENTER);
					colonnaQuant.setText("quantità");
					String quant = "";
					int idAbitudine =0;
					TableItem itemSel = null;
					for (int j = 0; j < listBevande.size(); j++) {
						int idBevanda = ((Tipologiabevanda)listBevande.get(j)).getIdTipologiaBevanda();
						if(abitudineSel!=null) {
							idAbitudine = abitudineSel.getIdAbitudiniAlimentari();
							Object bevandaAssunta = ad.getAssunzioneByAbitudineBevanda(idBevanda, idAbitudine);
							if (bevandaAssunta!=null) {
								quant = ((Assunzionebevande)bevandaAssunta).getQuantita();
								itemSel = tableBevande.getItem(j);
								itemSel.setText(3, quant);
								itemSel.setChecked(true);
							}
						}
					}
					colonnaQuant.pack();
					colonnaQuant.setResizable(false);
					
					TableForm.ordinamentoStringhe(tableBevande, 1);
					TableForm.ordinamentoStringhe(tableBevande, 2);
					TableForm.ordinamentoStringhe(tableBevande, 3);
					
					TableColumn colonnaCheck = new TableColumn(tableBevande, SWT.CENTER,0);
					colonnaCheck.setWidth(30);
					colonnaCheck.setResizable(false);
				}
				sShellInserimentoBevanda.close();
			}
		});
		
		buttonAnnullaNewBevanda = new Button(sShellInserimentoBevanda, SWT.NONE);
		buttonAnnullaNewBevanda.setBounds(new Rectangle(500, 150, 90, 28));
		buttonAnnullaNewBevanda.setText("Annulla");
		buttonAnnullaNewBevanda.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
						sShellInserimentoBevanda.close();
					}
				});
		
		sShellInserimentoBevanda.open();
	}
	
	private void riempiTabellaTipoBevande(Table table, ArrayList<Object> lista) {
		ArrayList<String> colonne = new ArrayList<String>();
		colonne.add("idTipologiaBevanda");
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
	
	/*private void riempiTabellaBevande(Table table, ArrayList<Object> lista) {
		ArrayList<String> colonne = new ArrayList<String>();
		colonne.add("id");
		colonne.add("tipologiabevanda");
		colonne.add("abitudinialimentari");
		colonne.add("quantita");
		
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
					if  (valuesObj[i] instanceof Tipologiabevanda) {  
						valuesObj[i] = ((Tipologiabevanda)valuesObj[i]).getIdTipologiaBevanda();
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
	}*/
}
