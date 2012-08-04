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
 * RunSettingsNew.java
 *
 * Created on 01-May-2009, 11:21:23
 */
package view;

import controller.Controller;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import model.MyGraph;
import model.dynamics.SIDynamics;
import model.dynamics.SIRDynamics;
import model.dynamics.SISDynamics;

/**
 * @author reseter
 */
public class RunSettingsNew extends javax.swing.JFrame {

    //available dynamics
//    private static final String[] availableTypes = {"SI", "SIS", "SIR"};
    private MyGraph g; //the graph these settings are for
    //    private Controller c; //the controller the settings will be sent to
    private int waitTime = 100;

    /**
     * Creates new form RunSettingsNew
     *
     * @param g           the graph to which the info will be attached
     * @param requestTime whether to show a text field asking for time or run for MAX_INT cycles
     */
    public RunSettingsNew(MyGraph g, boolean requestTime) {
//        this.c = c;
        this.g = g;
        initComponents();
        //hide the time text field and set its value to a big number
        if (!requestTime) {
            runTime.setVisible(false);
            runTime.setText("" + Integer.MAX_VALUE);
            runTimeLabel.setVisible(false);
        }
        pack();
        this.setVisible(true);
        dynamics.setSelectedIndex(1);
        pack();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tau = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        gamaLabel = new javax.swing.JLabel();
        gama = new javax.swing.JTextField();
        deltaT = new javax.swing.JTextField();
        speed = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dynamics = new javax.swing.JComboBox();
        cancel = new javax.swing.JButton();
        ok = new javax.swing.JButton();
        runTimeLabel = new javax.swing.JLabel();
        runTime = new javax.swing.JTextField();
        breakingRate = new javax.swing.JTextField();
        edgeBreakingLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Simulation settings");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setText("Dynamics");

        jLabel2.setText("Transmission rate");

        tau.setText("2");
        tau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tauKeyPressed(evt);
            }
        });

        jLabel3.setText("Time step");

        gamaLabel.setText("Recovery rate");

        gama.setText("1");
        gama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                gamaKeyPressed(evt);
            }
        });

        deltaT.setText("0.1");
        deltaT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                deltaTKeyPressed(evt);
            }
        });

        speed.setMajorTickSpacing(500);
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

        jLabel5.setText("Wait time between steps, ms");

        dynamics.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"SI", "SIS", "SIR"}));
        dynamics.setSelectedIndex(2);
        dynamics.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dynamicsItemStateChanged(evt);
            }
        });
        dynamics.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dynamicsKeyPressed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });
        cancel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cancelKeyPressed(evt);
            }
        });

        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        ok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                okKeyPressed(evt);
            }
        });

        runTimeLabel.setText("Run time");

        runTime.setText("10");
        runTime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                runTimeKeyPressed(evt);
            }
        });

        breakingRate.setText("0.1");
        breakingRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                breakingRateActionPerformed(evt);
            }
        });
        breakingRate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                breakingRateKeyPressed(evt);
            }
        });

        edgeBreakingLabel.setText("Edge breaking rate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(deltaT, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(breakingRate, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(runTime, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(578, 578, 578))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                                                                .addGap(391, 391, 391))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(runTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                                                                .addGap(264, 264, 264))
                                                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(22, 22, 22)
                                                                .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 465, Short.MAX_VALUE)
                                                                .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(speed, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                                                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE))
                                                                .addGap(12, 12, 12)))
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(edgeBreakingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                                                .addGap(332, 332, 332))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(gama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                                        .addComponent(tau, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                                                        .addComponent(dynamics, 0, 680, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(gamaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap())))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dynamics, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(gamaLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(edgeBreakingLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(breakingRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deltaT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(runTimeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(runTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(speed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ok))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        //if OK is clicked, check the current state of the fields
        //parse the contents of the text field that should be active (based on the combos)
        //and attach them to the graph as a Dynamics object
        try {
            //attach the dynamics setting to the graph
            if (dynamics.getSelectedItem().toString().equals("SIR")) {
                MyGraph.setUserDatum("dynamics",
                        new SIRDynamics(Double.parseDouble(tau.getText()),
                                Double.parseDouble(deltaT.getText()), Double.parseDouble(gama.getText())));
            } else if (dynamics.getSelectedItem().toString().equals("SIS")) {
                MyGraph.setUserDatum("dynamics",
                        new SISDynamics(Double.parseDouble(tau.getText()),
                                Double.parseDouble(deltaT.getText()), Double.parseDouble(gama.getText()),
                                Double.parseDouble(breakingRate.getText())));
            } else {
                MyGraph.setUserDatum("dynamics",
                        new SIDynamics(Double.parseDouble(tau.getText()),
                                Double.parseDouble(deltaT.getText())));
            }
            //attach the running time to the graph
            MyGraph.setUserDatum("time",
                    new Integer(Integer.parseInt(runTime.getText())));

            //attach the speed multiplier to the graph
            MyGraph.setUserDatum("speed", waitTime);
            //make sure the graphs is in a proper state
            Controller.validateNodeStates();
            dispose();
//            Controller.initSim(g);
//            Controller.runSim();
        } catch (IllegalArgumentException ex) {
            Exceptions.showIllegalArgumentNotification(ex);
        }

    }//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
//        Controller.initSim(g, null);//decouple the view from the data to enable garbage collection
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void speedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedStateChanged
        JSlider source = (JSlider) evt.getSource();
        if (!source.getValueIsAdjusting()) {
            waitTime = source.getValue();
        }
    }//GEN-LAST:event_speedStateChanged

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
            pack();
            validate();
            repaint();
        }
    }//GEN-LAST:event_dynamicsItemStateChanged

    private void okKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_okKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_okKeyPressed

    private void cancelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cancelKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_cancelKeyPressed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_formKeyPressed

    private void dynamicsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dynamicsKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_dynamicsKeyPressed

    private void tauKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tauKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_tauKeyPressed

    private void gamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gamaKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_gamaKeyPressed

    private void deltaTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deltaTKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_deltaTKeyPressed

    private void runTimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_runTimeKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_runTimeKeyPressed

    private void speedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_speedKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_speedKeyPressed

    private void breakingRateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_breakingRateKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_breakingRateKeyPressed

    private void breakingRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_breakingRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_breakingRateActionPerformed

    private void listenForKeys(KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            ok.doClick();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cancel.doClick();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField breakingRate;
    private javax.swing.JButton cancel;
    private javax.swing.JTextField deltaT;
    private javax.swing.JComboBox dynamics;
    private javax.swing.JLabel edgeBreakingLabel;
    private javax.swing.JTextField gama;
    private javax.swing.JLabel gamaLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton ok;
    private javax.swing.JTextField runTime;
    private javax.swing.JLabel runTimeLabel;
    private javax.swing.JSlider speed;
    private javax.swing.JTextField tau;
    // End of variables declaration//GEN-END:variables
}
