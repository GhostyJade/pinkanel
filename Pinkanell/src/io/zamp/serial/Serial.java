package io.zamp.serial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.swing.JOptionPane;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import io.ghostyjade.pinkanell.PinkanellMain;
import io.ghostyjade.utils.listener.GoalListener;

/**
 *
 * @author Zamp
 * @since v1.0
 */
public class Serial implements SerialPortEventListener {

	private GoalListener listener;

	private SerialPort serialPort;

	/**
	 * 
	 */
	private int point1;
	/**
	 * 
	 */
	private int point2;

	private static final String PORT_NAMES[] = { "/dev/tty.usbmodem14201", // Mac OS X
			// "/dev/tty.usbmodemFD121",
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};

	private BufferedReader input;
	private static final int TIME_OUT = 2000;
	private static final int BAUD_RATE = 9600;

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
			JOptionPane.showMessageDialog(PinkanellMain.getWindow().getJFrame(), PinkanellMain.getI18n().getTranslationString("ui.messageE"), PinkanellMain.getI18n().getTranslationString("ui.error"), JOptionPane.ERROR_MESSAGE);
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
		}
	}

	public Serial(GoalListener listener) {
		this("0", listener);
	}

	public Serial(String ncom, GoalListener listener) {
		this.listener = listener;
		if (Integer.parseInt(ncom) >= 3 && Integer.parseInt(ncom) <= 9)
			PORT_NAMES[2] = "COM" + ncom;
		System.out.println("Serial Comms Started");
	}

	private void assignPoint(String s) {
		if (s.contains("-1"))
			return;
		if (s.startsWith("1"))
			point1 = Integer.parseInt(s.substring(1, s.length()));
		if (s.startsWith("2"))
			point2 = Integer.parseInt(s.substring(1, s.length()));
	}

	public int getTeamOneScore() {
		return point1;
	}

	public int getTeamTwoScore() {
		return point2;
	}

	public BufferedReader getInput() {
		return input;
	}

}
