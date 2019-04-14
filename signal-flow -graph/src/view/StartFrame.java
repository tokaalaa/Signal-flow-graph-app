package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Color;
import javax.swing.JLabel;


public class StartFrame {
	 private static DrawArea canvas;
	private static JFrame frame;
	private static StartFrame window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		window = new StartFrame();
	}
			

	/**
	 * Create the application.
	 */
	public StartFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(153, 204, 255));
		frame.setSize(1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		canvas = new DrawArea();
        canvas.setBounds(200, 0, frame.getWidth(), frame.getHeight());
		frame.getContentPane().add(canvas);
		
		
		final JLabel lblNewLabel = new JLabel("Current Mood: --");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setBounds(21, 11, 153, 23);
		frame.getContentPane().add(lblNewLabel);

		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(0);
				lblNewLabel.setText("Current Mood: Select");
			}
		});
		btnSelect.setBounds(21, 45, 153, 30);
		frame.getContentPane().add(btnSelect);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(4);
				lblNewLabel.setText("Current Mood: Clear");
			}
		});
		btnClear.setBounds(21, 95, 153, 30);
		frame.getContentPane().add(btnClear);
		
		JButton btnAddNode = new JButton("Add Node");
		btnAddNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(1);
				lblNewLabel.setText("Current Mood: Add Node");
			}
		});
		btnAddNode.setBounds(21, 146, 153, 30);
		frame.getContentPane().add(btnAddNode);
		
		JButton btnAddEdge = new JButton("Add Edge");
		btnAddEdge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				canvas.setCurrentMode(2);
				lblNewLabel.setText("Current Mood: Add Edge");
			}
		});
		btnAddEdge.setBounds(21, 196, 153, 30);
		frame.getContentPane().add(btnAddEdge);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(3);
				lblNewLabel.setText("Current Mood: Remove");
			}
		});
		btnRemove.setBounds(21, 247, 153, 30);
		frame.getContentPane().add(btnRemove);
		
		JButton btnSetInputNode = new JButton("Set Source Node");
		btnSetInputNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(5);
				lblNewLabel.setText("Current Mood: Set Source");
			}
		});
		btnSetInputNode.setBounds(21, 298, 153, 30);
		frame.getContentPane().add(btnSetInputNode);
		
		JButton btnSetOutputNode = new JButton("Set Sink Node");
		btnSetOutputNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(6);
				lblNewLabel.setText("Current Mood: Set Sink");
			}
		});
		btnSetOutputNode.setBounds(21, 347, 153, 30);
		frame.getContentPane().add(btnSetOutputNode);
		
		JButton solve = new JButton("Solve");
		solve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.setCurrentMode(7);
				lblNewLabel.setText("Current Mood: Solve");
				
			}
		});
		solve.setBounds(21, 396, 153, 30);
		frame.getContentPane().add(solve);

		
		frame.setEnabled(true);
		frame.setVisible(true);
	        

	}
}
