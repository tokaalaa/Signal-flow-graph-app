package control;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Base;
import model.Edge;
import model.TF;
import view.VirtualEdge;
import view.VirtualNode;

public class Controller {
	private Base base;
	private ArrayList<String> paths = new ArrayList<String>();
	private ArrayList<String> pathsGain = new ArrayList<String>();
	private ArrayList<String> indivLoops = new ArrayList<String>();
	private ArrayList<String> nonTouchingLoops = new ArrayList<String>();
	private ArrayList<String> delta = new ArrayList<String>();
	private double Tf;
	
	@SuppressWarnings("unchecked")
	public void solve(ArrayList<VirtualEdge> edges, ArrayList<VirtualNode> nodes,
			VirtualNode source, VirtualNode sink) {
		int input = 0, output = 0, from = 0, to = 0;
		
		ArrayList<Edge>[] adjList = new ArrayList[nodes.size()];
		
		for (int i = 0; i < adjList.length; i++) {
			adjList[i]= new ArrayList<Edge>();
		}
		//to form adjList which is array of arraylist of edges
		for (VirtualEdge e : edges) {
			for (int i = 0; i < nodes.size(); i++) {
				if(e.getFrom() == nodes.get(i)) {
					from = i;
				}
				if(e.getTo() == nodes.get(i)) {
					to = i;
				}
				if(source == nodes.get(i)) {
					input = i;
				} else if (sink == nodes.get(i)) {
					output = i;
				}
			}
			adjList[from].add(new Edge(from, to, e.getGain()));
		}
		//the first create of base
		base = Base.getInstance(adjList);
		getResult(input, output);
		showResult();	
	}
	
	@SuppressWarnings("rawtypes")
	private void getResult(int source,int sink)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Tf = TF.calculateTF(source, sink);
		double[] deltas = base.getDeltas();
		ArrayList<ArrayList<Edge>> cycles = base.getLoops();
		ArrayList<ArrayList<Edge>> forwardPaths = base.getPaths();
		ArrayList<ArrayList<int[]>> allCombin = base.getNonTouchedLoops();
		
		
		for (int i = 0; i < forwardPaths.size(); i++) {
			String p = "P" + (i + 1) + ": ";
			String g = "gainP" + (i + 1) + ": ";
			int j;
			for ( j = 0; j < forwardPaths.get(i).size(); j++) {
				p = p + forwardPaths.get(i).get(j).getFrom() + " -> ";
				g = g + forwardPaths.get(i).get(j).getGain() + " * ";
			}
			p = p + forwardPaths.get(i).get(j-1).getTo() + "\n";
			g = g.substring(0, g.length() - 2) + "\n";
			paths.add(p);
			pathsGain.add(g);
		}
			


		
		String[] gainL = new String[cycles.size()];
		for (int i = 0; i < cycles.size(); i++) {
			String c ="L" + (i + 1) + ": ";
			String g = "gainL" + (i + 1) + ": ";
			int j ;
			for ( j = 0; j < cycles.get(i).size(); j++) {
				c = c+ cycles.get(i).get(j).getFrom() + " -> " ;
				g = g + cycles.get(i).get(j).getGain() + " * ";
			}
			c = c + cycles.get(i).get(j-1).getTo()+ "\t (";
			c = c + g.substring(0, g.length() - 2) + ")\n";
			gainL[i] = g.substring(0, g.length() - 2);
			indivLoops.add(c);
		}
		
		
		
		for (int i = 0; i < allCombin.size(); i++) {		
			String com = (i + 2)+ " non-touching loops : ";
			for (int j = 0; j < allCombin.get(i).size(); j++) {
				int[] temp = allCombin.get(i).get(j);
				com +="(";
				String g = "";
				for (int j2 = 0; j2 < temp.length; j2++) {
					com += "L" + (temp[j2]+1) + " ";
					g = gainL[temp[j2]] + " * ";
				}
					com += ")  + gain( " + g.substring(0, g.length() - 2) + " )\n";
			}
			com+="\n";
			nonTouchingLoops.add(com);
		}
		

		
		delta.add("Delta = " + deltas[0] + "\n");
		for (int i = 1; i < deltas.length; i++) {
			delta.add("Delta " + i + " = " + deltas[i] + "\n");
		}
	}
