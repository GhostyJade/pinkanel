package io.taglioiscoding.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * 
 * @author taglioIsCoding
 *
 */

public class MainWindow {

	private JFrame frame;
	private JPanel panel;
	private JLabel lblVmax;
	private JLabel valueVmed;
	private JLabel lblVmed, valueVmax, Player1, Player2, points1, points2, scoreDivisor, goal;
	
	

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * 
	 * Initialize the contents of the frame.
	 * 
	 * @author taglioIsCoding
	 * 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		frame.setResizable(false);
		//XXX frame.setUndecorated(true);
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setBackground(Color.WHITE);
		frame.setBounds(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.ORANGE, 3));
		panel.setBounds(6, 30, 826, 592);

		changeToGoal();

		lblVmax = new JLabel("Velocità massima:");
		lblVmax.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblVmax.setBounds(844, 93, 358, 72);

		valueVmed = new JLabel("000.00");
		valueVmed.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		valueVmed.setBounds(1228, 189, 193, 96);

		lblVmed = new JLabel("Velocità media:");
		lblVmed.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblVmed.setBounds(844, 206, 358, 62);

		valueVmax = new JLabel("000.00");
		valueVmax.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		valueVmax.setBounds(1228, 93, 185, 72);

		Player1 = new JLabel("Player 1");
		Player1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		Player1.setBounds(431, 652, 236, 41);

		Player2 = new JLabel("Player 2");
		Player2.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		Player2.setBounds(759, 641, 241, 62);

		points1 = new JLabel("0");
		points1.setBounds(460, 652, 104, 197);
		points1.setHorizontalAlignment(SwingConstants.CENTER);
		points1.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		points2 = new JLabel("0");
		points2.setBounds(731, 630, 206, 236);
		points2.setHorizontalAlignment(SwingConstants.CENTER);
		points2.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(lblVmax);
		frame.getContentPane().add(valueVmed);
		frame.getContentPane().add(lblVmed);
		frame.getContentPane().add(valueVmax);
		frame.getContentPane().add(Player1);
		frame.getContentPane().add(Player2);
		frame.getContentPane().add(points1);
		frame.getContentPane().add(points2);
		
		
		

		scoreDivisor = new JLabel(":");
		scoreDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		scoreDivisor.setFont(new Font("Lucida Grande", Font.BOLD, 99));
		scoreDivisor.setBounds(657, 702, 73, 96);
		frame.getContentPane().add(scoreDivisor);
	}

	public void changeToCameraPreview() {
		panel.setBorder(new LineBorder(Color.ORANGE, 3));
		panel.setBounds(0, 0,((Toolkit.getDefaultToolkit().getScreenSize().width) /2 - 10),
				640);

		lblVmax.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblVmax.setBounds(61, 654, 358, 72);

		valueVmed.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		valueVmed.setBounds(445, 750, 193, 96);

		lblVmed.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblVmed.setBounds(61, 767, 358, 62);

		valueVmax.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		valueVmax.setBounds(445, 654, 185, 72);

		Player1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		Player1.setBounds(797, 680, 236, 41);

		Player2.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		Player2.setBounds(1125, 669, 241, 62);

		points1.setBounds(826, 680, 104, 197);
		points1.setHorizontalAlignment(SwingConstants.CENTER);
		points1.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		points2.setBounds(1097, 658, 206, 236);
		points2.setHorizontalAlignment(SwingConstants.CENTER);
		points2.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		scoreDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		scoreDivisor.setFont(new Font("Lucida Grande", Font.BOLD, 99));
		scoreDivisor.setBounds(1023, 730, 73, 96);
		
		JPanel cameraPanel = new JPanel();
		cameraPanel.setBorder(new LineBorder(new Color(0, 255, 0), 3));
		cameraPanel.setBounds(panel.getWidth(), 0 ,((Toolkit.getDefaultToolkit().getScreenSize().width) /2 - 10),
				 640);
		frame.getContentPane().add(cameraPanel);
	}
	
	
	public void changeToGoal() {
		panel.setBorder(new LineBorder(Color.RED, 3));
		//panel.setBounds(0, 0,(Toolkit.getDefaultToolkit().getScreenSize().width),
		//		Toolkit.getDefaultToolkit().getScreenSize().height);
		
		goal = new JLabel("GOOOOOOOAL");
		goal.setBounds(0,0,(Toolkit.getDefaultToolkit().getScreenSize().width),
			Toolkit.getDefaultToolkit().getScreenSize().height);
		goal.setVerticalAlignment(SwingConstants.CENTER);
		goal.setHorizontalAlignment(SwingConstants.CENTER);
		goal.setFont(new Font("Lucida Grande", Font.PLAIN, 150));
		goal.setBorder(new LineBorder(Color.RED, 3));	
		frame.getContentPane().add(goal);
	}
	
	
	
	/**
	 * Show the window.
	 * 
	 * @author GhostyJade
	 */
	public void create() {
		frame.setVisible(true);
	}
}
