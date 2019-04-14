package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import javax.swing.JComponent;
import javax.swing.JOptionPane;

import control.Controller;


@SuppressWarnings("serial")
public class DrawArea extends JComponent{

	private int currentMode = -1;
	/**
	 * constants
	 */
	private static final int selectMode = 0;
	private static final int addNodeMode = 1;
	private static final int addEdgeMode = 2;
	private static final int deleteMode = 3;
	private static final int clearMode = 4;
	private static final int setSourceMode = 5;
	private static final int setSinkMode = 6;
	private static final int solve = 7;
	private Controller control = new Controller();

	/**
	 * current displayed nodes and edges
	 */
	private ArrayList<VirtualNode> nodes = new ArrayList<VirtualNode>();
	private ArrayList<VirtualEdge> edges = new ArrayList<VirtualEdge>();
	private VirtualNode currentNode = null;
	/**
	 * flags
	 */
    private VirtualNode firstAdded, secondAdded;
    private boolean chooseSink = false, chooseSource = false;
    private VirtualNode sink, source;
		DrawArea(){
	addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
        	switch(currentMode) {
        	case selectMode:
        		select(e);
        		break;
        		
        	case addNodeMode:
        		addNode(e);
        		break;
        		
        	case addEdgeMode:
        		addEdge(e);
        		break;
        	}
    	
    			}
      });
}
		private void select(MouseEvent e) {
			// TODO Auto-generated method stub
			for(int i = 0; i < nodes.size(); i++) {
    			VirtualNode n = nodes.get(i);
    			if(n.contains(e.getPoint())) {
    				 currentNode = n;
    				 break;
    			}
    		}
    		
    		repaint();
		}
		private void addNode(MouseEvent e) {
			VirtualNode newNode = new VirtualNode(e.getX(),e.getY());
    		nodes.add(newNode);
    		currentNode = newNode;
    		repaint();
		}
		
		private void addEdge(MouseEvent e) {
			currentNode = null;
    		if (firstAdded == null) {
    			for(VirtualNode n: nodes) {
    				if(n.contains(e.getPoint())) {
    					firstAdded = n;
    					break;
    				}
    			}
    		}else if (secondAdded == null) {
    			for(VirtualNode n: nodes) {
    				if(n.contains(e.getPoint())) {
    					secondAdded = n;
    					break;
    				}
    		}
    			}
    		if(secondAdded != null) {
    			//Open gain window--------------------------------------------------------
    			String inputGain = JOptionPane.showInputDialog("Enter Gain:");
    			try {
    			int gain = Integer.parseInt(inputGain);
    			VirtualEdge d = new VirtualEdge(firstAdded,secondAdded,gain);
    			edges.add(d);
    			firstAdded = null;
    			secondAdded = null;
    			repaint();
    			} catch(NumberFormatException e1) {  
        			firstAdded = null;
        			secondAdded = null;   
    			  } 
    		}
		}
		
		/**
		 * paint
		 */
		public void paintComponent(Graphics g) {
		  // set background color
		  g.setColor(Color.WHITE);
		  g.fillRect(0, 0, getWidth(), getHeight());
		  
		 for(VirtualNode n : nodes) {
			  n.draw(g);
		  }

		  for(VirtualEdge e: edges) {
			  e.draw(g);
		  }
		  
		  if(currentNode != null) {//draw rectangle around the selected node
			  g.setColor(Color.GRAY);
			  // -2, 4 are added to increase the dimensions of the rectangle
			  g.drawRect (currentNode.getX() - 2, currentNode.getY() - 2,
				currentNode.getRadius() + 4, currentNode.getRadius() + 4);
		  }
	  }
		
		public void setCurrentMode(int c) {
		firstAdded = null;
		secondAdded = null;
		  currentMode = c;
		  switch (c) {
		  	case clearMode:
		  		clear();
			  	break;
		  	case deleteMode:
		  		delete();
		  		break;
		  	case setSourceMode:
		  		setSource();
		  		break;
		  	case setSinkMode:
		  		setSink();
		  		break;
		  	case solve:
		  		if (chooseSink && chooseSource) {
		  			control.solve(edges, nodes, source, sink);
		  		}
		  		break;
		  }
	  }
		/**
		 * On button click methods
		 */
		private void clear() {
		  nodes = new ArrayList<VirtualNode>();
		  edges = new ArrayList<VirtualEdge>();
		  currentNode = null;
		  chooseSink = false;
		  chooseSource = false;
		  firstAdded = null;
		  secondAdded = null;
		  repaint();
	  }
		private void delete() {
		  if(currentNode != null) {
			  ArrayList<VirtualEdge> toRemove = new ArrayList<VirtualEdge>();
	  			for(VirtualEdge d : edges) {
					if(d.getFrom() == currentNode || d.getTo() == currentNode)
						toRemove.add(d);
				}
	  			edges.removeAll(toRemove);
				nodes.remove(currentNode);
				if(currentNode.isSink()) {
					chooseSink = false;
				} else if (currentNode.isSource()) {
					chooseSource = false;
				}
				currentNode = null;
				repaint();
				  }
	  }
		private void setSource() {
			if(currentNode != null && !chooseSource && !currentNode.isSink()) {
    			for(VirtualNode n: nodes) {
    				if(n == currentNode) {
    				n.setSource();
    				currentNode = n;
    				chooseSource = true;
    				source = n;
    				break;
    				}
    				}
        		repaint();	
    		}
		}
		private void setSink(){
			if(currentNode != null && !chooseSink && !currentNode.isSource()) {
			for(VirtualNode n: nodes) {
				if(n == currentNode) {
				n.setSink();
				chooseSink = true;
				sink = n;
				currentNode = n;
				break;
				}
				}
			repaint();
			
		}
			}
}

