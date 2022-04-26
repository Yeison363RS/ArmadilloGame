package runing;

import java.io.IOException;

import javax.swing.ImageIcon;
import controllers.Presenter;
import models.SetBoardCamp;
import persistence.PersistenceManager;
import views.FrameMain;
/**
 * Se encarga de darle la funcion de ejecucion a la aplicacion contiene el hilo
 * principal
 * 
 * @author Yeison Rodriguez
 *
 */
public class RunApp {
							
	public static void main(String[] args) throws IOException, NumberFormatException, InterruptedException {
		SetBoardCamp<ImageIcon> sq = new SetBoardCamp<>(new ImageIcon[] { 
				new ImageIcon(RunApp.class.getResource("/resources/suelom.png")),
				new ImageIcon(RunApp.class.getResource("/resources/boton.png")),
				new ImageIcon(RunApp.class.getResource("/resources/campom.png")), 
				new ImageIcon(RunApp.class.getResource("/resources/Armadillor.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillor2.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillou.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillou2.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillod.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillod2.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillol.png")),
				new ImageIcon(RunApp.class.getResource("/resources/Armadillol2.png")),
				new ImageIcon(RunApp.class.getResource("/resources/dogm.png")),
				new ImageIcon(RunApp.class.getResource("/resources/dogm2.png")),
				new ImageIcon(RunApp.class.getResource("/resources/cave.png"))});
		Presenter.instanceOf().addPersistence(new PersistenceManager());
		Presenter.instanceOf().addInstanceLogic(sq);
		FrameMain frame = new FrameMain();
	}
}
