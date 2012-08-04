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
 * Display.java
 *
 * Created on 15-Jul-2009, 14:27:03
 */
package view;

import controller.*;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.algorithms.layout.util.VisRunner;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.ObservableGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.util.Animator;
import model.MyEdge;
import model.MyGraph;
import model.MyVertex;
import model.Strings;
import org.apache.commons.collections15.functors.ConstantTransformer;
import view.CustomMouse.CustomGraphMouse;
import view.CustomVisualization.CenterLabelPositioner;
import view.CustomVisualization.CustomEdgeLabeller;
import view.CustomVisualization.CustomVertexLabeler;
import view.CustomVisualization.CustomVertexRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import model.dynamics.SIDynamics;
import model.dynamics.SIRDynamics;
import model.dynamics.SISDynamics;

/**
 * @author mb724
 */
public class Display extends JFrame {

    private static InfoGatherer gatherer;
    //declared as fields rather than as local variables so that their value can be altered by listeners
    public static VisualizationViewer vv;
    private static ScalingControl scaler;
    //    EditingModalGraphMouse<MyVertex, MyEdge> graphMouse;
    private static CustomGraphMouse graphMouse;
    static AnnotationControls<MyVertex, MyEdge> annotationControls;
    static JPanel annotationControlsToolbar;
    //the current layout
    private static PersistentLayoutImpl2<MyVertex, MyEdge> persistentLayout;
    //whether this window is a standard free roam or inludes interactive controls
    private Mode mode;
    //the stats display associated with this window
    StatsThread st;
    private static int waitTime = 100;

    /**
     * Creates new form Display
     *
     * @param mode what mode this window should be in (the only difference the
     * presence or absence of a single button and its function)
     * @param n initial number of vertices in the small-world tutorial, ignored
     * in any other mode
     */
    public Display(Mode mode, int n) {
        boolean cancelled = false; //if the user fails to provide input
        this.mode = mode;
        gatherer = InfoGatherer.getInstance();
        initComponents();
        //assume
        //todo these modes are obsolete, remove
        if (mode.equals(Mode.SF_INTERACTIVE)) {
            Generator.generateRectangularLattice(1, 2);
            redisplayCompletely();
            isom.setSelected(true);
            transform.setSelected(true);

        } else if (mode.equals(Mode.SW_INTERACTIVE)) {
            Generator.generateKleinbergSmallWorld(n, 2, 0);
            circleL.setSelected(true);
            redisplayCompletely();
            transform.setSelected(true);
            layout.setVisible(false);

        } else {
            // normal mode
            Generator.generateRectangularLattice(0, 0);
        }
        if (!cancelled) {
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            redisplayCompletely();
        } else {
            this.dispose();
        }
        parseSimulationParameters(null);//trigger parsing of default values for transmission params
    }

    /**
     * Sets the info gatherer this displays cooperates with. Must be explicitly
     * called after the both the display and the are created
     *
     * @param i
     */
    public void setInfoGatherer(InfoGatherer i) {
        gatherer = i;
    }

    /**
     * Recalculates and display the stats of the graph and of the vertex passed
     * in. If it is null, returns all local statistics to "0.0"
     *
     * @param v
     */
    public static void recalculateStats(MyVertex v) {
        Stats.recalculateAll();
        //populate information labels accross the screen
        totalCC.setText("" + Stats.getCC());
        totalAPL.setText("" + Stats.getAPL());
        totalAD.setText("" + Stats.getAvgDegree());
        totalA.setText("" + Stats.getWeightedDegreeCorrelation());
        //information about a certain node
        if (v != null) {
            localCC.setText("" + Stats.getCC(v));
            localAPL.setText("" + Stats.getAPL(v));
            localBC.setText("" + Stats.getBC(v));
            in.setText("" + MyGraph.getInstance().inDegree(v));
            out.setText("" + MyGraph.getInstance().outDegree(v));

        } else {
            localCC.setText("0.0");
            localAPL.setText("0.0");
            localBC.setText("0.0");
            in.setText("0.0");
            out.setText("0.0");
        }
//        repaint();
//        validate();
//        if (st != null && st.isVisible()) {
//            st.updateContents();
//        }
        //TODO commented thsi out, but it should be there
    }

