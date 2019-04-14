package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class VirtualNode {
	private Ellipse2D.Double circle;
	private int x, y;
	private final int radius = 30;
	private boolean sink = false, source = false;
	private Point center;
	private Color color;
	VirtualNode(int x, int y){
		this.x = x;
		this.y = y;
		center = new Point(x + 15, y + 15); // radius/2 = 15
		color = Color.BLUE;
		circle = new Ellipse2D.Double(x, y, getRadius(), getRadius());
	}
	public Point getCenter(){
		return center;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void draw(Graphics canvas) {
		Graphics2D g2 = (Graphics2D) canvas;
		g2.setColor(color);
		g2.fill(circle);
		g2.draw(circle);
	}
	
	public int getRadius() {
		return radius;
	}
	public boolean contains(Point p) {
		return circle.contains(p);
	}
	
	public void setSink() {
		sink = true;
		color = Color.CYAN;
	}
	
	public void setSource() {
		source = true;
		color = Color.GREEN;
	}
	public boolean isSink() {
		return sink;
	}
	
	public boolean isSource() {
		return source;
	}
}
