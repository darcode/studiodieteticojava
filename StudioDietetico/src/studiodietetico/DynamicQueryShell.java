package studiodietetico;

import hibernate.Prenotazione;

import java.util.ArrayList;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import service.DynNode;
import service.Utils;

import org.eclipse.swt.custom.CCombo;

import command.VisitaDAO;

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
		sShell.setLayout(new GridLayout());
		sShell.setText("Seleziona data e ora");
		sShell.setSize(new Point(319, 373));
		final DateTime calendar = new DateTime (sShell, SWT.CALENDAR | SWT.BORDER);
		Label filler1 = new Label(sShell, SWT.NONE);
		calendar.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				System.out.println("widgetSelected()"); 
			}
			public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
			}
		});
		final DateTime time = new DateTime (sShell, SWT.TIME | SWT.SHORT);
		Label filler = new Label(sShell, SWT.NONE);
		buttonOk = new Button(sShell, SWT.NONE);
		buttonOk.setText("Ok");
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
		buttonCancella = new Button(sShell, SWT.NONE);
		buttonCancella.setBounds(new Rectangle(62, 108, 90, 27));
		buttonCancella.setText("Cancella");
		buttonCancella.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				item.getTreeNode().setText(new String[] {item.getTreeNode().getText(), ""});						
				sShell.close();
			}
		});
	
		
		
		
		
		
				
		if (item.getPathClass().contains("Date")) {
			
			System.out.println("Date");
			
		} else if (item.getPathClass().contains("Boolean")|item.getPathClass().contains("boolean")) {
			
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
		
			
		} else if (item.getPathClass().contains("Integer")|item.getPathClass().contains("int")) {
			
			System.out.println("Integer");
			
		} else {
			System.out.println(item.getPathClass()+" ---> "+item.getTreeNode().getText());
		}
		
		
		
		//TODO la shell deve restituire come output una stringa formattata per aggiungere il criterio
		
		
		
		
		
		
		
		return sShell;
	}

	
}