    public VisualizationViewer getVV() {
        return vv;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vertexLabel = new javax.swing.ButtonGroup();
        layouts = new javax.swing.ButtonGroup();
        modeSelection = new javax.swing.ButtonGroup();
        edgeLabel = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        select = new javax.swing.JToggleButton();
        edit = new javax.swing.JToggleButton();
        transform = new javax.swing.JToggleButton();
        annotate = new javax.swing.JToggleButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        totalCC = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jLabel3 = new javax.swing.JLabel();
        totalAPL = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        jLabel5 = new javax.swing.JLabel();
        totalAD = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        jLabel7 = new javax.swing.JLabel();
        totalA = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JToolBar.Separator();
        jSeparator17 = new javax.swing.JToolBar.Separator();
        showDDToolbar = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel9 = new javax.swing.JLabel();
        localCC = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jLabel11 = new javax.swing.JLabel();
        localAPL = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jLabel13 = new javax.swing.JLabel();
        localBC = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jLabel14 = new javax.swing.JLabel();
        in = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JToolBar.Separator();
        jLabel15 = new javax.swing.JLabel();
        out = new javax.swing.JLabel();
        pane = new javax.swing.JPanel();
        jToolBar4 = new javax.swing.JToolBar();
        zoomInToolbar = new javax.swing.JButton();
        zoomOutToolbar = new javax.swing.JButton();
        jToolBar5 = new javax.swing.JToolBar();
        pauseSimToolbarButton = new javax.swing.JButton();
        doStepToolbarButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        enableGUIToolbarCheckbox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        speed = new javax.swing.JSlider();
        dynamics = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        tau = new javax.swing.JTextField();
        gamaLabel = new javax.swing.JLabel();
        gama = new javax.swing.JTextField();
        edgeBreakingLabel = new javax.swing.JLabel();
        breakingRate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        deltaT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        newDoc = new javax.swing.JMenuItem();
        fileSave = new javax.swing.JMenuItem();
        fileLoad = new javax.swing.JMenuItem();
        fileGenerate1 = new javax.swing.JMenuItem();
        fileQuit1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        zoomIn = new javax.swing.JMenuItem();
        zoomOut = new javax.swing.JMenuItem();
        showDD = new javax.swing.JMenuItem();
        jSeparator20 = new javax.swing.JSeparator();
        dumbToJpg = new javax.swing.JMenuItem();
        label2 = new javax.swing.JMenu();
        vDEgree = new javax.swing.JRadioButtonMenuItem();
        vCC = new javax.swing.JRadioButtonMenuItem();
        vBC = new javax.swing.JRadioButtonMenuItem();
        vDist = new javax.swing.JRadioButtonMenuItem();
        vGeneration = new javax.swing.JRadioButtonMenuItem();
        vID = new javax.swing.JRadioButtonMenuItem();
        vNone = new javax.swing.JRadioButtonMenuItem();
        label3 = new javax.swing.JMenu();
        eWeight = new javax.swing.JRadioButtonMenuItem();
        eID = new javax.swing.JRadioButtonMenuItem();
        eBC = new javax.swing.JRadioButtonMenuItem();
        eNone = new javax.swing.JRadioButtonMenuItem();
        layout = new javax.swing.JMenu();
        kk = new javax.swing.JRadioButtonMenuItem();
        fr = new javax.swing.JRadioButtonMenuItem();
        isom = new javax.swing.JRadioButtonMenuItem();
        spring = new javax.swing.JRadioButtonMenuItem();
        circleL = new javax.swing.JRadioButtonMenuItem();
        menuSimulation = new javax.swing.JMenu();
        simRun = new javax.swing.JMenuItem();
        simRunUntil = new javax.swing.JMenuItem();
        simPauseMenuItem = new javax.swing.JMenuItem();
        simStop = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JSeparator();
        infect = new javax.swing.JMenuItem();
        setSusceptible = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        helpHowTo = new javax.swing.JMenuItem();
        helpAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Free Roam Mode");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mouse mode"));
        jToolBar1.setRollover(true);
        jToolBar1.setEnabled(false);

        modeSelection.add(select);
        select.setText("Select");
        select.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        select.setFocusable(false);
        select.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        select.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        select.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectItemStateChanged(evt);
            }
        });
        jToolBar1.add(select);

        modeSelection.add(edit);
        edit.setText("Edit");
        edit.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        edit.setFocusable(false);
        edit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        edit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        edit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                editItemStateChanged(evt);
            }
        });
        jToolBar1.add(edit);

        modeSelection.add(transform);
        transform.setText("Transform View");
        transform.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        transform.setFocusable(false);
        transform.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        transform.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        transform.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                transformItemStateChanged(evt);
            }
        });
        jToolBar1.add(transform);

        modeSelection.add(annotate);
        annotate.setText("Annotate");
        annotate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        annotate.setFocusable(false);
        annotate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        annotate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        annotate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                annotateItemStateChanged(evt);
            }
        });
        jToolBar1.add(annotate);

        jToolBar2.setBorder(javax.swing.BorderFactory.createTitledBorder("Graph statistics"));
        jToolBar2.setRollover(true);

        jLabel1.setText("Clustering coefficient = ");
        jToolBar2.add(jLabel1);

        totalCC.setText("0");
        jToolBar2.add(totalCC);
        jToolBar2.add(jSeparator2);

        jLabel3.setText("APL = ");
        jToolBar2.add(jLabel3);

        totalAPL.setText("0");
        jToolBar2.add(totalAPL);
        jToolBar2.add(jSeparator4);

        jLabel5.setText("Avg degree = ");
        jToolBar2.add(jLabel5);

        totalAD.setText("0");
        jToolBar2.add(totalAD);
        jToolBar2.add(jSeparator3);

        jLabel7.setText("Degree correlation = ");
        jToolBar2.add(jLabel7);

        totalA.setText("0");
        jToolBar2.add(totalA);
        jToolBar2.add(jSeparator10);
        jToolBar2.add(jSeparator17);

        showDDToolbar.setText("Show Detailed Statistics");
        showDDToolbar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        showDDToolbar.setFocusable(false);
        showDDToolbar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        showDDToolbar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        showDDToolbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDDToolbarActionPerformed(evt);
            }
        });
        jToolBar2.add(showDDToolbar);
        jToolBar2.add(jSeparator5);

        jToolBar3.setBorder(javax.swing.BorderFactory.createTitledBorder("Node statistics"));
        jToolBar3.setOrientation(1);
        jToolBar3.setRollover(true);

        jLabel9.setText("Clustering coefficient");
        jToolBar3.add(jLabel9);

        localCC.setText("0.0");
        jToolBar3.add(localCC);
        jToolBar3.add(jSeparator6);

        jLabel11.setText("APL");
        jToolBar3.add(jLabel11);

        localAPL.setText("0.0");
        jToolBar3.add(localAPL);
        jToolBar3.add(jSeparator8);

        jLabel13.setText("Betweenness dentrality");
        jToolBar3.add(jLabel13);

        localBC.setText("0.0");
        jToolBar3.add(localBC);
        jToolBar3.add(jSeparator9);

        jLabel14.setText("In degree");
        jToolBar3.add(jLabel14);

        in.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        in.setText("0.0");
        jToolBar3.add(in);
        jToolBar3.add(jSeparator14);

        jLabel15.setText("Out degree");
        jToolBar3.add(jLabel15);

        out.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        out.setText("0.0");
        jToolBar3.add(out);

        pane.setMinimumSize(new java.awt.Dimension(100, 100));
        pane.setName("pane"); // NOI18N

        javax.swing.GroupLayout paneLayout = new javax.swing.GroupLayout(pane);
        pane.setLayout(paneLayout);
        paneLayout.setHorizontalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        paneLayout.setVerticalGroup(
            paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jToolBar4.setBorder(javax.swing.BorderFactory.createTitledBorder("Zoom level"));
        jToolBar4.setRollover(true);
        jToolBar4.setEnabled(false);

        zoomInToolbar.setText("Zoom in");
        zoomInToolbar.setFocusable(false);
        zoomInToolbar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomInToolbar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomInToolbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInToolbarActionPerformed(evt);
            }
        });
        jToolBar4.add(zoomInToolbar);

        zoomOutToolbar.setText("Zoom out");
        zoomOutToolbar.setFocusable(false);
        zoomOutToolbar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoomOutToolbar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoomOutToolbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutToolbarActionPerformed(evt);
            }
        });
        jToolBar4.add(zoomOutToolbar);

        jToolBar5.setBorder(javax.swing.BorderFactory.createTitledBorder("Simulation controls"));
        jToolBar5.setRollover(true);
        jToolBar5.setEnabled(false);

        pauseSimToolbarButton.setText("Resume");
        pauseSimToolbarButton.setFocusable(false);
        pauseSimToolbarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pauseSimToolbarButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pauseSimToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseSimToolbarButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(pauseSimToolbarButton);

        doStepToolbarButton.setText("Do step");
        doStepToolbarButton.setFocusable(false);
        doStepToolbarButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        doStepToolbarButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        doStepToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doStepToolbarButtonActionPerformed(evt);
            }
        });
        jToolBar5.add(doStepToolbarButton);
        jToolBar5.add(jSeparator1);

        enableGUIToolbarCheckbox.setSelected(true);
        enableGUIToolbarCheckbox.setText("Enable visualisation");
        enableGUIToolbarCheckbox.setFocusable(false);
        enableGUIToolbarCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableGUIToolbarCheckboxActionPerformed(evt);
            }
        });
        jToolBar5.add(enableGUIToolbarCheckbox);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Disease controls"));

        jLabel2.setText("Dynamics");

        speed.setMajorTickSpacing(1000);
        speed.setMaximum(5000);
        speed.setPaintLabels(true);
        speed.setPaintTicks(true);
        speed.setValue(1500);
        speed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedStateChanged(evt);
            }
        });
        speed.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                speedKeyPressed(evt);
            }
        });

        dynamics.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SI", "SIS", "SIR" }));
        dynamics.setSelectedIndex(2);
        dynamics.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dynamicsItemStateChanged(evt);
            }
        });

        jLabel4.setText("Transmission rate");

        tau.setText("2");
        tau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                handleSimControlInput(evt);
            }
        });

        gamaLabel.setText("Recovery rate");

        gama.setText("1");
        gama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                gamaKeyReleased(evt);
            }
        });

        edgeBreakingLabel.setText("Edge breaking rate");

        breakingRate.setText("0.1");
        breakingRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakingRateActionPerformed(evt);
            }
        });
        breakingRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                breakingRateKeyReleased(evt);
            }
        });

        jLabel6.setText("Time step");

        deltaT.setText("0.1");
        deltaT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                deltaTKeyReleased(evt);
            }
        });

        jLabel8.setText("Wait time between steps, ms");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(115, 115, 115))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jSeparator7)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(133, 133, 133))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(gamaLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(edgeBreakingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(30, 30, 30)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(breakingRate, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dynamics, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(gama)
                                    .addComponent(tau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(deltaT, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(speed, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dynamics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edgeBreakingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(breakingRate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deltaT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuFile.setText("File");

        newDoc.setText("New document");
        newDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newDocActionPerformed(evt);
            }
        });
        menuFile.add(newDoc);

        fileSave.setText("Save...");
        fileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveActionPerformed(evt);
            }
        });
        menuFile.add(fileSave);

        fileLoad.setText("Load...");
        fileLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileLoadActionPerformed(evt);
            }
        });
        menuFile.add(fileLoad);

        fileGenerate1.setText("Generate...");
        fileGenerate1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileGenerate1ActionPerformed(evt);
            }
        });
        menuFile.add(fileGenerate1);

        fileQuit1.setText("Quit");
        fileQuit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileQuit1ActionPerformed(evt);
            }
        });
        menuFile.add(fileQuit1);

        jMenuBar1.add(menuFile);

        jMenu2.setText("View");

        zoomIn.setText("Zoom IN");
        jMenu2.add(zoomIn);

        zoomOut.setText("Zoom OUT");
        jMenu2.add(zoomOut);

        showDD.setText("Show Detailed Statistics ");
        showDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showDDActionPerformed(evt);
            }
        });
        jMenu2.add(showDD);
        jMenu2.add(jSeparator20);

        dumbToJpg.setText("Save to .jpg");
        dumbToJpg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dumbToJpgActionPerformed(evt);
            }
        });
        jMenu2.add(dumbToJpg);

        jMenuBar1.add(jMenu2);

        label2.setText("Label vertices with...");

        vertexLabel.add(vDEgree);
        vDEgree.setMnemonic('A');
        vDEgree.setText("Degree");
        vDEgree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vDEgreeActionPerformed(evt);
            }
        });
        label2.add(vDEgree);

        vertexLabel.add(vCC);
        vCC.setMnemonic('B');
        vCC.setText("Clustering coefficient");
        vCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vCCActionPerformed(evt);
            }
        });
        label2.add(vCC);

        vertexLabel.add(vBC);
        vBC.setMnemonic('C');
        vBC.setText("Betweennesss centrality");
        vBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vBCActionPerformed(evt);
            }
        });
        label2.add(vBC);

        vertexLabel.add(vDist);
        vDist.setMnemonic('D');
        vDist.setText("Distance from selected vertex");
        vDist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vDistActionPerformed(evt);
            }
        });
        label2.add(vDist);

        vertexLabel.add(vGeneration);
        vGeneration.setMnemonic('E');
        vGeneration.setText("Disease generation");
        vGeneration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vGenerationActionPerformed(evt);
            }
        });
        label2.add(vGeneration);

        vertexLabel.add(vID);
        vID.setMnemonic('F');
        vID.setSelected(true);
        vID.setText("ID");
        vID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vIDActionPerformed(evt);
            }
        });
        label2.add(vID);

        vertexLabel.add(vNone);
        vNone.setMnemonic('G');
        vNone.setText("Nothing");
        vNone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vNoneActionPerformed(evt);
            }
        });
        label2.add(vNone);

        jMenuBar1.add(label2);

        label3.setText("Label edges with...");

        edgeLabel.add(eWeight);
        eWeight.setMnemonic('H');
        eWeight.setText("Weight");
        eWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eWeightActionPerformed(evt);
            }
        });
        label3.add(eWeight);

        edgeLabel.add(eID);
        eID.setMnemonic('I');
        eID.setText("ID");
        eID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eIDActionPerformed(evt);
            }
        });
        label3.add(eID);

        edgeLabel.add(eBC);
        eBC.setMnemonic('J');
        eBC.setText("Centrality");
        eBC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eBCActionPerformed(evt);
            }
        });
        label3.add(eBC);

        edgeLabel.add(eNone);
        eNone.setMnemonic('K');
        eNone.setSelected(true);
        eNone.setText("Nothing");
        eNone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eNoneActionPerformed(evt);
            }
        });
        label3.add(eNone);

        jMenuBar1.add(label3);

        layout.setText("Change layout");
        layout.setToolTipText("Change the way vertices are positioned");

        layouts.add(kk);
        kk.setMnemonic('0');
        kk.setText("KKLayout");
        kk.setToolTipText("Kamada-Kawai algorithm");
        kk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kkActionPerformed(evt);
            }
        });
        layout.add(kk);

        layouts.add(fr);
        fr.setMnemonic('1');
        fr.setSelected(true);
        fr.setText("FRLayout");
        fr.setToolTipText("Fruchterman- Reingold algorithm");
        fr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frActionPerformed(evt);
            }
        });
        layout.add(fr);

        layouts.add(isom);
        isom.setMnemonic('2');
        isom.setText("ISOMLayout");
        isom.setToolTipText("Self-organizing map algorithm");
        isom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                isomActionPerformed(evt);
            }
        });
        layout.add(isom);

        layouts.add(spring);
        spring.setMnemonic('3');
        spring.setText("SpringLayout");
        spring.setToolTipText("A simple force-based algorithm");
        spring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                springActionPerformed(evt);
            }
        });
        layout.add(spring);

        layouts.add(circleL);
        circleL.setMnemonic('4');
        circleL.setText("CircleLayout");
        circleL.setToolTipText("Arranges vertices in a circle");
        circleL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circleLActionPerformed(evt);
            }
        });
        layout.add(circleL);

        jMenuBar1.add(layout);

        menuSimulation.setText("Simulation");

        simRun.setText("Run until infinity");
        simRun.setEnabled(false);
        simRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simRunActionPerformed(evt);
            }
        });
        menuSimulation.add(simRun);

        simRunUntil.setText("Run until...");
        simRunUntil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simRunUntilActionPerformed(evt);
            }
        });
        menuSimulation.add(simRunUntil);

        simPauseMenuItem.setText("Pause");
        simPauseMenuItem.setEnabled(false);
        simPauseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simPauseMenuItemActionPerformed(evt);
            }
        });
        menuSimulation.add(simPauseMenuItem);

        simStop.setText("Stop");
        simStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simStopActionPerformed(evt);
            }
        });
        menuSimulation.add(simStop);
        menuSimulation.add(jSeparator13);

        infect.setText("Infect nodes...");
        infect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infectActionPerformed(evt);
            }
        });
        menuSimulation.add(infect);

        setSusceptible.setText("Set all to susceptible");
        setSusceptible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSusceptibleActionPerformed(evt);
            }
        });
        menuSimulation.add(setSusceptible);

        jMenuBar1.add(menuSimulation);

        menuHelp.setText("Help");

        helpHowTo.setText("How to");
        helpHowTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpHowToActionPerformed(evt);
            }
        });
        menuHelp.add(helpHowTo);

        helpAbout.setText("About");
        helpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpAboutActionPerformed(evt);
            }
        });
        menuHelp.add(helpAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 882, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(578, 578, 578)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToolBar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jToolBar4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showDDToolbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDDToolbarActionPerformed
        try {
            if (st == null) {
                st = new StatsThread();
                st.start();
            } else {
                st.setVisible(true);
                st.toFront();
                st.updateContents();
            }
        } catch (ArithmeticException e) {//the graph is empty, division by 0
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_showDDToolbarActionPerformed

    public void loadLayout(String path) throws IOException, ClassNotFoundException {
        persistentLayout.restore(path);
    }

    public void loadLayout(InputStream is) throws IOException, ClassNotFoundException {
        persistentLayout.restore(is);
    }

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    }//GEN-LAST:event_formWindowClosing

    private void zoomInToolbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInToolbarActionPerformed
        try {
            zoomIn();
        } catch (NullPointerException e) {
            //no graph exists, but that's ok
        }
    }//GEN-LAST:event_zoomInToolbarActionPerformed

    private void zoomOutToolbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutToolbarActionPerformed
        try {
            zoomOut();
        } catch (NullPointerException e) {
            //no graph exists, but that's ok
        }
    }//GEN-LAST:event_zoomOutToolbarActionPerformed

    private void annotateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_annotateItemStateChanged
        if (annotate.isSelected()) {
            getGraphMouse().setMode(ModalGraphMouse.Mode.ANNOTATING);
        }

        annotationControlsToolbar.setVisible(annotate.isSelected());
    }//GEN-LAST:event_annotateItemStateChanged

    private void transformItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_transformItemStateChanged
        if (transform.isSelected()) {
            getGraphMouse().setMode(ModalGraphMouse.Mode.TRANSFORMING);
        }
    }//GEN-LAST:event_transformItemStateChanged

    private void editItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_editItemStateChanged
        if (edit.isSelected()) {
            getGraphMouse().setMode(ModalGraphMouse.Mode.EDITING);
        }
    }//GEN-LAST:event_editItemStateChanged

    private void selectItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectItemStateChanged
        if (select.isSelected()) {
            getGraphMouse().setMode(ModalGraphMouse.Mode.PICKING);
        }
    }//GEN-LAST:event_selectItemStateChanged

    private void kkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kkActionPerformed
