package io.ghostyjade.heatmap;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import org.bytedeco.opencv.opencv_core.Point;

/**
 * The heatmap shows how many times the ball has gone on a certain point.
 * 
 * ma, scus, io non ho capito, ma e HEATMAP o HOTMAP?! credo/spero heatmap.
 * ma trah, cioè quello bipolare che scrive una volta heatmap e poi hotmap non sono io
 * <3 :D
 * 
 * @author GhostyJade
 */
public class HeatMap extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	/**
	 * The image width
	 */
	private int width;
	/**
	 * The image height
	 */
	private int height;

	/**
	 * The scale factor
	 */
	private final int scale = 9;

	/**
	 * The rendered image
	 */
	private BufferedImage image;
	/**
	 * The image data. It stores colors as integer values
	 */
	private int[] pixels;

	/**
	 * The renderer object
	 */
	private Renderer renderer;

	/**
	 * Is rendering?
	 */
	private boolean render = true;

	/**
	 * The Class constructor.
	 * 
	 * @param imgSize    the image dimension
	 * @param canvasSize the canvas dimension
	 */
	public HeatMap(Dimension imgSize, Dimension canvasSize) {
		this.width = imgSize.width;
		this.height = imgSize.height;
		image = new BufferedImage(width / scale, height / scale, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		renderer = new Renderer(width / scale, height / scale);
		setPreferredSize(canvasSize);
		setMaximumSize(canvasSize);
		setMinimumSize(canvasSize);
	}

	/**
	 * Set the specified value to the heatmap.
	 * 
	 * @param p the new point
	 */
	public void setPoint(Point p) {
		renderer.setPoint(p.x() / scale, p.y() / scale);
	}

	/**
	 * Render the heatmap to the canvas
	 */
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
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	/**
	 * Updates and render the canvas.
	 */
	@Override
	public void run() {
		while (render) {
			render();
		}
	}

}
