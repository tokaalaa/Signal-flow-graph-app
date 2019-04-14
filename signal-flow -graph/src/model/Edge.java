package model;

public class Edge {
	private int from, to;
	private double gain ;
	
	public Edge(int from , int to , double gain){
		this.from = from;
		this.to = to;
		this.gain = gain;
	}
	
	public int getFrom(){
		return from ;
	}
	
	public int getTo(){
		return to ;
	}
	
	public double getGain(){
		return gain;
	}
	
}
