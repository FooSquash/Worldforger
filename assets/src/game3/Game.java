package game3;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import javafx.scene.canvas.GraphicsContext;

public class Game extends Screen implements KeyListener, Runnable, ComponentListener, MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static int GAME_RATE = 7; //the higher, the slower
	
	static int shootScale = 7;
	static int windowX, windowY;
	static JPanel guiPanel, mainPanel;
	static PanelClass gp;
	static ArrayList<Entity> ents = new ArrayList<Entity>(0);
	static ArrayList<Tile> tiles = new ArrayList<Tile>(0);
	static double wScale, hScale;
	static int gameState = 0;
	static double mouseX = 0, mouseY = 0, camX = 0, camY = 0, mouseXRel = 0, mouseYRel = 0;
//	static NPC npcGuy;
	static boolean[] keyPressed = new boolean[1000];
	static Player player;
	static int screenWidth, screenHeight;
	static boolean loading = true, maximized = false;
	static long timer;
	static boolean paused = false;
	static boolean readyToUpdate = true;
	static boolean mouseDown = false;
	static long lastLoopTime = 0, moveFactor = 0, moveFactorTemp = 0;
	static boolean speaking = false;
	static String speech = null;
	static boolean flash;
	static Sound shoot;
	static boolean toRestart = false, showFPS = false;
	static BufferedImage tileImage;
	static Graphics tileGraphics;
	static int toSpawnStage = -1; //-1 indicated that no stage should be spawned
	
	Image offscreen;
	Graphics offg;
	Graphics2D off2;
	
	public Game(int width, int height) {		
		super("Worldforger");		
		addComponentListener(this);
		screenWidth = width;
		screenHeight = height;
		wScale = 1.0;
		hScale = 1.0;
		shoot = new Sound("src/game3/sounds/266977_coolguy244e_gun-shot5.wav");
//		setUndecorated(true);
		setResizable(true);
//		setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		gp = new PanelClass(screenWidth, screenHeight);
		add(gp);
		pack();
		
		addKeyListener(this);
		setFocusable(true);
//		setMinimumSize(new Dimension(959, 539));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
		loading = true;
		Spawner.setSpriteImages();
	}
	
	public void gameLoop() {
		for(int i = 0; i < ents.size(); i++) {
//			System.out.println(ents.size() + "i : " + ents.get(i).classPath);
			ents.get(i).update();
//			System.out.print("f: " + ents.size());
//			System.out.println(ents.get(i).classPath);
			if(ents.get(i).toDelete)
				ents.remove(ents.get(i));
		}
			
		updateCamera();
		updateMousePos();
//		System.out.println(Thread.getAllStackTraces().keySet());
	}
	
	public void updateCamera() {
		camX = player.x;
		camY = player.y;
	}
	
	public void updateMousePos() {
		mouseXRel = (int) (mouseX / wScale + camX - MID_W);
		mouseYRel = -(int) (mouseY / hScale - camY - MID_H);
	}
	
	@Override
	public void run() {
		lastLoopTime = System.currentTimeMillis();
		long lastLoopTimeN;
		while(true) {
//			System.out.println(loading);
			if(toSpawnStage != -1) {
				Spawner.spawnStage(toSpawnStage);
				toSpawnStage = -1;
			}
			moveFactor = System.currentTimeMillis() - lastLoopTime;	
			lastLoopTime = System.currentTimeMillis();
			lastLoopTimeN = System.nanoTime();
//			System.out.println(moveFactor);
			if(!loading && !paused) {
				gameLoop();	
			}
			wScale = gp.getWidth() / (double)SC_WIDTH;
			hScale = gp.getHeight() / (double)SC_HEIGHT;											
//			System.out.println("Stop 0: " + (System.currentTimeMillis() - lastLoopTime));
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice device = env.getDefaultScreenDevice();
		    GraphicsConfiguration config = device.getDefaultConfiguration();
//		    System.out.println("Stop 0: " + (System.currentTimeMillis() - lastLoopTime));
		    offscreen = config.createCompatibleImage(gp.getWidth(), gp.getHeight(), Transparency.TRANSLUCENT);
//		    offscreen = new BufferedImage(gp.getWidth(), gp.getHeight(), BufferedImage.TYPE_INT_ARGB);
//		    System.out.println("Stop 0.5: " + (System.currentTimeMillis() - lastLoopTime));
		    offg = offscreen.getGraphics();
			off2 = (Graphics2D)offg;
//			System.out.println(wScale + ", " + hScale);
//			System.out.println("Stop 1: " + (System.currentTimeMillis() - lastLoopTime));
			off2.scale(wScale, hScale);
//			System.out.println("Stop 2: " + (System.currentTimeMillis() - lastLoopTime));
			off2.clearRect(0, 0, SC_WIDTH, SC_HEIGHT);
//			loading = true;
			if(loading) {
//				loadProg++;
//				System.out.println(loadProg + " " + loading);
				off2.setColor(Color.BLACK);
				off2.fillRect(0, 0, SC_WIDTH, SC_HEIGHT);
				off2.setColor(Color.BLUE);
				off2.setFont(new Font("normal", Font.PLAIN, 40));
				off2.drawString("LOADING", 0, 50);
//				for(int i = 0; i < (loadProg / 500) % 5; i++)
//					g2.drawString(".", i*50 + 200, 50);
			}
			else if(paused) {
				off2.setColor(Color.BLACK);
				off2.fillRect(0, 0, SC_WIDTH, SC_HEIGHT);
				off2.setColor(Color.BLUE);
				off2.setFont(new Font("normal", Font.PLAIN, 40));
				off2.drawString("PAUSED", 0, 50);
			}
			else {
//				System.out.println("Stop 3: " + (System.currentTimeMillis() - lastLoopTime));
//				for(int i = 0; i < tiles.size(); i++) {
////					System.out.println("check 1: " + (System.nanoTime() - lastLoopTimeN) / 10000);
//					if(tiles.get(i).x < camX + SC_WIDTH && tiles.get(i).x > camX - SC_WIDTH && tiles.get(i).y < camY + SC_HEIGHT && tiles.get(i).y > camY - SC_HEIGHT) {
////						System.out.println("check 2: " + (System.nanoTime() - lastLoopTimeN) / 10000);
//						off2.drawImage(tiles.get(i).img, (int)(tiles.get(i).x + MID_W - camX), (int)(-tiles.get(i).y + MID_H + camY), tiles.get(i).width, tiles.get(i).height, null); //THIS IS TAKING A LONG TIME
////						System.out.println("check 3: " + (System.nanoTime() - lastLoopTimeN) / 10000);
//					}
//				}
//				System.out.println(tileImage.getWidth() + ", " + tileImage.getHeight());
//				off2.clipRect(0, 0, gp.getWidth(), gp.getHeight());
				BufferedImage cutTileImage = null;
				if(tileImage.getWidth() >= SC_WIDTH && tileImage.getHeight() >= SC_HEIGHT) {
					
				} else {
					
				}
				//cutTileImage = tileImage.getSubimage((int)(Spawner.lowX + MID_W + camX), (int)(-Spawner.highY + MID_H + camY), SC_WIDTH, SC_HEIGHT);
//				if(tiles.size() > 0)
//					off2.drawImage(cutTileImage, (int)(SC_WIDTH - cutTileImage.getWidth() + (camX - (Spawner.lowX + MID_W))), (int)(-Spawner.highY + MID_H + camY), null);
				if(tiles.size() > 0)
					off2.drawImage(tileImage, (int)(Spawner.lowX + MID_W - camX), (int)(-Spawner.highY + MID_H + camY), null);
				//System.out.println("bg drawn at Y: " + (-Spawner.highY) + " + " + MID_H + " + " + camY);
//				System.out.println("Stop 4: " + (System.currentTimeMillis() - lastLoopTime));
				for(int i = 0; i < ents.size(); i++) {
//					System.out.println(ents.get(i).classPath);
					if(!ents.get(i).invisible && ents.get(i).x < camX + SC_WIDTH && ents.get(i).x > camX - SC_WIDTH && ents.get(i).y < camY + SC_HEIGHT && ents.get(i).y > camY - SC_HEIGHT)
						for(int j = 0; j < ents.get(i).parts.size(); j++) {
	//						Part currPart = ents.get(i).parts.get(j);
							//if(ents.get(i).classPath.contains("Proletariat")) {
//							System.out.println(ents.get(i).classPath + ": " + ents.get(i).parts.get(j).imgs.size() + " i: " + i + " j: " + j + " act: " + ents.get(i).parts.get(j).activeImg);
							//}
							off2.drawImage(ents.get(i).parts.get(j).imgs.get(ents.get(i).parts.get(j).activeImg), (int)(ents.get(i).x + MID_W - camX), (int)(-ents.get(i).y + MID_H + camY), ents.get(i).width, ents.get(i).height, null);	
//							if(ents.get(i) instanceof Proletariat) {
//								off2.drawImage(ents.get(i).parts.get(0).imgs.get(26), 1, 1, 300, 300, null);
//							}
							
						}
				}
//				System.out.println("Stop 5: " + (System.currentTimeMillis() - lastLoopTime));
				off2.setColor(Color.RED);
				off2.setFont(new Font("Sitka Banner", Font.PLAIN, 100));
				if(player.dead) {
					off2.drawString("You've died.", 400, 300);
					off2.drawString("'R' to restart.", 400, 400);
//					off2.drawString("'1'-'9' to select difficulty.", 400, 500);
				}
//				g.drawString("Width Scale: " + wScale + "    Height Scale: " + hScale, (int)(10 * wScale), (int)(50 * hScale));
//				g.drawString("Width: " + panel.getWidth() + "    Height: " + panel.getHeight(), 50, 70);
//				off2.drawString((int)(mouseX / wScale) + ", " + (int)(mouseY / hScale), 0, 10);
//				off2.drawString(mouseXRel + ", " + mouseYRel, 0, 40);
				
				if(showFPS)
					off2.drawString("FPS " + (int)(1.0 / (moveFactor / 1000.0)), 0, 100);
//				off2.drawString("FACTOR " + moveFactor, 0, 500);
				
				if(speaking) {
//					for(int i = 0; i < GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames().length; i++) {
//						off2.setFont(new Font(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[i], Font.PLAIN, 40));
//						off2.drawString(speech + " " + i, 50 + 200 * (i / 30), 30 * (i % 30));
//					}
					off2.setFont(new Font("Sitka Banner", Font.PLAIN, 40));
					off2.drawString(speech, 50, 1000);

				}
//				System.out.println("Stop 6: " + (System.currentTimeMillis() - lastLoopTime));
			}
			
			gp.repaint();
				
			try {
				Thread.sleep(GAME_RATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		keyPressed[e.getKeyCode()] = true;
		gp.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor"));
		if(key == KeyEvent.VK_F) {			
			dispose();
			if(!maximized) {
				gp.fullscreen = true;
				setUndecorated(true);
				setExtendedState(MAXIMIZED_BOTH);	
				maximized = true;
				pack();
				setLocationRelativeTo(null);
			}
			else {
				gp.fullscreen = false;
				setUndecorated(false);
				setExtendedState(NORMAL);
				maximized = false;
				gp.adjustWindowSize();
				pack();
				setLocation(windowX, windowY);
			}
			setVisible(true);
		}
		else if(key == KeyEvent.VK_ESCAPE) {
			paused = !paused;
		}
		else if(key == KeyEvent.VK_Q) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else if(key == KeyEvent.VK_O) {
			if(!speaking)
				Utility.displaytext("Hello");
			else
				speaking = false;
		}
		else if(key == KeyEvent.VK_R) {
			loading = true;
			toSpawnStage = 0;
		}
		else if(key == KeyEvent.VK_EQUALS) {
			Player.invincible = !Player.invincible;
		}
		else if(key == KeyEvent.VK_B) {
			showFPS = !showFPS;
		}
		else if(key == KeyEvent.VK_1) {
			shootScale = 9;
		}
		else if(key == KeyEvent.VK_2) {
			shootScale = 8;
		}
		else if(key == KeyEvent.VK_3) {
			shootScale = 7;
		}
		else if(key == KeyEvent.VK_4) {
			shootScale = 6;
		}
		else if(key == KeyEvent.VK_5) {
			shootScale = 5;
		}
		else if(key == KeyEvent.VK_6) {
			shootScale = 4;
		}
		else if(key == KeyEvent.VK_7) {
			shootScale = 3;
		}
		else if(key == KeyEvent.VK_8) {
			shootScale = 2;
		}
		else if(key == KeyEvent.VK_9) {
			shootScale = 1;
		}
		else if(key == KeyEvent.VK_0) {
			shootScale = 10000;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
	
	
	//CLASS FOR JPANEL
	
	class PanelClass extends JPanel implements MouseMotionListener, ComponentListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private int width, height;
		boolean fullscreen;

		public PanelClass(int width, int height) {
			this.width = width;
			this.height = height;
			this.fullscreen = false;
			
			addComponentListener(this);
			setPreferredSize(new Dimension(this.width, this.height));
			setSize(new Dimension(this.width, this.height));
			addMouseMotionListener(this);
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
			
//			System.out.println(createImage(1920, 1080));
			offscreen = new BufferedImage(1920, 1080, BufferedImage.TYPE_3BYTE_BGR);
//			System.out.println(offscreen);
			offg = offscreen.getGraphics();
			off2 = (Graphics2D)offg;
			setIgnoreRepaint(true);
			setDoubleBuffered(true);
			
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(offscreen, 0, 0, this);		
			
		}
		
		public void adjustWindowSize() {
			setPreferredSize(new Dimension(width, height));
			setSize(new Dimension(width, height));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
			mouseX = e.getX();
			mouseY = e.getY();
			mouseXRel = (int)(mouseX / wScale + camX - MID_W);
			mouseYRel = -(int)(mouseY / hScale - camY - MID_H);
		}

		@Override
		public void componentHidden(ComponentEvent arg0) {
		}

		@Override
		public void componentMoved(ComponentEvent arg0) {
		}

		@Override
		public void componentResized(ComponentEvent arg0) {
			if(!fullscreen) {
				width = getWidth();
				height = getHeight();
			}
		}

		@Override
		public void componentShown(ComponentEvent arg0) {
		}
		
	}








	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		if(!gp.fullscreen) {
			windowX = getX();
			windowY = getY();
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		
	}
	
}
