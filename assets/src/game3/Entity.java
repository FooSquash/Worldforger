package game3;

import java.awt.Dimension;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;


public class Entity extends Thing{

	public double x;
	public double y;
	public int width;
	public int height;
	public boolean invisible = false;
	int id;
	public ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
	public ArrayList<Part> parts = new ArrayList<Part>();
	public Image templateImg;
	public boolean toDelete = false;
	String classPath;
	
	//this is for the frames
	public static int numRows;
	public static ArrayList<Integer> numSprites = new ArrayList<Integer>(0);
	public static ArrayList<Dimension> boxSize = new ArrayList<Dimension>(0);
	public static ArrayList<ArrayList<Integer>> pixelsR = new ArrayList<ArrayList<Integer>>(0);
	public static ArrayList<ArrayList<Integer>> pixelsU = new ArrayList<ArrayList<Integer>>(0);
	public static ArrayList<ArrayList<Dimension>> frameSize = new ArrayList<ArrayList<Dimension>>(0);
//	public static Dimension[][] frameSize;
//	public static int[] numSprites;
//	public static Dimension[] boxSize;
//	public static int[][] pixelsFromLC;
//	public static Dimension[][] frameSize;
	//end
	
	public Entity() {
		
	}
	
	public Entity(String classPath, double x, double y, int width, int height) {
		this.classPath = "Entity" + classPath;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		hitboxes.add(new Hitbox(x, y, width, height));
	}
	
	public Entity(String classPath, String str) {
		String[] arr = str.split(" ");
		x = Double.parseDouble(arr[0]);
		y = Double.parseDouble(arr[1]);
		width = Integer.parseInt(arr[2]);
		height = Integer.parseInt(arr[3]);
		this.classPath = classPath;
	}
	
	public void update() {
		
	}
	
	public void destroy() {
		toDelete = true;
	}
	
	public String getClassPath() {
		return classPath;
	}
	
	public String getClassName() {
		return getClass().getSimpleName();
	}
	
	/**
	 * Sets parts of an entity
	 * @param pts the rows in the array represent the different parts, while the individual cells hold different images representing different frames
	 */
	public void setParts(Image[][] pts) {
		for(int i = 0; i < pts.length; i++) {
			parts.add(new Part());
			for(int j = 0; j < pts[i].length; j++)
				Part.getLast(parts).imgs.add(pts[i][j]);
		}
		if(width == 0 || height == 0) {
			width = parts.get(0).imgs.get(0).getWidth(null);
			height = parts.get(0).imgs.get(0).getHeight(null);
		}
		System.out.println("entity causing exception: " + classPath + " parts length: " + parts.get(0).imgs.size());
		templateImg = parts.get(0).imgs.get(0);
	}
	
	/**
	 * Sets parts of an entity (where there is only 1 part necessary)
	 * @param frames an array of the different frames of the entity
	 */
	public void setParts(Image[] frames) {
		setParts(new Image[][]{frames});
	}
	
	/**
	 * Sets image of an entity (where there is only 1 image necessary)
	 * @param img the image to be assigned to the entity
	 */
	public void setParts(Image img) {
		setParts(new Image[]{img});
	}

	public void setActiveImage(int index) {
		for(int i = 0; i < parts.size(); i++)
			parts.get(i).activeImg = index;
	}
	
	public boolean isTouching(Entity e) {
		for(Hitbox currHit : hitboxes)
			for(Hitbox otherHit : e.hitboxes)
				if(currHit.x <= otherHit.x + otherHit.width && currHit.x + currHit.width >= otherHit.x && currHit.y - currHit.height <= otherHit.y && currHit.y >= otherHit.y - otherHit.height)
					return true;
		return false;
	}
	
	public boolean isTouchingFixed(Entity e) {
		if(x <= e.x + e.width + 10&& x + width >= e.x - 10 && y - height <= e.y + 10&& y >= e.y - e.height - 10)
			return true;
		return false;
	}
	
	public ArrayList<Entity> entsTouching() {
		ArrayList<Entity> entsArr = new ArrayList<Entity>();
		for(Entity e : Game.ents) {
			if(isTouchingFixed(e)) {
				entsArr.add(e);
			}
		}
		return entsArr;
	}
	
	//Instance is the entity being touched, parameter is entity doing the touching. Returns direction the touch is coming from.
	public Direction touching(Entity e) {
		if(!isTouching(e))
			return null;
		double UDTouch, LRTouch; //store how much each side is being touched
		
		double lowX = x - e.width, highX = x + width, leftPosOfE = e.x;
		double adjustedVal = -lowX;
		highX += adjustedVal;
		leftPosOfE += adjustedVal;
		LRTouch = (leftPosOfE - highX / 2) / (highX / 2) * 100; //-100 through 0: left is touched more. 0 through 100: right is touched more.
		
		double lowY = y - height, highY = y + e.height, topPosOfE = e.y;
		adjustedVal = -lowY;
		highY += adjustedVal;
		topPosOfE += adjustedVal;
		UDTouch = (topPosOfE - highY / 2) / (highY / 2) * 100; //-100 through 0: bottom is touched more. 0 through 100: top is touched more.
		
		
		return null;
	}
	
	public void updateHitboxes() {
		for(Hitbox h : hitboxes) {
			h.x = x;
			h.y = y;
		}
	}
	
