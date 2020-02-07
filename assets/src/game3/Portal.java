package game3;

public class Portal extends Entity{
	
	public int loc;

//	public Portal(double x, double y, int width, int height, String modString) {	
//		super(".Portal", x, y, width, height);			
//		super.setParts(Utility.generateSprites("/game3/Images/transBG.png", 1, 1));		
//		parseModString(modString);
//		invisible = true;	
//	}
	
	public Portal(String s) {
		super("Portal", s);
		String[] arr = s.split(" ");
		super.setParts(Utility.generateSprites("/game3/Images/transBG.png", 1, 1));
		invisible = true;
//		width = 128;
//		height = 128;
		width = 64;
		height = 64;
		parseModString(arr[4]);
	}
	
	public Portal() {
		
	}
	
	private void parseModString(String modString) {
		loc = Integer.parseInt(modString);
	}
	
	@Override
	public String toString() {
//		System.out.println("hi");
		return "Portal " + x + " " + y + " " + width + " " + height + " " + loc;
	}
	
	public String toDefaultString() {
		return "Portal 0 0 " + width + " " + height + " 1";
	}

}
