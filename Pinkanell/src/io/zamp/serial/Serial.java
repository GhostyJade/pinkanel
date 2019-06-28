package io.zamp.serial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.listener.GoalListener;
import io.hmatte.pinkadb.Pinkadb;

/**
 * This class provides a bridge between this application and the Arduino board
 * using USB as serial communication.
 *
 * @author Zamp
 * @since v1.0
 */
public class Serial implements SerialPortEventListener {

	/**
	 * The goal listener object. Used to perform an action when a goal is performed
	 */
	private GoalListener listener;

	/**
	 * The serial port object
	 */
	private SerialPort serialPort;

	/**
	 * Team one score
	 */
	private int point1;
	/**
	 * Team two score
	 */
	private int point2;

	/**
	 * An array that stores all the port names for every operating system
	 */
	private static final String PORT_NAMES[] = { "/dev/tty.usbmodem14201", // Mac OS X
			// "/dev/tty.usbmodemFD121",
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};

	/**
	 * The input stream used to retrieve data from Arduino
	 */
	private BufferedReader input;
	/**
	 * Time before the com port is out.
	 */
	private static final int TIME_OUT = 2000;
	/**
	 * The baud rate. This value MUST be the same as the Arduino's serial one.
	 */
	private static final int BAUD_RATE = 9600;

	/**
	 * Class constructor. Note that this constructor dosn't take any parameter
	 * because initializes a non-Windows port
	 * 
	 * @param listener the goal listener
	 * @see #Serial(String, GoalListener)
	 */
	public Serial(GoalListener listener) {
		this("0", listener);
	}

	/**
	 * Class constructor.
	 * 
	 * @param ncom     the ncom port
	 * @param listener the {@link GoalListener} object
	 * @see #Serial(GoalListener)
	 */
	public Serial(String ncom, GoalListener listener) {
		this.listener = listener;
		if (Integer.parseInt(ncom) >= 3 && Integer.parseInt(ncom) <= 9)
			PORT_NAMES[2] = "COM" + ncom;
		System.out.println("Serial Comms Started");
	}

	/**
	 * Initialize the serial communication
	 */
	public void initialize() {
		CommPortIdentifier portId = null;
		Enumeration<?> portEnum = CommPortIdentifier.getPortIdentifiers();

		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			JOptionPane.showMessageDialog(PinkanellMain.getWindow().getJFrame(),
					PinkanellMain.getI18n().getTranslationString("ui.messageE"),
					PinkanellMain.getI18n().getTranslationString("ui.error"), JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
			serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
		} catch (Exception e) {
			System.err.println("Couldn't initialize the COM port: resource is busy.");
		}
	}

	@Override
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				assignPoint(inputLine);
				listener.actionPerform();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * Close the serial communication
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
			try {
				input.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Assign the score to the correct team.
	 * 
	 * @param s the string received from Arduino board
	 * @throws SQLException 
	 */
	private void assignPoint(String s) throws SQLException {
		if (s.contains("-1"))
			return;
		if (s.startsWith("1"))
			point1 = Integer.parseInt(s.substring(1, s.length()));
		if (s.startsWith("2"))
			point2 = Integer.parseInt(s.substring(1, s.length()));
		if (getTeamOneScore() == 10 || getTeamTwoScore() == 10 ) {
			Pinkadb.endUpdate(getTeamOneScore(), getTeamTwoScore());
		}
	}

	/**
	 * @return team one's score
	 */
	public int getTeamOneScore() {
		return point1;
	}

	/**
	 * @return team's two score.
	 */
	public int getTeamTwoScore() {
		return point2;
	}

}
