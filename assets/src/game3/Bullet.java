package game3;

public class Bullet extends Entity{
	
	public Direction dir;
	public int speed = 7;
	public boolean toDestroy = false;
	private double amountMoved;
	
	public Bullet(double x, double y, Direction d) {
		super("Bullet", x + " " + y + " 70 70");
		super.setParts(Utility.generateSprites("/game3/Images/Bullet.png", 1, 1));
		width = parts.get(0).imgs.get(0).getWidth(null) * 2;
		height = parts.get(0).imgs.get(0).getHeight(null) * 2;
		dir = d;
//		new Sound("src/game3/sounds/369528_johandeecke_short-gunshot.wav").play();
	}
	
	public void update() {
		amountMoved = speed / 10.0 * Game.moveFactor;
		switch(dir) {
			case DOWN:
				y -= amountMoved;
				break;
			case UP:
				y += amountMoved;
				break;
			case RIGHT:
				x += amountMoved;
				break;
			case LEFT:
				x -= amountMoved;
				break;
		}
		if(tileInDirection(dir, (int)amountMoved + 2) || toDestroy)
			destroy();
	}
	
}
