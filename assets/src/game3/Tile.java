package game3;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game3.editor.EditorGame;

public class Tile extends Thing{
	
	//Akash's wood tile: 20 x 21 w/ 10 spaces in between and from left, 12 from top
	
	static Image templateImg;
	public Image img;
	public double x;
	public double y;
	public int width, tangWidth;
	public int height, tangHeight;
	boolean solid;
	public String id;
	
//	public Tile(String id, double x, double y, int width, int height, boolean solid) {
//		this.id = id;
//		this.x = x;
//		this.y = y;
//		this.width = width;
////		this.tangWidth = width;
//		this.height = height;
////		this.tangHeight = height;
//		this.solid = solid;
//		setImage("src/game3/Images/" + this.id + ".png");
//		if(this.width == 0 || this.height == 0) {
//			this.width = img.getWidth(null);
//			this.height = img.getHeight(null);
//		}
//	}
	
//	public Tile(String id, double x, double y) {
//		this(id, x, y, EditorGame.tileSize, EditorGame.tileSize, isTileTypeSolid(id));
//	}
	
	public Tile(String s) {
//		super.setParts(Utility.generateSprites("src/game3/Images/" + , (int)width, (int)height, 1, 1));
		String[] arr = s.split(" ");
		x = Double.parseDouble(arr[0]);
		y = Double.parseDouble(arr[1]);
		width = Integer.parseInt(arr[2]);
		height = Integer.parseInt(arr[3]);
		id = arr[4];
		setImage("/game3/Images/" + id);
		solid = Boolean.valueOf(arr[5]);
	}
	
	public Tile() {
		// TODO Auto-generated constructor stub
	}

	public Tile(String id, Image image, double x, double y, int width, int height, boolean solid) {
		this.id = id;
		if(id.contains("Walls_")) {
			this.tangWidth = 80;
			this.tangHeight = 80;
		}	
		else {
//			y += 30;
			this.tangWidth = width;
			this.tangHeight = height - 4;
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.solid = solid;
		this.img = image;
		if(this.width == 0 || this.height == 0) {
			this.width = img.getWidth(null);
			this.height = img.getHeight(null);
		}
	}
	
//	public Tile(String id, Image image, double x, double y, int width, int height, int tangWidth, int tangHeight, boolean solid) {
//		this.id = id;
//		this.x = x;
//		this.y = y;
//		this.width = width;
//		
//		this.height = height;
//		
//		this.solid = solid;
//		this.img = image;
//		if(this.width == 0 || this.height == 0) {
//			this.width = img.getWidth(null);
//			this.height = img.getHeight(null);
//		}
//	}

	public void setImage(String url) {
		try {
			img = ImageIO.read(new Object().getClass().getResourceAsStream(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Tile " + x + " " + y + " " + width + " " + height + " " + id + " " + solid;
	}
	
	public String toStringNoCoords() {
		return width + " " + height + " " + id + " " + solid;
	}
	
	public String toStringNoType() {
		return x + " " + y + " " + width + " " + height;
	}
	
	public static boolean isTileTypeSolid(String tileID) {
		if(tileID.equals("Flower_Field_Tile"))
			return false;
		else if(tileID.equals("Stone_Wall_Tile"))
			return true;
		return false;
	}
	
	public boolean tileInVicinity(Direction d, int range) { //CHECK THESE CONDITIONS
		for(Tile e : Game.tiles) {
			if(!equals(e) && e.solid)
				if(d.equals(Direction.UP) && (/*limit e to the left of object*/ x < e.x + e.width && /*limit e to the right of object*/ x + width > e.x && /*limit e below object*/ y - height < e.y && /*limit e above object*/ y > e.y - e.height - range))
					return true;
				else if(d.equals(Direction.DOWN) && (/*limit e to the left of object*/ x < e.x + e.width && /*limit e to the right of object*/ x + width > e.x && /*limit e below object*/ y - height < e.y + range && /*limit e above object*/ y > e.y - e.height))
					return true;
				else if(d.equals(Direction.LEFT) && (/*limit e to the left of object*/ x < e.x + e.width + range && /*limit e to the right of object*/ x + width > e.x && /*limit e below object*/ y - height < e.y && /*limit e above object*/ y > e.y - e.height))
					return true;
				else if(d.equals(Direction.RIGHT) && (/*limit e to the left of object*/ x < e.x + e.width && /*limit e to the right of object*/ x + width > e.x - range && /*limit e below object*/ y - height < e.y && /*limit e above object*/ y > e.y - e.height))
					return true;
		}
		return false;
	}

	public String toDefaultString() {
		return "Tile 0 0 120 120 Marble_Floor.png false";
	}
	
}
