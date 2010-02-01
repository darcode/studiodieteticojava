package service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.part.ViewPart;




public class Utils {

	
	public static  boolean isBlankOrNull(String s) {
		if (s != null && !s.equals(""))
			return false;
		else return true;
	}
	

	public static String getStringFromBoundle(String key,
			Object[] parms) {

		ResourceBundle res = ResourceBundle.getBundle("it.exhicon.bionetwork.action.ApplicationResource");
		MessageFormat formatter = new MessageFormat("");
		formatter.applyPattern(res.getString(key));

		return formatter.format(parms);
	}
	
	public static String getStringFromBoundle(String boundle, String key,
			Object[] parms) {

		ResourceBundle res = ResourceBundle.getBundle(boundle);
		MessageFormat formatter = new MessageFormat("");
		formatter.applyPattern(res.getString(key));

		return formatter.format(parms);
	}

	public static String getStringFromApplicationResource(String key, Object[] parms) {
		ResourceBundle res = ResourceBundle.getBundle("it.exhicon.bionetwork.action.ApplicationResource");
		MessageFormat formatter = new MessageFormat("");
		formatter.applyPattern(res.getString(key));

		return formatter.format(parms);
	}

	
	public static Date convertStringToDate(String dataString, String formato) {		
		DateFormat format = new SimpleDateFormat(formato);
		Date myParsedDate = null;
		try {
			myParsedDate = format.parse(dataString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return myParsedDate;
	}
	
    public static String ConvertDateToString(Date data, String formato)    {
    	String s = null;
    	if (data!=null) {
            SimpleDateFormat dataFormat = new SimpleDateFormat(formato); 
            StringBuilder now = new StringBuilder( dataFormat.format( data ) );
            s = now.toString();			
		}   
		return s; 
    }
    
    public static double giorniFraDueDate( GregorianCalendar dallaDataGC, GregorianCalendar allaDataGC ) {

		// conversione in millisecondi
		long dallaDataMilliSecondi = dallaDataGC.getTimeInMillis();
		long allaDataMilliSecondi = allaDataGC.getTimeInMillis();

		long millisecondiFraDueDate = allaDataMilliSecondi - dallaDataMilliSecondi;

		// conversione in giorni con la divisione intera
		double giorniFraDueDate_DivInt = millisecondiFraDueDate / 86400000;
		
		// conversione in giorni con la divisione reale
		double giorniFraDueDate_DivReal = millisecondiFraDueDate / 86400000.0;

		// conversione in giorni con arrotondamento della divisione reale
		double giorniFraDueDate_DivRealRound = Math.round( millisecondiFraDueDate / 86400000.0 );

		return giorniFraDueDate_DivRealRound;
	}
    
    public static void showView(String viewId) {
    	//IWorkbenchPage.showView("myViewID",	null, IWorkbenchPage.VIEW_CREATE); 
    	IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		//Messages.trace("lenght:" + views.length);
		if (views.length == 0 || !views[0].getId().equals(viewId)) {
			workbench.getActiveWorkbenchWindow().getActivePage()
					.resetPerspective();

			try {
				workbench.getActiveWorkbenchWindow().getActivePage().showView(
						viewId);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
			getActiveView().setFocus();
		}
	}
 

    public static IViewPart getActiveView() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IViewReference[] views = workbench.getActiveWorkbenchWindow()
				.getActivePage().getViewReferences();
		if (views.length > 0)
			return views[0].getView(false);
		else
			return null;
	}
    public static String rimuoviS(String field){
		int indice = field.length();
		indice--;
		field = field.substring(0, indice);
		return field;
	}
	
	public static String upperCase(String field){
		String prima = field.substring(0, 1).toUpperCase();
		field = prima.concat(field.substring(1, field.length()));
		return field;
	}
	
	public static Color getStandardWhiteColor() {
		return Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	}
}
