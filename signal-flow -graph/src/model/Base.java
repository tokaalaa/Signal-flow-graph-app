package model;

import java.util.ArrayList;
import java.util.Set;

public class Base {
	//create an object of SingleObject
	private static Base instance;
	//all information for calculation
	private ArrayList<Edge>[] adjList;
	private ArrayList<ArrayList<Edge>> paths;
	private ArrayList<ArrayList<Edge>> loops;
	private ArrayList<ArrayList<int[]>> nonTouchedLoops;
	private double[] deltas;
	private boolean[] visited;
	private Set< ArrayList<Integer>> arrangedLoop;
	
	//make the constructor private so that this class cannot be
	//instantiated
	private Base(ArrayList<Edge>[] adjacentList){
		adjList = adjacentList;
	    visited = new boolean[adjList.length];
	}

	//Get the only object available
	public static Base getInstance(ArrayList<Edge>[] adjacentList){  
		if (instance == null){  
			instance = new  Base(adjacentList);
		}               
		return instance;  
	 }
	
	public ArrayList<Edge>[] getAdjList(){
		return adjList;
	}
	
	public ArrayList<ArrayList<Edge>> getPaths(){
		return paths;
	}
	
	public ArrayList<ArrayList<Edge>> getLoops(){
		return loops;
	}
	
	public ArrayList<ArrayList<int[]>> getNonTouchedLoops(){
		return nonTouchedLoops;
	}
	
	public double[] getDeltas(){
		return deltas;
	}
	
	public boolean[] getVisited() {
		return visited;
	}
	
	public void setPaths(ArrayList<ArrayList<Edge>> path){
		paths = path;
	}
	
	public void setLoops(ArrayList<ArrayList<Edge>> loop){
		loops = loop;
	}
	
	public void setNonTouchedLoops(ArrayList<ArrayList<int[]>> nonTouchedLoop){
		nonTouchedLoops = nonTouchedLoop;
	}
	
	public void setDeltas(double[] delta){
		deltas = delta;
	}
	
	public void setVisited(boolean[] visit){
		visited = visit;
	}

	public Set< ArrayList<Integer>> getArLoops() {
		return arrangedLoop;
	}

	public void setArLoops(Set< ArrayList<Integer>> arrangedLoop) {
		this.arrangedLoop = arrangedLoop;
	}
}
