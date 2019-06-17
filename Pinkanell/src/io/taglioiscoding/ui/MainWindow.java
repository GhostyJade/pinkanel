package io.taglioiscoding.ui;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;

/**
 * 
 * @author taglioIsCoding
 *
 */

public class MainWindow {

	private JFrame frame;

	

	/**
	 * Create the application.
	 * 
	 * 
	 * 
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
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setBackground(Color.WHITE);
		frame.setBounds(0, 0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(0, 30, 0, 0);
		
		JLabel label_5 = new JLabel("");
		label_5.setBounds(0, 30, 0, 0);
		
		JLabel lblNewLabel = new JLabel("Pinkanel");
		lblNewLabel.setBounds(191, 6, 53, 16);
		
		JLabel label_6 = new JLabel("");
		label_6.setBounds(53, 42, 0, 0);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 30, 254, 135);
		
		JLabel lblNewLabel_1 = new JLabel("Vmax:");
		lblNewLabel_1.setBounds(272, 42, 39, 16);
		
		JLabel label_7 = new JLabel("");
		label_7.setBounds(171, 30, 0, 0);
		
		JLabel label = new JLabel("000.00");
		label.setBounds(323, 42, 44, 16);
		
		JLabel label_8 = new JLabel("");
		label_8.setBounds(191, 30, 0, 0);
		
		JLabel lblVmed = new JLabel("Vmed:");
		lblVmed.setBounds(272, 70, 39, 16);
		
		JLabel label_9 = new JLabel("");
		label_9.setBounds(250, 165, 0, 0);
		
		JLabel label_1 = new JLabel("000.00");
		label_1.setBounds(323, 70, 44, 16);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setBounds(163, 173, 59, 16);
		
		JLabel label_10 = new JLabel("");
		label_10.setBounds(343, 165, 0, 0);
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setBounds(262, 173, 49, 16);
		
		JLabel label_11 = new JLabel("");
		label_11.setBounds(392, 139, 0, 0);
		
		JLabel label_2 = new JLabel("0");
		label_2.setBounds(171, 201, 27, 52);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("Lucida Grande", Font.PLAIN, 43));
		
		JLabel label_12 = new JLabel("");
		label_12.setBounds(419, 139, 0, 0);
		
		JLabel label_3 = new JLabel("0");
		label_3.setBounds(272, 201, 27, 52);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Lucida Grande", Font.PLAIN, 43));
		
		
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(label_5);
		frame.getContentPane().add(label_4);
		frame.getContentPane().add(label_6);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(label_7);
		frame.getContentPane().add(label);
		frame.getContentPane().add(label_8);
		frame.getContentPane().add(lblVmed);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(label_1);
		frame.getContentPane().add(label_9);
		frame.getContentPane().add(lblPlayer);
		frame.getContentPane().add(lblPlayer_1);
		frame.getContentPane().add(label_10);
		frame.getContentPane().add(label_11);
		frame.getContentPane().add(label_2);
		frame.getContentPane().add(label_3);
		frame.getContentPane().add(label_12);
		
		JLabel label_14 = new JLabel(":");
		label_14.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		label_14.setBounds(233, 201, 11, 37);
		frame.getContentPane().add(label_14);
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
