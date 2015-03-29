package ui;

import main.Main;

import org.lwjgl.input.Mouse;

import graphics.Renderer;

public class Button {

	protected int onMouseOverID;
	protected int normalID;
	protected int x;
	protected int y;
	protected int width;
	protected int height;

	private boolean clicked;
	private boolean pressed;
	private boolean visible;

	public Button(int x, int y, int width, int height, String normalFilePath,
			String mouseOverFilePath) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.normalID = Renderer.uploadTexture(normalFilePath);
		this.onMouseOverID = Renderer.uploadTexture(mouseOverFilePath);

		// System.out.println(normalID);
		// System.out.println(onMouseOverID);

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

	}
	
	public void input(){
		if (clicked) {
			clicked = false;
		}

		if (!isMouseOver()) {
			pressed = false;
			return;
		}

		if (Mouse.isButtonDown(0)) {
			pressed = true;
		} else if (pressed) {
			// The mouse was released on the button
			pressed = false;
			clicked = true;
		}
	}
	
	public boolean isClicked(){
		return this.clicked;
	}
}
