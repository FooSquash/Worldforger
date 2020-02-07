package game3;

public enum Profession {
	FARMER("farmer", 1), SERF("peasant", 0), MERCHANT("merchant", 3), BLACKSMITH("blacksmith", 2), ARTISAN("artisan", 2), SOLDIER("soldier", 2), PHILOSOPHER("thinker", 1);
	
	String profName;
	int avgWealth; //0 is poorest, 4 is wealthiest
	
	private Profession(String profName, int avgWealth) {
		this.profName = profName;
		this.avgWealth = avgWealth;
	}
	
	@Override
	public String toString() {
		return profName;
	}
	
}
