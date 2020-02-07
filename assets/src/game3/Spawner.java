package game3;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.List;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Spawner {
	
	private static ArrayList<Entity> ents = Game.ents;
	private static ArrayList<Tile> tiles = Game.tiles;
	public static Double lowX, highY, lowY, highX;

	public static void spawnStage(int stage) {
		lowX = null;
		highY = null;
		Game.player = new Player("0 0 64 64");
		Player player = Game.player;
		Scanner sc;
		sc = new Scanner(new Object().getClass().getResourceAsStream("/game3/maps/map" + stage + ".info"));
//		switch (stage) {
//		case 0:
		ents.clear();
		tiles.clear();
//		setSpriteImages();
		parseInfo(sc);
//		Game.player = new Player("0 0 64 64");
		ents.add(player);
//		player.setParts(new Image[][]{Utility.generateSprites("src/game3/Images/Player.png", 2, 4)});
//		player.width = 128;
//		player.height = 128;
//		System.out.println("test");
		
		
		if(stage == 0) {
			player.x = -170;
			player.y = 700;
		}
		else if(stage == 1) {
			player.x = -160;
			player.y = 350;
		}

		if(tiles.size() > 0) {
			Game.tileImage = new BufferedImage((int)(highX - lowX), (int)(highY - lowY), Transparency.TRANSLUCENT);
			Game.tileGraphics = Game.tileImage.getGraphics();
			for(int i = 0; i < tiles.size(); i++) {
				Game.tileGraphics.drawImage(tiles.get(i).img, (int)(tiles.get(i).x - lowX), (int)(-(tiles.get(i).y - highY)), tiles.get(i).width, tiles.get(i).height, null); //THIS IS TAKING A LONG TIME
			}
		}
//		try {
//			Game.tileImage = ImageIO.read(new Object().getClass().getResourceAsStream("/game3/Images/testRedBlock.png"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		Game.lastLoopTime = System.currentTimeMillis();
		Game.loading = false;
			
//			break;
//		case 1:
//			break;
//		}
	}
	
	public static void spawnTileBlock() {
		
	}
	
//	public static void spawnTileLine(int tileWidth, int tileHeight, int lineLength, int x, int y, Direction lineDirection) {
//		for(int i = 0; i < lineLength; i++)
//			if(lineDirection.equals(Direction.RIGHT))
//				tiles.add(new Tile(tileWidth * (x + i), tileHeight * y, tileWidth, tileHeight, true));
//			else if(lineDirection.equals(Direction.LEFT))
//				tiles.add(new Tile(tileWidth * (x - i), tileHeight * y, tileWidth, tileHeight, true));
//			else if(lineDirection.equals(Direction.DOWN))
//				tiles.add(new Tile(tileWidth * x, tileHeight * (y - i), tileWidth, tileHeight, true));
//			else if(lineDirection.equals(Direction.UP))
//				tiles.add(new Tile(tileWidth * x, tileHeight * (y + i), tileWidth, tileHeight, true));
//	}
	
	public static void setSpriteImages() {
//		ArrayList<Integer> numSprites = new ArrayList<Integer>();
//		Proletariat.numRows = 6;
//		Proletariat.numSprites = new int[Proletariat.numRows];
//		Proletariat.boxSize = new Dimension[Proletariat.numRows];
//		Proletariat.pixelsFromLC = new int[Proletariat.numRows][];
//		Proletariat.frameSize = new Dimension[Proletariat.numRows][];
		
		Scanner scanner = new Scanner(new Object().getClass().getResourceAsStream("/game3/TextFiles/Proletariat.txt"));
		scanner.nextLine();
		scanner.nextLine();
		String[] firstLine = scanner.nextLine().split("\\|");
		Proletariat.boxSize.add(Utility.toDimensions(firstLine[1]));
		int lineCounter = 0;
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String ProletariatSpecs[] = line.split("\\|");
			Proletariat.numSprites.add(Integer.parseInt(ProletariatSpecs[0]));
//			System.out.println("numsprites" + );
//			Proletariat.boxSize.add(Utility.toDimensions(ProletariatSpecs[1]));
			for(int i = 1; i < ProletariatSpecs.length; i++) {
//				System.out.println(i);
				if(i % 2 == 1) {
					Proletariat.pixelsR.add(new ArrayList<Integer>());
					Proletariat.pixelsU.add(new ArrayList<Integer>());
					Proletariat.pixelsR.get(lineCounter).add(Integer.parseInt(ProletariatSpecs[i].split(" ")[0]));
					Proletariat.pixelsR.get(lineCounter).add(Integer.parseInt(ProletariatSpecs[i].split(" ")[1]));
				} else {
					Proletariat.frameSize.add(new ArrayList<Dimension>());
					Proletariat.frameSize.get(lineCounter).add(Utility.toDimensions(ProletariatSpecs[i]));
				}
			}
			lineCounter++;
		}
		scanner.close();
	}
	
	public static void parseInfo(Scanner scanner) {
		Scanner scanner2;
		String line;
		while(scanner.hasNextLine()) {		
			line = scanner.nextLine();	
			scanner2 = new Scanner(line);
			String next = scanner2.next();
//			if(next.contains(".")) {
				String className = next;
//				if(className.contains("Portal"))
//					ents.add(Utility.getEntFromClasspath(className, scanner2.nextDouble(), scanner2.nextDouble(), scanner2.nextInt()));
//				else {
//					ents.add(Utility.getEntFromClasspath(className, scanner2.nextDouble(), scanner2.nextDouble(), 0));
					
					if(!next.equals("Tile")) {
						try {
							ents.add((Entity)Class.forName("game3." + next).getConstructor(String.class).newInstance(line.substring(line.indexOf(" ") + 1)));
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException
								| ClassNotFoundException e) {
							e.printStackTrace();
							
						}
					} else {
						try {
							tiles.add((Tile)Class.forName("game3." + next).getConstructor(String.class).newInstance(line.substring(line.indexOf(" ") + 1)));
							if(lowX == null || tiles.get(tiles.size() - 1).x < lowX)
								lowX = tiles.get(tiles.size() - 1).x;
							if(highX == null || tiles.get(tiles.size() - 1).x > highX)
								highX = tiles.get(tiles.size() - 1).x;
							if(lowY == null || tiles.get(tiles.size() - 1).y < lowY)
								lowY = tiles.get(tiles.size() - 1).y;
							if(highY == null || tiles.get(tiles.size() - 1).y > highY)
								highY = tiles.get(tiles.size() - 1).y;
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException
								| ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
						
//				}
//				scanner2.nextInt();
//				scanner2.nextInt();
//			}
//			else
////				if(next.substring(0, 2).equals("[]")) {
////					//format will be "code topx topy bottomx bottomy width height solid?"
////					
////					//x and y of top left point
////					int topX = scanner2.nextInt(); 
////					int topY = scanner2.nextInt();
////					//x and y of bottom right point
////					int bottomX = scanner2.nextInt(); 
////					int bottomY = scanner2.nextInt();
////					//width and height
////					int tileW = scanner2.nextInt();
////					int tileH = scanner2.nextInt();
////					//solid?
////					boolean tileSolid = Boolean.valueOf(scanner2.next());
//////					System.out.println(topX + " " + topY + " " + bottomX + " " + bottomY + " " + tileW + " " + tileH);
////					for(int i = topY; i >= bottomY; i -= tileH)
////						for(int j = topX; j <= bottomX; j += tileW) {
////							tiles.add(new Tile(next.substring(2), j, i, tileW, tileH, tileSolid));
////						}
////				}
////				else {
//					try { //top: 84 x 80, bottom: 84 x 52
//						int sheetIndex = Integer.parseInt(next);
//						String nam = scanner2.next();
//						Image[] array = null;
//						Tile t = null;
//						if(nam.equals("Wooden_Flooring_TileSheet")) {
//							array = Utility.generateTiles("src/game3/Images/" + nam + ".png", 80, 84, 3, 3);
//							t = new Tile(sheetIndex + " " + nam, array[sheetIndex], scanner2.nextDouble(), scanner2.nextDouble(), scanner2.nextInt(), scanner2.nextInt(), scanner2.nextBoolean());
//						}			
//						else {
//							array = Utility.generateTiles("src/game3/Images/" + nam + ".png", 84, 132, 3, 3);
//							t = new Tile(sheetIndex + " " + nam, array[sheetIndex], scanner2.nextDouble(), scanner2.nextDouble(), scanner2.nextInt(), scanner2.nextInt(), scanner2.nextBoolean());
//						}					
//						tiles.add(t);					
//					}
//					catch(NumberFormatException e) {
//						tiles.add(new Tile(next, scanner2.nextDouble(), scanner2.nextDouble(), scanner2.nextInt(), scanner2.nextInt(), Boolean.valueOf(scanner2.next())));
//					}
////				}
		}
	}

}
