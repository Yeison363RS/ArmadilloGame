package controllers;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import models.Capturar;
import models.ComparatorSquare;
import models.Match;
import models.SetBoardCamp;
import models.Square;
import persistence.PersistenceManager;
import views.Constants;
import views.FrameMain;
import views.PanelBoard;
import views.StateGame;

/**
 * 
 * @author Yeison Rodriguez
 * @param <T> parametro que tipifica el objeto que se requiere en el juego
 */
public class Presenter<T> implements ActionListener, KeyListener, MouseListener {

	private SetBoardCamp<Color> setSquare;
	private FrameMain jFrame;
	private PanelBoard panelBoard;
	private long startTime = 0;
	private PersistenceManager persistence;
	private Thread threadCap;
	private static Presenter controllers = null;
	Capturar captura = new Capturar();

	/**
	 * Crea una instancia del controlador
	 * 
	 * @return la instancia de la clase Controller
	 */
	public static Presenter instanceOf() {
		if (controllers == null) {
			controllers = new Presenter();
		}
		return controllers;
	}
	/**
	 * Crea instancia de un componente especifico
	 * 
	 * @param component es el componente que se quiere instanciar
	 */
	public void addInstance(Component component) {
		if (component.getClass() == FrameMain.class) {
			this.jFrame = (FrameMain) component;
		} else if (component.getClass() == PanelBoard.class) {
			this.panelBoard = (PanelBoard) component;
		}
	}

	public void showNotice(boolean confirm) {
		panelBoard.setAction(confirm);
		panelBoard.setState(StateGame.AVISO);
		playSoundMove(confirm?Constants.PATH_SOUND_WINNER:Constants.PATH_SOUND_LOSER);
	}

	public void addInstanceLogic(SetBoardCamp<Color> setSquare) {
		this.setSquare = setSquare;
	}
	public void addPersistence(PersistenceManager persistence) {
		this.persistence = persistence;
	}

	public void startAlarm() {
		this.panelBoard.startMirorRepaint();
	}
	public void SaveGame() {
		persistence.write(setSquare.generateMatch());
	}
	public ArrayList<Match> getMatchSaved(){
		return persistence.readListMatchs();
	}
	public ArrayList<ImageIcon> getCaptures() {
		return PersistenceManager.getCaptures();
	}
	public void takeCaptures() {
		this.threadCap = new Thread() {
			public void run() {
				int cont = 0;
				while (panelBoard.isVisible()&& panelBoard.getState()== StateGame.PANEL_GAME) {
					try {
						captura.capturarPantalla(String.valueOf(cont));
						panelBoard.setImagesCaptures(getCaptures());
						SaveGame(); 
					} catch (AWTException | IOException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					cont++;
				}
			}
		};

	}
	public void startCaptures() {
		if(!this.threadCap.isAlive()) {
			this.threadCap.start();
		}
	}
	public int getLives() {
		return setSquare.getLives();
	}
	public void playSound(final String path) {
		this.jFrame.playSoundMove(path);
	}

	public char getDirection() {
		return setSquare.getDirectionArmadillo();
	}

	public <T> void getSquares() {
		Square<T>[][] squares = (Square<T>[][]) setSquare.getSquaresValues();
		Square<T>[][] squaresBackground = (Square<T>[][]) setSquare.getSquaresBackground();
		ComparatorSquare<ImageIcon> comparator = new ComparatorSquare<ImageIcon>() {
			@Override
			public boolean compare(ImageIcon dataOne, ImageIcon dataTwo) {
				return dataOne == dataTwo;
			}

			@Override
			public ImageIcon getvalue(ImageIcon data) {
				return data;
			}
		};
		panelBoard.drawMenu();
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[0].length; j++) {
				if (squares[i][j] != null) {
					panelBoard.drawSquare(squaresBackground[i][j].getCoord2d(),
							comparator.getvalue((ImageIcon) squares[i][j].getData()));
					if (squaresBackground[i][j].getSecondState() != null) {
						panelBoard.drawSquare(squaresBackground[i][j].getCoord2d(),
								comparator.getvalue((ImageIcon) squaresBackground[i][j].getSecondData()));
					}
					if (squares[i][j].getState() != null) {
						panelBoard.drawSquare(squares[i][j].getCoord2d(),
								comparator.getvalue((ImageIcon) squares[i][j].getData()));
					}
					if (squares[i][j].getSecondState() != null) {
						panelBoard.drawSquare(squares[i][j].getCoord2d(),
								comparator.getvalue((ImageIcon) squares[i][j].getSecondData()));
					}
				}
			}
		}
	}

	/**
	 * Obtiene la altura del tablero
	 * 
	 * @return el tamanio del tablero de juego
	 */
	public Dimension getHeigthPanel() {
		return this.panelBoard.getSize();
	}

	public void repaintPanel() {
		this.panelBoard.repaintPanel();
	}

	/**
	 * Muestra el tablero de juego
	 * 
	 * @param level es el nivel actual aprobado
	 */
	public void showBoardGame(int level) {
		jFrame.setSettingsViewGame();
	}

	/**
	 * Reproduce el sonido especifico
	 * 
	 * @param path la ruta del sonido que se quiere reproducir
	 */
	public void playSoundMove(String path) {
		this.jFrame.playSoundMove(path);
	}

	public void fillMatrixInit() {
		this.setSquare.fillInitMatrix();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case Constants.COMAND_NAME_BUTTON_START:
			
		default:
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(panelBoard.isPlaying()) {
			if (arg0.getKeyCode() == KeyEvent.VK_E ) {
				if (startTime == 0) {
					this.startTime = System.currentTimeMillis();
				} else if ((System.currentTimeMillis() - startTime) >= 500) {
					playSound(Constants.PATH_SOUND_DIG);
					setSquare.destroyeCamp();
					this.panelBoard.repaintPanel();
					startTime = 0;
				}
			} else {
				moveArmadilloPre(arg0.getKeyCode());
				this.panelBoard.repaintPanel();
			}
		}
	}

	public void moveArmadilloPre(int keyCode) {
		
		switch (keyCode) {
		case 37:
			setSquare.moveArmadillo(Constants.LEFT);
			break;
		case 38:
			setSquare.moveArmadillo(Constants.UP);
			break;
		case 39:
			setSquare.moveArmadillo(Constants.RIGTH);
			break;
		case 40:
			setSquare.moveArmadillo(Constants.DOWM);
			break;
		default:
			break;
		}
	}
	public void TakeAction(int option){
		switch (option) {
			case 0:
				panelBoard.setState(StateGame.PANEL_GAME);
				setSquare.restart();
				fillMatrixInit();
				startCaptures();
			break;
			case 1:
				jFrame.setSettingsJPanelMatchSaved();
			break;
			case 2:
				panelBoard.setState(StateGame.CAPTURES);
			break;
			case 3:
				panelBoard.imageChangeSelection(1);
			break;
			case 4:
				panelBoard.imageChangeSelection(2);
			break;
		default:
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_E && panelBoard.isPlaying()) {
			startTime = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(panelBoard.isVisible()) {
			panelBoard.calculateOption(arg0.getX(), arg0.getY());
		}
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	public void cagarPartida() {
		if(jFrame.isIndex()) {
			setSquare.setDatasMatch(persistence.getMatch(jFrame.getIdMatch()));
			jFrame.setSettingsViewGame();
			panelBoard.setState(StateGame.PANEL_GAME);
		}
		
	}

}
