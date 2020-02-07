package game3;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private File soundFile;
	
	public Sound(String fileName) {
		soundFile = new File(fileName);
	}

	public void play() {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = inputStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip)AudioSystem.getLine(info);
			clip.open(inputStream);
			clip.start();
		} catch (LineUnavailableException e) {
			System.out.println("line un");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io");
		} catch (UnsupportedAudioFileException e) {
			System.out.println("uns audio");
		}
	}
}