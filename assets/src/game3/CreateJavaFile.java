package game3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class CreateJavaFile {

	public static void main(String[] args) {
		Scanner sc2;
		try {
			sc2 = new Scanner(new File("src/game3/MapInfoOld.info"));
		} catch (FileNotFoundException e1) {
			sc2 = null;
			e1.printStackTrace();
		}
		
		PrintStream active;
		try {
			active = new PrintStream(new File("src/game3/MapInfo.info"));
		} catch (FileNotFoundException e) {
			active = null;
			e.printStackTrace();
		}

		while(sc2.hasNextLine()) {
			active.println(sc2.nextLine());
		}
		
		active.println("hi");
		
	}

}
