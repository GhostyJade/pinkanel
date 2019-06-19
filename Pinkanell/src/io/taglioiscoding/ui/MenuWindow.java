package io.taglioiscoding.ui;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.Constants;

public class MenuWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel lblCm;
	private JLabel lblInsertTheField_1;
	private JLabel lblCm_1;
	private JComboBox<String> comboWidth;
	private JComboBox<String> comboHeigt;
	private JComboBox<String> comboLen;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public MenuWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblInsertTheField = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.insertWidth"));
		lblInsertTheField.setBounds(31, 135, 135, 16);
		contentPane.add(lblInsertTheField);

		lblCm = new JLabel("Cm");
		lblCm.setBounds(303, 135, 61, 16);
		contentPane.add(lblCm);

		lblInsertTheField_1 = new JLabel(PinkanellMain.getI18n().getTranslationString("ui.insertHeight"));
		lblInsertTheField_1.setBounds(34, 169, 132, 16);
		contentPane.add(lblInsertTheField_1);

		lblCm_1 = new JLabel("Cm");
		lblCm_1.setBounds(303, 169, 61, 16);
		contentPane.add(lblCm_1);

		comboLen = new JComboBox<String>();
		comboLen.setModel(new DefaultComboBoxModel<String>(new String[] { "Italiano", "English" }));
		comboLen.setBounds(141, 46, 123, 27);
		contentPane.add(comboLen);

		comboWidth = new JComboBox<String>();
		comboWidth.setModel(new DefaultComboBoxModel<String>(new String[] { "10", "20", "30" }));
		comboWidth.setBounds(168, 131, 123, 27);
		contentPane.add(comboWidth);

		comboHeigt = new JComboBox<String>();
		comboHeigt.setModel(new DefaultComboBoxModel<String>(new String[] { "10", "20", "30" }));
		comboHeigt.setBounds(168, 165, 123, 27);
		contentPane.add(comboHeigt);
	}

	public void local() {
		String s = comboLen.getItemAt(comboLen.getSelectedIndex());
		switch (s) {
		case "Italiano":
			Constants.LOCALE_NAME = "it_IT";
			break;
		case "English":
			Constants.LOCALE_NAME = "en_EN";
			break;
		}
	}

	public void showWindow() {
		setVisible(true);
	}

}
