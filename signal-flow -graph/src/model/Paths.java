package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Paths {
	private static Base base = Base.getInstance(null);
	private static ArrayList<Edge>[] adjList = base.getAdjList();
	private static boolean[] visited = base.getVisited();
	private static ArrayList<ArrayList<Edge>> paths;
	
	static void calculatePaths(int source, int sink){
		base.setPaths(new ArrayList<ArrayList<Edge>>());
		paths = base.getPaths();
		LinkedList<Integer> pathNodes = new LinkedList<Integer>();
		pathNodes.add(source);
	    checkPath(source, sink, pathNodes);
	    base.setDeltas(new double[paths.size() + 1]);
	}
	
	static void checkPath(int source ,int sink , LinkedList<Integer> pathNodes){
		//check finish and arrive to sink node		
		if (source == sink){
			addPath(pathNodes);
			return ;
		}
		
		for (int j = 0; j < adjList[source].size(); j++) {
			int to = adjList[source].get(j).getTo();	
			if (visited[to] == false){	
				pathNodes.add(to);
				visited[to] = true ;
				checkPath(to, sink, pathNodes);
				visited[to] = false ;
				pathNodes.removeLast();
			}
		}
		
	}
	
	static void addPath(LinkedList<Integer> pathNodes)
	{
		final long uniquePath = pathNodes.stream().distinct().count();
		//check to not add new path result in self_loop
		if (uniquePath == pathNodes.size()) {
		paths.add(new ArrayList<Edge>());
		Iterator<Integer> i = pathNodes.iterator();
		int from , to =i.next();
		while(i.hasNext())
		{
			from = to;
			to = i.next();
			
			for (int j = 0; j < adjList[from].size(); j++) {
				if(adjList[from].get(j).getTo() == to) {
					paths.get(paths.size()-1).add(adjList[from].get(j));
					//System.out.println(adjList[from].remove(j));
					//break;
				}
			}
		}
		base.setPaths(paths);
		}
	}
		
	static double pathGain(int i)
	{
		double gain = 1 ;
		for (int j = 0; j < paths.get(i).size(); j++) {
			gain *= paths.get(i).get(j).getGain();
		}
		
		return gain ;
	}

}
