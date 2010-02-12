package forms;

import hibernate.Paziente;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import service.Utils;
import studiodietetico.AnamnesiShell;
import studiodietetico.InserisciDietaView;
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
	private static Table listElencoPazienti = null;
	private Label labelSelPaz = null;
	private Button buttonInsNewPaz = null;
	private Button buttonPrenota = null;
	private Button buttonAnagrafica = null;
	private Button buttonVisualizzaDieta = null;
	private Button buttonAnamnesi = null;
	private static ArrayList<Object> paz; // @jve:decl-index=0:
	private Button buttonInsDieta = null;

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
		labelSelPaz.setText("Selezonare il paziente dalla lista");
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
		buttonInsDieta = new Button(top, SWT.NONE);
		buttonInsDieta.setText("Inserisci dieta");
		buttonInsDieta
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						Utils.showView(InserisciDietaView.VIEW_ID);
					}
				});
		buttonAnamnesi
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(
							org.eclipse.swt.events.SelectionEvent e) {
						Utils.showView("StudioDietetico.ProvaAnamnesiView");
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
		riempiTabellaEntita(listElencoPazienti, paz);
		listElencoPazienti.getColumn(0).setWidth(0);
		// ArrayList<String> p = new ArrayList<String>();
		// for (Object paziente : paz) {
		// p.add(((Paziente)paziente).getCognome() + " " +
		// ((Paziente)paziente).getNome() + "  ("
		// + ((Paziente)paziente).getDataNascita() + ")");
		// }
		// String[] pazientiArray = (String[]) p.toArray((new String[0]));
		
		
		//ordinamento
		Listener sortListener = new Listener() {
	        public void handleEvent(Event e) {
	            TableItem[] items = listElencoPazienti.getItems();
	            Collator collator = Collator.getInstance(Locale.getDefault());
	            TableColumn column = (TableColumn)e.widget;
	            /*int index = 0;
	            if(column == columnNome){
	            	index = 0;
	            }else if (column == columnTipologia) {
	            	index = 1;
				}else if (column == columnCalorie) {
					index = 2;
				}else if (column == columnIdAlimento) {
					index = 3;
				}*/
	            String value1 = null;
	            String value2 = null;
	            //int valueInt1 = 0;
	            //int valueInt2 = 0;
	            for (int i = 1; i < items.length; i++) {
	                //if((index==0)||(index==1)) {
	                	value1 = items[i].getText(1);
	                	for (int j = 0; j < i; j++){
		                    value2 = items[j].getText(2);
		                    if (collator.compare(value1, value2) < 0) {
		                        String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
		                        		items[i].getText(4), items[i].getText(5), items[i].getText(6),items[i].getText(7), items[i].getText(8),
		                        		items[i].getText(9), items[i].getText(10), items[i].getText(11), items[i].getText(12), items[i].getText(13), items[i].getText(14)};
		                        items[i].dispose();
		                        TableItem item = new TableItem(listElencoPazienti, SWT.NONE, j);
		                        item.setText(values);
		                        items = listElencoPazienti.getItems();
		                        break;
		                    }
		                }
	            }
	                /*}else{
	                	if(items[i].getText(index).equals(""))
	                		valueInt1=Integer.MAX_VALUE;
	                	else
	                		valueInt1 = Integer.parseInt(items[i].getText(index));
	                	  for (int j = 0; j < i; j++){
	                		  if(items[j].getText(index).equals(""))
	                			  valueInt2=Integer.MAX_VALUE;
	                		  else
	                			  valueInt2 = Integer.parseInt(items[j].getText(index));
			                    if (valueInt1 < valueInt2) {
			                        String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3)};
			                        items[i].dispose();
			                        TableItem item = new TableItem(tableAlimenti, SWT.NONE, j);
			                        item.setText(values);
			                        items = tableAlimenti.getItems();
			                        break;
			                    }
			                }
	                }*/
	                	
	              
	            
	        listElencoPazienti.setSortColumn(column);
	        }
	    };
	    listElencoPazienti.getColumn(2).addListener(SWT.Selection, sortListener);
	    //listElencoPazienti.getColumn(1).addListener(SWT.Selection, sortListener);
	    //listElencoPazienti.getColumn(3).addListener(SWT.Selection, sortListener);

	}

	public static Paziente getPazienteSelezionato() {
		Paziente paziente = (Paziente) paz.get(listElencoPazienti.getSelectionIndex());
		return paziente;
	}
}
