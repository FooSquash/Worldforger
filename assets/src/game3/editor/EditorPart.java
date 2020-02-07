package game3.editor;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class EditorPart {

	ArrayList<Image> imgs;
	Image img;
	int activeImg;
	
	public EditorPart() {
		imgs = new ArrayList<Image>(0);
		activeImg = 0;
	}
	
	public EditorPart(String URL) {
		imgs = new ArrayList<Image>(0);
		try {
			imgs.add(ImageIO.read(new Object().getClass().getResourceAsStream(URL)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		activeImg = 0;
	}
	
}
