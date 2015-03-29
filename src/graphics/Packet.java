package graphics;

import map.Connection;
import map.Connection.State;

public class Packet {

	private int x;
	private int y;

	private int size = 4;
	private int tick = 0;
	private int tickTick = 0;

	private double pos = 0;

	private Connection conn;
	private boolean forward;

	private double speed;

	private boolean remove;
	private boolean inc;

	private boolean onCourse;

	public Packet(Connection conn) {
		this.conn = conn;
		onCourse = true;
		if (conn.getState() == State.Unactive) {
			remove = true;
		}
		if (Math.random() > 0.5) {
			this.forward = true;
			this.x = (int) conn.getNode(1).getX();
			this.y = (int) conn.getNode(1).getY();
		} else {
			this.forward = false;
			this.x = (int) conn.getNode(2).getX();
			this.y = (int) conn.getNode(2).getY();
		}
		this.speed = Math.random() / 10;

		System.out.println(speed);
	}

	public void update() {
		if (conn.getState() == State.Unactive) {
			remove = true;
			return;
		}
		tickTick++;
		if (tickTick % 10 == 0) {
			if (inc) {
				size++;
				if (size == 6) {
					inc = false;
				}
			} else {
				size--;
				if (size == 2) {
					inc = true;
				}
			}
		}
		pos += speed;
		if (pos > 1) {
			remove = true;
			return;
		}
		if (forward) {
			x = (int) ((int) conn.getNode(2).getX() + (pos * (conn.getNode(1)
					.getX() - conn.getNode(2).getX())));
			y = (int) ((int) conn.getNode(2).getY() + (pos * (conn.getNode(1)
					.getY() - conn.getNode(2).getY())));
		} else {
			x = (int) ((int) conn.getNode(1).getX() + (pos * (conn.getNode(2)
					.getX() - conn.getNode(1).getX())));
			y = (int) ((int) conn.getNode(1).getY() + (pos * (conn.getNode(2)
					.getY() - conn.getNode(1).getY())));
		}

	}

	public void render() {
		Renderer.drawRectangle(x - size / 2, y - size / 2, size, size,
				Colour.RED);
	}

	public boolean needsRemove() {
		return this.remove;
	}

}
