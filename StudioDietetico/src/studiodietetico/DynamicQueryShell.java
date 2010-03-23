package studiodietetico;

import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import service.DynNode;
import service.Utils;

import org.eclipse.swt.custom.CCombo;

public class DynamicQueryShell {
	
	private Shell sShell;  //  @jve:decl-index=0:visual-constraint="10,13"
	private Button buttonOk = null;
	private Label etichetta = null;
	private Text text = null;
	private Label nomeAttributo = null;
	private Button buttonCancella = null;
	private Button buttonMatching = null;
	private CCombo cCombo = null;
	private Button button = null;
	private CCombo cCombo1 = null;
	
	public DynamicQueryShell(){	
		
	}	
	
	public Shell createShell(final DynNode item){		
		
		
		
		
		if (item.getPathClass().contains("Integer")|item.getPathClass().contains("int")) {
				
			sShell = new Shell();
			sShell.setSize(new Point(340, 233));
			buttonOk = new Button(sShell, SWT.NONE);
			buttonOk.setText("Ok");
			buttonOk.setBounds(new Rectangle(172, 165, 106, 27));
			etichetta = new Label(sShell, SWT.NONE);
			etichetta.setBounds(new Rectangle(87, 9, 117, 34));
			etichetta.setText("Inserisci il valore");
			nomeAttributo = new Label(sShell, SWT.NONE);
			nomeAttributo.setBounds(new Rectangle(26, 87, 121, 22));
			nomeAttributo.setText(item.getTreeNode().getText());
			buttonCancella = new Button(sShell, SWT.NONE);
			buttonCancella.setBounds(new Rectangle(62, 165, 90, 27));
			buttonCancella.setText("Cancella");
			text = new Text(sShell, SWT.BORDER);
			text.setBounds(new Rectangle(215, 62, 113, 25));
			buttonMatching = new Button(sShell, SWT.NONE);
			buttonMatching.setBounds(new Rectangle(221, 105, 103, 27));
			buttonMatching.setText("Altro campo");
			cCombo1 = new CCombo(sShell, SWT.NONE);
			cCombo1.setBounds(new Rectangle(154, 86, 55, 27));
			cCombo1.add("<");
			cCombo1.add("=<");
			cCombo1.add(">");
			cCombo1.add(">=");
			cCombo1.add("=");
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
			
			sShell = new Shell();
			sShell.setSize(new Point(340, 233));
			buttonOk = new Button(sShell, SWT.NONE);
			buttonOk.setText("Ok");
			buttonOk.setBounds(new Rectangle(172, 165, 106, 27));
			etichetta = new Label(sShell, SWT.NONE);
			etichetta.setBounds(new Rectangle(87, 9, 117, 34));
			etichetta.setText("Inserisci il valore");
			nomeAttributo = new Label(sShell, SWT.NONE);
			nomeAttributo.setBounds(new Rectangle(26, 87, 121, 22));
			nomeAttributo.setText(item.getTreeNode().getText());
			buttonCancella = new Button(sShell, SWT.NONE);
			buttonCancella.setBounds(new Rectangle(62, 165, 90, 27));
			buttonCancella.setText("Cancella");
			text = new Text(sShell, SWT.BORDER);
			text.setBounds(new Rectangle(215, 62, 113, 25));
			buttonMatching = new Button(sShell, SWT.NONE);
			buttonMatching.setBounds(new Rectangle(221, 105, 103, 27));
			buttonMatching.setText("Altro campo");
			cCombo1 = new CCombo(sShell, SWT.NONE);
			cCombo1.setBounds(new Rectangle(154, 86, 55, 27));
			cCombo1.add("<");
			cCombo1.add("=<");
			cCombo1.add(">");
			cCombo1.add(">=");
			cCombo1.add("=");
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
			
			GridData gridData6 = new GridData();
			gridData6.horizontalAlignment = GridData.CENTER;
			gridData6.verticalAlignment = GridData.CENTER;
			GridData gridData5 = new GridData();
			gridData5.horizontalAlignment = GridData.CENTER;
			gridData5.verticalAlignment = GridData.CENTER;
			GridData gridData4 = new GridData();
			GridData gridData1 = new GridData();
			gridData1.horizontalSpan = 2;
			GridData gridData = new GridData();
			gridData.horizontalAlignment = GridData.CENTER;
			gridData.horizontalSpan = 2;
			gridData.verticalAlignment = GridData.CENTER;
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 2;
			gridLayout.makeColumnsEqualWidth = false;
			sShell = new Shell();
			sShell.setText("Seleziona data e ora");
			sShell.setLayout(gridLayout);
			sShell.setSize(new Point(320, 332));
			final DateTime calendar = new DateTime (sShell, SWT.CALENDAR | SWT.BORDER);
			calendar.setLayoutData(gridData1);
			calendar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					System.out.println("widgetSelected()"); 
				}
				public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
				}
			});
			final DateTime time = new DateTime (sShell, SWT.TIME | SWT.SHORT);
			time.setLayoutData(gridData);
			button = new Button(sShell, SWT.NONE);
			button.setText("Confronta con un altro campo");
			button.setLayoutData(gridData4);
			Label filler17 = new Label(sShell, SWT.NONE);
			buttonCancella = new Button(sShell, SWT.NONE);			
			buttonCancella.setBounds(new Rectangle(62, 108, 90, 27));
			buttonCancella.setLayoutData(gridData6);
			buttonCancella.setText("Cancella");
			buttonOk = new Button(sShell, SWT.NONE);
			buttonOk.setText("Ok");
			buttonOk.setLayoutData(gridData5);
			buttonOk.setBounds(new Rectangle(172, 165, 106, 27));
			buttonOk.addSelectionListener (new SelectionAdapter () {
				public void widgetSelected (SelectionEvent e) {
					System.out.println ("Calendar date selected (MM/DD/YYYY) = " + (calendar.getMonth () + 1) + "/" + calendar.getDay () + "/" + calendar.getYear ());
					System.out.println ("Time selected (HH:MM) = " + time.getHours () + ":" + (time.getMinutes () < 10 ? "0" : "") + time.getMinutes ());
					String dateString = calendar.getYear ()+"-"+(calendar.getMonth () + 1)+"-"+calendar.getDay ()+" "+time.getHours () +":"+(time.getMinutes () < 10 ? "0" : "") + time.getMinutes ()+":00";
					String formato = "yyyy-MM-dd HH:mm:ss";
					Date selectedData = Utils.convertStringToDate(dateString, formato); 
					
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(),selectedData.toString()});				
					//TODO inserire il criteria adeguato				
					
					sShell.close ();
				}
			});
			buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
				public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
					item.getTreeNode().setText(new String[] {item.getTreeNode().getText(), ""});						
					sShell.close();
				}
			});
			
		} else if (item.getPathClass().contains("Boolean")|item.getPathClass().contains("boolean")) {
			
			sShell = new Shell();
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
			
			sShell = new Shell();
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
			
		} else if (item.getPathClass().contains("String")) {
			
			sShell = new Shell();
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
		
			
		} else {
			System.out.println(item.getPathClass()+" ---> "+item.getTreeNode().getText());
		}
		
		
		
		//TODO la shell deve restituire come output una stringa formattata per aggiungere il criterio
		
		
		
		
		
		
		
		return sShell;
	}

	
}