//        redisplayCompletely();
        this.changeLayout();
    }//GEN-LAST:event_kkActionPerformed

    private void frActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frActionPerformed
//        redisplayCompletely();
        this.changeLayout();
    }//GEN-LAST:event_frActionPerformed

    private void isomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_isomActionPerformed
//        redisplayCompletely();
        this.changeLayout();
    }//GEN-LAST:event_isomActionPerformed

    private void springActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_springActionPerformed
//        redisplayCompletely();
        this.changeLayout();
    }//GEN-LAST:event_springActionPerformed

    private void circleLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circleLActionPerformed
//        redisplayCompletely();
        this.changeLayout();
    }//GEN-LAST:event_circleLActionPerformed

    private void showDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showDDActionPerformed
        showDDToolbar.doClick();
    }//GEN-LAST:event_showDDActionPerformed

    private void dumbToJpgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dumbToJpgActionPerformed
        FileDialog window = new FileDialog(this, "Save", FileDialog.SAVE);
        window.setSize(500, 500);
        window.setVisible(true);
        String path = window.getDirectory() + window.getFile();
        if (!path.equals("nullnull")) {//if the user clicks CANCEL path will be set to "nullnull"
            if (!path.endsWith(".jpg")) {
                path += ".jpg";
            }
            File f = new File(path);
            IOClass.writeJPEGImage(vv, f);
        }

    }//GEN-LAST:event_dumbToJpgActionPerformed

    private void simRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simRunActionPerformed
        MyGraph g = MyGraph.getInstance(); //get once, use twice, to save processor time
        if (g != null) {
            RunSettingsNew r = new RunSettingsNew(g, false);//run for MAX_INT steps
            //tell simulator where to display simulation, this gets decoupled if user clicks cancel
            //or after simulation completes
//            Controller.initSim(g, vv);
        } else {
            Exceptions.showNoGraphWarning();
        }
    }//GEN-LAST:event_simRunActionPerformed

    private void simRunUntilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simRunUntilActionPerformed
        MyGraph g = MyGraph.getInstance(); //get once, use twice, to save processor time
        if (g != null) {
            //gather data
            RunSettingsNew r = new RunSettingsNew(g, true);//ask how many steps

            //tell simulator where to display simulation, this gets decoupled if user clicks cancel
            //or after simulation completes
//            Controller.initSim(g, vv);
        } else {
            Exceptions.showNoGraphWarning();
        }

        //        c.runSimulation(this.MyGraph.getInstance());
        //        c.setSimulationDisplay(null);//decouple the simulator from the display
    }//GEN-LAST:event_simRunUntilActionPerformed

    private void simStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simStopActionPerformed
        try {
            Controller.stopSim();
        } catch (NullPointerException e) {
            Exceptions.showNoGraphWarning();
        }
    }//GEN-LAST:event_simStopActionPerformed

    private void infectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infectActionPerformed
