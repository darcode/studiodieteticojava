package studiodietetico;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import service.DynNode;
import org.eclipse.swt.custom.CCombo;

public class DynamicQueryShell {
	
	private Shell sShell;  //  @jve:decl-index=0:visual-constraint="10,13"
	private Button buttonOk = null;
	private Label etichetta = null;
	private Text text = null;
	private Label nomeAttributo = null;
	private Button buttonCancella = null;
	private Button buttonMatching = null;
	private Label label = null;
	private CCombo cCombo = null;
	
	public DynamicQueryShell(){	
		
	}	
	
	public Shell createShell(final DynNode item){
		
		sShell = new Shell();
				
		if (item.getPathClass().contains("Boolean")|item.getPathClass().contains("boolean")) {
			
			System.out.println("Boolean");
			
			sShell.setSize(new Point(350, 194));
			etichetta = new Label(sShell, SWT.NONE);
			etichetta.setBounds(new Rectangle(119, 10, 117, 34));
			etichetta.setText("Seleziona il valore");
			nomeAttributo = new Label(sShell, SWT.NONE);
			nomeAttributo.setBounds(new Rectangle(36, 61, 121, 22));
			nomeAttributo.setText(item.getTreeNode().getText());
			buttonOk = new Button(sShell, SWT.NONE);
			buttonOk.setText("Ok");
			buttonOk.setBounds(new Rectangle(179, 108, 106, 27));
			buttonCancella = new Button(sShell, SWT.NONE);
			buttonCancella.setBounds(new Rectangle(62, 108, 90, 27));
			buttonCancella.setText("Cancella");
			cCombo = new CCombo(sShell, SWT.NONE);
			cCombo.setBounds(new Rectangle(218, 57, 85, 27));
			cCombo.add("Vero");
			cCombo.add("Falso");
			cCombo.setEditable(false);
			cCombo.select(0);		
			buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
						public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
							item.getTreeNode().setText(new String[] {item.getTreeNode().getText(), ""});						
							sShell.close();
						}
					});
			buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					String selezione = cCombo.getText();
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(),selezione});
					
					if (selezione.equals("Vero")) {
						//TODO costruire il Criteria adeguato
						System.out.println(selezione);
					} else {
						//TODO costruire il Criteria adeguato
						System.out.println(selezione);
					}
					sShell.close();
				}
			});
			
		} else if (item.getPathClass().contains("Char")|item.getPathClass().contains("char")) {
			
			System.out.println("Char");
			
			sShell.setSize(new Point(340, 233));
			buttonOk = new Button(sShell, SWT.NONE);
			buttonOk.setText("Ok");
			buttonOk.setBounds(new Rectangle(172, 165, 106, 27));
			etichetta = new Label(sShell, SWT.NONE);
			etichetta.setBounds(new Rectangle(87, 9, 117, 34));
			etichetta.setText("Inserisci il valore");
			nomeAttributo = new Label(sShell, SWT.NONE);
			nomeAttributo.setBounds(new Rectangle(36, 61, 121, 22));
			nomeAttributo.setText(item.getTreeNode().getText());
			buttonCancella = new Button(sShell, SWT.NONE);
			buttonCancella.setBounds(new Rectangle(62, 165, 90, 27));
			buttonCancella.setText("Cancella");
			text = new Text(sShell, SWT.BORDER);
			text.setBounds(new Rectangle(180, 62, 113, 25));
			label = new Label(sShell, SWT.NONE);
			label.setBounds(new Rectangle(22, 113, 200, 15));
			label.setText("Confronta con un altro campo:");
			buttonMatching = new Button(sShell, SWT.NONE);
			buttonMatching.setBounds(new Rectangle(227, 108, 40, 27));
			buttonMatching.setText("Qui");
			buttonMatching.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					//TODO dare la possibilità di selezionare un altro campo (dello stesso tipo)
					//dall'albero della query
					System.out.println("dare la possibilità di selezionare un altro campo (dello stesso tipo) dall'albero della query");					
				}
			});
			buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(), ""});						
					sShell.close();
				}
			});
			buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(),text.getText()});
					sShell.close();					
				}
			});
			
		} else if (item.getPathClass().contains("Double")|item.getPathClass().contains("double")) {
			
			System.out.println("Double");
			
		} else if (item.getPathClass().contains("String")) {
			
			System.out.println("String");
			
			sShell.setSize(new Point(340, 233));
			buttonOk = new Button(sShell, SWT.NONE);
			buttonOk.setText("Ok");
			buttonOk.setBounds(new Rectangle(172, 165, 106, 27));
			etichetta = new Label(sShell, SWT.NONE);
			etichetta.setBounds(new Rectangle(87, 9, 117, 34));
			etichetta.setText("Inserisci il valore");
			nomeAttributo = new Label(sShell, SWT.NONE);
			nomeAttributo.setBounds(new Rectangle(36, 61, 121, 22));
			nomeAttributo.setText(item.getTreeNode().getText());
			buttonCancella = new Button(sShell, SWT.NONE);
			buttonCancella.setBounds(new Rectangle(62, 165, 90, 27));
			buttonCancella.setText("Cancella");
			text = new Text(sShell, SWT.BORDER);
			text.setBounds(new Rectangle(180, 62, 113, 25));
			label = new Label(sShell, SWT.NONE);
			label.setBounds(new Rectangle(22, 113, 200, 15));
			label.setText("Confronta con un altro campo:");
			buttonMatching = new Button(sShell, SWT.NONE);
			buttonMatching.setBounds(new Rectangle(227, 108, 40, 27));
			buttonMatching.setText("Qui");
			buttonMatching.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					//TODO dare la possibilità di selezionare un altro campo (dello stesso tipo)
					//dall'albero della query
					System.out.println("dare la possibilità di selezionare un altro campo (dello stesso tipo) dall'albero della query");					
				}
			});
			buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(), ""});						
					sShell.close();
				}
			});
			buttonOk.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(),text.getText()});
					sShell.close();					
				}
			});
		
			
		} else if (item.getPathClass().contains("Date")) {
			
			System.out.println("Date");
			
		} else if (item.getPathClass().contains("Integer")|item.getPathClass().contains("int")) {
			
			System.out.println("Integer");
			
		} else {
			System.out.println(item.getPathClass()+" ---> "+item.getTreeNode().getText());
		}
		
		
		
		//TODO la shell deve restituire come output una stringa formattata per aggiungere il criterio
		
		
		
		
		
		
		
		return sShell;
	}

	
}
