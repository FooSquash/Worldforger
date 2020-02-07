package game3;

import java.awt.Dimension;
import java.awt.Image;

import game3.NPC;


public class Proletariat extends NPC{

//	static int numRows = 6;
	
//	public Giant(double x, double y) {
//		this(x, y, 1);
//	}
	
	private int imgRowNum;
	
	public boolean attacking;
	public int attackTimer;
	
	public Proletariat(String s) {
		super("Proletariat", s);
		
		super.setParts(Utility.generateSprites("/game3/Images/Proletariat.png", 1, 2));
		String[] arr = s.split(" ");
		imgRowNum = 0;
		attacking = false;
		width = parts.get(0).imgs.get(0).getWidth(null) * 4;
		height = parts.get(0).imgs.get(0).getHeight(null) * 4;
	}
	
	public Proletariat() {
		
	}
	
	@Override
	public void update() {
//		System.pout.println(costumeNum + costumeFacing);
		if(state == 1) {
			if(updateAttack()) {
				if(costumeNum == 2) {
					updateCostume(375);
				}
				else if(costumeNum == 5) {
					updateCostume(125);
				}
				else {
					updateCostume(50);
				}
				
				//50, 50, 750, 50, 50, 250
			}
			else {
				if(costumeNum == 0) {
					if(x + width < Game.player.x) {
						changeDirection(Direction.RIGHT.getValue());
					} else if(x > Game.player.x + Game.player.width) {
						changeDirection(Direction.LEFT.getValue());
					} else if(y < Game.player.y - Game.player.height) {
						changeDirection(Direction.UP.getValue());
					} else if(y - height > Game.player.y) {
						changeDirection(Direction.DOWN.getValue());
					}
				}
				
				
				updateMoving(6);
				updateCostume(50);
			}
			
			setActiveImage(costumeNum + costumeFacing);
		}
		else if(state == 2) {
		}
		updateHitboxes();
		for(Entity e : Game.ents) {
//			System.out.println(isTouchingFixed(e));
			if(isTouchingFixed(e) && e instanceof PlayerBullet) {
				((PlayerBullet)e).toDelete = true;
				destroy();
			}
		}
	}
	
	public boolean updateAttack() {
//		System.out.println(costumeNum + costumeFacing);
		int range = 2;
//		System.out.println(attacking);
		if(attacking) {
//			if(attackTimerPassed(400)) {
//				System.out.println("numsprites " + Proletariat.numSprites.get(imgRowNum));
//				costumeNum = (costumeNum + 1) % Proletariat.numSprites.get(imgRowNum);
				if(costumeNum == 5) {
					attacking = false;
					return true;
				}
//			}
			return true;
		}
		else if(entsInDirection(Direction.RIGHT, range).contains(Game.player)) {
			direction = 0;
			costumeNum = 0;
			imgRowNum = 4 + direction;
			costumeFacing = 34 + direction*6;
			attacking = true;
			return true;
		}
		else if(entsInDirection(Direction.LEFT, range).contains(Game.player)) {
			direction = 1;
			costumeNum = 0;
			imgRowNum = 4 + direction;
			costumeFacing = 34 + direction*6;
			attacking = true;
			return true;
		}
		else if(entsInDirection(Direction.DOWN, range).contains(Game.player)) {
			direction = 2;
			costumeNum = 0;
			imgRowNum = 4 + direction;
			costumeFacing = 34 + direction*6;
			attacking = true;
			return true;
		}
		else if(entsInDirection(Direction.UP, range).contains(Game.player)) {
			direction = 3;
			costumeNum = 0;
			imgRowNum = 4 + direction;
			costumeFacing = 34 + direction*6;
			attacking = true;
			return true;
		}

		return false;
	}

	public void updateMoving(int speed) {
		double moveFactor = speed / 10.0 * Game.moveFactor;
		if(direction == 0) {
			if(tileInDirection(Direction.RIGHT, (int)moveFactor)/* || isEntInDirection(Direction.RIGHT, (int)moveFactor)*/) {
				changeDirection(1);
			}
			else {
//				imgRowNum = 0;
				costumeFacing = 0;
				move(Direction.RIGHT, speed);
			}
		}
		else if(direction == 1) {
			if(tileInDirection(Direction.LEFT, (int)moveFactor)/* || isEntInDirection(Direction.LEFT, (int)moveFactor)*/) {
				changeDirection(0);
			}
			else {
//				imgRowNum = 1;
				costumeFacing = Proletariat.numSprites.get(0);
				move(Direction.LEFT, speed);
			}
		}
		else if(direction == 2) {
			if(tileInDirection(Direction.DOWN, (int)moveFactor)/* || isEntInDirection(Direction.DOWN, (int)moveFactor)*/) {
				changeDirection(3);
			}
			else {
//				imgRowNum = 2;
				costumeFacing = Proletariat.numSprites.get(0) + Proletariat.numSprites.get(1);
				move(Direction.DOWN, speed);
			}
		}
		else if(direction == 3) {
			if(tileInDirection(Direction.UP, (int)moveFactor)/* || isEntInDirection(Direction.UP, (int)moveFactor)*/) {
				changeDirection(2);
			}
			else {
//				imgRowNum = 3;
				costumeFacing = Proletariat.numSprites.get(0) + Proletariat.numSprites.get(1) + Proletariat.numSprites.get(2);
				move(Direction.UP, speed);
			}
		}
	}
	
	public void updateCostume(int timer) {
		if(costumeTimerPassed(timer))
			costumeNum = (costumeNum + 1) % Proletariat.numSprites.get(imgRowNum);
	}
	
	public void changeDirection(int d) {
		direction = d;
		imgRowNum = d;
//		costumeNum = 0;
		width = parts.get(0).imgs.get(Proletariat.numSprites.get(imgRowNum) * imgRowNum).getWidth(null) * 4;
		height = parts.get(0).imgs.get(Proletariat.numSprites.get(imgRowNum) * imgRowNum).getHeight(null) * 4;
	}
	
	/*public void updateMoving(int speed) {
		double moveFactor = speed / 10.0 * Game.moveFactor + 2;
		//direction = 0;
		//costumeFacing = 0;
		move(Direction.RIGHT, speed);
	}*/
	
	public String toString() {
		return "Proletariat " + x + " " + y + " " + width + " " + height + " 1"; 
	}
	
	public String toDefaultString() {
		return "Proletariat 0 0 " + width + " " + height + " 1";
	}
	
}
