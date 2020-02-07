package game3.editor;

public class EditorMain {

	public static void main(String[] args) {
		//minimum functional size: 480 x 270
		
//		Game game = new Game(480, 270);
//		Game game = new Game(2400, 1350);
		EditorGame game = new EditorGame(1920, 1080);
//		System.out.println("done initializing");
		Thread thread = new Thread(game);
		thread.start();
//		EditorGame.loading = true;
	}
	
}
