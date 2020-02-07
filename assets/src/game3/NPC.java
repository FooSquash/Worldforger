package game3;

import game3.Direction;
import game3.Game;
import game3.Entity;

public class NPC extends Character{
	
//	private static final String[] firstNamesM = {"Dragonblood", "Jerry", "Abe", "Charlie", "Germain", "Anton", "Samuel", "Jakard", "Lincoln", "Japor", "Grex", "Lentus"};
//	private static final String[] lastNames = {"Farsight", "Lucem", "Quantus", "Heath", "Pickett", "Ross", "Layden", "Pondum", "Smite", "Ranken", "Sorbe", "Anite"};
	
	String firstName, lastName, wealthString;
	public int age, wealth, directionTimer, state, direction;
	boolean nameRevealed;
//	Profession prof;
	
	public NPC() {
		
	}
	
	public NPC(String classpath, String s) {
//		super(".NPC" + classPath, x, y, width, height);
		super(classpath, s);
		String[] arr = s.split(" ");
		direction = (int)(Math.random() * 4);
		directionTimer = 150;
		
		this.state = Integer.parseInt(arr[4]);
	}
	
	@Override
	public void update() {
	}
	
	public boolean directionTimerPassed(int modValue) {
		directionTimer = (directionTimer + 1) % modValue;
		return directionTimer == 0;
	}
	
	public void updateMoving(int speed) {
		double moveFactor = speed / 10.0 * Game.moveFactor + 2;
		if(direction == 0) {
			if(tileInDirection(Direction.RIGHT, (int)moveFactor) || isEntInDirection(Direction.RIGHT, (int)moveFactor)) {
				direction = 1;
			}
			else {
				costumeFacing = 1;
				move(Direction.RIGHT, speed);
			}
		}
		else if(direction == 1) {
			if(tileInDirection(Direction.LEFT, (int)moveFactor) || isEntInDirection(Direction.LEFT, (int)moveFactor)) {
				direction = 0;
			}
			else {
				costumeFacing = 0;
				move(Direction.LEFT, speed);
			}
		}
		else if(direction == 2) {
			if(tileInDirection(Direction.DOWN, (int)moveFactor) || isEntInDirection(Direction.DOWN, (int)moveFactor)) {
				direction = 3;
			}
			else {
				costumeFacing = 2;
				move(Direction.DOWN, speed);
			}
		}
		else if(direction == 3) {
			if(tileInDirection(Direction.UP, (int)moveFactor) || isEntInDirection(Direction.UP, (int)moveFactor)) {
				direction = 2;
			}
			else {
				costumeFacing = 3;
				move(Direction.UP, speed);
			}
		}
	}
//	
	public void updateCostume() {
		if(costumeTimerPassed(50))
			costumeNum = (costumeNum + 1) % 4;
	}
//	
	public String toDefaultString() {
		return "NPC 0 0 100 100 1";
	}
//	
}
