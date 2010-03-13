package studiodietetico;

import hibernate.Parametroantropometrico;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

import command.EsameClinicoDAO;
import command.ParametroAntropometricoDAO;

public class InserisciParametroAntropometricoShell {

	private Shell sShell = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private Label label = null;
	private Label label1 = null;
	private Text txtnome = null;
	private Text txtDescrizione = null;
	private Button btnRegistra = null;
	private ParametroAntropometricoDAO parametroDAO;
	int idparametro  = 0;
	private Parametroantropometrico parametromem =  null;
	

	public InserisciParametroAntropometricoShell() {
		parametroDAO = new ParametroAntropometricoDAO();
	}
	public InserisciParametroAntropometricoShell(int idparam) {
		//esamemem = EsameClinicoDAO.getEsameClinico(idparametro);
		idparametro = idparam;
		parametroDAO = new ParametroAntropometricoDAO();
	}
	
	/**
	 * This method initializes sShell
	 */
	public void createShellInsParametroAntropometrico() {
		sShell = new Shell();
		sShell.setText("Parametro Antropometrico");
		sShell.setSize(new Point(275, 217));
		sShell.setLayout(null);
		label = new Label(sShell, SWT.NONE);
		label.setBounds(new Rectangle(15, 14, 33, 13));
		label.setText("*Nome");
		label1 = new Label(sShell, SWT.NONE);
		label1.setBounds(new Rectangle(16, 76, 54, 13));
		label1.setText("Descrizione");
		txtnome = new Text(sShell, SWT.BORDER);
		txtnome.setBounds(new Rectangle(14, 34, 123, 19));
		txtDescrizione = new Text(sShell, SWT.BORDER);
		txtDescrizione.setBounds(new Rectangle(15, 95, 126, 19));
		btnRegistra = new Button(sShell, SWT.NONE);
		btnRegistra.setBounds(new Rectangle(135, 134, 105, 23));
		btnRegistra.setText("Registra Parametro");
		btnRegistra.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				ParametroAntropometricoDAO p = new ParametroAntropometricoDAO();
				//se idparametro = 0 allora inserisce, altrimenti fa update
				p.registraParametro(idparametro,txtnome.getText(), txtDescrizione.getText());
				txtnome.setText("");
				txtDescrizione.setText("");
			}
		});
		if (idparametro != 0) {
			
			parametromem = parametroDAO.getParametro(idparametro);
			txtnome.setText(parametromem.getNome());
			txtDescrizione.setText(parametromem.getDescrizione());
		}
		sShell.open();
		//statoiniziale();
	}
	
	/*public void statoiniziale(){
		//carico la lista
		ParametroAntropometricoDAO p = new ParametroAntropometricoDAO();
		ArrayList<Parametroantropometrico> lis = p.getParametri();
		for (Parametroantropometrico para : lis) {
			listParametro.add(para.getNome());
		}
		idparametro = 0;
		txtnome.setEnabled(false);
		txtDescrizione.setEnabled(false);
		btnRegistra.setEnabled(false);
		txtnome.setText("");
		txtDescrizione.setText("");
	}*/

}
