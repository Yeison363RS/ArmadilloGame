package test;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import views.Constants;

public class testAudio {
	public testAudio() {
		
	}
	public void playSound(final String path) {
		File file=new File(path);
		System.out.println(file.exists());
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(getClass().getResourceAsStream(path));
					clip.open(inputStream);
					clip.start();
				} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}
	public static void main(String[] args) {
		testAudio test=new testAudio();
		test.playSound(Constants.PATH_SOUND_BACK);
	}
}
