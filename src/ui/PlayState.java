package ui;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import map.Connection;
import map.GenerateNodes;
import map.Node;
import map.Connection.State;

import org.lwjgl.opengl.GL11;

import stuffShaunWantButNoOneElseWantsToDo.ShaunsShittlyLittleTextFileReader;
import engine.GameState;
import engine.GameStateManager;
import graphics.Packet;
import graphics.Renderer;

public class PlayState implements GameState {

	private int lives = 3;
	private int nodesDestroyed = 0;
	private int nodesRemaining;
	
	private int restoreTick = 120;
	
	private GameStateManager gsm;
	private int backgroundID;
	private int overlayID;
	private ArrayList<NodeButton> nodeButtons;
	private ArrayList<Packet> packets;
	private ArrayList<Connection> conns;
	private ArrayList<AtomicInteger> alpha;
	private ArrayList<NodeButton> fading;
	private boolean incOp;
	private Node mouseOver;
	private Node focussed;
	
	private boolean gameRunning = false;
	
	private int pTick = 0;
	
	

	
	public PlayState(GameStateManager gsm) {
		this.gsm = gsm;
		this.incOp = false;
		this.fading = new ArrayList<NodeButton>();
		this.backgroundID = Renderer.uploadTexture("resources/playBackground.png");
		this.overlayID = Renderer.uploadTexture("resources/overlay.png");
		mouseOver = null;
		focussed = null;
		nodeButtons = new ArrayList<NodeButton>();
		this.packets = new ArrayList<Packet>();
		
		GenerateNodes genNodes = ShaunsShittlyLittleTextFileReader.getNodes();
		Map<String, Node> map = genNodes.getUrlToNode();
		
		double minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		
		//long = x;
		
		ArrayList<Double> lats = new ArrayList<Double>();
		ArrayList<Double> longs = new ArrayList<Double>();
		
		for (String key : map.keySet()) {
			Node n = map.get(key);
			
			
			
			if (n.getURL().equals("upenn.edu")) {
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
			if (n.getNumberOfConnections() == 0) {
				continue;
			}
			
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
			
			System.out.println(n.getURL() + "    " + n.getTrust());
			nodeButtons.add(new NodeButton(key, map.get(key), x, y));
			
			for (int i = 0; i < nodeButtons.size(); i++) {
				for (int j = i + 1; j < nodeButtons.size(); j++) {
					if (Math.abs(nodeButtons.get(i).getX() - nodeButtons.get(j).getX()) < 40) {
						int cx = nodeButtons.get(j).getX() +  (nodeButtons.get(i).getX() - nodeButtons.get(j).getX())/2;
						if (nodeButtons.get(j).getX() < nodeButtons.get(i).getX()) {
							nodeButtons.get(i).setX(cx + 20); 
							nodeButtons.get(i).getNode().setX(cx + 20);
							nodeButtons.get(j).setX(cx - 20);
							nodeButtons.get(j).getNode().setX(cx - 20);
						} else {
							nodeButtons.get(i).setX(cx - 20); 
							nodeButtons.get(i).getNode().setX(cx - 20);
							nodeButtons.get(j).setX(cx + 20);
							nodeButtons.get(j).getNode().setX(cx + 20);
						}
					}
					
					
					if (Math.abs(nodeButtons.get(i).getY() - nodeButtons.get(j).getY()) < 40) {
						int cx = nodeButtons.get(j).getY() +  (nodeButtons.get(i).getY() - nodeButtons.get(j).getY())/2;
						if (nodeButtons.get(j).getY() < nodeButtons.get(i).getY()) {
							nodeButtons.get(i).setY(cx + 20); 
							nodeButtons.get(i).getNode().setY(cx + 20);
							nodeButtons.get(j).setY(cx - 20);
							nodeButtons.get(j).getNode().setY(cx - 20);
						} else {
							nodeButtons.get(i).setY(cx - 20); 
							nodeButtons.get(i).getNode().setY(cx - 20);
							nodeButtons.get(j).setY(cx + 20);
							nodeButtons.get(j).getNode().setY(cx + 20);
						}
					}
				}
			}
		}
		
		conns = genNodes.getConnections();
		alpha = new ArrayList<>();
		for (Connection c : conns) {
			alpha.add(new AtomicInteger(0));
		}
		
		System.out.println(conns.size());
		System.out.println(alpha.size());
	
		

		Random ran = new Random();
		int i = ran.nextInt(nodeButtons.size());
		focussed = nodeButtons.get(i).getNode();
		nodeButtons.get(i).setFocussed(true);
		
	}
	
	@Override
	public void input() {
		
		if (gameRunning) {

			mouseOver = null;
			for (NodeButton n : nodeButtons) {
				n.input();
				
				if (n.isClicked()) {
					if (focussed.isConnectedTo(n.getNode())) {
						System.out.println("Connected to focussed");
						for (int i = conns.size()-1; i >= 0; i--) {
							if (conns.get(i).getState() == Connection.State.Unactive) {
								continue;
							}
							if (conns.get(i).getNode(1).equals(focussed) && conns.get(i).getNode(2).equals(n.getNode()) || conns.get(i).getNode(2).equals(focussed) && conns.get(i).getNode(1).equals(n.getNode())) {
								conns.get(i).setState(Connection.State.Unactive);
							}
						}
						focussed = n.getNode();
						n.setFocussed(true);
					}
					
				}
				if (n.isMouseOver()) {
					mouseOver = n.getNode();
				}
				if (!n.getNode().equals(focussed)) {
					n.setFocussed(false);
				}
				
			}
			
			nodesRemaining = nodeButtons.size();
			
			Random ran = new Random();
			
			if (pTick % 50 == 0) {
				incOp = !incOp;
			}
			
			for (AtomicInteger i : alpha) {
				if (incOp && i.get() < 220) {
					i.set(i.get() + ran.nextInt(6));
				} else if(i.get() > 100){
					i.set(i.get() - ran.nextInt(6));
				}
				
			}
			
			
		} else {
			Random ran = new Random();
			for (AtomicInteger i : alpha) {
				i.set(i.get() + ran.nextInt(8));
			}
		}
		
	}

	@Override
	public void update() {
		if (gameRunning) {
			if (restoreTick == 0) {
				for (Connection connection : conns) {
					if (connection.getState() == State.Unactive) {
						
						double chance = (connection.getNode(1).getTrust() * connection.getNode(2).getTrust())/1000000.0;
						if (chance > Math.random()) {
							System.out.println("Restore");
							connection.setState(State.Active);
							restoreTick = 120;
							break;
						}
					}
				}
			} else {
				restoreTick--;
			}
			
			
			for (int i = nodeButtons.size()-1; i >= 0; i--) {
				if (nodeButtons.get(i).getNode().getNumberOfConnections() == 0) {
					Node n = nodeButtons.get(i).getNode();
					fading.add(nodeButtons.get(i));
					nodeButtons.remove(i);
					nodesDestroyed++;
					nodesRemaining--;
					for (int j = conns.size()-1; j >= 0; j--) {
						if (conns.get(j).getNode(1).equals(n) || conns.get(j).getNode(2).equals(n)) {
							conns.remove(j);
						}
					}
					
					if (nodeButtons.isEmpty()) {
						if (lives > 0) {
							System.out.println("You Win");
						}
						System.exit(0);
					}
					if (n.equals(focussed)) {
						lives--;
						System.out.println("You have " + lives + " remaining.");
						if (lives == 0) {
							System.out.println("You Lose");
							System.exit(0);
						}
						Random ran = new Random();
						int x = ran.nextInt(nodeButtons.size());
						focussed = nodeButtons.get(x).getNode();
						nodeButtons.get(x).setFocussed(true);
					}
					
					
				}
			}
			
			for (NodeButton b : nodeButtons) {
				if (b.getNode().isConnectedTo(focussed)) {
					b.setDanger(true);
				}else {
					b.setDanger(false);
				}
			}
			
			for (NodeButton b : fading) {
				b.fade();
				
			}
			
			for (int i = fading.size()-1; i >= 0; i--) {
				if (fading.get(i).needsRemoving()) {
					fading.remove(i);
				}
			}
			
			pTick++;
			
			if (pTick % 1 == 0) {
				Random ran = new Random();
				packets.add(new Packet(conns.get(ran.nextInt(conns.size()))));
				
			}
			
			for (Packet p : packets) {
				p.update();
			}

		} else {
			gameRunning = true;
			for (AtomicInteger atomicInteger : alpha) {
				if (atomicInteger.get() < 255) {
					gameRunning = false;
					break;
				}
			}
		}
		
		
		
		
		
		
		
		
		

	}

	@Override
	public void render() {
		Renderer.drawTextureRectangle(backgroundID, 0, 0, 800, 700);
		
		for (int i = 0; i < conns.size(); i++){
			Connection c = conns.get(i);
			if (c.getState() == Connection.State.Unactive) {
				continue;
			}
			
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if (c.getNode(1).equals(mouseOver) || c.getNode(2).equals(mouseOver)) {
				 GL11.glColor4f(1.0f, 0.0f, 0.2f, (float)alpha.get(i).get() / 255);
			} else if(c.getNode(1).equals(focussed) || c.getNode(2).equals(focussed)) {
				 GL11.glColor4f(1.0f, 0.0f, 1.0f, (float)alpha.get(i).get() / 255);
			} else {
				GL11.glColor4f(0.0f, 1.0f, 0.2f, Math.min((float)alpha.get(i).get() / 255, 0.65f));
			}
		   
		    GL11.glLineWidth(2.0f);
		    GL11.glBegin(GL11.GL_LINE_STRIP);

		    GL11.glVertex2d(c.getNode(1).getX(), c.getNode(1).getY());
		    GL11.glVertex2d(c.getNode(2).getX(), c.getNode(2).getY());
		    GL11.glEnd();
		    GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
		
		for (int i = packets.size() -1; i >=0; i--) {
			if (packets.get(i).needsRemove()) {
				packets.remove(i);
			} else {
				packets.get(i).render();
			}
		}
		
		
		for (NodeButton nodeButton : nodeButtons) {
			nodeButton.render();
		}
		
		Renderer.drawTextureRectangle(overlayID, 0, 600, 800, 100);
		
		BufferedImage lvs = Renderer.uploadTextAsTexture("Lives Remaining: " + lives, new Font("Verdana", Font.BOLD, 14));
		int lvsId = Renderer.uploadTexture(lvs);
		Renderer.drawTextureRectangle(lvsId, 50, 615, lvs.getWidth(), lvs.getHeight());
		Renderer.deleteTexture(lvsId);
		System.out.println(nodesDestroyed);
		BufferedImage nds = Renderer.uploadTextAsTexture("Nodes Destroyed: " + nodesDestroyed, new Font("Verdana", Font.BOLD, 14));
		int ndsId = Renderer.uploadTexture(nds);
		Renderer.drawTextureRectangle(ndsId, 50, 635, nds.getWidth(), nds.getHeight());
		Renderer.deleteTexture(ndsId);
		
		BufferedImage ndrs = Renderer.uploadTextAsTexture("Nodes Remaining: " + nodesRemaining, new Font("Verdana", Font.BOLD, 14));
		int ndrId = Renderer.uploadTexture(ndrs);
		Renderer.drawTextureRectangle(ndrId, 50, 655, ndrs.getWidth(), ndrs.getHeight());
		Renderer.deleteTexture(ndrId);
		
		for (NodeButton b : fading) {
			b.render();
		}
	}

	@Override
	public void destroy() {


	}

}
