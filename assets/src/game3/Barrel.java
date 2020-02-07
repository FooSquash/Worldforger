package game3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Barrel extends Entity{
	
	public Barrel(String s) {
		super("Barrel", s);
		
		super.setParts(Utility.generateSprites("src/game3/Images/Barrel_Sprite_2.png", 1, 1));
		String[] arr = s.split(" ");
	}

	public Barrel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Barrel" + " " + x + " " + y + " " + width + " " + height;
	}
	
	public String toDefaultString() {
		return "Barrel 0 0 100 100";
	}

}
