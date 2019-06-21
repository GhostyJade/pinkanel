package io.ghostyjade.heatmap;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.bytedeco.opencv.opencv_core.Point;

public class HeatMap extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private int width, height;

	private int scale = 3;

	private BufferedImage image;
	private int[] pixels;

	private Renderer renderer;

	public HeatMap(Dimension d) {
		this.width = d.width;
		this.height = d.height;
		image = new BufferedImage(width / scale, height / scale, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderer = new Renderer(width / scale, height / scale);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
	}

	public void setPoint(Point p) {
		renderer.setPoint(p.x() / scale, p.y() / scale);// FIXME
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		for (int i = 0; i < renderer.pixels.length; i++) {
			pixels[i] = renderer.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		g.dispose();
		bs.show();
	}

	private boolean render = true;

	@Override
	public void run() {
		while (render) {
			render();
		}
	}

}
