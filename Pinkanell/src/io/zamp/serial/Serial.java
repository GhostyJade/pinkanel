package io.zamp.serial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 *
 * @author Stage 2018/2019
 * @since v1.0
 */
public class Serial implements SerialPortEventListener {

	private SerialPort serialPort;

	private int point1, point2;

	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
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
			return;
		}

		try {
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));

			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				assignPoint(inputLine);
			} catch (Exception e) {
			}
		}
	}

	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	public Serial(String ncom) {
		if (Integer.parseInt(ncom) >= 3 && Integer.parseInt(ncom) <= 9)
			PORT_NAMES[2] = "COM" + ncom;
		initialize();
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000000);
				} catch (InterruptedException ie) {
				}
			}
		};
		t.start();
		System.out.println("Serial Comms Started");
	}

	private void assignPoint(String s) {

		if (s.startsWith("1")) {
			point1 = Integer.parseInt(s.substring(1, s.length()));
			System.out.println("Giocatore 1 " + point1);
			
		} else {
			point2 = Integer.parseInt(s.substring(1, s.length()));
			System.out.println("Giocatore 2 " + point2);
		}
	}

	public int getPlayerOnePoints() {
		return point1;
	}

	public int getPlayerTwoPoints() {
		return point2;
	}
}
