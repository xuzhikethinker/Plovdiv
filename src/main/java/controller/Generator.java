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
 * Generates graphs according to given criteria
 */
package controller;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import edu.uci.ics.jung.algorithms.generators.random.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.graph.MyGraph;
import edu.uci.ics.jung.graph.ObservableGraph;
import model.MyEdge;
import model.MyVertex;
import model.factories.*;

import java.util.HashSet;

/**
 * This class handles provides my own lattice/ random generators, and the other generators are taken from JUNG 2.0,
 * with each Factory replaced by a MyFactory to enable me to use .create(graph), which takes into account the contents
 * of the internal buffer of the generator when issuing numbers to newly added graph elements
 *
 * @author reseter
 */
public class Generator {

    public Generator() {
    }

    public static ObservableGraph generateRandom(int v, int e) {
        VertexFactory vf = Controller.getVertexFactory();
        vf.reset();
        EdgeFactory ef = Controller.getEdgeFactory();
        ef.reset();
        RandomGenerator r = new RandomGenerator(new GraphFactory2(), vf, ef, v, e);
        ObservableGraph g = r.create();
        System.out.println("Generator has created: " + g);
        return g;
    }

//    public static MyGraph generateErdosRenyi(int v, double p){
//        ErdosRenyiGenerator gen = new ErdosRenyiGenerator(new GraphFactory(), new VertexFactory2(),
//                new EdgeFactory2(), v, p);
//        return (MyGraph) gen.create();
//    }

    /**
     * Generates a "rectangular" lattice, where the nodes form little rectangles/squares
     *
     * @param m
     * @param n
     */
    public static ObservableGraph generateRectangularLattice(int m, int n) {
        //@see notes 17 JUL 2009
        ObservableGraph g = MyGraph.getNewInstance();
        VertexFactory vf = Controller.getVertexFactory();
        vf.reset();
        EdgeFactory ef = Controller.getEdgeFactory();
        ef.reset();

        //feed stuff to the buffer
        MyVertex[][] allNodes = new MyVertex[m][n];
        for (int i = 0; i <
                m; i++) {//give vertices and ID and add them to the graph
            for (int j = 0; j <
                    n; j++) {
                allNodes[i][j] = vf.create();
                g.addVertex(allNodes[i][j]);
            }

        }
        for (int i = 0; i <
                m - 1; i++) {//initialize it
            for (int j = 0; j <
                    n - 1; j++) {
                g.addEdge(ef.create(), allNodes[i][j], allNodes[i][j + 1]);//link to the right
                g.addEdge(ef.create(), allNodes[i][j], allNodes[i + 1][j]);// link down
            }

        }
        //last row and column not initialized yet
        for (int i = 0; i <
                m - 1; i++) {
            g.addEdge(ef.create(), allNodes[i][n - 1], allNodes[i + 1][n - 1]);//TODO modify to include last row and last column
        }

        for (int i = 0; i <
                n - 1; i++) {
            g.addEdge(ef.create(), allNodes[m - 1][i], allNodes[m - 1][i + 1]);//TODO modify to include last row and last column
        }
//        MyGraph.setInstance(g);
        return g;
    }

    /**
     * Generates a MxN hexagonal lattice
     *
     * @param m
     * @param n
     */
    public static ObservableGraph generateHexagonalLattice(int m, int n) {
        //@see notes 17 JUL 2009
        ObservableGraph g = MyGraph.getNewInstance();
        VertexFactory vf = Controller.getVertexFactory();
        vf.reset();
        EdgeFactory ef = Controller.getEdgeFactory();
        ef.reset();
        MyVertex[][] nodes = new MyVertex[m][n];
        for (int i = 0; i <
                m; i++) {//initialize vertices and add them to the graph
            for (int j = 0; j <
                    n; j++) {
                nodes[i][j] = vf.create();
                g.addVertex(nodes[i][j]);
            }

        }

        //link to the right
        for (int i = 0; i <
                m; i++) {
            for (int j = 0; j <
                    n; j++) {
                try {
                    g.addEdge(ef.create(), nodes[i][j], nodes[i][j + 1]);
                } catch (ArrayIndexOutOfBoundsException e) {
                }//nothing, i know i'm out of bounds
            }
        }

        //link to bottom right/ left, depending on the row number- see paper notes
        //link to left for even row numbers, link to left for odd rows
        for (int i = 0; i <
                m; i++) {
            for (int j = 0; j <
                    n; j++) {
                try {
                    g.addEdge(ef.create(), nodes[i][j], nodes[i + 1][j - 1 + 2 * (i % 2)]);
                } catch (ArrayIndexOutOfBoundsException e) {
                }//nothing, i know i'm out of bounds
            }
        }

        //link to bottom left
        for (int i = 0; i <
                m; i++) {
            for (int j = 0; j <
                    n; j++) {
                try {
                    g.addEdge(ef.create(), nodes[i][j], nodes[i + 1][j]);
                } catch (ArrayIndexOutOfBoundsException e) {
                }//nothing, i know i'm out of bounds
            }
        }

//        MyGraph.setInstance(g);
        return g;
    }

    public static ObservableGraph generateKleinbergSmallWorld(int m, int n, double clusteringExponent) {
//        VertexFactory vf = Controller.getVertexFactory();
//        vf.reset();
//        EdgeFactory ef = Controller.getEdgeFactory();
//        ef.reset();
//        KleinbergSmallWorldGenerator gen = new KleinbergSmallWorldGenerator(new GraphFactory2(),
//                vf, ef, m, n, clusteringExponent);
//
//        return (MyGraph) gen.create();

        //make sure numbering starts from 1
        Controller.getEdgeFactory().reset();
        Controller.getVertexFactory().reset();
        SmallWorldGenerator gen = new SmallWorldGenerator(new GraphFactory2(),
                Controller.getVertexFactory(), Controller.getEdgeFactory(), m, n, clusteringExponent);
        return gen.create();
    }

    public static ObservableGraph generateEppsteinPowerLaw(int numVert, int numEdges, int r) {
        VertexFactory2 vf = new VertexFactory2();
        EdgeFactory2 ef = new EdgeFactory2();
        EppsteinPowerLawGenerator<MyVertex, MyEdge> gen = new EppsteinPowerLawGenerator(new GraphFactory2(), vf, ef, numVert, numEdges, r);
        return (MyGraph) gen.create();
    }

    /**
     * Simple evolving scale-free random graph generator. At each time step, a
     * new vertex is created and is connected to existing vertices according
     * to the principle of "preferential attachment", whereby vertices with
     * higher degree have a higher probability of being selected for attachment.
     *
     * @param evolveSteps
     * @param numVertices
     * @param numEdgesToAttach
     */
    public static ObservableGraph generateScaleFree(int evolveSteps, int numVertices, int numEdgesToAttach) {
        VertexFactory vf = Controller.getVertexFactory();
        vf.reset();
        EdgeFactory ef = Controller.getEdgeFactory();
        ef.reset();
        HashSet<MyVertex> seeds = new HashSet<MyVertex>();
        //make sure numbering starts from 1
        BarabasiAlbertGenerator gen = new BarabasiAlbertGenerator(
                new GraphFactory2(), vf, ef,
                numVertices, numEdgesToAttach, seeds);
        gen.evolveGraph(evolveSteps);
        return (MyGraph) gen.create();
    }
}
