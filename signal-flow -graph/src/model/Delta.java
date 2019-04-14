package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Delta {
	private static Base base = Base.getInstance(null);
	private static ArrayList<Edge>[] adjList = base.getAdjList();
	private static ArrayList<ArrayList<Edge>> paths = base.getPaths();
	private static ArrayList<ArrayList<Edge>> loops = base.getLoops();
	private static ArrayList<ArrayList<int[]>> nonTouchedLoops;
	private static double[] deltas;

	
	private static double getGain(int[] takenCycles)
	{
		double gain  = 1 ;
		
		for (int i = 0; i < takenCycles.length; i++) {
			for (int j = 0; j < loops.get(takenCycles[i]).size(); j++) {
		
				gain *= loops.get(takenCycles[i]).get(j).getGain();
			}
		}
		return gain ;
	}

	static void calculateDelta() {	
		base.setNonTouchedLoops(new ArrayList<ArrayList<int[]>>());
	    nonTouchedLoops = base.getNonTouchedLoops();
		base.setDeltas(new double[paths.size() + 1]);
		deltas = base.getDeltas();
		
		for (int i = -1; i < paths.size(); i++) {
			double delta = 1  , gain=0 ;
			int cnt = 1 ;
			boolean[] outCycles = new boolean[loops.size()];
			int[] takenCycles ;
		
			if(i != -1) {
				outCycles = outCycles(i);
			}
			do
			{
				takenCycles = new int [cnt];
				gain = loopsCombinsGain(0,0,outCycles,takenCycles);
				delta += Math.pow(-1, cnt)*gain;
				cnt++;
			
			}while(gain != 0) ;
			deltas[i + 1] = delta;
		}
		base.setDeltas(deltas);
	}
	
	 private static double loopsCombinsGain(int index ,int p , boolean[] outCycles , int[] takenCycles)
	    {
	    	
	    	if(index == takenCycles.length){
	    		saveCombination(takenCycles);
	    		return getGain(takenCycles);
	    	}
	    	
	    	double sum  = 0 ;
	    	for (int i = p ; i < loops.size(); i++) {
				
	    		if(!outCycles[i] && isNonTouching(i,takenCycles,index))
	    		{
	    			takenCycles[index] = i;
	    			sum += loopsCombinsGain(index+1,i+1,outCycles,takenCycles);
	    		}
	    		 
			}
	    	
	    	return sum ;
	    }
	 
	 private static boolean isNonTouching(int cycle , int[] takenCycles , int index) {
	    	for (int i = 0; i < index; i++) {			
	    		if(isTwoTouchedLoop(loops.get(cycle),loops.get(takenCycles[i])))
	    			return false;
			}  	
	    	return true;  	
	    }
	    
	 private static boolean isTwoTouchedLoop( ArrayList<Edge> loop1 , ArrayList<Edge> loop2 ){
			Set<Integer> nodes = new HashSet<Integer>();
			
			//put all nodes in loop1 in set
			for(int i=0 ; i < loop1.size() ; ++i){
				nodes.add(loop1.get(i).getTo());
			}
			//check if any node in loop one equal any node in loop2
			for(int i=0 ; i < loop2.size() ; ++i){
				if( nodes.contains(loop2.get(i).getTo())){
					return true ;
				}
			}
			//if we finish for loop and didn't return any thing, then they are be non touched
			return false ;
		}
	    
	    private static boolean[] outCycles(int outPath)
	    {
	    	boolean[] outNode = new boolean[adjList.length];
	    	boolean[] outCycle = new boolean[loops.size()];
	    	
	    	outNode[0]= true ;
	    	for (int i = 0; i < paths.get(outPath).size(); i++) {	
	    		outNode[paths.get(outPath).get(i).getTo()] = true ;
			}
	    	
	    	for (int i = 0; i < loops.size(); i++) {
	    		for (int j = 0; j < loops.get(i).size(); j++) {
					
	    			if(outNode[loops.get(i).get(j).getTo()])
	    			{
	    				outCycle[i] = true ;
	    				break;
	    			}	
				}
			}
	    	
	    	return outCycle;
	    }
	    
		private static void  saveCombination(int[] combination)
		{
			if(combination.length!=1){
				int[] c = new int[combination.length];
				for (int j = 0; j < c.length; j++) {
					c[j] = combination[j];
				}
				if(nonTouchedLoops.size() < combination.length-1)
					nonTouchedLoops.add(new ArrayList<int[]>());
				 
				nonTouchedLoops.get(combination.length-2).add(c);
		
			}
			base.setNonTouchedLoops(nonTouchedLoops);
		}
}
