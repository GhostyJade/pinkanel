package io.taglioiscoding.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.Settings;

/**
 * 
 * @author taglioIsCoding
 * @author GhostyJade
 * @since v1.0
 */

public class MainWindow {

	private JFrame frame;
	private JPanel hotmapPanel, cameraPanel;
	private JLabel lblVmax;
	private JLabel valueVmed;
	private JLabel lblVmed, valueVmax, player1, player2, points1, points2, scoreDivisor, goal;

	private final Font lucidaGrande40 = new Font("Lucida Grande", Font.PLAIN, 40);

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
		frame.getContentPane().setFont(lucidaGrande40);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setBackground(Color.WHITE);
		frame.setBounds(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(closeWindow());
		hotmapPanel = new JPanel();
		hotmapPanel.setBorder(new LineBorder(Color.ORANGE, 3));
		hotmapPanel.setBounds(6, 30, 826, 592);

		lblVmax = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.maxspeed"));
		lblVmax.setFont(lucidaGrande40);
		lblVmax.setBounds(844, 93, 358, 72);

		valueVmed = new JLabel("000.00");
		valueVmed.setFont(lucidaGrande40);
		valueVmed.setBounds(1228, 189, 193, 96);

		lblVmed = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.averagespeed"));
		lblVmed.setFont(lucidaGrande40);
		lblVmed.setBounds(844, 206, 358, 62);

		valueVmax = new JLabel("000.00");
		valueVmax.setFont(lucidaGrande40);
		valueVmax.setBounds(1228, 93, 185, 72);

		player1 = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.player1"));
		player1.setFont(lucidaGrande40);
		player1.setBounds(431, 652, 236, 41);

		player2 = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.player2"));
		player2.setFont(lucidaGrande40);
		player2.setBounds(759, 641, 241, 62);

		points1 = new JLabel("0");
		points1.setBounds(460, 652, 104, 197);
		points1.setHorizontalAlignment(SwingConstants.CENTER);
		points1.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		points2 = new JLabel("0");
		points2.setBounds(731, 630, 206, 236);
		points2.setHorizontalAlignment(SwingConstants.CENTER);
		points2.setFont(new Font("Lucida Grande", Font.PLAIN, 99));

		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(hotmapPanel);
		frame.getContentPane().add(lblVmax);
		frame.getContentPane().add(valueVmed);
		frame.getContentPane().add(lblVmed);
		frame.getContentPane().add(valueVmax);
		frame.getContentPane().add(player1);
		frame.getContentPane().add(player2);
		frame.getContentPane().add(points1);
		frame.getContentPane().add(points2);

		scoreDivisor = new JLabel(":");
		scoreDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		scoreDivisor.setFont(new Font("Lucida Grande", Font.BOLD, 99));
		scoreDivisor.setBounds(657, 702, 73, 96);
		frame.getContentPane().add(scoreDivisor);
	}

	/**
	 * A {@link KeyListener} that perform some tasks when certain keys has been hit
	 * 
	 * @author GhostyJade
	 */
	private KeyListener closeWindow() {
		return new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					Settings.storeSettingsToFile("pinkanell.properties");
					System.exit(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_P)
					changeToCameraPreview();
				if (e.getKeyCode() == KeyEvent.VK_M)
					changeToMatchPreview();
				if (e.getKeyCode() == KeyEvent.VK_S)
					new MenuWindow().showWindow();
			}
		};
	}

	/**
	 * Preview of the UI during the match
	 */
	public void changeToMatchPreview() {
		lblVmax.setBounds(844, 93, 358, 72);
		lblVmed.setBounds(844, 206, 358, 62);
		valueVmax.setBounds(1228, 93, 185, 72);
		valueVmed.setBounds(1228, 189, 193, 96);
		player1.setBounds(431, 652, 236, 41);
		player2.setBounds(759, 641, 241, 62);
		points1.setBounds(460, 652, 104, 197);
		points2.setBounds(731, 630, 206, 236);
		scoreDivisor.setBounds(657, 702, 73, 96);
		hotmapPanel.setBounds(6, 30, 826, 592);
		if (cameraPanel != null)
			frame.getContentPane().remove(cameraPanel);
		PinkanellMain.getRecognizerInstance().destroyPanel();
	}

	/**
	 * Preview of camera vision that we used for debugging
	 */
	public void changeToCameraPreview() {
		hotmapPanel.setBorder(new LineBorder(Color.ORANGE, 3));
		hotmapPanel.setBounds(0, 0, ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 10), 640);

		lblVmax.setBounds(61, 654, 358, 72);
		valueVmed.setBounds(445, 750, 193, 96);
		lblVmed.setBounds(61, 767, 358, 62);
		valueVmax.setBounds(445, 654, 185, 72);
		player1.setBounds(797, 680, 236, 41);
		player2.setBounds(1125, 669, 241, 62);

		points1.setBounds(826, 680, 104, 197);
		points1.setHorizontalAlignment(SwingConstants.CENTER);

		points2.setBounds(1097, 658, 206, 236);
		points2.setHorizontalAlignment(SwingConstants.CENTER);

		scoreDivisor.setHorizontalAlignment(SwingConstants.CENTER);
		scoreDivisor.setBounds(1023, 730, 73, 96);

		PinkanellMain.getRecognizerInstance().createPanel();
		cameraPanel = new JPanel();
		cameraPanel.setBorder(new LineBorder(new Color(0, 255, 0), 3));
		cameraPanel.add(PinkanellMain.getRecognizerInstance().getCameraPane());
		cameraPanel.setBounds(hotmapPanel.getWidth(), 0, ((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - 10),
				640);
		frame.getContentPane().add(cameraPanel);
	}

	/**
	 * First test for the goal animation
	 */
	public void changeToGoal() {
		hotmapPanel.setBorder(new LineBorder(Color.RED, 3));
		goal = new JLabel("GOOOOOOOAL");
		goal.setBounds(0, 0, (Toolkit.getDefaultToolkit().getScreenSize().width),
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

	/**
	 * Reload the text strings
	 * 
	 * @author GhostyJade
	 */
	public void reloadTexts() {
		lblVmax.setText(PinkanellMain.getI18n().getTranslationString("ui.maxspeed"));
		lblVmed.setText(PinkanellMain.getI18n().getTranslationString("ui.averagespeed"));
		player1.setText(PinkanellMain.getI18n().getTranslationString("ui.player1"));
		player2.setText(PinkanellMain.getI18n().getTranslationString("ui.player1"));

	}

	public void updateScore() {
		points1.setText(String.valueOf(PinkanellMain.getSerial().getTeamOneScore()));		
		points2.setText(String.valueOf(PinkanellMain.getSerial().getTeamTwoScore()));
		valueVmax.setText(String.valueOf(PinkanellMain.getMath().getMaxSpeed()));
		valueVmed.setText(String.valueOf(PinkanellMain.getMath().getAverageSpeed()));
		//TODO set velocity
	}
}
