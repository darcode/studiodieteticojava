/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * PrenotazioneTimeSeriesChart.java
 * -------------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
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

import hibernate.Prestazione;

import java.awt.Color;
import java.awt.Frame;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import command.MedicoDAO;

/**
 * An example of a time series chart. For the most part, default settings are
 * used, except that the renderer is modified to show filled shapes (as well as
 * lines) at each data point.
 */
public class MediciTimeSeriesChart extends GraficiComposite {
	private static String titolo;

	/**
	 * A demonstration application showing how to create a simple time series
	 * chart. This example uses monthly data.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public MediciTimeSeriesChart(String title, Composite parent, int style,
			int tipo) {
		super(parent, style);

		Label titolo = new Label(this, SWT.NONE);
		titolo.setFont(new Font(titolo.getDisplay(), "arial", 15, SWT.BOLD));
		titolo.setText(title);
		GridData gdLbl = new GridData(SWT.FILL);
		titolo.setLayoutData(gdLbl);
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
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private static JFreeChart createChart(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				titolo, // title
				"Date", // x-axis label
				"Occorrenze", // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 * 
	 * @return The dataset.
	 */
	private static XYDataset createDataset(int tipo) {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		System.out.println("medico time graph");
		// anno 2010
		try {
			TimeSeries s1 = new TimeSeries("2010");
			for (Object item : MedicoDAO.getPrestazioni(2010)) {
				Prestazione item1 = (Prestazione)((Object[])item)[0];
				if (s1.getDataItem(new Day(item1.getId().getDataTurno())) == null)
					s1.addOrUpdate(new Day(item1.getId().getDataTurno()), 1);
				else
					s1.addOrUpdate(new Day(item1.getId().getDataTurno()), s1
							.getDataItem(new Day(item1.getId().getDataTurno()))
							.getValue().intValue() + 1);

			}
			dataset.addSeries(s1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataset;

	}

	/**
	 * Creates a panel for the demo (used by SuperDemo.java).
	 * 
	 * @return A panel.
	 */
	public static JPanel createDemoPanel(int tipo) {
		JFreeChart chart = createChart(createDataset(tipo));
		ChartPanel panel = new ChartPanel(chart);
		panel.setFillZoomRectangle(true);
		panel.setMouseWheelEnabled(true);
		return panel;
	}

}
