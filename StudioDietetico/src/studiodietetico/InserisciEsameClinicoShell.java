package studiodietetico;

import java.util.ArrayList;

import hibernate.Esameclinico;
import hibernate.Parametroesame;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import command.DietaDAO;
import command.EsameClinicoDAO;
import command.ParametroClinicoDAO;

public class InserisciEsameClinicoShell {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="0,2"
	private Group groupEsame = null;
	private Text txtDescrizione = null;
	private Label label1 = null;
	private Text txtnome = null;
	private Label label = null;
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
	private Button btnAggiungiParametro = null;
	private Button btnEliminaParametro = null;
	private Button btnAnnullaPar = null;
	private Table tableParametri = null;
	int indicerow = -1;
	Parametroesame parametromem = new Parametroesame();
	private ParametroClinicoDAO par = new ParametroClinicoDAO();
	private EsameClinicoDAO esameClinicoDao ;
	private Button btnRegistra = null;
	private Esameclinico esamemem = null;  //  @jve:decl-index=0:
	private Button buttonChiudi = null;


	public InserisciEsameClinicoShell() {
		esameClinicoDao = new EsameClinicoDAO();
	}
	public InserisciEsameClinicoShell(int idesame) {
		esamemem = EsameClinicoDAO.getEsameClinico(idesame);
		esameClinicoDao = new EsameClinicoDAO();
	}
	/**
	 * This method initializes sShell
	 */
	
