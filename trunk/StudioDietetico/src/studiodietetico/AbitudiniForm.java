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
		GridData gdTable =  new GridData();
		gdTable.horizontalAlignment = SWT.FILL;
		gdTable.verticalAlignment = SWT.FILL;
		gdTable.grabExcessHorizontalSpace = true;
		gdTable.grabExcessVerticalSpace = true;
			
		GridData gdForm = new GridData(SWT.BORDER);
		gdForm.grabExcessHorizontalSpace = true;
		gdForm.horizontalAlignment = SWT.FILL;
		gdForm.verticalAlignment = SWT.FILL;
		gdForm.grabExcessVerticalSpace = true;
		this.setLayoutData(gdForm);
		GridLayout glForm = new GridLayout(1,false);
		this.setLayout(glForm);
		this.setBackground(common.Utils.getStandardWhiteColor());
		
		GridData gdTop = new GridData();
		gdTop.horizontalAlignment = SWT.FILL;
		gdTop.verticalAlignment = SWT.FILL;
		gdTop.grabExcessHorizontalSpace = true;
		
		top = new Composite(this, SWT.NONE);
		top.setLayout(new GridLayout(3, false));
		top.setLayoutData(gdTop);
		GridData gdLabel =  new GridData();
		gdLabel.horizontalAlignment = SWT.FILL;
		
		labelSelItem = new Label(top, SWT.NONE);
		labelSelItem.setText("* Numero di pasti consumati in una giornata");
		labelSelItem.setLayoutData(gdLabel);
		spinnerNumPasti = new Spinner(top, SWT.LEFT);
		spinnerNumPasti.setMinimum(1);
		spinnerNumPasti.setSelection(numPastiAb);
		
		checkBoxColazione = new Button(top, SWT.CHECK);
		checkBoxColazione.setText("* Indicare se si consuma abitualmente la prima colazione");
		checkBoxColazione.setSelection(colazioneAb);
		
		labelIntegratori = new Label(top, SWT.NONE);
		labelIntegratori.setText("Eventuali integratori assunti");
		labelIntegratori.setLayoutData(gdLabel);
		textAreaIntegratori = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaIntegratori.setText(integratoriAb);
		GridData gdTxtInte = new GridData(SWT.BORDER);
		gdTxtInte.horizontalSpan = 2;
		gdTxtInte.grabExcessHorizontalSpace = true;
		gdTxtInte.horizontalAlignment = SWT.FILL;
		textAreaIntegratori.setLayoutData(gdTxtInte);
		
		labelCibiNonGraditi = new Label(top, SWT.NONE);
		labelCibiNonGraditi.setText("Cibi non graditi");
		labelCibiNonGraditi.setLayoutData(gdLabel);
		textAreaCibiNonGraditi = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaCibiNonGraditi.setText(gradimentoCibiAb);
		textAreaCibiNonGraditi.setLayoutData(gdTxtInte);
		
		labelPreferenzaSchema = new Label(top, SWT.NONE);
		labelPreferenzaSchema.setText("Organizzazione schema dietetico preferita");
		labelPreferenzaSchema.setLayoutData(gdLabel);
		textAreaPreferenzaSchema = new Text(top, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaPreferenzaSchema.setText(preferenzaSchemaAb);
		textAreaPreferenzaSchema.setLayoutData(gdTxtInte);
		
		checkBoxPastoFuori = new Button(top, SWT.CHECK | SWT.TOP);
		checkBoxPastoFuori.setText("Assunzione pasti fuori casa");
		checkBoxPastoFuori.setSelection(pastiFuoriAb);
		checkBoxPastoFuori.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if (checkBoxPastoFuori.getSelection()) {
							gruppoPastiFuori.setEnabled(true);
						} else
							gruppoPastiFuori.setEnabled(false);
					}
				});
		
		GridData gdCenter = new GridData();
		gdCenter.horizontalAlignment = SWT.FILL;
		gdCenter.verticalAlignment = SWT.FILL;
		gdCenter.grabExcessHorizontalSpace = true;
		gdCenter.grabExcessVerticalSpace = true;
		
		Composite center = new Composite(this, SWT.NONE);
		center.setLayout(new GridLayout(2, false));
		center.setLayoutData(gdCenter);
		
		gruppoPastiFuori = new Group(top, SWT.NONE);
		GridData gdgruppoPastiFuori =  new GridData();
		gdgruppoPastiFuori.horizontalAlignment = SWT.FILL;
		gdgruppoPastiFuori.verticalAlignment = SWT.FILL;
		gdgruppoPastiFuori.grabExcessHorizontalSpace = true;
		gdgruppoPastiFuori.grabExcessVerticalSpace = true;
		gdgruppoPastiFuori.horizontalSpan = 2;
		gruppoPastiFuori.setLayoutData(gdgruppoPastiFuori);
		gruppoPastiFuori.setLayout(new GridLayout(1,true));
		gruppoPastiFuori.setText("Modalità pasti fuori");
		gruppoPastiFuori.setEnabled(false);
		GridData gdTblGiorni = new GridData();
		gdTblGiorni.horizontalAlignment = SWT.FILL;
		gdTblGiorni.verticalAlignment = SWT.FILL;
		gdTblGiorni.grabExcessHorizontalSpace = true;
		gdTblGiorni.grabExcessVerticalSpace = true;
		tableGiornoSettimanale = new Table(gruppoPastiFuori, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		tableGiornoSettimanale.setLinesVisible(true);
		tableGiornoSettimanale.setHeaderVisible(true);
		tableGiornoSettimanale.setLayoutData(gdTblGiorni);
		
		GridData gdtableGiornoSettimanale =  new GridData();
		gdtableGiornoSettimanale.horizontalAlignment = SWT.FILL;
		gdtableGiornoSettimanale.verticalAlignment = SWT.FILL;
		gdtableGiornoSettimanale.grabExcessHorizontalSpace = true;
		gdtableGiornoSettimanale.grabExcessVerticalSpace = true;
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
		tableGiornoSettimanale.setLayoutData(gdtableGiornoSettimanale);
		
		GridData gdBottom = new GridData();
		gdBottom.horizontalAlignment = SWT.FILL;
		gdBottom.verticalAlignment = SWT.FILL;
		gdBottom.grabExcessHorizontalSpace = true;
		
		
		
		//ASSUNZIONE BEVANDE
		gruppoBevande = new Group(center, SWT.NONE);
		gruppoBevande.setText("Assunzione bavande");
		GridData gdgruppoBevande =  new GridData();
		gdgruppoBevande.horizontalAlignment = SWT.FILL;
		gdgruppoBevande.verticalAlignment = SWT.FILL;
		gdgruppoBevande.grabExcessHorizontalSpace = true;
		gdgruppoBevande.grabExcessVerticalSpace = true;
		gruppoBevande.setLayoutData(gdgruppoBevande);
		gruppoBevande.setLayout(new GridLayout(1,false));
		tableBevande = new Table(gruppoBevande,SWT.BORDER| SWT.CHECK | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gdTblBevande = new GridData();
		gdTblBevande.horizontalAlignment = SWT.FILL;
		gdTblBevande.verticalAlignment = SWT.FILL;
		gdTblBevande.grabExcessHorizontalSpace = true;
		gdTblBevande.grabExcessVerticalSpace = true;
		tableBevande.setLayoutData(gdTblBevande);
		tableBevande.setLinesVisible(true);
		tableBevande.setHeaderVisible(true);
		
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
	    
		buttonInsertBevanda = new Button(center, SWT.NONE);
		buttonInsertBevanda.setText("Inserisci nuova bevanda");
		buttonInsertBevanda.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {						
				createSShellInserimentoBevanda();
			}
		});
		Composite bottom = new Composite(this, SWT.BORDER);
		bottom.setLayout(new GridLayout(2, false));
		bottom.setLayoutData(gdBottom);
		
	    labelNote = new Label(bottom, SWT.NONE);
		labelNote.setText("Note");
		labelNote.setLayoutData(gdLabel);
		textAreaNote = new Text(bottom, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		textAreaNote.setText(noteAb);
		GridData gdTxtNote = new GridData(SWT.BORDER);
		gdTxtNote.grabExcessHorizontalSpace = true;
		gdTxtNote.horizontalAlignment = SWT.FILL;
		gdTxtNote.heightHint = 40;
		textAreaNote.setLayoutData(gdTxtNote);
		
		buttonInsertAbitudine = new Button(this, SWT.CENTER);
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
