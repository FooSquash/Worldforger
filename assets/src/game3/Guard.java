package game3;

import java.awt.Image;
import java.awt.event.KeyEvent;

import game3.NPC;

public class Guard extends NPC{

//	public Giant(double x, double y) {
//		this(x, y, 1);
//	}
	
	public Guard(String s) {
		super("Guard", s);
		
		super.setParts(Utility.generateSprites("/game3/Images/Guard.png", 1, 4));
		String[] arr = s.split(" ");
		width = parts.get(0).imgs.get(0).getWidth(null) * 4;
		height = parts.get(0).imgs.get(0).getHeight(null) * 4;
	}
	
	public Guard() {
		
	}
	
	@Override
	public void update() {
		if(state == 1) {
			if(moveTimerPassed(1)) {
				updateDirection();
				updateCostume();
				updateMoving(5);
				updateShooting();
			}
		}
		else if(state == 2) {
		}
		updateHitboxes();
		for(Entity e : Game.ents) {
			if(isTouchingFixed(e) && e instanceof PlayerBullet) {
				((PlayerBullet)e).toDelete = true;
				destroy();
			}
		}
	}
	
	public void updateDirection() {
//		if(x < Game.player.x + Game.player.width - 30)
		if(directionTimerPassed(20)) {
			direction = (int)(Math.random() * 4);
		}
	}
	
	public void updateCostume() {
//		if(costumeTimerPassed(50))
//			costumeNum = 0;
	}
	
	public void updateShooting() {
		if(shootingTimerPassed()) {
			shootingTimer = 800;
			if(direction == 3) {
				Game.ents.add(new Bullet(x + width/2, y, Direction.UP));
			}
			else if(direction == 2) {
				Game.ents.add(new Bullet(x + width/2, y - height, Direction.DOWN));
			}
			else if(direction == 0) {
				Game.ents.add(new Bullet(x + width, y - height/2, Direction.RIGHT));
			}
			else if(direction == 1) {
				Game.ents.add(new Bullet(x, y - height/2, Direction.LEFT));
			}
		}
	}
	
	public String toString() {
		return "Guard " + x + " " + y + " " + width + " " + height + " 1"; 
	}
	
	public String toDefaultString() {
		return "Guard 0 0 " + width + " " + height + " 1";
	}
	
}
