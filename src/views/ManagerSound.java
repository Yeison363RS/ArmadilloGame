package views;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Es la clase responsable de manejar los sonidos y la alineacion de los mismos
 * dentro de la aplicacion
 * 
 * @author Yeison Rodriguez
 *
 */
public class ManagerSound {
	private int BUFFER_SIZE = 8000;
	private AudioInputStream audioStream;
	private AudioFormat audioFormat;
	private SourceDataLine sourceLine;
	private File file; 

	public ManagerSound(String path) {
		toAssignInputAudio(path);
		playSound();
	}

	public void toAssignInputAudio(String path) {
		try {
			this.file=new File(this.getClass().getResource(path).toURI());
		} catch (Exception e) {
			System.exit(1);
		}
		try {
			this.audioStream =AudioSystem.getAudioInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void playSound() {
		this.audioFormat = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try {
			this.sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			System.exit(1);
		} catch (Exception e) {
			System.exit(1);
		}
	}

	public void startSound() {
		sourceLine.start();
		int nBytesRead = 0;
		byte[] abData = new byte[BUFFER_SIZE];
		while (nBytesRead != -1) {
			try {
				nBytesRead = audioStream.read(abData, 0, abData.length);
			} catch (IOException e) {
			}
			if (nBytesRead >= 0) {
				@SuppressWarnings("unused")
				int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
			}
		}
		sourceLine.drain();
		sourceLine.close();
	}

	public void stopSound() {
		sourceLine.stop();
		sourceLine.drain();
		sourceLine.close();
	}

}
