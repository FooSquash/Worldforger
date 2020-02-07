package game3;

import java.awt.Image;

import game3.NPC;

public class Giant extends NPC{

//	public Giant(double x, double y) {
//		this(x, y, 1);
//	}
	
	public Giant(String s) {
		super("Barrel", s);
		
		super.setParts(Utility.generateSprites("src/game3/Images/Player.png", 2, 4));
		String[] arr = s.split(" ");
	}
	
	public Giant() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		if(state == 1) {
			if(moveTimerPassed(1)) {
				updateCostume();
				updateMoving(2);
				if(directionTimerPassed(50))
					direction = (int)(Math.random() * 4);
			}
		}
		else if(state == 2) {
		}
		updateHitboxes();
	}
	
}