//        try {
        InfectDisp d = new InfectDisp(MyGraph.getInstance());
//        } catch (NullPointerException e) {
//            Exceptions.showNoGraphWarning();
//        }
    }//GEN-LAST:event_infectActionPerformed

    private void setSusceptibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSusceptibleActionPerformed
        if (MyGraph.getInstance() != null) {
            Controller.setAllSusceptible();
            vv.repaint();
        } else {
            Exceptions.showNoGraphWarning();
        }
    }//GEN-LAST:event_setSusceptibleActionPerformed

    private void helpHowToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpHowToActionPerformed
        JOptionPane.showMessageDialog(this, Strings.help);
    }//GEN-LAST:event_helpHowToActionPerformed

    private void helpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpAboutActionPerformed
        JOptionPane.showMessageDialog(this, Strings.about);
    }//GEN-LAST:event_helpAboutActionPerformed

    private void newDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newDocActionPerformed
        MyGraph.flushInstance();
        redisplayCompletely();
    }//GEN-LAST:event_newDocActionPerformed

    private void fileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveActionPerformed
        gatherer.showSave(this, MyGraph.getInstance());
    }//GEN-LAST:event_fileSaveActionPerformed

    private void fileLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileLoadActionPerformed
        gatherer.showLoad(this);
    }//GEN-LAST:event_fileLoadActionPerformed

    private void fileGenerate1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileGenerate1ActionPerformed
        gatherer.showGenerate(this);
    }//GEN-LAST:event_fileGenerate1ActionPerformed

    private void fileQuit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileQuit1ActionPerformed
        // TODO a bit rude, bit OK as a temp solution
        System.exit(1);
    }//GEN-LAST:event_fileQuit1ActionPerformed

    private void vDEgreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vDEgreeActionPerformed
