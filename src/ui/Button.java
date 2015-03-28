package ui;

import main.Main;

import org.lwjgl.input.Mouse;

import graphics.Renderer;

public class Button {

	private int onMouseOverID;
	private int normalID;
	private int x;
	private int y;
	private int width;
	private int height;

	private boolean clicked;
	private boolean visible;

	public Button(int x, int y, int width, int height, String normalFilePath,
			String mouseOverFilePath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.normalID = Renderer.uploadTexture("resources/test.png");
		this.onMouseOverID = Renderer.uploadTexture("resources/test2.png");

		System.out.println(normalID);
		System.out.println(onMouseOverID);

	}

	public void setVisible(boolean b) {
		this.visible = b;
	}

	public boolean isMouseOver() {
		if (Mouse.getX() > x && Mouse.getX() < x + width
				&& Main.WINDOW_HEIGHT - Mouse.getY() > y
				&& Main.WINDOW_HEIGHT - Mouse.getY() < y + height) {
			return true;
		}
		return false;
	}

	public void render() {
		if (isMouseOver()) {
			Renderer.drawTextureRectangle(onMouseOverID, x, y, width, height);
		} else {
			Renderer.drawTextureRectangle(normalID, x, y, width, height);
		}
		
		
		// System.out.println("mouseOver");
		

		// System.out.println("normal");
		
}
}
