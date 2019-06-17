package io.taglioiscoding.ui;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.border.LineBorder;

/**
 * 
 * @author taglioIsCoding
 *
 */

public class MainWindow {

	private JFrame frame;

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
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setBackground(Color.WHITE);
		frame.setBounds(0, 0, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.ORANGE, 3));
		panel.setBounds(6, 30, 826, 592);

		JLabel lblVmax = new JLabel("Velocità massima:");
		lblVmax.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblVmax.setBounds(844, 93, 358, 72);

		JLabel valueVmed = new JLabel("000.00");
		valueVmed.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		valueVmed.setBounds(1228, 189, 193, 96);

		JLabel lblVmed = new JLabel("Velocità media:");
		lblVmed.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblVmed.setBounds(844, 206, 358, 62);

		JLabel valueVmax = new JLabel("000.00");
		valueVmax.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		valueVmax.setBounds(1228, 93, 185, 72);

		JLabel Player1 = new JLabel("Player 1");
		Player1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		Player1.setBounds(431, 652, 236, 41);

		JLabel Player2 = new JLabel("Player 2");
		Player2.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		Player2.setBounds(759, 641, 241, 62);

		JLabel points1 = new JLabel("0");
		points1.setBounds(460, 652, 104, 197);
		points1.setHorizontalAlignment(SwingConstants.CENTER);
		points1.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		JLabel points2 = new JLabel("0");
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

		JLabel scoreDivisor = new JLabel(":");
		scoreDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		scoreDivisor.setFont(new Font("Lucida Grande", Font.BOLD, 99));
		scoreDivisor.setBounds(657, 702, 73, 96);
		frame.getContentPane().add(scoreDivisor);
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
