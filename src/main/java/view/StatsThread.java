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

package view;

import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import model.MyEdge;
import model.MyVertex;

/**
 * Starts a new thread for the StatsDisplay window, so that the
 * JFreeChart-based display can work
 * @author mb724
 */
public class StatsThread extends Thread implements GraphEventListener<MyVertex, MyEdge> {

    private StatsDisplay sd;

    @Override
    public void run() {
        sd = new StatsDisplay();
        sd.setSize(500, 500);
        sd.setVisible(true);
    }

    public void toFront(){
        sd.toFront();
    }

    public void updateContents(){
        sd.updateContents();
    }

    public boolean isVisible(){
        return sd.isVisible();
    }

    public void setVisible(boolean flag){
        sd.setVisible(flag);
    }

    void kill() {
        sd.setVisible(false);
        sd.dispose();
        this.interrupt();
    }

	@Override
	public void handleGraphEvent(GraphEvent<MyVertex, MyEdge> evt) {
		sd.handleGraphEvent(evt);
	}
}