	public Entity portalInVicinity(int range) {
		if(entInDirection(Direction.LEFT, range) instanceof Portal)
			return entInDirection(Direction.LEFT, range);
		else if(entInDirection(Direction.RIGHT, range) instanceof Portal)
			return entInDirection(Direction.RIGHT, range);
		else if(entInDirection(Direction.UP, range) instanceof Portal)
			return entInDirection(Direction.UP, range);
		else if(entInDirection(Direction.DOWN, range) instanceof Portal)
			return entInDirection(Direction.DOWN, range);
		else
			return null;
	}
	
	public boolean isEntInDirection(Direction d, int range) {
		if(entInDirection(d, range) == null)
			return false;
		return true;
	}
	
	public Entity entInDirection(Direction d, int range) {
		for(Entity e : Game.ents) {
			if(!equals(e) && !(e instanceof Bullet))
				if(d.equals(Direction.UP) && (/*limit e to the left of object*/ x < e.x + e.width - 30 && /*limit e to the right of object*/ x + width > e.x + 30 && /*limit e below object*/ y - height < e.y - 30 && /*limit e above object*/ y > e.y - e.height - range))
					return e;
				else if(d.equals(Direction.DOWN) && (/*limit e to the left of object*/ x < e.x + e.width - 30 && /*limit e to the right of object*/ x + width > e.x + 30 && /*limit e below object*/ y - height < e.y + range && /*limit e above object*/ y > e.y - e.height + 30))
					return e;
				else if(d.equals(Direction.LEFT) && (/*limit e to the left of object*/ x < e.x + e.width + range && /*limit e to the right of object*/ x + width > e.x + 30 && /*limit e below object*/ y - height < e.y - 30 && /*limit e above object*/ y > e.y - e.height + 30))
					return e;
				else if(d.equals(Direction.RIGHT) && (/*limit e to the left of object*/ x < e.x + e.width - 30 && /*limit e to the right of object*/ x + width > e.x - range && /*limit e below object*/ y - height < e.y - 30 && /*limit e above object*/ y > e.y - e.height + 30))
					return e;
		}
		return null;
	}
	
	public ArrayList<Entity> entsInDirection(Direction d, int range) {
		ArrayList<Entity> entArr = new ArrayList<Entity>();
		for(Entity e : Game.ents) {
			if(!equals(e) && !(e instanceof Bullet))
				if(d.equals(Direction.UP) && (/*limit e to the left of object*/ x < e.x + e.width - 30 && /*limit e to the right of object*/ x + width > e.x + 30 && /*limit e below object*/ y - height < e.y - 30 && /*limit e above object*/ y > e.y - e.height - range))
					entArr.add(e);
				else if(d.equals(Direction.DOWN) && (/*limit e to the left of object*/ x < e.x + e.width - 30 && /*limit e to the right of object*/ x + width > e.x + 30 && /*limit e below object*/ y - height < e.y + range && /*limit e above object*/ y > e.y - e.height + 30))
					entArr.add(e);
				else if(d.equals(Direction.LEFT) && (/*limit e to the left of object*/ x < e.x + e.width + range && /*limit e to the right of object*/ x + width > e.x + 30 && /*limit e below object*/ y - height < e.y - 30 && /*limit e above object*/ y > e.y - e.height + 30))
					entArr.add(e);
				else if(d.equals(Direction.RIGHT) && (/*limit e to the left of object*/ x < e.x + e.width - 30 && /*limit e to the right of object*/ x + width > e.x - range && /*limit e below object*/ y - height < e.y - 30 && /*limit e above object*/ y > e.y - e.height + 30))
					entArr.add(e);
		}
		return entArr;
	}
	
	public boolean tileInDirection(Direction d, int range) { //CHECK THESE CONDITIONS
		for(Tile e : Game.tiles) {
			if(e.solid) {
				if(d.equals(Direction.UP) && (/*limit e to the left of object*/ x < e.x + e.width && /*limit e to the right of object*/ x + width > e.x && /*limit e below object*/ y - height < e.y && /*limit e above object*/ y > e.y - e.height - range))
					return true;
				else if(d.equals(Direction.DOWN) && (/*limit e to the left of object*/ x < e.x + e.width && /*limit e to the right of object*/ x + width > e.x && /*limit e below object*/ y - height < e.y + range && /*limit e above object*/ y > e.y - e.height))
					return true;
				else if(d.equals(Direction.LEFT) && (/*limit e to the left of object*/ x < e.x + e.width + range && /*limit e to the right of object*/ x + width > e.x && /*limit e below object*/ y - height < e.y && /*limit e above object*/ y > e.y - e.height))
					return true;
				else if(d.equals(Direction.RIGHT) && (/*limit e to the left of object*/ x < e.x + e.width && /*limit e to the right of object*/ x + width > e.x - range && /*limit e below object*/ y - height < e.y && /*limit e above object*/ y > e.y - e.height))
					return true;
			}
				
		}
		return false;
	}
	
	//for editor
	
	//for editor
	@Override
	public String toString() {
		return classPath + " " + x + " " + y + " " + width + " " + height;
	}
	
	public String toStringNoType() {
		return x + " " + y + " " + width + " " + height;
	}
	
}
