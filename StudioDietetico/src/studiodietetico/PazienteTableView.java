package studiodietetico;

import hibernate.Paziente;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.part.ViewPart;

import command.PazienteDAO;
import common.Utils;
import org.eclipse.swt.widgets.Button;

public class PazienteTableView extends ViewPart {
	private Composite top = null;
	private TableForm classVis;
	private ArrayList<Object> pazienti;
	private Button buttonPrenotaVisita = null;
	private Shell sShellMessElimina;
	public static Paziente pazienteSel = null;

	public PazienteTableView() {}

	@Override
	public void createPartControl(Composite parent) {
		top = new Composite(parent, SWT.NONE);
		pazienti = PazienteDAO.getPazientiObject();
		PazienteDAO pd = new PazienteDAO();
		//TODO aggiungere parametri
		classVis = new TableForm(top, SWT.BORDER, pazienti, "","","", pd, "PazienteTableView");
		classVis.setBounds(new Rectangle(6, 50, 800, 332));
		classVis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		classVis.setLayout(new GridLayout(1, true));
		classVis.setBackground(Utils.getStandardWhiteColor());
		buttonPrenotaVisita = new Button(classVis.top, SWT.NONE);
		buttonPrenotaVisita.setBounds(new Rectangle(260, 284, 110, 25));
		buttonPrenotaVisita.setText("PrenotaVisita");
		buttonPrenotaVisita.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				if(classVis.getTableVisualizzazione().getSelectionCount()>0) {
					TableItem itemSel = classVis.getTableVisualizzazione().getItem(classVis.getTableVisualizzazione().getSelectionIndex());
					int idPazienteSel = Integer.parseInt(itemSel.getText(0));
					pazienteSel = PazienteDAO.getPazienteByID(idPazienteSel);
					Utils.showView("StudioDietetico.prenotavisita"); 
				} else {
					createMessSelElemCanc();
				}
			}
		});
		
		classVis.nascondiColonne(new int[] {0,1,8,9,10,11,12,13,14,15});
		
		classVis.aggiornaCombo();
		
		classVis.ordinamentoData(classVis.getTableVisualizzazione(), 4);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 2);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 3);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 5);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 6);
		classVis.ordinamentoStringhe(classVis.getTableVisualizzazione(), 7);
	
		
	}

	@Override
	public void setFocus() {
		classVis.setFocus();
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

}  //  @jve:decl-index=0:visual-constraint="10,10,431,253"
