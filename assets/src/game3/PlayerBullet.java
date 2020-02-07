package game3;

public class PlayerBullet extends Entity{
	
	public Direction dir;
	public boolean toDestroy;
	public int speed = 7;
	private double amountMoved;
	
	public PlayerBullet(double x, double y, Direction d) {
		super("PlayerBullet", x + " " + y + " 70 70");
		super.setParts(Utility.generateSprites("/game3/Images/Bullet.png", 1, 1));
		width = parts.get(0).imgs.get(0).getWidth(null) * 3;
		height = parts.get(0).imgs.get(0).getHeight(null) * 3;
		dir = d;
		toDestroy = false;
//		Game.shoot.play();
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
