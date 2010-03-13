package studiodietetico;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import hibernate.Parametroantropometrico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import command.ParametroAntropometricoDAO;
import org.eclipse.swt.widgets.List;

public class ParametroAntropometricoView extends ViewPart {

	public static final String ID = "studiodietetico.ParametroAntropometricoView"; // TODO Needs to be whatever is mentioned in plugin.xml
	private Composite top = null;
	private Label label = null;
	private Label label1 = null;
	private Text txtnome = null;
	private Text txtDescrizione = null;
	private Button btnRegistra = null;
	private List listParametro = null;
	private Label lblParametro = null;
	private Button btnAggiungiNuovo = null;
	private Button btnElimina = null;
	private Button btnAnnulla = null;
	int idparametro  = 0;

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
		label = new Label(top, SWT.NONE);
		label.setBounds(new Rectangle(16, 114, 38, 18));
		label.setText("*Nome");
		label1 = new Label(top, SWT.NONE);
		label1.setBounds(new Rectangle(16, 143, 62, 19));
		label1.setText("Descrizione");
		txtnome = new Text(top, SWT.BORDER);
		txtnome.setBounds(new Rectangle(81, 116, 119, 19));
		txtDescrizione = new Text(top, SWT.BORDER);
		txtDescrizione.setBounds(new Rectangle(81, 142, 119, 19));
		btnRegistra = new Button(top, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(15, 196, 107, 23));
		btnRegistra.setText("Registra Parametro");
		listParametro = new List(top, SWT.NONE);
		listParametro.setBounds(new Rectangle(14, 26, 180, 64));
		listParametro
				.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
					public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
						ParametroAntropometricoDAO par = new ParametroAntropometricoDAO();
						Parametroantropometrico parametro =  par.getParametro(listParametro.getItem(listParametro.getSelectionIndex()));
						txtnome.setText(parametro.getNome());
						txtDescrizione.setText(parametro.getDescrizione());
						idparametro = parametro.getIdParametroAntropometrico();
						statosecondario();
					}
				});
		lblParametro = new Label(top, SWT.NONE);
		lblParametro.setBounds(new Rectangle(14, 9, 54, 13));
		lblParametro.setText("Parametri");
		btnAggiungiNuovo = new Button(top, SWT.NONE);
		btnAggiungiNuovo.setBounds(new Rectangle(208, 23, 158, 23));
		btnAggiungiNuovo.setText("Aggiungi nuovo parametro");
		btnAggiungiNuovo.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				statosecondario();
			}
		});
		btnElimina = new Button(top, SWT.NONE);
		btnElimina.setBounds(new Rectangle(135, 196, 93, 23));
		btnElimina.setText("Elimina Parametro");
		btnElimina.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ParametroAntropometricoDAO p = new ParametroAntropometricoDAO();
				Parametroantropometrico par = (Parametroantropometrico) p.getParametro(listParametro.getItem(listParametro.getSelectionIndex()));
				p.cancellaParametro(par);
				JOptionPane.showMessageDialog(null,
						"Parametro cancellato","AVVISO",JOptionPane.INFORMATION_MESSAGE);
				statoiniziale();
			}
		});
		btnAnnulla = new Button(top, SWT.NONE);
		btnAnnulla.setBounds(new Rectangle(246, 196, 54, 23));
		btnAnnulla.setText("Annulla");
		btnAnnulla.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				statoiniziale();
			}
		});
		btnRegistra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ParametroAntropometricoDAO p = new ParametroAntropometricoDAO();
				//se idparametro = 0 allora inserisce, altrimenti fa update
				p.registraParametro(idparametro,txtnome.getText(), txtDescrizione.getText());
				JOptionPane.showMessageDialog(null,
						"Parametro registrato","AVVISO",JOptionPane.INFORMATION_MESSAGE);
				statoiniziale();
			}
		});
		statoiniziale();
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
	
	public void statoiniziale(){
		//carico la lista
		listParametro.removeAll();
		ParametroAntropometricoDAO p = new ParametroAntropometricoDAO();
		ArrayList<Parametroantropometrico> lis = p.getParametri();
		for (Parametroantropometrico para : lis) {
			listParametro.add(para.getNome());
		}
		idparametro = 0;
		listParametro.setEnabled(true);
		btnAggiungiNuovo.setEnabled(true);
		txtnome.setEnabled(false);
		txtDescrizione.setEnabled(false);
		btnRegistra.setEnabled(false);
		btnElimina.setEnabled(false);
		btnAnnulla.setEnabled(false);
		txtnome.setText("");
		txtDescrizione.setText("");
	}

	public void statosecondario(){
		listParametro.setEnabled(false);
		btnAggiungiNuovo.setEnabled(false);
		txtnome.setEnabled(true);
		txtDescrizione.setEnabled(true);
		btnRegistra.setEnabled(true);
		btnElimina.setEnabled(true);
		btnAnnulla.setEnabled(true);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10,382,304"
