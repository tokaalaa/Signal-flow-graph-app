package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Loops {
	private static Base base = Base.getInstance(null);
	private static ArrayList<Edge>[] adjList = base.getAdjList();
	private static boolean[] visited = base.getVisited();
	private static Set< ArrayList<Integer>> arrLoops;
	private static ArrayList<ArrayList<Edge>> loops;

	static void calculateLoops(){
		base.setArLoops(new HashSet< ArrayList<Integer> >());
		arrLoops = base.getArLoops();
		
		base.setLoops(new ArrayList<ArrayList<Edge>>());
		loops = base.getLoops();
		Stack<Integer> s = new Stack<Integer>();

		for(int i = 0 ; i < adjList.length ; ++i){
			if(visited[i] == false ){
				checkLoop( i, s );
			}
		}
	}
	
	static void checkLoop( int node , Stack<Integer> s ){
		visited[node] = true ;
		s.push(node);
		
		for(int i = 0 ; i < adjList[node].size() ; ++i){
			int to = adjList[node].get(i).getTo() ;
			if(visited[to] == true){
				addLoop(to ,s);
			}
			else{
				checkLoop( to , s );
			}
		}
		
		s.pop();
		visited[node] = false ;
	}
	
	static void addLoop(int to, Stack<Integer> s) {
		Stack<Integer> temp = new Stack<Integer>() ;
		Stack<Integer> path = new Stack<Integer>() ;
		ArrayList<Integer> test = new ArrayList<Integer>() ;
		
		path.push(to);
		test.add(to);
		
		while( s.peek() != to ){
			test.add(s.peek());
			path.push(s.peek());
			temp.push(s.pop());
		}
		
		while(!temp.empty()){
			s.push( temp.pop() );
		}
		
		ArrayList<Edge> cycle = new ArrayList<Edge>();
		
		int f = -1 ; //from
		int t = path.pop(); //to
		int src = t ;//save to in another place to check
		
		//check if any node have two paths to the same way to form loop
		while(!path.empty()){
			f = t ;
			t = path.pop();
			
			for(int i = 0; i < adjList[f].size(); ++i){
				if(adjList[f].get(i).getTo() == t ){
					cycle.add(adjList[f].get(i));
					break ;
				}
			}
		}
		
		//get the second repeated loop node from previous node
		for(int i=0 ; i < adjList[t].size() ; ++i){
			if(adjList[t].get(i).getTo() == src ){
				cycle.add(adjList[t].get(i));
				break ;
			}
		}
		
		Collections.sort(test);
		
		//check if this loop exists in our loops or not
		if(!arrLoops.contains(test)){
			arrLoops.add(test);
			loops.add(cycle);
		}
		
		base.setLoops(loops);
		base.setArLoops(arrLoops);
	}
	
}
