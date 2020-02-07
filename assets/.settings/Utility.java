package game3;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import game3.editor.EditorGame;
//import game3.editor.EditorTile;

public class Utility {
	
	public static Map<String, Thing> map = new HashMap<String, Thing>();
	
	public static Class<?> highestClass(Class<?> c) {
		while(!c.getSimpleName().equals("NPC"))
			c = c.getSuperclass();
		return c;
	}
	
	//for all characters, 5 pixels from top edge and left side, 5 in between
	public static Image[] generateSprites(String url, int rows, int columns) {
		
//		System.out.println("1: " + (System.currentTimeMillis() - EditorGame.lastLoopTime2));
//		System.out.println("2: " + (System.currentTimeMillis() - EditorGame.lastLoopTime2));
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(new Object().getClass().getResourceAsStream(url));		
		} catch (IOException e) {
			sheet = null;
			e.printStackTrace();
		}
		
		int width = sheet.getWidth();
		int height = sheet.getHeight();
		
		if(rows == 1 && columns == 1)		
			return new Image[]{sheet};
		
		Image[] completed = new BufferedImage[rows * columns];
		int sprWidth = (width - 5 * columns - 5) / columns;
		int sprHeight = (height - 5 * rows - 5) / rows;
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				completed[(i * columns) + j] = sheet.getSubimage(j * sprWidth + 5 * j + 5, i * sprHeight + 5 * i + 5, sprWidth, sprHeight);
					
		return completed;
	}
	
	public static Image[] generateTiles(String url, int width, int height, int rows, int columns) {
		BufferedImage sheet;
		try {
			sheet = ImageIO.read(new File(url));		
		} catch (IOException e) {
			sheet = null;
			e.printStackTrace();
		}
		
		if(rows == 1 && columns == 1)		
			return new Image[]{sheet};
		
		Image[] completed = new BufferedImage[rows * columns];
		
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < columns; j++)
				completed[(i * columns) + j] = sheet.getSubimage(j * width + (10 * (j + 1)), i * height + (10 * (i + 1)), width, height);
		
		return completed;
	}
	
//	public static Entity getEntFromCode(String code, double x, double y) {
//		updateMap(x, y);
////		System.out.println(map.get(code));
//		return (Entity)map.get(code);
//		
//	}
	
//	public static Entity getEntFromCode(String code, double x, double y, int stageTo) {
//		updateMap(x, y, "" + stageTo);
//		return (Entity)map.get(code);
//	}
	
	public static Entity getEntFromCode(String code) {
		updateMap(code);
		return (Entity)map.get(code);
	}
	
//	public static Entity getEntFromClasspath(String path, double x, double y) {
//		return getEntFromCode(path.substring(path.lastIndexOf(".") + 1), x, y);
//	}
//	
//	public static Entity getEntFromClasspath(String path, double x, double y, int stageTo) {
//		return getEntFromCode(path.substring(path.lastIndexOf(".") + 1), x, y, stageTo);
//	}
	
	public static Object getLast(ArrayList<?> list) {
		return list.get(list.size() - 1);
	}
	
//	public static void updateMap( y) {
//		map.put("Portal", new Portal(x, y, 64, 64, "" + EditorGame.portalSel.getSelectedIndex()));
//		updateRestOfMapdouble x, double(x, y);	
//	}
//	
//	public static void updateMap(double x, double y, String modString) {
//		map.put("Portal", new Portal(x, y, 64, 64, modString));
//		updateRestOfMap(x, y);	
//	}
	
	public static void updateMap(String s) {
//		map.put("Portal", new Portal(x, y, 64, 64, modString));
		updateRestOfMap(s);	
	}
	
	//Akash's wood tile: 20 x 21 w/ 10 spaces in between and from left, 12 from top
//	public static void updateRestOfMap(double x, double y) {
//		//tiles MUST have the word "Tile" in their key
//		map.put("Giant", new Giant("" + x + " " + y + " 64 64 1"));
//		map.put("Slime", new Slime("" + x + " " + y + " 64 64 1"));
//		map.put("Flower Tile", new Tile("Flower_Field_Tile.png " + x + " " + y + " 100 100"));
////		map.put("Stone Wall Tile", new Tile("StoSne_Wall_Tile", x, y));
//		map.put("Barrel", new Barrel("" + x + " " + y + " 50 50"));
//		map.put("SmallBarrel", new SmallBarrel("" + x + " " + y + " 50 50"));
//		map.put("Proletariat", new Proletariat("" + x + " " + y + " 50 50 1"));
//		
//		Image[] woodArray = generateTiles("src/game3/Images/Wooden_Flooring_TileSheet.png", 80, 84, 3, 3);
//		for(int i = 0; i < woodArray.length; i++)
//			map.put("Wood Tile " + i, new Tile(i + " Wooden_Flooring_TileSheet", woodArray[i], x, y, 80, 84, false));
//		
//		woodArray = generateTiles("src/game3/Images/Wooden_Walls_TileSheet.png", 84, 132, 3, 3);
//		for(int i = 0; i < woodArray.length; i++)
//			map.put("Wood Wall Tile " + i, new Tile(i + " Wooden_Walls_TileSheet", woodArray[i], x, y, 84, 132, true));
//	}
	
	public static void updateRestOfMap(String s) {
		//tiles MUST have the word "Tile" in their key
//		map.put("Giant", new Giant());
//		map.put("Slime", new Slime());
		map.put("Tile", new Tile());
//		map.put("Stone Wall Tile", new Tile("StoSne_Wall_Tile", x, y));
//		map.put("Barrel", new Barrel());
//		map.put("SmallBarrel", new SmallBarrel());
//		map.put("Proletariat", new Proletariat());
		map.put("KGBUnit", new KGBUnit());
		map.put("Guard", new Guard());
		map.put("Soldier", new Soldier());
		
//		Image[] woodArray = generateTiles("src/game3/Images/Wooden_Flooring_TileSheet.png", 80, 84, 3, 3);
//		for(int i = 0; i < woodArray.length; i++)
//			map.put("Wood Tile " + i, new Tile(i + " Wooden_Flooring_TileSheet", woodArray[i], x, y, 80, 84, false));
//		
//		woodArray = generateTiles("src/game3/Images/Wooden_Walls_TileSheet.png", 84, 132, 3, 3);
//		for(int i = 0; i < woodArray.length; i++)
//			map.put("Wood Wall Tile " + i, new Tile(i + " Wooden_Walls_TileSheet", woodArray[i], x, y, 84, 132, true));
	}
	
//	public static Tile getTileFromCode(String code, double x, double y) {
//		updateMap(x, y, "" + EditorGame.portalSel.getSelectedIndex());
//		return (Tile)map.get(code);
//	}
	
	public static void displaytext(String text) {
		Game.speech = text;
		Game.speaking = true;
	}
	
}
