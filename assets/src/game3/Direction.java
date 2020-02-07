package game3;

import java.awt.event.KeyEvent;

public enum Direction {
	
	RIGHT(KeyEvent.VK_D, 0), LEFT(KeyEvent.VK_A, 1), DOWN(KeyEvent.VK_S, 2), UP(KeyEvent.VK_W, 3);
	
	private int keyValue, intValue;
	
	Direction(int keyValue, int intValue) {
		this.keyValue = keyValue;
		this.intValue = intValue;
	}
	
	public boolean isPressed() {
		return Game.keyPressed[keyValue];
	}
	
	public int getValue() {
		return intValue;
	}
	
}
