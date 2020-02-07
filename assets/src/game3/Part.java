package game3;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Part {

	public ArrayList<Image> imgs;
	Image img;
	public int activeImg;
	
	public Part() {
		imgs = new ArrayList<Image>(0);
		activeImg = 0;
	}
	
	public Part(String URL) {
		imgs = new ArrayList<Image>(0);
		try {
			imgs.add(ImageIO.read(new Object().getClass().getResourceAsStream(URL)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		activeImg = 0;
	}
	
	public static Part getLast(ArrayList<Part> list) {
		return list.get(list.size() - 1);
	}
	
}
