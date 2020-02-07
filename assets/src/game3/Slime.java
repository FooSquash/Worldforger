package game3;

import java.awt.Image;

import game3.NPC;

public class Slime extends NPC{

	public Slime(String s) {
		super("Slime", s);
		super.setParts(new Image[][]{Utility.generateSprites("src/game3/Images/Player.png", 2, 4)});
	}
	
	public Slime() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		
	}

}
