package models;

import controllers.Presenter;
import views.Constants;

import java.awt.Point;
/**
 * Es la clase que maneja las utilidades que le presentan los cuadros del area
 * de juego
 * 
 * @author Yeison Rodriguez
 *
 * @param <T> parametro que tipifica el objeto usado en la aplicacion
 */

public final class SetBoardCamp<T> {

	private final Square<T>[][] squaresMatrix;
	private final Square<T>[][] squaresMatrixBackground;
	private final T[] elements;
	private boolean isInit;
	private Armadillo armadillo;
	private int numberLives;
	private boolean isMoving;
	public SetBoardCamp(T[] elements) {
		this.numberLives = 3;
		this.isInit = true;
		this.isMoving = false;
		this.elements = elements;
		this.armadillo = new Armadillo();
		this.squaresMatrix = new Square[13][20];
		this.squaresMatrixBackground = new Square[13][20];
	}

	public void canBeMoved(Square<T> square, int x, int y) {
		if (y >= 0 && y < 20 && x >= 0 && x < 13) {
			if (squaresMatrix[x][y].getSecondState() == null
					|| squaresMatrix[x][y].getSecondState() == EnumState.CAVE) {
				setPositionArmadillo(square, x, y);
			}
		}
	}

	public boolean isAvalibleSquare(int x, int y) {
		if (squaresMatrix[x][y].getSecondState() == EnumState.DOG) {
			gameOver();
			return false;
		}
		return true;
	}

	public void changeDirectionConfim(char key) {
		if (armadillo.getDirection() != key) {
			squaresMatrix[armadillo.getX()][armadillo.getY()].changeDataDirection(key);
		}
	}

	public void moveArmadillo(char key) {
		changeDirectionConfim(key);
		if(!isMoving) {
			switch (key) {
			case Constants.UP:
				armadillo.setDirection(Constants.UP);
				canBeMoved(squaresMatrix[armadillo.getX()][armadillo.getY()], armadillo.getX() - 1, armadillo.getY());
				break;
			case Constants.DOWM:
				armadillo.setDirection(Constants.DOWM);
				canBeMoved(squaresMatrix[armadillo.getX()][armadillo.getY()], armadillo.getX() + 1, armadillo.getY());
				break;
			case Constants.RIGTH:
				armadillo.setDirection(Constants.RIGTH);
				canBeMoved(squaresMatrix[armadillo.getX()][armadillo.getY()], armadillo.getX(), armadillo.getY() + 1);
				break;
			case Constants.LEFT:
				armadillo.setDirection(Constants.LEFT);
				canBeMoved(squaresMatrix[armadillo.getX()][armadillo.getY()], armadillo.getX(), armadillo.getY() - 1);
				break;
			default:
				break;
			}
		}
	}

	public void isDestroye(int x, int y) {
		if (y >= 0 && y < 20 && x >= 0 && x < 13) {
			squaresMatrix[x][y].setSecondState(null);
		}
	}

	public void destroyeCamp() {
		switch (armadillo.getDirection()) {
		case Constants.UP:
			isDestroye(armadillo.getX() - 1, armadillo.getY());
			break;
		case Constants.DOWM:
			isDestroye(armadillo.getX() + 1, armadillo.getY());
			break;
		case Constants.RIGTH:
			isDestroye(armadillo.getX(), armadillo.getY() + 1);
			break;
		case Constants.LEFT:
			isDestroye(armadillo.getX(), armadillo.getY() - 1);
			break;
		default:
			break;
		}
	}