private void showResult() {
		
		//create new Jframe to print all calculations
		JFrame res = new JFrame();
		res.setSize(890,400);
		res.setResizable(false );
		res.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		res.getContentPane().setLayout(null);
		res.setLocationRelativeTo(null);
		res.setTitle("Results");
		res.getContentPane().setBackground(new Color(153, 204, 255));
		
		//------------------forward paths---------------------------------
		JLabel Label1 = new JLabel("Forward Paths");
		Label1.setEnabled(false);
		Label1.setBounds(5, 3, 153, 23);
		res.add(Label1);
		
		JTextArea forwardPaths = new JTextArea();
		forwardPaths.setBounds(0, 23, 150, 300);
		forwardPaths.setFont(new Font("Serif",  Font.BOLD, 12));
		forwardPaths.setEditable(false);
		
		JScrollPane scrol1 = new JScrollPane(forwardPaths);
		scrol1.setBounds(0, 23, 150, 300);
		scrol1.setAutoscrolls(true);
		scrol1.setVisible(true);
		scrol1.setWheelScrollingEnabled(true);
		
		res.getContentPane().add(scrol1);
		
		
		//-------------------gains----------------------------------------
		JLabel Label2 = new JLabel("gains");
		Label2.setEnabled(false);
		Label2.setBounds(155, 3, 153, 23);
		res.add(Label2);
		
		JTextArea gains = new JTextArea();
		gains.setBounds(150, 23, 150, 300);
		gains.setFont(new Font("Serif",  Font.BOLD, 12));
		gains.setEditable(false);
		
		JScrollPane scrol2 = new JScrollPane(gains);
		scrol2.setBounds(150, 23, 150, 300);
		scrol2.setAutoscrolls(true);
		scrol2.setVisible(true);
		scrol2.setWheelScrollingEnabled(true);
		
		res.getContentPane().add(scrol2);
		
		
		//------------------individual loops------------------------------------
		JLabel Label3 = new JLabel("Individual loops");
		Label3.setEnabled(false);
		Label3.setBounds(350, 3, 153, 23);
		res.add(Label3);
		
		JTextArea individualLoops = new JTextArea();
		individualLoops.setBounds(345, 23, 145, 300);
		individualLoops.setFont(new Font("Serif",  Font.BOLD, 12));
		individualLoops.setEditable(false);
		
		JScrollPane scrol3 = new JScrollPane(individualLoops);
		scrol3.setBounds(345, 23, 145, 300);
		scrol3.setAutoscrolls(true);
		scrol3.setVisible(true);
		scrol3.setWheelScrollingEnabled(true);
		
		res.getContentPane().add(scrol3);
		
		//------------------non-touching loops------------------------------------
		JLabel Label4 = new JLabel("Non-touching loops");
		Label4.setEnabled(false);
		Label4.setBounds(545, 3, 153, 23);
		res.add(Label4);
		
		JTextArea nonTouching = new JTextArea();
		nonTouching.setBounds(540, 23, 145, 300);
		nonTouching.setFont(new Font("Serif",  Font.BOLD, 12));
		nonTouching.setEditable(false);
		
		JScrollPane scrol4 = new JScrollPane(nonTouching);
		scrol4.setBounds(540, 23, 145, 300);
		scrol4.setAutoscrolls(true);
		scrol4.setVisible(true);
		scrol4.setWheelScrollingEnabled(true);
		
		res.getContentPane().add(scrol4);
		
		// ------------------------------deltas---------------------------------------
		JLabel Label5 = new JLabel("Deltas");
		Label5.setEnabled(false);
		Label5.setBounds(745, 3, 153, 23);
		res.add(Label5);
		
		JTextArea deltas = new JTextArea();
		deltas.setBounds(740, 23, 140, 300);
		deltas.setFont(new Font("Serif",  Font.BOLD, 12));
		deltas.setEditable(false);
		
		JScrollPane scrol5 = new JScrollPane(deltas);
		scrol5.setBounds(740, 23, 140, 300);
		scrol5.setAutoscrolls(true);
		scrol5.setVisible(true);
		scrol5.setWheelScrollingEnabled(true);
		
		res.getContentPane().add(scrol5);
		
		for (int i = 0; i < paths.size(); i++) {
			forwardPaths.append(paths.get(i));
		}
		for (int i = 0; i < pathsGain.size(); i++) {
			gains.append(pathsGain.get(i));
		}
		for (int i = 0; i < indivLoops.size(); i++) {
			individualLoops.append(indivLoops.get(i));
		}
		for (int i = 0; i < nonTouchingLoops.size(); i++) {
			nonTouching.append(nonTouchingLoops.get(i));
		}
		for (int i = 0; i < delta.size(); i++) {
			deltas.append(delta.get(i));
		}
		
		//---------------------transfer function---------------------------------------
		JLabel Label6 = new JLabel("Overall transfer function =" + Tf);
		Label6.setEnabled(false);
		Label6.setBounds(350, 350, 300, 23);
		res.add(Label6);

		res.setVisible(true);
	}

}
