package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import map.Connection;
import map.GenerateNodes;
import map.Node;

import org.lwjgl.opengl.GL11;

import engine.GameState;
import engine.GameStateManager;
import graphics.Renderer;

public class PlayState implements GameState {

	private GameStateManager gsm;
	private int backgroundID;
	private ArrayList<NodeButton> nodeButtons;
	ArrayList<Connection> conns;
	private Node focussed;

	
	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		this.backgroundID = Renderer.uploadTexture("resources/playBackground.png");
		focussed = null;
		nodeButtons = new ArrayList<NodeButton>();
		
		GenerateNodes genNodes = new GenerateNodes("mit.edu",100);
		Map<String, Node> map = genNodes.getUrlToNode();
		
		double minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		
		//long = x;
		
		ArrayList<Double> lats = new ArrayList<Double>();
		ArrayList<Double> longs = new ArrayList<Double>();
		
		for (String key : map.keySet()) {
			Node n = map.get(key);
			if (n.getURL().equals("upenn.edu")) {
				System.out.println("UPENN");
				n.setLatitude(39);
				n.setLongitude(-75);
			}
			lats.add(n.getLatitude());
			longs.add(n.getLongitude());
			if (n.getLatitude() > maxY) {
				maxY = n.getLatitude();
			}
			if (n.getLatitude() < minY) {
				minY = n.getLatitude();
			}
			if (n.getLongitude() > maxX) {
				maxX = n.getLongitude();
			}
			if (n.getLongitude() < minX) {
				minX = n.getLongitude();
			}
		}
		
		Collections.sort(longs);
		Collections.sort(lats);
		
		double xq1 = longs.get(longs.size()/5);
		double xq2 = longs.get(2*longs.size()/5);
		double xq3 = longs.get(3*longs.size()/5);
		double xq4 = longs.get(4*longs.size()/5);
		
		double yq1 = lats.get(lats.size()/5);
		double yq2 = lats.get(2*lats.size()/5);
		double yq3 = lats.get(3*lats.size()/5);
		double yq4 = lats.get(4*lats.size()/5);

		
		double gapX = maxX - minX;
		double gapY = maxY - minY;
		
		
		
		
		
		for (String key : map.keySet()) {
			Node n = map.get(key);
			int x;
			int y;
			
			
			if (n.getLongitude() < xq1) {
				gapX = xq1 - minX;
				x = 100 + (int) (120 * (n.getLongitude() - minX) / gapX);
			} else if (n.getLongitude() < xq2){
				gapX = xq2 - xq1;
				x = 220 + (int) (120 * (n.getLongitude() - xq1) / gapX);
			} else if (n.getLongitude() < xq3){
				gapX = xq3 - xq2;
				x = 340 + (int) (120 * (n.getLongitude() - xq2) / gapX);
			} else if (n.getLongitude() < xq4){
				gapX = xq4 - xq3;
				x = 460 + (int) (120 * (n.getLongitude() - xq3) / gapX);
			} else {
				gapX = maxX - xq4;
				x = 580 + (int) (120 * (n.getLongitude() - xq4) / gapX);
			}
			
			
			
			if (n.getLatitude() < yq1) {
				gapY = yq1 - minY;
				y = 100 + (int) (80 * (n.getLatitude() - minY) / gapY);
			} else if (n.getLatitude() < yq2){
				gapY = yq2 - yq1;
				y = 180 + (int) (80 * (n.getLatitude() - yq1) / gapY);
			} else if (n.getLatitude() < yq3){
				gapY = yq3 - yq2;
				y = 260 + (int) (80 * (n.getLatitude() - yq2) / gapY);
			} else if (n.getLatitude() < yq4){
				gapY = yq4 - yq3;
				y = 340 + (int) (80 * (n.getLatitude() - yq3) / gapY);
			} else {
				gapY = maxY - yq4;
				y = 420 + (int) (80 * (n.getLatitude() - yq4) / gapY);
			}

			n.setX(x);
			n.setY(600 - y);
			
			System.out.println(n.getURL() + "    " + x + "        " + y);
			nodeButtons.add(new NodeButton(key, map.get(key), x, y));
			
			for (int i = 0; i < nodeButtons.size(); i++) {
				for (int j = i + 1; j < nodeButtons.size(); j++) {
					if (Math.abs(nodeButtons.get(i).getX() - nodeButtons.get(j).getX()) < 30) {
						int cx = nodeButtons.get(j).getX() +  (nodeButtons.get(i).getX() - nodeButtons.get(j).getX())/2;
						if (nodeButtons.get(j).getX() < nodeButtons.get(i).getX()) {
							nodeButtons.get(i).setX(cx + 15); 
							nodeButtons.get(i).getNode().setX(cx + 15);
							nodeButtons.get(j).setX(cx - 15);
							nodeButtons.get(j).getNode().setX(cx - 15);
						} else {
							nodeButtons.get(i).setX(cx - 15); 
							nodeButtons.get(i).getNode().setX(cx - 15);
							nodeButtons.get(j).setX(cx + 15);
							nodeButtons.get(j).getNode().setX(cx + 15);
						}
					}
				}
			}
		}
		
		conns = genNodes.getConnections();
		
		for (Connection c: conns) {
			System.out.println(c);
		}
		
		
		
		
	}
	
	@Override
	public void input() {
		focussed = null;
		for (NodeButton n : nodeButtons) {
			if (n.isMouseOver()) {
				focussed = n.getNode();
			}
		}

	}

	@Override
	public void update() {


	}

	@Override
	public void render() {
		Renderer.drawTextureRectangle(backgroundID, 0, 0, 800, 600);
		
		for (Connection c : conns) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if (c.getNode(1).equals(focussed) || c.getNode(2).equals(focussed)) {
				 GL11.glColor3f(1.0f, 0.0f, 0.2f);
			} else {
				 GL11.glColor3f(0.0f, 1.0f, 0.2f);
			}
		   
		    GL11.glLineWidth(2.0f);
		    GL11.glBegin(GL11.GL_LINE_STRIP);

		    GL11.glVertex2d(c.getNode(1).getX(), c.getNode(1).getY());
		    GL11.glVertex2d(c.getNode(2).getX(), c.getNode(2).getY());
		    GL11.glEnd();
		    GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
		
		for (NodeButton nodeButton : nodeButtons) {
			nodeButton.render();
		}
	}

	@Override
	public void destroy() {


	}

}
