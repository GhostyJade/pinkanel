package io.ghostyjade.heatmap;

public class Renderer {

	private int width, height;

	public int[] pixels;

	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}

	public int getColor(int x, int y) {
		if (x < 0 || y < 0 || x > width || y > height)
			throw new IndexOutOfBoundsException("The value (" + x + "; " + y + ") is not in range.");
		return pixels[x + y * width];
	}

	// Black->YELLOW(0xffff00)->ORANGE(0xff6400)->RED(0xff0000)
	public int calculateColor(int x, int y) {
		if (x < 0 || y < 0 || x > width || y > height)
			// throw new IndexOutOfBoundsException("The value (" + x + "; " + y + ") is not
			// in range.");
			return 0;
		int srcColor = getColor(x, y);
		int red = ((srcColor >> 16) & 0xFF);
		int green = ((srcColor >> 8) & 0xFF);
		int blue = ((srcColor >> 0) & 0xFF);
		if ((red == green) && (red < 250 || green < 250)) {
			red = green = (red + VALUE);
		} else {
			if (green > 0) {
				green -= VALUE;
			}
		}
		return ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF) << 0);
	}

	private final int VALUE = 25;

	public void setPixel(int x, int y, int color) {
		if (x < 0 || y < 0 || x > width || y > height)
			throw new IndexOutOfBoundsException("The value (" + x + "; " + y + ") is not in range.");
		pixels[x + y * width] = color;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setPoint(int x, int y) {
		setPixel(x, y, calculateColor(x, y));
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

}
