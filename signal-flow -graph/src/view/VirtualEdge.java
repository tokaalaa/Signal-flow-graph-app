package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.Random;

public class VirtualEdge {
	
	private AffineTransform tx = new AffineTransform();
	private Line2D.Double line;
	private Polygon arrowHead = new Polygon(); 
	
	private QuadCurve2D.Double myArc ;
	private VirtualNode from, to;
	private int gain;
	private int midX, midY;
	private int rX ,rY;
	private Point gainPosition;
	
	private Font font = new Font("Serif", Font.PLAIN, 20);
	
	VirtualEdge(VirtualNode s, VirtualNode t, int g){
	from = s;
	to = t;
	gain = g;
	Random generator = new Random();
	rY = generator.nextInt()%150  ;
	rX = generator.nextInt()%150  ;
	midX = (from.getX()+to.getX())/2 ;
	midY = (from.getY()+to.getY())/2+rY ;
	
	myArc = new QuadCurve2D.Double(from.getCenter().x ,from.getCenter().y, midX, midY,
			to.getCenter().x, to.getCenter().y);
	
	if(to.getX()> from.getX()) {
	arrowHead.addPoint( to.getX() ,to.getY());
	arrowHead.addPoint( to.getX() - 10, to.getY() + 20);
	arrowHead.addPoint(to.getX() - 10 , to.getY() - 20);
	gainPosition = new Point (to.getX() - 15, to.getY() - 20);
	} else {
		arrowHead.addPoint( to.getX() + to.getRadius() ,to.getY());
		arrowHead.addPoint( to.getX() + to.getRadius() + 10, to.getY() + 20);
		arrowHead.addPoint( to.getX() + to.getRadius() + 10, to.getY() - 20);
		gainPosition = new Point(to.getX() + to.getRadius(), to.getY() - 20);
	}
	}
	
	public VirtualNode getFrom() {
		return from;
	}
	public VirtualNode getTo() {
		return to;
	}
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.draw(myArc);
		drawArrowHead((Graphics2D) g);
		g2.setColor(Color.red);
		g2.setFont(font);
		g2.drawString(Integer.toString(gain), gainPosition.x, gainPosition.y);
	}
	

	private void drawArrowHead(Graphics2D g2d) { 
	    Graphics2D g = (Graphics2D) g2d.create();
	    g.setColor(Color.BLACK); 
	    g.fill(arrowHead);
	}
/**
	private void drawArrowHead(Graphics2D g2d) { 
		line = new Line2D.Double(0,0,100,100);
			arrowHead.addPoint( to.getX(),to.getY());
			arrowHead.addPoint( to.getX() - 10, to.getY() + 20);
			arrowHead.addPoint(to.getX() - 10, to.getY() - 20);	
		g2d.setColor(Color.BLACK);
	    tx.setToIdentity();
	    double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
	    tx.translate(line.x2, line.y2);
	    tx.rotate((angle-Math.PI/2d));  

	    Graphics2D g = (Graphics2D) g2d.create();
	    g.setColor(Color.BLACK);
	    g.setTransform(tx);   
	    g.fill(arrowHead);

	}*/

	public double getGain() {
		// TODO Auto-generated method stub
		return gain;
	}


}
