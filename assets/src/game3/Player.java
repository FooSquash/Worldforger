package game3;

import java.awt.Image;
import java.awt.event.KeyEvent;

public class Player extends Character{
	
	public static boolean invincible = false;
	
	private final int SPEED = 5;
	
	public boolean dead;
	
	public Player(String s) {
		super(".Player", s);
		setParts(new Image[][]{Utility.generateSprites("/game3/Images/Player_Character.png", 2, 8)});
		String[] arr = s.split(" ");
		width = parts.get(0).imgs.get(0).getWidth(null) * 4;
		height = parts.get(0).imgs.get(0).getHeight(null) * 4;
		direction = 0;
		dead = false;
	}
	
	@Override
	public void update() {
//		if(moveTimerPassed(1)) {
		updateCostume();
		updateMoving();
		updateShooting();
//		}	
		updateHitboxes();
		
//		for(Entity e : Game.ents) {
//			if(e instanceof Portal) {
//				System.out.println("yup portal");
//			}
//		}
		if(portalInVicinity(10) != null) {
//			Utility.displaytext("PORTAL DETECTED");
//			System.out.println("portal");
			Game.loading = true;
			Game.toSpawnStage = ((Portal)portalInVicinity(100)).loc;
		}
		else {
			Utility.displaytext("");
		}
		if(!invincible) {
			for(Entity e : entsTouching()) {
				if(e instanceof Bullet) {
					dead = true;
					destroy();
				} else if(e instanceof Proletariat && ((Proletariat)e).costumeNum == 5 && ((Proletariat)e).attacking) {
					dead = true;
					destroy();
				}
			}
			
		}
		
//		if(isEntInDirection(Direction.LEFT, 50))
//			Utility.displaytext("Hello there!");
//		else if(isEntInDirection(Direction.RIGHT, 50))
//			Utility.displaytext("Hello there!");
//		else if(isEntInDirection(Direction.UP, 50))
//			Utility.displaytext("Hello there!");
//		else if(isEntInDirection(Direction.DOWN, 50))
//			Utility.displaytext("Hello there!");
//		else
//			Utility.displaytext("");
//		if(touch) {
//			
//		}
	}
	
	public void updateMoving() {
		int moveFactor = (int)(SPEED / 10.0 * Game.moveFactor + 2);
		moving = false;			
//		System.out.println("Initial: " + Game.moveFactor);
//		System.out.println("Left: " + Game.moveFactor);
		if(Direction.LEFT.isPressed() && !tileInDirection(Direction.LEFT, moveFactor + 2)/* && !isEntInDirection(Direction.LEFT, moveFactor + 2)*/) {
//			System.out.println("Left pr: " + Game.moveFactorTemp);
			costumeNum = 5;
			move(Direction.LEFT, SPEED);
			
		}					
//		System.out.println("Right: " + Game.moveFactor);
		if(Direction.RIGHT.isPressed() && !tileInDirection(Direction.RIGHT, moveFactor + 2)/* && !isEntInDirection(Direction.RIGHT, moveFactor + 2)*/) {
//			System.out.println("Right pr: " + Game.moveFactorTemp);
			costumeNum = 4;
			move(Direction.RIGHT, SPEED);
			
		}
//		System.out.println("Up: " + Game.moveFactor);
		if(Direction.UP.isPressed() && !tileInDirection(Direction.UP, moveFactor + 2)/* && !isEntInDirection(Direction.UP, moveFactor + 2)*/) {
//			costumeFacing = 4;
			costumeNum = 7;
			move(Direction.UP, SPEED);
			
		}
//		System.out.println("Down: " + Game.moveFactor);
		if(Direction.DOWN.isPressed() && !tileInDirection(Direction.DOWN, moveFactor + 2)/* && !isEntInDirection(Direction.DOWN, moveFactor + 2)*/) {
			costumeFacing = 0;
			costumeNum = 6;
			move(Direction.DOWN, SPEED);
			
		}		
		if(Direction.UP.isPressed() && Direction.DOWN.isPressed()) {
			moving = false;
			setActiveImage(0);
		}
	}
	
	public void updateCostume() {
		if(costumeTimerPassed(50)) {
//			if(!moving)
//				setActiveImage(costumeFacing);
//			else {
//				costumeNum = (costumeNum + 1) % 4 + 4;
////				if(costumeNum == 1 || costumeNum == 3) {
////					walk.play();
//////					Spawner.birds.setValue(walk.getValue());
////				}			
//			}
		}
	}
	
	@Override
	public void updateHitboxes() {
		for(Hitbox h : hitboxes) {
			h.x = x;
			h.y = y;
			h.width = width;
			h.height = height;
		}
	}
	
	public void updateShooting() {
		if(shootingTimerPassed()) {
			shootingTimer = 500;
			if(Game.keyPressed[KeyEvent.VK_UP]) {
				Game.ents.add(new PlayerBullet(x + width/2, y, Direction.UP));
			}
			else if(Game.keyPressed[KeyEvent.VK_DOWN]) {
				Game.ents.add(new PlayerBullet(x + width/2, y - height, Direction.DOWN));
			}
			else if(Game.keyPressed[KeyEvent.VK_RIGHT]) {
				Game.ents.add(new PlayerBullet(x + width, y - height/2, Direction.RIGHT));
			}
			else if(Game.keyPressed[KeyEvent.VK_LEFT]) {
				Game.ents.add(new PlayerBullet(x, y - height/2, Direction.LEFT));
			}
			else
				shootingTimer = 0;
		}
	}
	
}