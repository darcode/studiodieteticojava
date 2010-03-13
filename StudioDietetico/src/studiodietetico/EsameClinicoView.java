package studiodietetico;

import hibernate.Esameclinico;
import hibernate.Parametroantropometrico;
import hibernate.Parametroesame;

import java.util.ArrayList;

import javax.swing.text.TabExpander;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import command.EsameClinicoDAO;
import command.ParametroAntropometricoDAO;
import command.ParametroClinicoDAO;
import org.eclipse.jface.viewers.TableViewer;

public class EsameClinicoView extends ViewPart {

	public static final String ID = "studiodietetico.EsameClinicoView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private Button btnAnnulla = null;
	private Button btnElimina = null;
	private Button btnRegistra = null;
	private Button btnAggiungiNuovo = null;
	private List listEsameClinico = null;
	private Label lblParametro = null;
	private Group groupParametroEsame = null;
	private Text txtnomeparametro = null;
	private Label label2 = null;
	private Text txtDescrizioneParametro = null;
	private Label label11 = null;
	private Text txtMinUomo = null;
	private Label label21 = null;
	private Label label22 = null;
	private Text txtMaxUomo = null;
	private Text txtMinDonna = null;
	private Text txtMaxDonna = null;
	private Label label211 = null;
	private Label label221 = null;
	private Text txtMinBambino = null;
	private Text txtMaxBambino = null;
	private Label label212 = null;
	private Label label222 = null;
	private Table tableParametri = null;
	private Group groupEsame = null;
	private Text txtDescrizione = null;
	private Label label1 = null;
	private Text txtnome = null;
	private Label label = null;
	private Button btnAggiungiParametro = null;
	private Button btnEliminaParametro = null;
	private Button btnAnnullaPar = null;
	private EsameClinicoDAO es = new EsameClinicoDAO();  //  @jve:decl-index=0:
	private ParametroClinicoDAO par = new ParametroClinicoDAO();  //  @jve:decl-index=0:
	Esameclinico esamemem = new Esameclinico();
	Parametroesame parametromem = new Parametroesame();
	int indicerow = -1;

