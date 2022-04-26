package models;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Capturar{
	private static final String DB_CAPTURES_PATH= "./db/captures";
	  public static void capturarPantalla(String Nombre) throws
	           AWTException, IOException {
	     BufferedImage captura = new Robot().createScreenCapture(
	           new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()) );
	     File file = new File(DB_CAPTURES_PATH+"/"+Nombre + ".jpg");
	     ImageIO.write(captura, "jpg", file);
	  }
	}
