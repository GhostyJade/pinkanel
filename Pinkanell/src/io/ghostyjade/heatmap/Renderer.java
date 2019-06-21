package io.ghostyjade.heatmap;

/**
 * This class provides some useful functions to render an image using pixels.
 * 
 * @author GhostyJade
 * @since v1.0
 */
public class Renderer {

	/**
	 * A constant used to edit the pixel's value.
	 */
	private final int VALUE = 25;
	/**
	 * The screen width
	 */
	private int width;
	/**
	 * The screen height
	 */
	private int height;

	/**
	 * The pixel color array
	 */
	public int[] pixels;

	/**
	 * 
	 * @param width
	 * @param height
	 */
	public Renderer(int width, int height) {
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
	}

	/**
	 * Gets a pixel color.
	 * 
	 * @param x the x pixel's position
	 * @param y the y pixel's position
	 * @return the color of the specified pixel
	 * @throws IndexOutOfBoundsException if the specified position is out of image's
	 *                                   bounds
	 */
	private int getColor(int x, int y) {
		if (x < 0 || y < 0 || x > width || y > height)
			throw new IndexOutOfBoundsException("The value (" + x + "; " + y + ") is not in range.");
		return pixels[x + y * width];
	}

	/**
	 * Create a new color from the given color.
	 * <p>
	 * This is used to create the heatmap pixel's color. Black(0x000000) ->
	 * YELLOW(0xffff00) -> ORANGE(0xff6400) -> RED(0xff0000)
	 * </p>
	 * 
	 * @param srcColor the source pixel color
	 * @return a new hex color value
	 */
	private int calculateColor(int srcColor) {
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

	/**
	 * Sets the specified color at the specified position.
	 * 
	 * @param x     the pixel's x position
	 * @param y     the pixel's position
	 * @param color the color
	 */
	private void setPixel(int x, int y, int color) {
		pixels[x + y * width] = color;
	}

	/**
	 * @return the image width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the image height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set the point position.
	 * 
	 * @param x point x
	 * @param y point y
	 */
	public void setPoint(int x, int y) {
		if (x < 0 || y < 0 || x > width || y > height)
			throw new IndexOutOfBoundsException("The value (" + x + "; " + y + ") is not in range.");
		setPixel(x, y, calculateColor(getColor(x, y)));
	}

}