	private TableViewer tableViewer = null;
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
		btnAnnulla = new Button(top, SWT.NONE);
		btnAnnulla.setText("Annulla");
		btnAnnulla.setBounds(new Rectangle(537, 501, 65, 23));
		btnAnnulla.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				statoiniziale();
			}
		});
		btnElimina = new Button(top, SWT.NONE);
		btnElimina.setBounds(new Rectangle(430, 500, 97, 23));
		btnElimina.setText("Elimina Esame");
		btnElimina.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if (esamemem.getIdEsameClinico() == 0) {  //non cè nulla da eliminare(è un esame nuovo)
					statoiniziale();
				}else {
					Esameclinico esame = new Esameclinico();
					esame.setIdEsameClinico(esame.getIdEsameClinico());
					esame.setNome(txtnome.getText());
					esame.setDescrizione(txtDescrizione.getText());
					es.cancellaEsame(esame);
				}
				
			}
		});
		btnRegistra = new Button(top, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(317, 500, 105, 23));
		btnRegistra.setText("Registra Esame");
		btnRegistra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Esameclinico esame;
					if (esamemem.getIdEsameClinico() != null) {  //esame già esistente
						esamemem.setNome(txtnome.getText());
						esamemem.setDescrizione(txtDescrizione.getText());
						 esame =  es.registraEsame(esamemem);
					}else {  //nuovo esame
						esame =  es.registranewEsame(txtnome.getText(), txtDescrizione.getText());
					}
					
					for (int i =0; i<= tableParametri.getItemCount() - 1; ++i) {
						TableItem item = tableParametri.getItem(i);
						par.registraParametroClinico(Integer.parseInt(item.getText(0)), item.getText(1), item.getText(2), item.getText(3), item.getText(4), item.getText(5), item.getText(6), item.getText(7), item.getText(8),esame);
					}
					statoiniziale();
			}
		});
		btnAggiungiNuovo = new Button(top, SWT.NONE);
		btnAggiungiNuovo.setBounds(new Rectangle(203, 25, 146, 23));
		btnAggiungiNuovo.setText("Aggiungi nuovo esame clinico");
		btnAggiungiNuovo
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						statosecondario();
					}
				});
		listEsameClinico = new List(top, SWT.NONE);
		listEsameClinico.setBounds(new Rectangle(14, 26, 181, 64));
		listEsameClinico
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						
						Esameclinico esame =  es.getEsameClinico(listEsameClinico.getItem(listEsameClinico.getSelectionIndex()));
						txtnome.setText(esame.getNome());
						txtDescrizione.setText(esame.getDescrizione());
						esamemem=esame;
						statosecondario();
					}
				});
		lblParametro = new Label(top, SWT.NONE);
		lblParametro.setBounds(new Rectangle(12, 12, 66, 13));
		lblParametro.setText("Esami Clinici");
		createGroupParametroEsame();
		tableParametri = new Table(top, SWT.SINGLE | SWT.FULL_SELECTION
		        | SWT.HIDE_SELECTION);
		tableParametri.setHeaderVisible(true);
		tableParametri.setLinesVisible(true);
		tableParametri.setBounds(new Rectangle(13, 335, 612, 145));
		tableParametri.setCapture(true);
		tableParametri.setLinesVisible(true);
		tableParametri.showSelection();
		tableViewer = new TableViewer(tableParametri);
		tableParametri.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				Point px = new Point(e.x, e.y);
				TableItem item = tableParametri.getItem(px);
				indicerow =  tableParametri.getSelectionIndex();
				Parametroesame p = new Parametroesame();
				p.setIdParametroEsame(Integer.parseInt(item.getText(0)));
				p.setNome(item.getText(1));
				p.setDescrizione(item.getText(2));
				p.setMinUomo(item.getText(3));
				p.setMaxUomo(item.getText(4));
				p.setMinDonna(item.getText(5));
				p.setMaxDonna(item.getText(6));
				p.setMinBambino(item.getText(7));
				p.setMaxBambino(item.getText(8));
				parametromem = p;
				txtnomeparametro.setText(item.getText(1));
				txtDescrizioneParametro.setText(item.getText(2));
				txtMinUomo.setText(item.getText(3));
				txtMaxUomo.setText(item.getText(4));
				txtMinDonna.setText(item.getText(5));
				txtMaxDonna.setText(item.getText(6));
				txtMinBambino.setText(item.getText(7));
				txtMaxBambino.setText(item.getText(8));
			}
		});
		
		createGroupEsame();
		TableColumn tableColumn11 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn11.setWidth(0);
		tableColumn11.setText("Id");
		TableColumn tableColumn = new TableColumn(tableParametri, SWT.NONE);
		tableColumn.setWidth(95);
		tableColumn.setText("Nome Parametro");
		TableColumn tableColumn1 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn1.setWidth(90);
		tableColumn1.setText("Descrizione");
		TableColumn tableColumn2 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn2.setWidth(65);
		tableColumn2.setText("Min.Uomo");
		TableColumn tableColumn3 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn3.setWidth(68);
		tableColumn3.setText("Max.Uomo");
		TableColumn tableColumn4 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn4.setWidth(70);
		tableColumn4.setText("Min.Donna");
		TableColumn tableColumn5 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn5.setWidth(70);
		tableColumn5.setText("Max.Donna");
		TableColumn tableColumn6 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn6.setWidth(75);
		tableColumn6.setText("Min.Bambino");
		TableColumn tableColumn7 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn7.setWidth(79);
		tableColumn7.setText("Max.Bambino");
		statoiniziale();
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	/**
	 * This method initializes groupParametroEsame	
	 *
	 */
	private void createGroupParametroEsame() {
		groupParametroEsame = new Group(top, SWT.NONE);
		groupParametroEsame.setLayout(null);
		groupParametroEsame.setText("Parametro Esame Clinico");
		groupParametroEsame.setBounds(new Rectangle(11, 180, 612, 141));
		txtnomeparametro = new Text(groupParametroEsame, SWT.BORDER);
		txtnomeparametro.setBounds(new Rectangle(7, 28, 106, 19));
		label2 = new Label(groupParametroEsame, SWT.NONE);
		label2.setBounds(new Rectangle(7, 14, 33, 13));
		label2.setText("*Nome");
		txtDescrizioneParametro = new Text(groupParametroEsame, SWT.BORDER);
		txtDescrizioneParametro.setBounds(new Rectangle(128, 27, 106, 19));
		label11 = new Label(groupParametroEsame, SWT.NONE);
		label11.setBounds(new Rectangle(129, 13, 60, 13));
		label11.setText("Descrizione");
		txtMinUomo = new Text(groupParametroEsame, SWT.BORDER);
		txtMinUomo.setBounds(new Rectangle(6, 73, 76, 19));
		label21 = new Label(groupParametroEsame, SWT.NONE);
		label21.setBounds(new Rectangle(5, 60, 76, 13));
		label21.setText("Val. Min. Uomo");
		label22 = new Label(groupParametroEsame, SWT.NONE);
		label22.setBounds(new Rectangle(104, 60, 79, 13));
		label22.setText("Val. Max. Uomo");
		txtMaxUomo = new Text(groupParametroEsame, SWT.BORDER);
		txtMaxUomo.setBounds(new Rectangle(105, 72, 76, 19));
		txtMinDonna = new Text(groupParametroEsame, SWT.BORDER);
		txtMinDonna.setBounds(new Rectangle(202, 72, 76, 19));
		txtMaxDonna = new Text(groupParametroEsame, SWT.BORDER);
		txtMaxDonna.setBounds(new Rectangle(304, 72, 76, 19));
		label211 = new Label(groupParametroEsame, SWT.NONE);
		label211.setBounds(new Rectangle(204, 59, 79, 13));
		label211.setText("Val. Min. Donna");
		label221 = new Label(groupParametroEsame, SWT.NONE);
		label221.setBounds(new Rectangle(302, 60, 83, 13));
		label221.setText("Val. Max. Donna");
		txtMinBambino = new Text(groupParametroEsame, SWT.BORDER);
		txtMinBambino.setBounds(new Rectangle(408, 73, 76, 19));
		txtMaxBambino = new Text(groupParametroEsame, SWT.BORDER);
		txtMaxBambino.setBounds(new Rectangle(512, 71, 76, 19));
		label212 = new Label(groupParametroEsame, SWT.NONE);
		label212.setBounds(new Rectangle(408, 61, 88, 13));
		label212.setText("Val. Min. Bambino");
		label222 = new Label(groupParametroEsame, SWT.NONE);
		label222.setBounds(new Rectangle(513, 58, 90, 13));
		label222.setText("Val. Max. Bambino");
		btnAggiungiParametro = new Button(groupParametroEsame, SWT.NONE);
		btnAggiungiParametro.setBounds(new Rectangle(295, 106, 104, 23));
		btnAggiungiParametro.setText("Aggiungi Parametro");
		btnAggiungiParametro
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						if (indicerow == -1) {
							TableItem item = new TableItem(tableParametri, SWT.NONE);
							Parametroesame param;
							item.setText(new String[] {Integer.toString(0) , txtnomeparametro.getText(),txtDescrizioneParametro.getText(),txtMinUomo.getText(),txtMaxUomo.getText(),txtMinDonna.getText(),txtMaxDonna.getText(),txtMinBambino.getText(),txtMaxBambino.getText() });
						} else {
							TableItem tableItem= tableParametri.getItem(indicerow);
							tableItem.setText(0, Integer.toString(parametromem.getIdParametroEsame()));
							tableItem.setText(1, txtnomeparametro.getText());
							tableItem.setText(2, txtDescrizioneParametro.getText());
							tableItem.setText(3, txtMinUomo.getText());
							tableItem.setText(4, txtMaxUomo.getText());
							tableItem.setText(5, txtMinDonna.getText());
							tableItem.setText(6, txtMaxDonna.getText());
							tableItem.setText(7, txtMinBambino.getText());
							tableItem.setText(8, txtMaxBambino.getText());
						}
						/*if ((Integer)parametromem.getIdParametroEsame() != 0) {  //il parametro non è nuovo ma è in modifica
							Parametroesame p = new Parametroesame();
							p = parametromem;
							for (int i = 0; i<= tableParametri.getItemCount() - 1; ++i) {
								TableItem tableItem= tableParametri.getItem(i);
								if (Integer.parseInt(tableItem.getText(0))  == p.getIdParametroEsame() ) {
									tableItem.setText(0, Integer.toString(p.getIdParametroEsame()));
									tableItem.setText(1, txtnomeparametro.getText());
									tableItem.setText(2, txtDescrizioneParametro.getText());
									tableItem.setText(3, txtMinUomo.getText());
									tableItem.setText(4, txtMaxUomo.getText());
									tableItem.setText(5, txtMinDonna.getText());
									tableItem.setText(6, txtMaxDonna.getText());
									tableItem.setText(7, txtMinBambino.getText());
									tableItem.setText(8, txtMaxBambino.getText());
								}
							}
							parametromem = new Parametroesame();
						}
						else{
							TableItem item = new TableItem(tableParametri, SWT.NONE);
							Parametroesame param;
						      if (esamemem.getIdEsameClinico() != null) {  //è un esame esistente e registro in DB il parametro
						    	  param = par.registraParametroClinico(0, txtnomeparametro.getText(),txtDescrizioneParametro.getText(),txtMinUomo.getText(),txtMaxUomo.getText(),txtMinDonna.getText(),txtMaxDonna.getText(),txtMinBambino.getText(),txtMaxBambino.getText(),esamemem);  
						    	  item.setText(new String[] {Integer.toString(param.getIdParametroEsame()) , txtnomeparametro.getText(),txtDescrizioneParametro.getText(),txtMinUomo.getText(),txtMaxUomo.getText(),txtMinDonna.getText(),txtMaxDonna.getText(),txtMinBambino.getText(),txtMaxBambino.getText() });
						      }	    
						      else {
						    	  item.setText(new String[] {Integer.toString(0) , txtnomeparametro.getText(),txtDescrizioneParametro.getText(),txtMinUomo.getText(),txtMaxUomo.getText(),txtMinDonna.getText(),txtMaxDonna.getText(),txtMinBambino.getText(),txtMaxBambino.getText() });
						      }
						      
						}*/
						  indicerow = -1;
						  parametromem = new Parametroesame();
					      txtnomeparametro.setText("");
					      txtDescrizioneParametro.setText("");
					      txtMinUomo.setText("");
					      txtMaxUomo.setText("");
					      txtMinDonna.setText("");
					      txtMaxDonna.setText("");
					      txtMinBambino.setText("");
					      txtMaxBambino.setText("");
					}
				});
		btnEliminaParametro = new Button(groupParametroEsame, SWT.NONE);
		btnEliminaParametro.setBounds(new Rectangle(408, 106, 101, 23));
		btnEliminaParametro.setText("Elimina Parametro");
		btnEliminaParametro
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						par.cancellaParametroClinico(parametromem);
						tableParametri.remove(indicerow);
						indicerow = -1;
						parametromem = new Parametroesame();
				      txtnomeparametro.setText("");
				      txtDescrizioneParametro.setText("");
				      txtMinUomo.setText("");
				      txtMaxUomo.setText("");
				      txtMinDonna.setText("");
				      txtMaxDonna.setText("");
				      txtMinBambino.setText("");
				      txtMaxBambino.setText("");
					}
				});
		btnAnnullaPar = new Button(groupParametroEsame, SWT.NONE);
		btnAnnullaPar.setBounds(new Rectangle(519, 107, 70, 23));
		btnAnnullaPar.setText("Annulla");
		btnAnnullaPar
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						txtnomeparametro.setText("");
					      txtDescrizioneParametro.setText("");
					      txtMinUomo.setText("");
					      txtMaxUomo.setText("");
					      txtMinDonna.setText("");
					      txtMaxDonna.setText("");
					      txtMinBambino.setText("");
					      txtMaxBambino.setText("");
					}
				});
	}

	/**
	 * This method initializes groupEsame	
	 *
	 */
	private void createGroupEsame() {
		groupEsame = new Group(top, SWT.NONE);
		groupEsame.setLayout(null);
		groupEsame.setText("Esame Clinico");
		groupEsame.setBounds(new Rectangle(15, 98, 288, 62));
		txtDescrizione = new Text(groupEsame, SWT.BORDER);
		txtDescrizione.setBounds(new Rectangle(146, 32, 118, 19));
		label1 = new Label(groupEsame, SWT.NONE);
		label1.setBounds(new Rectangle(146, 19, 54, 13));
		label1.setText("Descrizione");
		txtnome = new Text(groupEsame, SWT.BORDER);
		txtnome.setBounds(new Rectangle(6, 33, 109, 19));
		label = new Label(groupEsame, SWT.NONE);
		label.setBounds(new Rectangle(7, 19, 33, 13));
		label.setText("*Nome");
	}
	
	public void statoiniziale(){
		//carico la lista
		listEsameClinico.removeAll();
		tableParametri.removeAll();
		ArrayList<Esameclinico> lis = es.getEsamiClinici();
		for (Esameclinico esame : lis) {
			listEsameClinico.add(esame.getNome());
		}
		esamemem = new Esameclinico();
		parametromem = new Parametroesame();
		listEsameClinico.setEnabled(true);
		btnAggiungiNuovo.setEnabled(true);
		txtnome.setEnabled(false);
		txtDescrizione.setEnabled(false);
		txtnomeparametro.setEnabled(false);
		txtDescrizioneParametro.setEnabled(false);
		txtMinUomo.setEnabled(false);
		txtMaxUomo.setEnabled(false);
		txtMinDonna.setEnabled(false);
		txtMaxDonna.setEnabled(false);
		txtMinBambino.setEnabled(false);
		txtMaxBambino.setEnabled(false);
		btnAggiungiParametro.setEnabled(false);
		btnEliminaParametro.setEnabled(false);
		btnAnnullaPar.setEnabled(false);
		btnAggiungiParametro.setEnabled(false);
		btnRegistra.setEnabled(false);
		btnElimina.setEnabled(false);
		btnAnnulla.setEnabled(false);
		txtnome.setText("");
		txtDescrizione.setText("");
		txtnomeparametro.setText("");
	      txtDescrizioneParametro.setText("");
	      txtMinUomo.setText("");
	      txtMaxUomo.setText("");
	      txtMinDonna.setText("");
	      txtMaxDonna.setText("");
	      txtMinBambino.setText("");
	      txtMaxBambino.setText("");
	}

	public void statosecondario(){
		tableParametri.removeAll();
		caricaTableParametri();
		listEsameClinico.setEnabled(false);
		btnAggiungiNuovo.setEnabled(false);
		txtnome.setEnabled(true);
		txtDescrizione.setEnabled(true);
		txtnomeparametro.setEnabled(true);
		txtDescrizioneParametro.setEnabled(true);
		txtMinUomo.setEnabled(true);
		txtMaxUomo.setEnabled(true);
		txtMinDonna.setEnabled(true);
		txtMaxDonna.setEnabled(true);
		txtMinBambino.setEnabled(true);
		txtMaxBambino.setEnabled(true);
		btnAggiungiParametro.setEnabled(true);
		btnEliminaParametro.setEnabled(true);
		btnAnnullaPar.setEnabled(true);
		btnRegistra.setEnabled(true);
		btnElimina.setEnabled(true);
		btnAnnulla.setEnabled(true);
	}
	
	public void caricaTableParametri() {
		if (esamemem.getIdEsameClinico()!= null) {
			ArrayList<Parametroesame> list = es.getParametriEsame(esamemem.getIdEsameClinico());
			for (Parametroesame p: list) {
				TableItem item = new TableItem(tableParametri, SWT.None);
				item.setText(0, Integer.toString(p.getIdParametroEsame()));
				item.setText(1, p.getNome());
				item.setText(2, p.getDescrizione());
				item.setText(3, p.getMinUomo());
				item.setText(4, p.getMaxUomo());
				item.setText(5, p.getMinDonna());
				item.setText(6, p.getMaxDonna());
				item.setText(7, p.getMinBambino());
				item.setText(8, p.getMaxBambino());
			}
		}
		
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10,634,549"
