package grafici;

import java.lang.reflect.Array;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class GraficiComposite extends Composite {

	protected static String[] mesi = new String[]{"Gennaio","Febbraio","Marzo","Aprile","Maggio","Giugno","Luglio","Agosto","Settembre","Ottobre","Novembre","Dicembre"};
	public GraficiComposite(Composite parent, int style) {
		super(parent, style);
	}

	protected static void alertGraficoNonDisp() {
		Shell shell = new Shell(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
		MessageBox alert = new MessageBox(shell);
		alert
				.setMessage("Grafico non disponibile, seleziona un altro tipo di grafico");
		shell.open();
	}
}
