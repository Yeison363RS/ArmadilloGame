package models;

/**
 * Es la clase encargada de determinar la posicion de un cuadro dentro del
 * tablero de juego
 * 
 * @author Yeison Rodriguez
 */
public class Coord2D {

	private double x;
	private double y;

	/**
	 * corresponde a la ubicacion de los cuadros del juego dentro del tablero
	 * 
	 * @param x coordenada en x
	 * @param y coordenada en y
	 */
	public Coord2D(double x, double y) {
		this.y = y;
		this.x = x;
	}

	/**
	 * Obiene la posicion en el eje x
	 * 
	 * @return la posicion
	 */
	public double getX() {
		return x;
	}

	/**
	 * Envia una posicion en el eje
	 * 
	 * @param x posicion en el eje x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Obiene la posicion en el eje y
	 * 
	 * @return la posicion
	 */
	public double getY() {
		return y;
	}

	/**
	 * Envia una posicion en el eje
	 * 
	 * @param y posicion en el eje y
	 */
	public void setY(double y) {
		this.y = y;
	}
}
