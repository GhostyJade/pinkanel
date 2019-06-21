package io.taglioiscoding.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class UWin extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public UWin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHaiVinto = new JLabel("Hai Vinto!");
		lblHaiVinto.setBounds(169, 130, 67, 16);
		contentPane.add(lblHaiVinto);
	}
}
