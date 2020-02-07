package game3;

import javax.swing.JFrame;

public abstract class Screen extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int SC_WIDTH = 1920, SC_HEIGHT = 1080, MID_W = 960, MID_H = 540;
	
	public Screen(String title) {
		super(title);
	}

}
