package models;

import java.util.Random;

import controllers.Presenter;
import views.Constants;

/**
 * Pertenece a la clase de cada cuadro de juego independiente
 * 
 * @author Yeison Rodriguez
 *
 * @param <T>
 */
public final class Square<T> {

	private Coord2D coord2d;
	private final Random random;
	private T data;
	private T secondData;
	private final T[] elements;
	private EnumState enumState;
	private EnumState secondState;
	private int numberEle;
	private int indice=-1;

	public Square(T[] elements, Coord2D coord2d) {
		this.coord2d = coord2d;
		this.elements = elements;
		this.enumState = EnumState.EARTH;
		this.secondState = EnumState.FIELD;
		this.random = new Random();
		allotData();
		allotSecondData('D');
	}

	public void setSecondState(EnumState enumS) {
		this.secondState = enumS;
		allotSecondData(Presenter.instanceOf().getDirection());
	}

	public void changeDataDirection(char key) {
		this.indice=-1;
		switch (key) {
		case Constants.UP:
//			System.out.println("arriba");
			this.secondData=elements[5];
			numberEle=5;
			break;
		case Constants.DOWM:
//			System.out.println("abajo");
			this.secondData=elements[7];
			numberEle=7;
			break;
		case Constants.RIGTH:
//			System.out.println("derecha");
			this.secondData=elements[3];
			numberEle=3;
			break;
		case Constants.LEFT:
//			System.out.println("izquierda");
			this.secondData=elements[9];
			numberEle=9;
			break;
		default:
			break;
		}
	}

	public void setState(EnumState enumS) {
		this.enumState = enumS;
		allotData();
	}

	public void allotData() {
		if (enumState != null) {
			switch (enumState) {
			case EARTH:
				this.data = elements[0];
				break;
			case ALARM:
				this.data = elements[1];
				break;
			case FIELD:
				this.data = elements[2];
				break;
			case CAVE:
				this.data = elements[13];
				break;
			default:
				break;
			}
		}
	}

	public void allotSecondData(char key) {
		if (secondState != null) {
			switch (secondState) {
			case FIELD:
				this.secondData = elements[2];
				break;
			case CAVE:
				this.secondData = elements[13];
				break;
			case ARMADILLO:
				changeDataDirection(key);
				break;
			case DOG:
				this.secondData = elements[11];
				this.numberEle=11;
				this.indice=-1;
				break;
			default:
				break;
			}
		}
	}

	public T getElement(int index) {
		return elements[index];
	}

	public T getData() {
		return this.data;
	}

	public T getSecondData() {
		if(this.secondState==EnumState.ARMADILLO ||this.secondState==EnumState.DOG) {
			indice=(indice==-1)?1:-1;
			this.secondData=elements[numberEle+indice];
			numberEle=numberEle+indice;
		}
		return this.secondData;
	}

	public T[] getElements() {
		return elements;
	}

	public EnumState getSecondState() {
		return this.secondState;
	}

	public EnumState getState() {
		return this.enumState;
	}

	public void setElement(T element, int index) {
		elements[index] = element;
	}

	public void setCoord2D(Coord2D coord) {
		this.coord2d = coord;
	}

	public Coord2D getCoord2d() {
		return this.coord2d;
	}
}
