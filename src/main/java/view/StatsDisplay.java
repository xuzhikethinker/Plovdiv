/*
 * Copyright (c) 2009, Miroslav Batchkarov, University of Sussex
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of the University of Sussex nor the names of its
 *    contributors may be used to endorse or promote products  derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StatsDisplay.java
 *
 * Created on 11-Jun-2009, 02:43:03
 */
package view;

import controller.Stats;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import model.MyEdge;
import model.MyGraph;
import model.MyVertex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;

import static view.Utils.round;

/**
 * @author reseter
 */
public class StatsDisplay extends javax.swing.JFrame implements GraphEventListener<MyVertex, MyEdge> {

	/**
	 * Creates new form StatsDisplay
	 */
	public StatsDisplay() {
		initComponents();
//		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		updateContents();
		setSize(500, 500);
		setVisible(true);
		plotButton.doClick();
	}

	/**
	 * This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		distType = new javax.swing.ButtonGroup();
		scale = new javax.swing.ButtonGroup();
		jLabel1 = new javax.swing.JLabel();
		numVertex = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		numEdges = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		cc = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		avgDegree = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		avgPathLength = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		pane = new javax.swing.JPanel();
		plotButton = new javax.swing.JButton();
		jLabel8 = new javax.swing.JLabel();
		minDegree = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		maxDegree = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		loglog = new javax.swing.JCheckBox();
		cumulative = new javax.swing.JCheckBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Details");
		addWindowFocusListener(new java.awt.event.WindowFocusListener() {
			public void windowGainedFocus(java.awt.event.WindowEvent evt) {
				formWindowGainedFocus(evt);
			}

			public void windowLostFocus(java.awt.event.WindowEvent evt) {
				formWindowLostFocus(evt);
			}
		});
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent evt) {
				formWindowActivated(evt);
			}

			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}

			public void windowOpened(java.awt.event.WindowEvent evt) {
				formWindowOpened(evt);
			}
		});
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentMoved(java.awt.event.ComponentEvent evt) {
				formComponentMoved(evt);
			}

			public void componentResized(java.awt.event.ComponentEvent evt) {
				formComponentResized(evt);
			}

			public void componentShown(java.awt.event.ComponentEvent evt) {
				formComponentShown(evt);
			}
		});
		addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				formFocusGained(evt);
			}
		});
		addWindowStateListener(new java.awt.event.WindowStateListener() {
			public void windowStateChanged(java.awt.event.WindowEvent evt) {
				formWindowStateChanged(evt);
			}
		});
		addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				formPropertyChange(evt);
			}
		});

		jLabel1.setText("Vertex count:");

		numVertex.setText("jLabel1");

		jLabel3.setText("Edge count:");

		numEdges.setText("jLabel1");

		jLabel5.setText("Clustering coefficient:");

		cc.setText("jLabel1");

		jLabel7.setText("Average degree:");

		avgDegree.setText("jLabel1");

		jLabel9.setText("Average path length:");

		avgPathLength.setText("jLabel1");

		jButton1.setText("Hide");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout paneLayout = new javax.swing.GroupLayout(pane);
		pane.setLayout(paneLayout);
		paneLayout.setHorizontalGroup(
		paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGap(0, 847, Short.MAX_VALUE)
		);
		paneLayout.setVerticalGroup(
		paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGap(0, 381, Short.MAX_VALUE)
		);

		plotButton.setText("Plot");
		plotButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				plotButtonActionPerformed(evt);
			}
		});

		jLabel8.setText("Min degree");

		minDegree.setText("jLabel1");

		jLabel10.setText("Max degree");

		maxDegree.setText("jLabel1");

		jLabel11.setText("Degree distribution: *");

		jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12));
		jLabel2.setText("* If the graph does not appear, click Plot");

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribution type"));

		loglog.setText("Use log-log scale");
		loglog.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				loglogActionPerformed(evt);
			}
		});

		cumulative.setText("Plot cumulative distribution");
		cumulative.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cumulativeActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
		jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(jPanel1Layout.createSequentialGroup()
		.addContainerGap()
		.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addComponent(loglog)
		.addComponent(cumulative))
		.addContainerGap(81, Short.MAX_VALUE))
		);
		jPanel1Layout.setVerticalGroup(
		jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(jPanel1Layout.createSequentialGroup()
		.addComponent(loglog)
		.addGap(18, 18, 18)
		.addComponent(cumulative)
		.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		.addContainerGap()
		.addComponent(jButton1)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 421, Short.MAX_VALUE)
		.addComponent(jLabel2)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(plotButton))
		.addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addGroup(layout.createSequentialGroup()
		.addContainerGap()
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel1)
		.addGap(18, 18, 18)
		.addComponent(numVertex))
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel3)
		.addGap(18, 18, 18)
		.addComponent(numEdges))
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel5)
		.addGap(18, 18, 18)
		.addComponent(cc))
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel9)
		.addGap(18, 18, 18)
		.addComponent(avgPathLength))
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel7)
		.addGap(18, 18, 18)
		.addComponent(avgDegree)))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 307, Short.MAX_VALUE)
		.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel8)
		.addGap(18, 18, 18)
		.addComponent(minDegree))
		.addGroup(layout.createSequentialGroup()
		.addComponent(jLabel10)
		.addGap(18, 18, 18)
		.addComponent(maxDegree))
		.addComponent(jLabel11))))
		.addContainerGap())
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addContainerGap()
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel1)
		.addComponent(numVertex))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel3)
		.addComponent(numEdges))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel5)
		.addComponent(cc))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel7)
		.addComponent(avgDegree))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel9)
		.addComponent(avgPathLength))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel8)
		.addComponent(minDegree))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(jLabel10)
		.addComponent(maxDegree))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(jLabel11))
		.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
		.addGap(8, 8, 8)
		.addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addGap(18, 18, 18)
		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
		.addComponent(plotButton)
		.addComponent(jButton1)
		.addComponent(jLabel2))
		.addContainerGap())
		);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		dispose();
	}//GEN-LAST:event_jButton1ActionPerformed

	private void plotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plotButtonActionPerformed
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_plotButtonActionPerformed

	private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
		this.drawHistogram();
		validate();
	}//GEN-LAST:event_formComponentResized

	private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formWindowActivated

	private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formWindowStateChanged

	private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formWindowGainedFocus

	private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formWindowOpened

	private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
	}//GEN-LAST:event_formPropertyChange

	private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formComponentShown

	private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formComponentMoved

	private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formFocusGained

	private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_formWindowLostFocus

	private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
		setVisible(false);
		MyGraph.getInstance().removeGraphEventListener(this);
	}//GEN-LAST:event_formWindowClosing

	private void loglogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loglogActionPerformed
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_loglogActionPerformed

	private void cumulativeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cumulativeActionPerformed
		if (loglog.isSelected()) {
			drawLogLogHistogram();
		} else {
			drawHistogram();
		}
	}//GEN-LAST:event_cumulativeActionPerformed

	public void updateContents() {
		System.out.println("updateng pane in " + Thread.currentThread().getName());
		Stats.recalculateAll();
		MyGraph g = MyGraph.getInstance();
		avgDegree.setText("" + round(Stats.getAvgDegree()));
		avgPathLength.setText("" + round(Stats.getAPL()));
		cc.setText("" + round(Stats.getCC()));
		numEdges.setText("" + g.getEdgeCount());
		numVertex.setText("" + g.getVertexCount());
		minDegree.setText("" + Stats.getMinDegree());
		maxDegree.setText("" + Stats.getMaxDegree());
		plotButton.doClick();
		plotButton.doClick();
		plotButton.doClick();
		plotButton.doClick();
		plotButton.doClick();
		repaint();
		repaint();
		repaint();
		validate();
	}

	private void drawHistogram() {
		System.out.println("drawing histogram in " + Thread.currentThread().getName());
		final JFreeChart chart = prepareChartFromData();

//        chart.setBackgroundPaint(Color.white);
//        plot.setOutlinePaint(Color.black);
		createPanelForChart(chart);
		pane.repaint();
	}


	public void drawLogLogHistogram() {
		System.out.println("drawing log-log hist in " + Thread.currentThread().getName());
		final JFreeChart chart = prepareChartFromData();

		final NumberAxis domainAxis = new LogarithmicAxis("Log(Degree)");
		final NumberAxis rangeAxis = new LogarithmicAxis("Log(Number of vertices)");

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setDomainAxis(domainAxis);
		plot.setRangeAxis(rangeAxis);
//        chart.setBackgroundPaint(Color.white);
//        plot.setOutlinePaint(Color.black);
//        plot.setFixedDomainAxisSpace(new AxisSpace());
		createPanelForChart(chart);
		pane.repaint();
	}

	private void createPanelForChart(JFreeChart chart) {
		final ChartPanel chartPanel = new ChartPanel(chart);

		pane.setLayout(new BorderLayout());
		pane.removeAll();
		pane.add(chartPanel, BorderLayout.CENTER);
		pane.validate();
		pane.repaint();

	}

	private JFreeChart prepareChartFromData() {
		final JFreeChart chart = ChartFactory.createXYLineChart(
		"Degree distribution", // chart title
		"Degree", // domain axis label
		"Number of vertices", // range axis label
		prepareData(), // data
		PlotOrientation.VERTICAL,
		false, // include legend
		true,
		false);

		final XYPlot plot = chart.getXYPlot();

		plot.setRenderer(0, new XYSplineRenderer(1));

		final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		domainAxis.setRange(Stats.getMinDegree(), Stats.getMaxDegree());
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
	}

	private XYSeriesCollection prepareData() {
		final XYSeries s1 = new XYSeries("Degree");
		int[] buckets;
		if (!cumulative.isSelected()) {
			buckets = Stats.degreeDistribution();
		} else {
			buckets = Stats.cumulativeDegreeDistribution();
		}

		for (int i = loglog.isSelected() ? 1 : 0; i < buckets.length; i++) {
			if (buckets[i] > 0) {
				s1.add(i, buckets[i]); //0s not ok for log-log plotting
			}
		}

		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(s1);
		System.out.println("data series items: " + s1.getItems());
		return dataset;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel avgDegree;
	private javax.swing.JLabel avgPathLength;
	private javax.swing.JLabel cc;
	private javax.swing.JCheckBox cumulative;
	private javax.swing.ButtonGroup distType;
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JCheckBox loglog;
	private javax.swing.JLabel maxDegree;
	private javax.swing.JLabel minDegree;
	private javax.swing.JLabel numEdges;
	private javax.swing.JLabel numVertex;
	private javax.swing.JPanel pane;
	private static javax.swing.JButton plotButton;
	private javax.swing.ButtonGroup scale;
	// End of variables declaration//GEN-END:variables

	@Override
	public void handleGraphEvent(GraphEvent<MyVertex, MyEdge> evt) {
		System.out.println("Detailed stats handling event in thread " + Thread.currentThread().getName());
		Stats.printStatistics();
		updateContents();
	}
}
