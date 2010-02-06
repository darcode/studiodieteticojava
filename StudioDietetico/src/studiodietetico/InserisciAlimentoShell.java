package studiodietetico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

import command.DietaDAO;

public class InserisciAlimentoShell {
	
	private Shell shellInsertAlimento = null;  //  @jve:decl-index=0:visual-constraint="1018,119"
	private Button bInsNewAlimento = null;
	private Text textShellInsAlimento = null;
	private Label lShellInsAlimento = null;
	private Label lShellInsAlimento1 = null;
	private Text textShellInsAlimento1 = null;
	private Label lShellInsAlimento3 = null;
	private Spinner spinCalorie = null;
	private DietaDAO dietaDao = null;

	
	public InserisciAlimentoShell() {
		dietaDao = new DietaDAO();
	}
	/**
	 * This method initializes shellInsertAlimento	
	 *
	 */
	public void createShellInsertAlimento(final String interfacciaChiamante) {
		shellInsertAlimento = new Shell(Display.getCurrent(), SWT.APPLICATION_MODAL
				| SWT.SHELL_TRIM);
		shellInsertAlimento.setLayout(null);
		shellInsertAlimento.setText("Inserisci nuovo Alimento");
		shellInsertAlimento.setSize(new Point(330, 204));
		bInsNewAlimento = new Button(shellInsertAlimento, SWT.NONE);
		bInsNewAlimento.setBounds(new Rectangle(250, 143, 66, 23));
		bInsNewAlimento.setText("Inserisci");
		bInsNewAlimento
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(!textShellInsAlimento.getText().equals("")) {
					//StrutAlimento ali = new StrutAlimento(textShellInsAlimento.getText(), textShellInsAlimento1.getText(), spinCalorie.getDigits());
					dietaDao.insAlimento(textShellInsAlimento.getText(), textShellInsAlimento1.getText(), Integer.parseInt(spinCalorie.getText()));
					textShellInsAlimento.setText("");
					textShellInsAlimento1.setText("");
					spinCalorie.setDigits(0);
					if(interfacciaChiamante.equals("ricetta")){
					InserisciRicettaView.aggiornaAlimenti();
					}else if(interfacciaChiamante.equals("dieta")){
						InserisciDietaShell.aggiornaAlimenti();
						
					}
				}
			}
		});
		textShellInsAlimento = new Text(shellInsertAlimento, SWT.BORDER);
		textShellInsAlimento.setBounds(new Rectangle(17, 26, 218, 19));
		lShellInsAlimento = new Label(shellInsertAlimento, SWT.NONE);
		lShellInsAlimento.setBounds(new Rectangle(16, 9, 165, 13));
		lShellInsAlimento.setText("Inserisci nome alimento");
		lShellInsAlimento1 = new Label(shellInsertAlimento, SWT.NONE);
		lShellInsAlimento1.setBounds(new Rectangle(15, 58, 125, 13));
		lShellInsAlimento1.setText("Inserisci tipologia alimento");
		textShellInsAlimento1 = new Text(shellInsertAlimento, SWT.BORDER);
		textShellInsAlimento1.setBounds(new Rectangle(16, 73, 219, 19));
		lShellInsAlimento3 = new Label(shellInsertAlimento, SWT.NONE);
		lShellInsAlimento3.setBounds(new Rectangle(14, 103, 98, 13));
		lShellInsAlimento3.setText("Inserisci calorie");
		spinCalorie = new Spinner(shellInsertAlimento, SWT.NONE);
		spinCalorie.setBounds(new Rectangle(15, 122, 65, 18));
		shellInsertAlimento.open();
		
		
	}


	
	
}