//        vertexLabelIndex.put(MyGraph.getInstance(), vertexLabel.getSelection().getMnemonic() - 65);
        repaint();
    }//GEN-LAST:event_vDEgreeActionPerformed

    private void vCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vCCActionPerformed
//        vertexLabelIndex.put(MyGraph.getInstance(), vertexLabel.getSelection().getMnemonic() - 65);
        repaint();
    }//GEN-LAST:event_vCCActionPerformed

    private void vBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vBCActionPerformed
//        vertexLabelIndex.put(MyGraph.getInstance(), vertexLabel.getSelection().getMnemonic() - 65);
        repaint();
    }//GEN-LAST:event_vBCActionPerformed

    private void vDistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vDistActionPerformed
//        vertexLabelIndex.put(MyGraph.getInstance(), vertexLabel.getSelection().getMnemonic() - 65);
        repaint();
    }//GEN-LAST:event_vDistActionPerformed

    private void vGenerationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vGenerationActionPerformed
//        vertexLabelIndex.put(MyGraph.getInstance(), vertexLabel.getSelection().getMnemonic() - 65);
        repaint();
    }//GEN-LAST:event_vGenerationActionPerformed

    private void vIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vIDActionPerformed
        repaint();
    }//GEN-LAST:event_vIDActionPerformed

    private void vNoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vNoneActionPerformed
//        vertexLabelIndex.put(MyGraph.getInstance(), vertexLabel.getSelection().getMnemonic() - 65);
        repaint();
    }//GEN-LAST:event_vNoneActionPerformed

    private void eWeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eWeightActionPerformed
//        edgeLabelIndex.put(MyGraph.getInstance(), edgeLabel.getSelection().getMnemonic() - 72);
        repaint();
    }//GEN-LAST:event_eWeightActionPerformed

    private void eIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eIDActionPerformed
//        edgeLabelIndex.put(MyGraph.getInstance(), edgeLabel.getSelection().getMnemonic() - 72);
        repaint();
    }//GEN-LAST:event_eIDActionPerformed

    private void eBCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eBCActionPerformed
//        edgeLabelIndex.put(MyGraph.getInstance(), edgeLabel.getSelection().getMnemonic() - 72);
        repaint();
    }//GEN-LAST:event_eBCActionPerformed

    private void eNoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eNoneActionPerformed
//        edgeLabelIndex.put(MyGraph.getInstance(), edgeLabel.getSelection().getMnemonic() - 72);
        repaint();
    }//GEN-LAST:event_eNoneActionPerformed

    private void pauseSimToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseSimToolbarButtonActionPerformed
        String currText = pauseSimToolbarButton.getText().toLowerCase();
        if (currText.equals("pause")) {
            pauseSimToolbarButton.setText("Resume");
            Controller.pauseSim();
        } else {
            pauseSimToolbarButton.setText("Pause");
            Controller.resumeSim();
        }

    }//GEN-LAST:event_pauseSimToolbarButtonActionPerformed

    private void doStepToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doStepToolbarButtonActionPerformed
