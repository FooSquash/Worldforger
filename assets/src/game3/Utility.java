package game3;

import java.awt.Dimension;
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
		
		Image[] completed;
		
		if(url.equals("/game3/Images/Proletariat.png")) {
			int numSpritesTotal = 0;
			for(int i : Proletariat.numSprites) {
				numSpritesTotal += i;
			}
			completed = new BufferedImage[numSpritesTotal];
			System.out.println("completed is " + completed.length);
			System.out.println("boxsize is " + Proletariat.boxSize.size());
			
			//for(int i )
//			System.out.println("numSprites size: " + Proletariat.numSprites.size());
			int counter = 0;
			for(int i = 0; i < Proletariat.numSprites.size(); i++)
				for(int j = 0; j < Proletariat.numSprites.get(i); j++) {
					completed[counter] = sheet.getSubimage(j * Proletariat.boxSize.get(0).width + 1 * j + 1, i * Proletariat.boxSize.get(0).height + 9 * i + 1, Proletariat.boxSize.get(0).width, Proletariat.boxSize.get(0).height);
					counter++;
				}
									
//			for(int i = 0; i < 10; i++)
//				completed[i] = sheet.getSubimage(1 + (22 + 1)*i, 1, 22, 35);
//			System.out.println("the completed: " + completed.length);
		}
		else {
			completed = new BufferedImage[rows * columns];
			int sprWidth = (width - 5 * columns - 5) / columns;
			int sprHeight = (height - 5 * rows - 5) / rows;
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < columns; j++)
					completed[(i * columns) + j] = sheet.getSubimage(j * sprWidth + 5 * j + 5, i * sprHeight + 5 * i + 5, sprWidth, sprHeight);
		}
	
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
	
	public static Entity getEntFromCode(String code) {
		updateMap(code);
		return (Entity)map.get(code);
	}
	
	public static Object getLast(ArrayList<?> list) {
		return list.get(list.size() - 1);
	}
	
	public static void updateMap(String s) {
//		map.put("Portal", new Portal(x, y, 64, 64, modString));
		updateRestOfMap(s);	
	}
	
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
		map.put("Proletariat", new Proletariat());
		map.put("Portal", new Portal());
		
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

	public static Dimension toDimensions(String string) {
		return new Dimension(Integer.parseInt(string.substring(0, string.indexOf(" "))), Integer.parseInt(string.substring(string.indexOf(" ") + 1)));
	}
	
}
