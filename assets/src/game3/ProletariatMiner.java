package game3;

import java.awt.Image;

import game3.NPC;

public class ProletariatMiner extends NPC{

	public ProletariatMiner(String s) {
		super("ProletariatMiner", s);
		
		super.setParts(Utility.generateSprites("src/game3/Images/ProletariatMiner.png", 2, 1));
		String[] arr = s.split(" ");
	}
	
	@Override
	public void update() {
		if(state == 1) {
			if(moveTimerPassed(1)) {
				updateCostume();
				updateMoving(2);
				if(directionTimerPassed(50))
					direction = (int)(Math.random() * 2);
			}
		}
		else if(state == 2) {
		}
		updateHitboxes();
	}
	
	public void updateCostume() {
		if(costumeTimerPassed(50))
			costumeNum = (costumeNum + 1) % 2;
	}
	
}