//        Controller.doStepWithCurrentSettings();
        Controller.resumeSimForOneStep();
    }//GEN-LAST:event_doStepToolbarButtonActionPerformed

    private void enableGUIToolbarCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableGUIToolbarCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_enableGUIToolbarCheckboxActionPerformed

    private void simPauseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simPauseMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simPauseMenuItemActionPerformed

    private void speedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedStateChanged
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            waitTime = source.getValue();
        }
    }//GEN-LAST:event_speedStateChanged

    private void speedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_speedKeyPressed
        parseSimulationParameters(evt);
    }//GEN-LAST:event_speedKeyPressed

    private void dynamicsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dynamicsItemStateChanged
        JComboBox source = (JComboBox) evt.getItemSelectable();
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (source.getSelectedItem().toString().equals("SI")) {
                gamaLabel.setVisible(false);
                gama.setVisible(false);
                breakingRate.setVisible(false);
                edgeBreakingLabel.setVisible(false);
            }
            if (source.getSelectedItem().toString().equals("SIS")) {
                gamaLabel.setVisible(true);
                gama.setVisible(true);
                breakingRate.setVisible(true);
                edgeBreakingLabel.setVisible(true);
            }
            if (source.getSelectedItem().toString().equals("SIR")) {
                gamaLabel.setVisible(true);
                gama.setVisible(true);
//                gama.setText("1");
                breakingRate.setVisible(false);
                edgeBreakingLabel.setVisible(false);
            }
            parseSimulationParameters(null);
            pack();
            validate();
            repaint();
        }
    }//GEN-LAST:event_dynamicsItemStateChanged

    private void breakingRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakingRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_breakingRateActionPerformed

    private void handleSimControlInput(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_handleSimControlInput
        parseSimulationParameters(evt);
    }//GEN-LAST:event_handleSimControlInput

    private void gamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gamaKeyReleased
        parseSimulationParameters(evt);
    }//GEN-LAST:event_gamaKeyReleased

    private void breakingRateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_breakingRateKeyReleased
        parseSimulationParameters(evt);
    }//GEN-LAST:event_breakingRateKeyReleased

    private void deltaTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deltaTKeyReleased
        parseSimulationParameters(evt);
    }//GEN-LAST:event_deltaTKeyReleased

    /**
     * Parses the text of the provided text field. If that is not a valid
     * double, the text is highlighted to grab the user's attention
     *
     * @param tau
     * @return
     */
    private static double parseValueOrColourComponentOnError(JTextField textField) {
        double value = 0;
        try {
            value = Double.parseDouble(textField.getText());
            System.out.println("Raw string is " + textField.getText());
            System.out.println("Parsed value is " + value);
            textField.setForeground(Color.black);
        } catch (NumberFormatException ex) {
            textField.setForeground(Color.red);
        }
        return value;//TODO must return default value for field
    }

    //TODO convert this from a keylistener to a keybinding, http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
    private static void parseSimulationParameters(KeyEvent evt) {
        //check the current state of the fields
        //parse the contents of the text field that should be active (based on the combos)
        //and attach them to the graph as a Dynamics object
        //attach the dynamics setting to the graph
        double tauValue = parseValueOrColourComponentOnError(tau);
        double gamaValue = parseValueOrColourComponentOnError(gama);
        double deltaTValue = parseValueOrColourComponentOnError(deltaT);
        double brakingRateValue = parseValueOrColourComponentOnError(breakingRate);

        if (dynamics.getSelectedItem().toString().equals("SIR")) {
            MyGraph.setUserDatum("dynamics",
                    new SIRDynamics(tauValue, deltaTValue, gamaValue));
        } else if (dynamics.getSelectedItem().toString().equals("SIS")) {
            MyGraph.setUserDatum("dynamics",
                    new SISDynamics(tauValue, deltaTValue, gamaValue, brakingRateValue));
        } else {
            MyGraph.setUserDatum("dynamics", new SIDynamics(tauValue, deltaTValue));
        }
        //attach the running time to the graph
//            MyGraph.setUserDatum("time",
//                    new Integer(Integer.parseInt(runTime.getText())));

        //attach the speed multiplier to the graph
        MyGraph.setUserDatum("speed", waitTime);
        //make sure the graphs is in a proper state
        Controller.validateNodeStates();
    }

    /**
     * Initialises the display
     */
    public static void redisplayCompletely() {

//        ObservableGraph g = new ObservableGraph(MyGraph.getInstance());
        //clear all previous content
        pane.removeAll();
        pane.setLayout(new BorderLayout());

        persistentLayout =
                new PersistentLayoutImpl2<MyVertex, MyEdge>(getSelectedGraphLayout(MyGraph.getInstance()));
        vv =
                new VisualizationViewer<MyVertex, MyEdge>(persistentLayout, pane.getSize());
        vv.setPreferredSize(new Dimension(350, 350));
        vv.getRenderer().setVertexRenderer(
                new CustomVertexRenderer(vv.getPickedVertexState(), false));
        vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.black));
        vv.getRenderContext().setArrowFillPaintTransformer(new ConstantTransformer(Color.black));
        vv.getRenderContext().setArrowDrawPaintTransformer(new ConstantTransformer(Color.black));
        vv.getRenderContext().setVertexLabelTransformer(new CustomVertexLabeler());
        vv.getRenderContext().setEdgeLabelTransformer(new CustomEdgeLabeller());
//        vv.getRenderer().getVertexLabelRenderer().setPositioner(new InsidePositioner());
        vv.getRenderer().getVertexLabelRenderer().setPositioner(new CenterLabelPositioner());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);


        //#########  MOUSE  PLUGINS  ###############
        graphMouse =
                new CustomGraphMouse(vv.getRenderContext(), Controller.getVertexFactory(), Controller.getEdgeFactory());

        vv.setGraphMouse(graphMouse);
        //put the mouse in the selected mode
        if (select.isSelected()) {
            graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
        } else if (transform.isSelected()) {
            graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        } else if (annotate.isSelected()) {
            graphMouse.setMode(ModalGraphMouse.Mode.ANNOTATING);
        } else if (edit.isSelected()) {
            graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        }

        scaler = new CrossoverScalingControl();
        annotationControls =
                new AnnotationControls<MyVertex, MyEdge>(graphMouse.getAnnotatingPlugin());

        pane.add(vv, BorderLayout.CENTER);

        annotationControlsToolbar =
                new JPanel();
