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
 * InfectDisp.java
 *
 * Created on 14-Jun-2009, 21:16:31
 */
package view;

import controller.Controller;
import model.MyGraph;
import model.Strings;

import java.awt.event.KeyEvent;

/**
 * @author reseter
 */
public class InfectDisp extends javax.swing.JFrame {

    private MyGraph g; //the graph this window will infect

    /**
     * Creates new form InfectDisp
     *
     * @param g
     * @param c
     */
    public InfectDisp(MyGraph g) {
        this.g = g;
        initComponents();
        int numInf = Integer.parseInt(MyGraph.getUserDatum(Strings.numInfected, 0).toString());
        max.setText("Value should be between 0 and " + (g.getVertexCount() - numInf));
        setVisible(true);
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
        slider = new javax.swing.JSlider();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        number = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        max = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Infect random nodes");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jLabel1.setText("Infect at random, percent");

        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sliderKeyPressed(evt);
            }
        });

        jLabel2.setText("Infect this number of nodes*:");

        number.setMinimumSize(new java.awt.Dimension(30, 27));
        number.setPreferredSize(new java.awt.Dimension(40, 27));
        number.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                numberKeyPressed(evt);
            }
        });

        jLabel3.setText("*Please note: if you specify a number, the percentage slider is ignored");

        ok.setText("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jLabel4.setText("The slider value is rounded towards the nearest integer, but will not");

        max.setText("jLabel5");

        jLabel6.setText("exceed the number of susceptible nodes in the graph");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addGap(55, 55, 55)
                                                                .addComponent(max))
                                                        .addComponent(number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(slider, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)))
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(ok)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 387, Short.MAX_VALUE)
                                                .addComponent(cancel))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel3))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jSeparator3, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel4))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel5))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(slider, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(max))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(number, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(cancel)
                                        .addComponent(ok))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addComponent(jLabel6))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    private void listenForKeys(KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
            ok.doClick();
        }
        if (evt.getKeyChar() == KeyEvent.VK_ESCAPE) {
            cancel.doClick();
        }
    }

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        String content = number.getText();
        if (content.length() > 0) {
            try {
                int n = Integer.parseInt(content);
//                int numInfected = Integer.parseInt(g.getUserDatum(Strings.numInfected).toString());
                if (n < 0) {
                    throw new NumberFormatException();
                }
                //infect the number the user specified or as many as possible
                int x = (Integer.parseInt(MyGraph.getUserDatum(Strings.numSus).toString()));
                Controller.infectNodes(g, Math.min(n, Integer.parseInt(MyGraph.getUserDatum(Strings.numSus).toString())));
            } catch (NumberFormatException nfe) {
                System.out.println("Negative number entered!");
            }
        } else {
//            int n = slider.getValue();
//            try {
//                int numInfected = Integer.parseInt(g.getUserDatum(Strings.numInfected).toString());
//                if ((n * g.numVertices() / 100) > (g.numVertices() - numInfected)) {
//                    throw new NumberFormatException();
//                }
            //infect the number the user specified or as many as possible
            int n = Math.min(slider.getValue() * g.getVertexCount() / 100,
                    Integer.parseInt(MyGraph.getUserDatum(Strings.numSus).toString()));
            Controller.infectNodes(g, n);
//                System.out.println("Will infect: " + n);

//            }
//            catch (NumberFormatException nfe) {
//                controller.infectNodes(g, Integer.parseInt(g.getUserDatum(Strings.numSus).toString()));
////                getToolkit().beep();
////                number.requestFocus();
//            }
        }

        dispose();
    }//GEN-LAST:event_okActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_formKeyPressed

    private void sliderKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sliderKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_sliderKeyPressed

    private void numberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numberKeyPressed
        listenForKeys(evt);
    }//GEN-LAST:event_numberKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel max;
    private javax.swing.JTextField number;
    private javax.swing.JButton ok;
    private javax.swing.JSlider slider;
    // End of variables declaration//GEN-END:variables
}