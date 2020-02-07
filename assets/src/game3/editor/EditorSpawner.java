package game3.editor;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

import game3.Entity;
import game3.Proletariat;
import game3.Tile;
import game3.Utility;


public class EditorSpawner {
	
	private static ArrayList<Entity> ents = EditorGame.ents;
	private static ArrayList<Tile> tiles = EditorGame.tiles;

	public static void spawnStage(int stage) {
		
//		Player player = Game.player;
		
		System.out.println("check 2");
		Scanner sc;
		try {
			sc = new Scanner(new File("src/game3/maps/map" + stage + ".info"));
		} catch (FileNotFoundException e) {
			File file = new File("src/game3/maps/map" + stage + ".info");
			try {
				PrintStream out = new PrintStream(file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				sc = new Scanner(file);
			} catch (FileNotFoundException e1) {
				sc = null;
				e1.printStackTrace();
			}
		}
		switch (stage) {
		case 0:	
			parseInfo(sc);
			
//			ents.add(player);
//			player.setParts(new Image[][]{Utility.generateSprites("src/game3/Images/Player.png", 64, 64, 2, 4)});
//			player.width = 128;
//			player.height = 128;
			
			break;
		case 1:
			parseInfo(sc);
			break;
		}
		
		EditorGame.loading = false;
		
	}
	
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
		int lineCounter = 0;
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String ProletariatSpecs[] = line.split("\\|");
			Proletariat.numSprites.add(Integer.parseInt(ProletariatSpecs[0]));
			Proletariat.boxSize.add(Utility.toDimensions(ProletariatSpecs[1]));
			for(int i = 2; i < ProletariatSpecs.length; i++) {
				System.out.println(i);
				if(i % 2 == 0) {
					Proletariat.pixelsR.add(new ArrayList<Integer>());
					Proletariat.pixelsR.get(lineCounter).add(Integer.parseInt(ProletariatSpecs[i]));
				} else {
					Proletariat.frameSize.add(new ArrayList<Dimension>());
					Proletariat.frameSize.get(lineCounter).add(Utility.toDimensions(ProletariatSpecs[i]));
				}
			}
			lineCounter++;
		}
		scanner.close();
	}
	
	public static void spawnTileBlock() {
		
	}
	
	public static void parseInfo(Scanner scanner) {
		Scanner scanner2;
		String line;
		System.out.println("check 1");
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
//					System.out.println("game3." + next);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException
							| ClassNotFoundException e) {
						e.printStackTrace();
						
					}
				} else {
					try {
						tiles.add((Tile)Class.forName("game3." + next).getConstructor(String.class).newInstance(line.substring(line.indexOf(" ") + 1)));
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException | SecurityException
							| ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
						
//				}
		}
	}

}