//        JComboBox modeBox = graphMouse.getModeComboBox();
//        controls.add(modeBox);
        annotationControlsToolbar.add(annotationControls.getAnnotationsToolBar());
        pane.add(annotationControlsToolbar, BorderLayout.SOUTH);
        annotationControlsToolbar.setVisible(annotate.isSelected());

        pane.setVisible(true);
        pane.validate();
        vv.repaint();
        pane.repaint();
        parseSimulationParameters(null);
        //initially display nothing
        recalculateStats(null);


        /**
         * Generates an artificial mouse event to make the VisualizationViewer
         * repaint
         */
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                new MouseEvent(pane, MouseEvent.MOUSE_MOVED, 0, 0, -1, -1, 2, false));

    }

    public static void redisplayPartially() {

//        ObservableGraph g = new ObservableGraph(MyGraph.getInstance());
        //clear all previous content
//        pane.removeAll();
//        pane.setLayout(new BorderLayout());
//
//        persistentLayout =
//                new PersistentLayoutImpl2<MyVertex, MyEdge>(getSelectedGraphLayout(MyGraph.getInstance()));
//        vv =
//                new VisualizationViewer<MyVertex, MyEdge>(persistentLayout, pane.getSize());
//        vv.setPreferredSize(new Dimension(350, 350));
//        vv.getRenderer().setVertexRenderer(
//                new CustomVertexRenderer(vv.getPickedVertexState(), false));
//        vv.getRenderContext().setEdgeDrawPaintTransformer(new ConstantTransformer(Color.black));
//        vv.getRenderContext().setArrowFillPaintTransformer(new ConstantTransformer(Color.black));
//        vv.getRenderContext().setArrowDrawPaintTransformer(new ConstantTransformer(Color.black));
//        vv.getRenderContext().setVertexLabelTransformer(new CustomVertexLabeler());
//        vv.getRenderContext().setEdgeLabelTransformer(new CustomEdgeLabeller());
////        vv.getRenderer().getVertexLabelRenderer().setPositioner(new InsidePositioner());
//        vv.getRenderer().getVertexLabelRenderer().setPositioner(new CenterLabelPositioner());
//        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.AUTO);
//
//
//        //#########  MOUSE  PLUGINS  ###############
//        graphMouse =
//                new CustomGraphMouse(vv.getRenderContext(), Controller.getVertexFactory(), Controller.getEdgeFactory());
//
//        vv.setGraphMouse(graphMouse);
//        //put the mouse in the selected mode
//        if (select.isSelected()) {
//            graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
//        } else if (transform.isSelected()) {
//            graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
//        } else if (annotate.isSelected()) {
//            graphMouse.setMode(ModalGraphMouse.Mode.ANNOTATING);
//        } else if (edit.isSelected()) {
//            graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
//        }
//
//        scaler = new CrossoverScalingControl();
//        annotationControls =
//                new AnnotationControls<MyVertex, MyEdge>(graphMouse.getAnnotatingPlugin());
//
//        pane.add(vv, BorderLayout.CENTER);
//
//        annotationControlsToolbar =
//                new JPanel();
////        JComboBox modeBox = graphMouse.getModeComboBox();
////        controls.add(modeBox);
//        annotationControlsToolbar.add(annotationControls.getAnnotationsToolBar());
//        pane.add(annotationControlsToolbar, BorderLayout.SOUTH);
//        annotationControlsToolbar.setVisible(annotate.isSelected());
//
//        pane.setVisible(true);
        pane.validate();
        vv.repaint();
        pane.repaint();
        //initially display nothing
//        recalculateStats(MyGraph.getInstance());


        /**
         * Generates an artificial mouse event to make the VisualizationViewer
         * repaint
         */
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                new MouseEvent(pane, MouseEvent.MOUSE_MOVED, 0, 0, -1, -1, 2, false));

    }

    /**
     * Returns an appropriate layout based on the state of the layout selection
     * buttons
     *
     * @return
     */
    public static Layout<MyVertex, MyEdge> getSelectedGraphLayout(Graph g) {
        //ascii code of 0 is 48, 1 is 49, etc, and the menus have been assigned mnemonics from 0-5
        int type = layouts.getSelection().getMnemonic() - 48;
//        System.out.println("layout is: " + type);
        Layout<MyVertex, MyEdge> l = null;

        switch (type) {

            case 0: {
                l = new KKLayout<MyVertex, MyEdge>(g);
                break;

            }


            case 1: {
                l = new FRLayout<MyVertex, MyEdge>(g);
                break;

            }


            case 2: {
                l = new ISOMLayout<MyVertex, MyEdge>(g);
                break;

            }


            case 3: {
                l = new SpringLayout<MyVertex, MyEdge>(g);
//                ((SpringLayout)layout).setForceMultiplier(10.0); //how close nodes are together
                ((SpringLayout) l).setRepulsionRange(10000);
                break;

            }


            case 4: {
                l = new CircleLayout<MyVertex, MyEdge>(g);
                break;

            }


            case 5: {
                l = new BalloonLayout<MyVertex, MyEdge>((Forest<MyVertex, MyEdge>) g);
                break;

            }


            default: {
                l = new KKLayout<MyVertex, MyEdge>(g);
                break;

            }


        }

        return l;
    }

    /**
     * Based on AnimatingAddNodeDemo, should animate layout change
     */
    public void changeLayout() {
        try {
            ObservableGraph g = new ObservableGraph(MyGraph.getInstance());
            Layout oldLayout = vv.getGraphLayout();
            Layout newLayout = getSelectedGraphLayout(g);
            newLayout.setSize(oldLayout.getSize());
            oldLayout.initialize();
            Relaxer relaxer = new VisRunner((IterativeContext) oldLayout);
            relaxer.stop();
            relaxer.prerelax();
            LayoutTransition<MyVertex, MyEdge> lt =
                    new LayoutTransition<MyVertex, MyEdge>(vv, oldLayout, newLayout);
            Animator animator = new Animator(lt);
            animator.start();
//				vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
            vv.repaint();

        } catch (Exception ex) {
            System.out.println("Error while changing layout: " + ex.getMessage());

        }

    }

    /**
     * Enables external objects to change the layout
     *
     * @param newIndex The menu index of the desired new layout
     */
    public void changeLayout(int newIndex) {
        switch (newIndex) {

            case 0: {
                kk.doClick();
                break;

            }


            case 1: {
                fr.doClick();
                break;

            }


            case 2: {
                isom.doClick();
                break;

            }


            case 3: {
                spring.doClick();
                break;

            }


            case 4: {
                circleL.doClick();
                break;

            }


        }
    }

    /**
     * returns the index of the selectedvertex labeling option in the menu
     *
     * @return
     */
    public static int getSelectedVertexLabelingOption() {
        //the return type will begin with one of these options
        //Degree, Clustering, Centrality, Label
        //mnemonics here are set to A,B,C,D, so subtract 65 to get the selected index
        return vertexLabel.getSelection().getMnemonic() - 65;
    }

    public static int getSelectedEdgeLabelingOption() {
        //first one is H, so subtract 72
        return edgeLabel.getSelection().getMnemonic() - 72;
    }

    private void zoomIn() {
        scaler.scale(vv, 1.1f, vv.getCenter());
    }

    private void zoomOut() {
        scaler.scale(vv, 1 / 1.1f, vv.getCenter());
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    static javax.swing.JToggleButton annotate;
    private static javax.swing.JTextField breakingRate;
    private javax.swing.JRadioButtonMenuItem circleL;
    private static javax.swing.JTextField deltaT;
    static javax.swing.JButton doStepToolbarButton;
    private javax.swing.JMenuItem dumbToJpg;
    private static javax.swing.JComboBox dynamics;
    private javax.swing.JRadioButtonMenuItem eBC;
    private javax.swing.JRadioButtonMenuItem eID;
    private javax.swing.JRadioButtonMenuItem eNone;
    private javax.swing.JRadioButtonMenuItem eWeight;
    private javax.swing.JLabel edgeBreakingLabel;
    private static javax.swing.ButtonGroup edgeLabel;
    static javax.swing.JToggleButton edit;
    private javax.swing.JCheckBox enableGUIToolbarCheckbox;
    private javax.swing.JMenuItem fileGenerate1;
    private javax.swing.JMenuItem fileLoad;
    private javax.swing.JMenuItem fileQuit1;
    private javax.swing.JMenuItem fileSave;
    private javax.swing.JRadioButtonMenuItem fr;
    private static javax.swing.JTextField gama;
    private javax.swing.JLabel gamaLabel;
    private javax.swing.JMenuItem helpAbout;
    private javax.swing.JMenuItem helpHowTo;
    private static javax.swing.JLabel in;
    private javax.swing.JMenuItem infect;
    private javax.swing.JRadioButtonMenuItem isom;
    static javax.swing.JLabel jLabel1;
    private static javax.swing.JLabel jLabel11;
    private static javax.swing.JLabel jLabel13;
    private static javax.swing.JLabel jLabel14;
    private static javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    static javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    static javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    static javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private static javax.swing.JLabel jLabel9;
    private static javax.swing.JMenu jMenu2;
    private static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    static javax.swing.JToolBar.Separator jSeparator10;
    private javax.swing.JSeparator jSeparator13;
    private static javax.swing.JToolBar.Separator jSeparator14;
    static javax.swing.JToolBar.Separator jSeparator17;
    static javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    static javax.swing.JToolBar.Separator jSeparator3;
    static javax.swing.JToolBar.Separator jSeparator4;
    static javax.swing.JToolBar.Separator jSeparator5;
    private static javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private static javax.swing.JToolBar.Separator jSeparator8;
    private static javax.swing.JToolBar.Separator jSeparator9;
    static javax.swing.JToolBar jToolBar1;
    static javax.swing.JToolBar jToolBar2;
    static javax.swing.JToolBar jToolBar3;
    static javax.swing.JToolBar jToolBar4;
    static javax.swing.JToolBar jToolBar5;
    private javax.swing.JRadioButtonMenuItem kk;
    private static javax.swing.JMenu label2;
    private static javax.swing.JMenu label3;
    private static javax.swing.JMenu layout;
    private static javax.swing.ButtonGroup layouts;
    private static javax.swing.JLabel localAPL;
    private static javax.swing.JLabel localBC;
    private static javax.swing.JLabel localCC;
    private static javax.swing.JMenu menuFile;
    private static javax.swing.JMenu menuHelp;
    private static javax.swing.JMenu menuSimulation;
    private static javax.swing.ButtonGroup modeSelection;
    private javax.swing.JMenuItem newDoc;
    private static javax.swing.JLabel out;
    private static javax.swing.JPanel pane;
    static javax.swing.JButton pauseSimToolbarButton;
    static javax.swing.JToggleButton select;
    private javax.swing.JMenuItem setSusceptible;
    private javax.swing.JMenuItem showDD;
    static javax.swing.JButton showDDToolbar;
    private javax.swing.JMenuItem simPauseMenuItem;
    private javax.swing.JMenuItem simRun;
    private javax.swing.JMenuItem simRunUntil;
    private javax.swing.JMenuItem simStop;
    private javax.swing.JSlider speed;
    private javax.swing.JRadioButtonMenuItem spring;
    private static javax.swing.JTextField tau;
    static javax.swing.JLabel totalA;
    static javax.swing.JLabel totalAD;
    static javax.swing.JLabel totalAPL;
    static javax.swing.JLabel totalCC;
    static javax.swing.JToggleButton transform;
    private javax.swing.JRadioButtonMenuItem vBC;
    private javax.swing.JRadioButtonMenuItem vCC;
    private javax.swing.JRadioButtonMenuItem vDEgree;
    private javax.swing.JRadioButtonMenuItem vDist;
    private javax.swing.JRadioButtonMenuItem vGeneration;
    private javax.swing.JRadioButtonMenuItem vID;
    private javax.swing.JRadioButtonMenuItem vNone;
    private static javax.swing.ButtonGroup vertexLabel;
    private javax.swing.JMenuItem zoomIn;
    static javax.swing.JButton zoomInToolbar;
    private javax.swing.JMenuItem zoomOut;
    static javax.swing.JButton zoomOutToolbar;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the graphMouse
     */
    ModalGraphMouse getGraphMouse() {
        return graphMouse;
    }

    public enum Mode {

        NORMAL, SF_INTERACTIVE, SW_INTERACTIVE
    }
}
