package game3;

public class Main {

	public static Game game;
	
	public static void main(String[] args) {
//		for(int i = 0; i < GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames().length; i++) {
//			System.out.println(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()[i] + " " + i);
//		}
//		System.setProperty("sun.java2d.opengl","True");
		game = new Game(1920, 1080);
		new Thread(game).start();
		Spawner.spawnStage(0);
		
	}
	
}
	