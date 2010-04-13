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
import org.eclipse.swt.widgets.TreeItem;

import service.DynNode;
import service.Utils;

import org.eclipse.swt.custom.CCombo;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

public class DynamicQueryShell {
	
	private Criteria criteria;
	
	
	
	public DynamicQueryShell(Criteria crit){	
		criteria = crit;
	}	
	
	

	
}
