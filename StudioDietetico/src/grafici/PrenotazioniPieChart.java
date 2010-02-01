/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------------
 * PrenotazioniPieChart.java
 * ------------------
 * (C) Copyright 2003-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * Changes
 * -------
 * 09-Mar-2005 : Version 1, copied from the demo collection that ships with
 *               the JFreeChart Developer Guide (DG);
 *
 */

package grafici;

import hibernate.Tipologiavisita;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import command.VisitaDAO;

/**
 * A simple demonstration application showing how to create a pie chart using
 * data from a {@link DefaultPieDataset}.
 */
public class PrenotazioniPieChart extends GraficiComposite {
	private static String titolo;

	/**
	 * Default constructor.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public PrenotazioniPieChart(String title, Composite parent, int style,
			int tipo) {
		super(parent, style);
		titolo = title;
		Composite cmp = new Composite(this, SWT.FILL | SWT.EMBEDDED);
		GridData gdCmp = new GridData(SWT.FILL);
		gdCmp.horizontalAlignment = SWT.FILL;
		gdCmp.verticalAlignment = SWT.FILL;
		gdCmp.grabExcessHorizontalSpace = true;
		gdCmp.grabExcessVerticalSpace = true;
		cmp.setLayoutData(gdCmp);
		cmp.setLayout(new GridLayout(1, false));

		JPanel chartPanel = createDemoPanel(tipo);
		Frame graphFrame = SWT_AWT.new_Frame(cmp);
		graphFrame.add(chartPanel);
		graphFrame.pack();

		GridData gdThis = new GridData(SWT.FILL);
		gdThis.horizontalAlignment = SWT.FILL;
		gdThis.verticalAlignment = SWT.FILL;
		gdThis.grabExcessHorizontalSpace = true;
		gdThis.grabExcessVerticalSpace = true;

		this.setLayoutData(gdThis);
		this.setLayout(new GridLayout(1, false));
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return A sample dataset.
	 */
	private static PieDataset createDataset(int tipo) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (tipo == 1) {
			// Tipologia visita
			ArrayList<Tipologiavisita> tipiVisite = VisitaDAO
					.getTipologVisita();

			for (Tipologiavisita tipologia : tipiVisite) {
				Double percentuale = new Double(VisitaDAO
						.getPrenotazioniNumberPerTipologia(tipologia
								.getIdTipologiaVisita()));
				dataset.setValue(tipologia.getTipologia(), percentuale);
			}
		} else {
			alertGraficoNonDisp();
		}

		return dataset;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return A chart.
	 */
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart(titolo, // chart
				// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setNoDataMessage("No data available");

		return chart;

	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 * 
	 * @return A panel.
	 */
	public static JPanel createDemoPanel(int tipo) {
		JFreeChart chart = createChart(createDataset(tipo));
		return new ChartPanel(chart);
	}

}
