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

package controller;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.SparseDoubleMatrix2D;
import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.matrix.GraphMatrixOperations;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import model.*;
import model.factories.*;
import view.generatorSettings.GeneratorSettings;
import view.Display;

/**
 * Static methods to load, save and generate various graphs
 * @author mb724
 */
public class IOClass {

   
 
    /**
     * Load a layout for the given graph if it exists- meant to be used by the user
     * @param parent where to display it
     * @param path absolute path to the layout file, only used if the File is null
     * @param askForConfirmation whether to do it automatically
     */
    public static void loadLayout(Display parent, String path) {
        String name = "";
        int whereDot = path.lastIndexOf('.');
        if (0 < whereDot && whereDot <= path.length() - 2) {
            name = path.substring(0, whereDot);
        }

        name += ".layout";
        System.out.println("Will search for layout file: " + name);
        File f = new File(name);
        boolean okToLoad = true; //this is how the user affects loading
        if (f.exists()) {
            System.out.println("File exists " + name);
                okToLoad = (JOptionPane.showConfirmDialog(parent, "This graph has a layout file associated with it." +
                        "Do you want to load it?") == JOptionPane.YES_OPTION);
            System.out.println("Permission to load: " + okToLoad);
            if (okToLoad) {
                try {
                    System.out.println("Will load layout: " + name);
                        parent.loadLayout(name);
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                    ex.printStackTrace();
                }
            }

        }
    }
    /**
     * Loads layouts, for internal use only.
     * Does not check if the file exists, does not ask for confirmation
     * @param parent
     * @param is
     */
    public static void loadLayout(Display parent, InputStream is) {

                try {
                        parent.loadLayout(is);
                } catch (Exception ex) {
                    System.out.println(ex.getLocalizedMessage());
                    ex.printStackTrace();
                }
    }

   



    /**
     * copy the visible part of the graph to a file as a jpeg image
     * @param vv 
     * @param file
     */
    public static void writeJPEGImage(VisualizationViewer vv, File file) {
        int width = vv.getWidth();
        int height = vv.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        vv.paint(graphics);
        graphics.dispose();

        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


