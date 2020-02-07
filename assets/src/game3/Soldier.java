package game3;

import java.awt.Image;
import java.awt.event.KeyEvent;

import game3.NPC;

public class Soldier extends NPC{

//	public Giant(double x, double y) {
//		this(x, y, 1);
//	}
	
	public Soldier(String s) {
		super("Soldier", s);
		
		super.setParts(Utility.generateSprites("/game3/Images/Soldier.png", 1, 4));
		String[] arr = s.split(" ");
		width = parts.get(0).imgs.get(0).getWidth(null) * 4;
		height = parts.get(0).imgs.get(0).getHeight(null) * 4;
	}
	
	public Soldier() {
		
	}
	
	@Override
	public void update() {
		if(state == 1) {
			if(moveTimerPassed(1)) {
				updateDirection();
				updateCostume();
				updateMoving(3);
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
		if(directionTimerPassed(65)) {
			direction = (int)(Math.random() * 4);
		}
	}
	
	public void updateCostume() {
//		if(costumeTimerPassed(50))
//			costumeNum = (costumeNum + 1) % 8;
	}
	
	public void updateShooting() {
		if(shootingTimerPassed()) {
			shootingTimer = 500;
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
		return "Soldier " + x + " " + y + " " + width + " " + height + " 1"; 
	}
	
	public String toDefaultString() {
		return "Soldier 0 0 " + width + " " + height + " 1";
	}
	
}