	public void createShellInsEsameClinico() {
		sShell = new Shell();
		sShell.setText("Esame Clinico");
		sShell.setSize(new Point(654, 445));
		sShell.setLayout(null);
		tableParametri = new Table(sShell, SWT.SINGLE | SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
		tableParametri.setHeaderVisible(true);
		tableParametri.setLinesVisible(true);
		tableParametri.setBounds(new Rectangle(12, 254, 629, 102));
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
		TableColumn tableColumn11 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn11.setText("Id");
		tableColumn11.setWidth(0);
		TableColumn tableColumn = new TableColumn(tableParametri, SWT.NONE);
		tableColumn.setText("Nome Parametro");
		tableColumn.setWidth(95);
		TableColumn tableColumn1 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn1.setText("Descrizione");
		tableColumn1.setWidth(90);
		TableColumn tableColumn2 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn2.setText("Min.Uomo");
		tableColumn2.setWidth(65);
		TableColumn tableColumn3 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn3.setText("Max.Uomo");
		tableColumn3.setWidth(68);
		TableColumn tableColumn4 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn4.setText("Min.Donna");
		tableColumn4.setWidth(70);
		TableColumn tableColumn5 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn5.setText("Max.Donna");
		tableColumn5.setWidth(70);
		TableColumn tableColumn6 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn6.setText("Min.Bambino");
		tableColumn6.setWidth(75);
		TableColumn tableColumn7 = new TableColumn(tableParametri, SWT.NONE);
		tableColumn7.setText("Max.Bambino");
		tableColumn7.setWidth(79);
		createGroupEsame();
		createGroupParametroEsame();
		btnRegistra = new Button(sShell, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(535, 368, 98, 23));
		btnRegistra.setText("Registra Esame");
		buttonChiudi = new Button(sShell, SWT.NONE);
		buttonChiudi.setBounds(new Rectangle(432, 368, 98, 23));
		buttonChiudi.setText("Chiudi");
		buttonChiudi
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						sShell.close();
					}
				});
		btnRegistra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				Esameclinico esame;
					if (esamemem != null) {  //esame già esistente
						esamemem.setNome(txtnome.getText());
						esamemem.setDescrizione(txtDescrizione.getText());
						esame =  esameClinicoDao.registraEsame(esamemem);
						EsameClinicoTableView.aggiornaTableView();
						sShell.close();
					}else {  //nuovo esame
						esame =  esameClinicoDao.registranewEsame(txtnome.getText(), txtDescrizione.getText());
						EsameClinicoTableView.aggiornaTableView();
						sShell.close();
					}
					
					for (int i =0; i<= tableParametri.getItemCount() - 1; ++i) {
						TableItem item = tableParametri.getItem(i);
						par.registraParametroClinico(Integer.parseInt(item.getText(0)), item.getText(1), item.getText(2), item.getText(3), item.getText(4), item.getText(5), item.getText(6), item.getText(7), item.getText(8),esame);
					}
					statoiniziale();
			}
		});
		if (esamemem != null) {
			txtnome.setText(esamemem.getNome());
			txtDescrizione.setText(esamemem.getDescrizione());
			caricaTableParametri();
		}
		sShell.open();
	}

	public void caricaTableParametri() {
		if (esamemem != null) {
			ArrayList<Parametroesame> list = esameClinicoDao.getParametriEsame(esamemem.getIdEsameClinico());
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
	
	/**
	 * This method initializes groupEsame	
	 *
	 */
	private void createGroupEsame() {
		groupEsame = new Group(sShell, SWT.NONE);
		groupEsame.setLayout(null);
		groupEsame.setText("Esame Clinico");
		groupEsame.setBounds(new Rectangle(7, 11, 270, 68));
		txtDescrizione = new Text(groupEsame, SWT.BORDER);
		txtDescrizione.setBounds(new Rectangle(146, 32, 118, 19));
		label1 = new Label(groupEsame, SWT.NONE);
		label1.setBounds(new Rectangle(146, 19, 67, 13));
		label1.setText("Descrizione");
		txtnome = new Text(groupEsame, SWT.BORDER);
		txtnome.setBounds(new Rectangle(6, 33, 109, 19));
		label = new Label(groupEsame, SWT.NONE);
		label.setBounds(new Rectangle(7, 19, 49, 13));
		label.setText("*Nome");
	}

	/**
	 * This method initializes groupParametroEsame	
	 *
	 */
	private void createGroupParametroEsame() {
		groupParametroEsame = new Group(sShell, SWT.NONE);
		groupParametroEsame.setLayout(null);
		groupParametroEsame.setText("Parametro Esame Clinico");
		groupParametroEsame.setBounds(new Rectangle(10, 92, 609, 146));
		txtnomeparametro = new Text(groupParametroEsame, SWT.BORDER);
		txtnomeparametro.setBounds(new Rectangle(7, 28, 106, 19));
		label2 = new Label(groupParametroEsame, SWT.NONE);
		label2.setBounds(new Rectangle(7, 14, 48, 13));
		label2.setText("*Nome");
		txtDescrizioneParametro = new Text(groupParametroEsame, SWT.BORDER);
		txtDescrizioneParametro.setBounds(new Rectangle(128, 27, 106, 19));
		label11 = new Label(groupParametroEsame, SWT.NONE);
		label11.setBounds(new Rectangle(129, 13, 68, 13));
		label11.setText("Descrizione");
		txtMinUomo = new Text(groupParametroEsame, SWT.BORDER);
		txtMinUomo.setBounds(new Rectangle(6, 73, 76, 19));
		label21 = new Label(groupParametroEsame, SWT.NONE);
		label21.setBounds(new Rectangle(5, 60, 86, 13));
		label21.setText("Val. Min. Uomo");
		label22 = new Label(groupParametroEsame, SWT.NONE);
		label22.setBounds(new Rectangle(104, 60, 88, 13));
		label22.setText("Val. Max. Uomo");
		txtMaxUomo = new Text(groupParametroEsame, SWT.BORDER);
		txtMaxUomo.setBounds(new Rectangle(105, 72, 76, 19));
		txtMinDonna = new Text(groupParametroEsame, SWT.BORDER);
		txtMinDonna.setBounds(new Rectangle(202, 72, 76, 19));
		txtMaxDonna = new Text(groupParametroEsame, SWT.BORDER);
		txtMaxDonna.setBounds(new Rectangle(304, 72, 76, 19));
		label211 = new Label(groupParametroEsame, SWT.NONE);
		label211.setBounds(new Rectangle(204, 59, 89, 13));
		label211.setText("Val. Min. Donna");
		label221 = new Label(groupParametroEsame, SWT.NONE);
		label221.setBounds(new Rectangle(302, 60, 89, 13));
		label221.setText("Val. Max. Donna");
		txtMinBambino = new Text(groupParametroEsame, SWT.BORDER);
		txtMinBambino.setBounds(new Rectangle(408, 73, 76, 19));
		txtMaxBambino = new Text(groupParametroEsame, SWT.BORDER);
		txtMaxBambino.setBounds(new Rectangle(512, 71, 76, 19));
		label212 = new Label(groupParametroEsame, SWT.NONE);
		label212.setBounds(new Rectangle(408, 61, 98, 13));
		label212.setText("Val. Min. Bambino");
		label222 = new Label(groupParametroEsame, SWT.NONE);
		label222.setBounds(new Rectangle(513, 58, 97, 13));
		label222.setText("Val. Max. Bambino");
		btnAggiungiParametro = new Button(groupParametroEsame, SWT.NONE);
		btnAggiungiParametro.setBounds(new Rectangle(282, 106, 117, 23));
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
		btnEliminaParametro.setBounds(new Rectangle(408, 106, 107, 23));
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
	}
	
	public void statoiniziale(){
		//carico la lista
		tableParametri.removeAll();
		esamemem = new Esameclinico();
		parametromem = new Parametroesame();
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
		//btnElimina.setEnabled(false);
		//btnAnnulla.setEnabled(false);
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

}
