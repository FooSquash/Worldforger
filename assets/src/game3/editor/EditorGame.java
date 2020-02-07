package game3.editor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.MouseInputListener;

import game3.NPC;
import game3.Barrel;
import game3.Entity;
import game3.Part;
import game3.Player;
import game3.Screen;
import game3.Spawner;
import game3.Thing;
import game3.Tile;
import game3.Utility;

public class EditorGame extends Screen implements KeyListener, Runnable, ComponentListener, ActionListener, ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static int NUM_OF_STAGES = 20, GAME_RATE = 20, CAM_SPEED = 100;
	
	public static int tileSize = 32;
	static int windowX, windowY;
	static JPanel guiPanel, mainPanel;
	static PanelClass gp;
	static ArrayList<Entity> ents = new ArrayList<Entity>(0);
	static ArrayList<Tile> tiles = new ArrayList<Tile>(0);
	static JComboBox<Object> tileSelector;
	public static JComboBox<?> portalSel;
	static Map<String, Field[]> map1;
	static Class<Thing>[] selectionArray;
	static JComboBox<?> lvlSelector;
	static JTextField specBox;
	static JButton specBoxSubmit;
	static JButton clearButton = new JButton("clear stage"), focusButton = new JButton("Toggle Focus");
	static JSlider tileSizeChanger = new JSlider();
	static double wScale, hScale;
	static int gameState = 0;
	static double mouseX = 0, mouseY = 0, camX = 0, camY = 0, mouseXRel = 0, mouseYRel = 0, roundedX = 0, roundedY = 0;
	static int loadProg = 0;
	static NPC npcGuy;
	static boolean[] keyPressed = new boolean[1000];
	static boolean[] mousePressed = new boolean[5];
	static Player player;
	static int screenWidth, screenHeight, stage = 0, lastStage = 0;
	static boolean loading = true, maximized = false;
	static long timer;
	static long lastLoopTime = 0, moveFactor = 0, moveFactorTemp = 0;
	static boolean paused = false;
	static boolean readyToUpdate = true;
	static Image selectedImage;
	static Tile selectedTile = null;
	static boolean tileSelected = false;
	static int[] selectedDimensions= new int[2];
	public static double lastLoopTime2 = System.currentTimeMillis();
	static String entString = "";
	
	Image offscreen;
	Graphics offg;
	Graphics2D off2;

	public EditorGame(int width, int height) {
		super("Game Title");
//		loading = true;
		addComponentListener(this);
		screenWidth = width;
		screenHeight = height;
		// setUndecorated(true);
		setResizable(true);
		// setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
		// (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		gp = new PanelClass(screenWidth, screenHeight);
		guiPanel = new JPanel();
		String[] lvlString = new String[NUM_OF_STAGES];
		for(int i = 0; i < NUM_OF_STAGES; i++)
			lvlString[i] = "Stage " + i;
		lvlSelector = new JComboBox<Object>(lvlString);
		specBox = new JTextField("Enter stuff");
		specBoxSubmit = new JButton("Submit");
		
		String[] portalString = new String[20];
		for(int i = 0; i < 20; i++)
			portalString[i] = "to Stage " + i;
		portalSel = new JComboBox<String>(portalString);
		Utility.updateMap("");
		List<Thing> selectorArray = new ArrayList<Thing>(Utility.map.values());
		
//		ArrayList<Thing> selectorArray;
//		selectorArray = new ArrayList<Thing>(Utility.map.keySet());
//		new ArrayList
		
//		Arrays.sort(selectorArray);
//		selectorArray.add("Giant");
//		selectorArray.add("Slime");
//		selectorArray.add("Stone_Wall_Tile");
//		selectorArray.add("Flower_Field_Tile");
//		tileSelector = new JComboBox<Object>(selectorArray.toArray());
		tileSelector = new JComboBox<Object>(selectorArray.toArray());
//		System.out.println("here's the array: \n" + selectorArray[0] + "\nend of array");
	
		clearButton.addActionListener(this);
		focusButton.addActionListener(this);
		specBoxSubmit.addActionListener(this);
		tileSizeChanger.addChangeListener(this);
		tileSelector.addActionListener(this);
		
		tileSizeChanger.setMinimum(25);
		tileSizeChanger.setMaximum(200);
		tileSizeChanger.setValue(100);
		
		guiPanel.setLayout(new BoxLayout(guiPanel, BoxLayout.Y_AXIS));
		guiPanel.add(focusButton);
		guiPanel.add(tileSizeChanger);
		guiPanel.add(tileSelector);
		guiPanel.add(lvlSelector);
		guiPanel.add(clearButton);
		guiPanel.add(portalSel);
		guiPanel.add(specBox);
		guiPanel.add(specBoxSubmit);
		guiPanel.add(new JLabel("'E' to place, 'G' to save, 'Q' to quit"));
		
		focusButton.setFocusable(false);
		tileSelector.setFocusable(false);
		tileSelector.setEditable(false);
		lvlSelector.setFocusable(false);
		clearButton.setFocusable(false);
		tileSizeChanger.setFocusable(false);
		portalSel.setFocusable(false);
		guiPanel.setFocusable(false);
		specBoxSubmit.setFocusable(false);
		specBox.setFocusable(false);
		
		mainPanel = new JPanel();
		mainPanel.add(guiPanel);
		mainPanel.add(gp);
		gp.requestFocusInWindow();
		getContentPane().add(mainPanel);
		pack();
		
//		System.out.println("here's the length: " + ((Barrel)(tileSelector.getSelectedItem()) instanceof Entity));
//		System.out.println(selectorArray[0]);
//		System.out.println("here's the selected item: " + tileSelector.getSelectedItem());
		entString = ((Thing)(tileSelector.getSelectedItem())).toDefaultString();
//		entString = " ";
		
//		if(tileSelector.getSelectedItem().toString().contains("Tile")) 
//			entString = Utility.getTileFromCode(tileSelector.getSelectedItem().toString(), roundedX, roundedY).toString();
//		else
//			entString = Utility.getEntFromCode(tileSelector.getSelectedItem().toString(), roundedX, roundedY).toString();
		specBox.setText(entString);
		
		addKeyListener(this);
		setFocusable(true);
		// setMinimumSize(new Dimension(959, 539));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		player = new Player("0 0 64 64");
		Spawner.setSpriteImages();
		EditorSpawner.spawnStage(0);
	}

	public void gameLoop() {
		// for(int i = 0; i < ents.size(); i++)
		// ents.get(i).update();
		//if(loading)
//		gp.requestFocus();
		
//		else {
		updateCamera(); //not causing lag
		
		updateMousePos(); //not causing lag
		
		updateTilePlacement(); //not causing lag
		updateSelectedGUI();
		specBox.setText(entString); //not causing lag
		
//		}
		
		// System.out.println(tileSelector.getSelectedItem());

		wScale = gp.getWidth() / (double) SC_WIDTH;
		hScale = gp.getHeight() / (double) SC_HEIGHT;
	}

	public void updateCamera() {
		if(EditorDirection.UP.isPressed())
			camY += CAM_SPEED;
		if(EditorDirection.DOWN.isPressed())
			camY -= CAM_SPEED;
		if(EditorDirection.LEFT.isPressed())
			camX -= CAM_SPEED;
		if(EditorDirection.RIGHT.isPressed())
			camX += CAM_SPEED;
	}
	
	public void updateSelectedGUI() {
		if(tileSelector.getSelectedItem() instanceof Tile) {
			Tile theTile;
			try {
				theTile = (Tile)Class.forName("game3." + entString.substring(0, entString.indexOf(" "))).getConstructor(String.class).newInstance(entString.substring(entString.indexOf(" ") + 1));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				theTile = null;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setSelectedImage(theTile.img);
			setSelectedTile(theTile);
			tileSelected = true;
			selectedDimensions[0] = theTile.width;
			selectedDimensions[1] = theTile.height;
		}
		else {
			Entity tempEnt;
			try {
				tempEnt = (Entity)Class.forName("game3." + entString.substring(0, entString.indexOf(" "))).getConstructor(String.class).newInstance(entString.substring(entString.indexOf(" ") + 1));
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				tempEnt = null;
				e.printStackTrace();
			}
			setSelectedImage(tempEnt.parts.get(0).imgs.get(0));
			tileSelected = false;
			selectedDimensions[0] = tempEnt.width;
			selectedDimensions[1] = tempEnt.height;
//			System.out.println(selectedDimensions[0] + ", " + selectedDimensions[1]);
//			System.out.println(mouseXRel + ", " + mouseYRel);
		}
						
		lastStage = stage;
		stage = lvlSelector.getSelectedIndex();
		if(lastStage != stage) {
			tiles.clear();
			ents.clear();
			System.out.println("spawn stage");
			EditorSpawner.spawnStage(stage);
		}
			
	}
	
	public void updateMousePos() {
		mouseXRel = (int) (mouseX / wScale + camX - MID_W);
		mouseYRel = -(int) (mouseY / hScale - camY - MID_H);

		String tempPosString = "";
		String oldPos[] = entString.split(" ", 4);
		if(tileSelected) {
			roundedX = mouseXRel - (selectedTile.width - Math.floorMod((int)(-mouseXRel), selectedTile.width));		
			roundedY = mouseYRel + Math.floorMod((int)(-mouseYRel), selectedTile.height);
			tempPosString = " " + roundedX + " " + roundedY + " ";
		} else {
			tempPosString = " " + mouseXRel + " " + mouseYRel + " ";
		}
		entString = oldPos[0] + tempPosString + oldPos[3];
	}
	
	public void updateTilePlacement() {
		if(mousePressed[MouseEvent.BUTTON1]) {
			if(tileSelector.getSelectedItem() instanceof Tile) {	
				boolean flag = false, flag2 = false;
				Tile flagTile = null;
//				tiles.add(Utility.getTileFromCode(tileSelector.getSelectedItem().toString(), roundedX, roundedY));	
				tiles.add(new Tile(entString.substring(entString.indexOf(" ") + 1)));
				Tile currTile = tiles.get(tiles.size() - 1);
				
				for(Tile tile : tiles)
					if(!tile.equals(currTile))
						if(currTile.toString().equals(tile.toString()))
							flag = true;
						else if(currTile.toStringNoType().equals(tile.toStringNoType())) {
							flag2 = true;
							flagTile = tile;
						}							
				
				if(flag) {
					tiles.remove(currTile);
				}
					
				if(flag2) {
					tiles.remove(flagTile);
				}			
			}
			else {
				boolean flag = false;
//				if(Utility.getEntFromCode(tileSelector.getSelectedItem().toString(), 0, 0) instanceof Portal)
//					ents.add(Utility.getEntFromCode(tileSelector.getSelectedItem().toString(), mouseXRel - selectedDimensions[0] / 2, mouseYRel + selectedDimensions[1] / 2));
//				ents.add(Utility.getEntFromCode(tileSelector.getSelectedItem().toString(), mouseXRel - selectedDimensions[0] / 2, mouseYRel + selectedDimensions[1] / 2));
				try {
					ents.add((Entity)Class.forName("game3." + entString.substring(0, entString.indexOf(" "))).getConstructor(String.class).newInstance(entString.substring(entString.indexOf(" ") + 1))); //PROBLEM HERE: PLACING IN WRONG SPOT
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * try {
						ents.add((Entity)Class.forName("game3." + next).getConstructor(String.class).newInstance(line.substring(line.indexOf(" ") + 1)));
					System.out.println("game3." + next);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException
							| ClassNotFoundException e) {
						e.printStackTrace();
						
					}
				 */
				Entity currEnt = ents.get(ents.size() - 1);
				for(Entity ent : ents)
					if(!ent.equals(currEnt))
						if(currEnt.toString().equals(ent.toString()))
							flag = true;							
				if(flag)
					ents.remove(currEnt);
			}
			
		}
		else if(mousePressed[MouseEvent.BUTTON3]) {
			System.out.println("3 pressed");
			ArrayList<Tile> toRemove = new ArrayList<Tile>();
			for(Tile tile : tiles)
				if(mouseXRel < tile.x + tile.width && mouseXRel > tile.x && mouseYRel < tile.y && mouseYRel > tile.y - tile.height)
					toRemove.add(tile);
			for(Tile tile : toRemove)
				tiles.remove(tile);
			
			ArrayList<Entity> toRemove2 = new ArrayList<Entity>();
			for(Entity ent : ents)
				if(mouseXRel <= ent.x + ent.width && mouseXRel >= ent.x && mouseYRel <= ent.y && mouseYRel >= ent.y - ent.height)
					toRemove2.add(ent);
			for(Entity ent : toRemove2)
				ents.remove(ent);
		}

	}
	
	public static void setSelectedImage(Image img) {
		selectedImage = img;
	}
	
	public static void setSelectedTile(Tile t) {
		selectedTile = t;
	}

	@Override
	public void run() {
		lastLoopTime = System.currentTimeMillis();
		while (true) {			
			moveFactor = System.currentTimeMillis() - lastLoopTime;	
			lastLoopTime2 = System.currentTimeMillis();
			
//			System.out.println(moveFactor);
			lastLoopTime = System.currentTimeMillis();
			if(!loading && !paused) {
				gameLoop();	
			}
			wScale = gp.getWidth() / (double)SC_WIDTH;
			hScale = gp.getHeight() / (double)SC_HEIGHT;	
			
			offscreen = new BufferedImage(gp.getWidth(), gp.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			offg = offscreen.getGraphics();
			off2 = (Graphics2D)offg;
			off2.scale(wScale, hScale);
			off2.clearRect(0, 0, SC_WIDTH, SC_HEIGHT);
			if (loading) {
				loadProg++;
				off2.setColor(Color.BLACK);
				off2.fillRect(0, 0, getWidth(), getHeight());
				off2.setColor(Color.BLUE);
				off2.setFont(new Font("normal", Font.PLAIN, 40));
				off2.drawString("LOADING", 0, 50);
				for (int i = 0; i < (loadProg / 500) % 5; i++)
					off2.drawString(".", i * 50 + 200, 50);
			} else if (paused) {
				off2.setColor(Color.BLACK);
				off2.fillRect(0, 0, getWidth(), getHeight());
				off2.setColor(Color.BLUE);
				off2.setFont(new Font("normal", Font.PLAIN, 40));
				off2.drawString("PAUSED", 0, 50);
			} else {
				timer = System.nanoTime();
				off2.setColor(Color.BLACK);
				off2.fillRect(0, 0, getWidth(), getHeight());

				for (int i = 0; i < tiles.size(); i++)
					if (tiles.get(i).x < camX + SC_WIDTH && tiles.get(i).x > camX - SC_WIDTH
							&& tiles.get(i).y < camY + SC_HEIGHT && tiles.get(i).y > camY - SC_HEIGHT) {
						off2.drawImage(tiles.get(i).img, (int)(tiles.get(i).x + MID_W - camX), (int)(-tiles.get(i).y + MID_H + camY),
								tiles.get(i).width, tiles.get(i).height, null);
					}
				for (int i = 0; i < ents.size(); i++)
					if (ents.get(i).x < camX + SC_WIDTH && ents.get(i).x > camX - SC_WIDTH
							&& ents.get(i).y < camY + SC_HEIGHT && ents.get(i).y > camY - SC_HEIGHT)
						for (int j = 0; j < ents.get(i).parts.size(); j++) {
							Part currPart = ents.get(i).parts.get(j);
							off2.drawImage(currPart.imgs.get(currPart.activeImg), (int)(ents.get(i).x + MID_W - camX),
								(int)(-ents.get(i).y + MID_H + camY), ents.get(i).width, ents.get(i).height, null);
						}
				
				if(tileSelected)
					off2.drawImage(selectedImage, (int)(roundedX + MID_W - camX), (int)(-roundedY + MID_H + camY), selectedDimensions[0], selectedDimensions[1], null);
				else
					off2.drawImage(selectedImage, (int)((mouseXRel - selectedDimensions[0] / 2) + MID_W - camX), (int)(-(mouseYRel + selectedDimensions[1] / 2) + MID_H + camY), selectedDimensions[0], selectedDimensions[1], null);
							
				off2.setColor(Color.WHITE);
				off2.drawString((int) (mouseX / wScale) + ", " + (int) (mouseY / hScale), 0, 10);
				off2.drawString(mouseXRel + ", " + mouseYRel, 0, 40);
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
		if (key == KeyEvent.VK_F) {
			dispose();
			if (!maximized) {
				gp.fullscreen = true;
				setUndecorated(true);
				setExtendedState(MAXIMIZED_BOTH);
				maximized = true;
				pack();
				setLocationRelativeTo(null);
			} else {
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
		if (key == KeyEvent.VK_ESCAPE) {
			paused = !paused;
		} else if (key == KeyEvent.VK_Q) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} else if (key == KeyEvent.VK_E) { //add to tile array
			
		}
		else if(key == KeyEvent.VK_Z) {
			tileSelector.setFocusable(false);
			guiPanel.setFocusable(false);
		}
		else if(key == KeyEvent.VK_G) { //write to file
			PrintStream ps;
			try {
				ps = new PrintStream(new File("src/game3/maps/map" + stage + ".info"));
			} catch (FileNotFoundException ex) {
				ps = null;
				ex.printStackTrace();
			}
			
			//algorithm for organizing tiles
			if(tiles.size() > 0) {
				//sort array by x value
				for(int k = 0; k < tiles.size(); k++) 
					for(int j = 0; j < k; j++) {
						if(tiles.get(k).x < tiles.get(j).x) {
							Tile temp = tiles.get(k);
							for(int f = k; f > j; f--)
								tiles.set(f, tiles.get(f - 1));
							tiles.set(j, temp);
						} /*else if(tiles.get(k).x == tiles.get(j).x) {
							
							for(int k = 0; k < tiles.size(); k++) 
								for(int j = 0; j < k; j++) {
									if(tiles.get(k).x < tiles.get(j).x) {
										Tile temp = tiles.get(k);
										for(int f = k; f > j; f--)
											tiles.set(f, tiles.get(f - 1));
										tiles.set(j, temp);
									} else if(tiles.get(k).x == tiles.get(j).x) {
										
									}
								}
							
						}*/
					}
				/*double initY = tiles.get(0).y;
				for(int i = 1; i < tiles.size(); i++) {
					if(tiles.get(i-1).x == tiles.get(i).x && tiles.get(i-1).toStringNoCoords() == tiles.get(i).toStringNoCoords()) {
						
					} else {
						ps.println("TileBlock " + );
						initY = tiles.get(i).y;
					}
					ps.println(tiles.get(i).toString());
				}*/
				//print to file
				for(int i = 0; i < tiles.size(); i++) {	
					ps.println(tiles.get(i).toString());
					
//					ps.println(tiles.get(i).id + " " + tiles.get(i).y + " " + i);
				}
			}
				
			
			for(Entity ent : ents) {
				ps.println(ent.toString());
			}
			ps.close();
			JOptionPane pane = new JOptionPane();
			pane.setMessage("Stage " + stage + " saved!");
			pane.createDialog("save complete").setVisible(true);
			
		}
		else if(key == KeyEvent.VK_R) {
			
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
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(clearButton)) {
			tiles.clear();
			ents.clear();
		}
		else if(arg0.getSource().equals(specBoxSubmit)) {
			entString = (String)(JOptionPane.showInputDialog(this, "Enter string", "String changer", JOptionPane.PLAIN_MESSAGE, null, null, entString));
			specBox.setText(entString);
		}
		else if(arg0.getSource().equals(tileSelector)) {
			entString = ((Thing)(tileSelector.getSelectedItem())).toDefaultString();
//			if(tileSelector.getSelectedItem().toString().contains("Tile")) 
//				entString = Utility.getTileFromCode(tileSelector.getSelectedItem().toString(), roundedX, roundedY).toString();
//			else
//				entString = Utility.getEntFromCode(tileSelector.getSelectedItem().toString(), roundedX, roundedY).toString();
			specBox.setText(entString);
			updateSelectedGUI();
//			System.out.println("gui updated");
		}
//		else if(arg0.getSource().equals(focusButton)) {
//			tileSelector.setFocusable(!tileSelector.isFocusable());
//		}
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		if(arg0.getSource().equals(tileSizeChanger)) {
			tileSize = tileSizeChanger.getValue();
		}
//		else if(arg0.getSource().equals(specBoxSubmit)) {
//			gp.requestFocus();
//			System.out.println("request");
//		}
	}

	
	
	
	
	
	
	
	// CLASS FOR JPANEL

	class PanelClass extends JPanel implements ComponentListener, MouseInputListener {

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

			setFocusable(true);
			addComponentListener(this);
			setPreferredSize(new Dimension(this.width, this.height));
			setSize(new Dimension(this.width, this.height));
			addMouseMotionListener(this);
			addMouseListener(this);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void paintComponent(Graphics g) {
			g.drawImage(offscreen, 0, 0, this);	
		}

		public void adjustWindowSize() {
			setPreferredSize(new Dimension(width, height));
			setSize(new Dimension(width, height));
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseMoved(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		@Override
		public void componentHidden(ComponentEvent arg0) {
		}

		@Override
		public void componentMoved(ComponentEvent arg0) {
		}

		@Override
		public void componentResized(ComponentEvent arg0) {
			if (!fullscreen) {
				width = getWidth();
				height = getHeight();
			}
		}

		@Override
		public void componentShown(ComponentEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
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
			mousePressed[e.getButton()] = true;	
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mousePressed[e.getButton()] = false;
		}

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		if (!gp.fullscreen) {
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

}
