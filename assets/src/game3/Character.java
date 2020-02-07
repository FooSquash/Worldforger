package game3;

public class Character extends Entity{

	public int moveTimer, costumeFacing, costumeNum, direction;
	public double costumeTimer, shootingTimer, attackTimer;
	public boolean moving;
	public double amountMoved;
	
	public Character() {
		
	}
	
	public Character(String classPath, String s) {
		super("Character." + classPath, s);
		String[] arr = s.split(" ");
		moveTimer = 0;
		costumeTimer = 0;
		shootingTimer = 0;
		attackTimer = 0;
		costumeFacing = 0;
		costumeNum = 0;
		moving = false;
	}
	
	public boolean moveTimerPassed(int modValue) {
//		moveTimer = (int)(moveTimer + Game.moveFactor) % modValue;
//		return moveTimer == 0;
		return true;
	}
	
	public boolean costumeTimerPassed(int modValue) {
		costumeTimer += Game.moveFactor;
		if(costumeTimer >= modValue) {
			costumeTimer = 0;
			return true;
		}
		return false;
	}
	
	public boolean shootingTimerPassed() {
		if(shootingTimer <= 0)
			return true;
		shootingTimer -= Game.moveFactor;
		return false;
	}
	
	public boolean attackTimerPassed(int modValue) {
		attackTimer += Game.moveFactor;
		if(attackTimer >= modValue) {
			attackTimer = 0;
			return true;
		}
		return false;
	}
	
	public void move(Direction d, int speed) {
		moving = true;
		amountMoved = speed / 10.0 * Game.moveFactor;
		if(d.equals(Direction.UP))
			y += amountMoved;
		else if(d.equals(Direction.DOWN))
			y -= amountMoved;
		else if(d.equals(Direction.RIGHT))
			x += amountMoved;			
		else if(d.equals(Direction.LEFT))
			x -= amountMoved;
			
		if(this instanceof Proletariat) {
		}
		
		setActiveImage(costumeNum + costumeFacing);
	}

}