	public void setPositionArmadillo(Square<T> square, int row, int colum) {
		if (this.squaresMatrix[row][colum].getState() == EnumState.ALARM) {
			attack(row);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (!isMoving) {
					isMoving = true;
					calculateDiference(square);
					if (isAvalibleSquare(row, colum)) {
						if (setStateArmadillo(square, row, colum)) {
							square.setSecondState(null);
							armadillo.setPosition(row, colum);
						}
					}
				}
			}
		}).start();
	}

	public void decreaseLives() {
		Presenter.instanceOf().playSound(Constants.PATH_SOUND_ARMA);
		numberLives--;
		if (numberLives == 0) {
			Presenter.instanceOf().showNotice(false);
		}
	}

	public boolean setStateArmadillo(Square<T> square, int row, int colum) {
		if (this.squaresMatrix[row][colum].getSecondState() == EnumState.CAVE) {
			Presenter.instanceOf().showNotice(true);
			return false;
		} else {
			this.squaresMatrix[row][colum].setSecondState(square.getSecondState());
			return true;
		}
	}

	public char getDirectionArmadillo() {
		return this.armadillo.getDirection();
	}

	public void generateTransicion(Square square, int xValue, int yValue) {
		int widthAv = Presenter.instanceOf().getHeigthPanel().width - 100;
		int heigthAv = Presenter.instanceOf().getHeigthPanel().height - 100;
		double pointY = square.getCoord2d().getY();
		double pointX = square.getCoord2d().getX();
		if (xValue == 0) {
			for (int i = 15; i > 0; i--) {
				try {
					square.getCoord2d().setY(pointY + ((heigthAv / 13) / i) * yValue);
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			for (int i = 15; i > 0; i--) {
				square.getCoord2d().setX(pointX + ((widthAv / 20) / i) * xValue);
				try {
					Thread.sleep(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		square.getCoord2d().setX(pointX);
		square.getCoord2d().setY(pointY);
		if (square.getSecondState() == EnumState.ARMADILLO) {
			this.isMoving = false;
		}
	}

	public void calculateDiference(Square square) {
		switch (armadillo.getDirection()) {
		case Constants.DOWM:
			generateTransicion(square, 0, 1);
			break;
		case Constants.UP:
			generateTransicion(square, 0, -1);
			break;
		case Constants.LEFT:
			generateTransicion(square, -1, 0);
			break;
		case Constants.RIGTH:
			generateTransicion(square, 1, 0);
			break;
		default:
			break;
		}
	}


	public void restoreField(EnumState enumState, Dog dog) {
		if (dog.getY() != 20) {
			if (enumState != null) {
				EnumState enumS = squaresMatrixBackground[dog.getX()][dog.getY()].getSecondState();
				squaresMatrixBackground[dog.getX()][dog.getY()].setSecondState(null);
				squaresMatrix[dog.getX()][dog.getY()].setState(enumState);
				squaresMatrix[dog.getX()][dog.getY()].setSecondState(enumS);
			} else {
				squaresMatrix[dog.getX()][dog.getY()].setSecondState(null);
			}
		}

	}

	public int getLives() {
		return this.numberLives;
	}

	public void attack(int row) {
		Presenter.instanceOf().playSound(Constants.PATH_SOUND_DOG);
		new Thread(new Runnable() {
			@Override
			public void run() {
				Dog dog = new Dog();
				dog.setPosition(row, 20);
				EnumState enumAux = null;
				int num = 1;
				while ((dog.getY() - num) >= 0) {
					restoreField(enumAux, dog);
					dog.setPosition(dog.getX(), dog.getY() - num);
					if (squaresMatrix[dog.getX()][dog.getY()].getSecondState() == EnumState.ARMADILLO) {
						decreaseLives();
					}
					enumAux = squaresMatrix[dog.getX()][dog.getY()].getState();
					squaresMatrixBackground[dog.getX()][dog.getY()]
							.setSecondState(squaresMatrix[dog.getX()][dog.getY()].getSecondState());
					squaresMatrix[dog.getX()][dog.getY()].setState(null);
					squaresMatrix[dog.getX()][dog.getY()].setSecondState(EnumState.DOG);
					generateTransicion(squaresMatrix[dog.getX()][dog.getY()], -1, 0);
				}
				dog.setPosition(dog.getX(), 0);
				restoreField(enumAux, dog);
			}
		}).start();
	}

	public void gameOver() {

		System.out.println("SE ACABO");
	}

	public void fillInitMatrix() {
		int widthAv = Presenter.instanceOf().getHeigthPanel().width - 400;
		int heigthAv = Presenter.instanceOf().getHeigthPanel().height - 100;
		if (isInit) {
			fillMatrixBackground(widthAv, heigthAv);
			for (int i = 0; i < squaresMatrix.length; i++) {
				for (int j = 0; j < squaresMatrix[0].length; j++) {
					squaresMatrix[i][j] = new Square<>(this.elements,
							new Coord2D((j * (widthAv / 20)) + 400, (i * (heigthAv / 13)) + 100));
					if (i == 6 && j == 0) {
						squaresMatrix[i][j].setSecondState(EnumState.ARMADILLO);
						this.armadillo.setPosition(i, j);
					} else if (i == 6 && j == 19) {
						squaresMatrix[i][j].setSecondState(EnumState.CAVE);
					}
				}
			}
			this.isInit = false;
			generateAlarms();
		}
	}

	public void fillMatrixBackground(int width, int heigth) {
		for (int i = 0; i < squaresMatrixBackground.length; i++) {
			for (int j = 0; j < squaresMatrixBackground[0].length; j++) {
				squaresMatrixBackground[i][j] = new Square<>(this.elements,
						new Coord2D((j * (width / 20)) + 400, (i * (heigth / 13)) + 100));
				squaresMatrixBackground[i][j].setSecondState(null);
			}
		}
	}

	public void generateAlarms() {
		boolean is = true;
		for (int i = 0; i < squaresMatrix.length; i++) {
			is = is == true ? false : true;
			if (is) {
				squaresMatrix[i][6].setState(EnumState.ALARM);
				squaresMatrix[i][4].setState(EnumState.ALARM);
				squaresMatrix[i][11].setState(EnumState.ALARM);
			} else {
				squaresMatrix[i][2].setState(EnumState.ALARM);
			}
		}
	}

	public Point getLocationArmadillo() {
		return armadillo.getPosition();
	}

	/**
	 * metodo que retorna los valores de la matriz de juego
	 *
	 * @return matriz de generica de juego
	 */
	public Square<T>[][] getSquaresValues() {
		return this.squaresMatrix;
	}

	public Square<T>[][] getSquaresBackground() {
		return this.squaresMatrixBackground;
	}

	public Match generateMatch() {
		Match match = new Match();
		match.setStatesFirstBack(getEnumState(squaresMatrixBackground, 1));
		match.setStatesFirst(getEnumState(squaresMatrixBackground, 2));
		match.setStatesSecondBack(getEnumState(squaresMatrix, 1));
		match.setStatesSecond(getEnumState(squaresMatrix, 2));
		match.setNumberLives(numberLives);
		return match;
	}

	public EnumState[][] getEnumState(Square[][] square, int number) {
		EnumState[][] enumFirst = new EnumState[13][20];
		for (int i = 0; i < square.length; i++) {
			for (int j = 0; j < square[0].length; j++) {
				enumFirst[i][j] = (number == 1) ? square[i][j].getState() : square[i][j].getSecondState();
			}
		}
		return enumFirst;
	}

	public void restart() {
		this.numberLives = 3;
		this.isInit = true;
	}

	public void setEnumSecondState(EnumState[][] states, int number) {
		for (int i = 0; i < states.length; i++) {
			for (int j = 0; j < states[0].length; j++) {
				if (number == 1) {
					squaresMatrix[i][j].setState(states[i][j]);
				} else {
					squaresMatrix[i][j].setSecondState(states[i][j]);
				}
			}
		}
	}

	public void setEnumFirstState(EnumState[][] states, int number) {
		for (int i = 0; i < states.length; i++) {
			for (int j = 0; j < states[0].length; j++) {
				if (number == 1) {
					squaresMatrixBackground[i][j].setState(states[i][j]);
				} else {
					squaresMatrixBackground[i][j].setSecondState(states[i][j]);
				}
			}
		}
	}

	public void setDatasMatch(Match match) {
		this.numberLives = match.getNumberLives();
		setEnumFirstState(match.getStatesFirstBack(), 1);
		setEnumFirstState(match.getStatesFirst(), 2);
		setEnumSecondState(match.getStatesSecondBack(), 1);
		setEnumSecondState(match.getStatesSecond(), 2);
		calculatePositionArmadillo(match.getStatesSecond()); 
	}
	public void calculatePositionArmadillo(EnumState[][] states) {
		for (int i = 0; i < states.length; i++) {
			for (int j = 0; j < states[0].length; j++) {
				if(states[i][j]==EnumState.ARMADILLO) {
					armadillo.setPosition(i, j);
				}
			}
		}
	}
}
