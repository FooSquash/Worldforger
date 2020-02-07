package game3;

public class SmallBarrel extends Entity{
	
	public SmallBarrel(String s) {	
		super("SmallBarrel", s);
		
		super.setParts(Utility.generateSprites("src/game3/Images/Small_Barrel.png", 1, 1));
		String[] arr = s.split(" ");	}

	public SmallBarrel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return classPath + " " + x + " " + y + " " + width + " " + height;
	}

}
