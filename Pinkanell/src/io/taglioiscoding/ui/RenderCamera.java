package io.taglioiscoding.ui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.nio.Buffer;

import org.bytedeco.javacv.Frame;

/**
 * This class render frame into a canvas. At this moment, this class is useless
 * and not used, because is not implemented yet.
 * 
 * @author GhostyJade
 */
public class RenderCamera extends Canvas {
	private static final long serialVersionUID = 1L;

	private Frame frame = new Frame(100, 100, 100, 4);

	public RenderCamera() {
		render();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		Buffer[] b = frame.image;
		System.out.println(b[0]);
		// g.drawImage(img, x, y, observer)
		g.dispose();
		bs.show();
	}

}
