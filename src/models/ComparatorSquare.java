package models;

/**
 * Esta interfaz es la encargada de generalizar el programa
 * 
 * @author Yeison Rodriguez
 * @param <T> Elemento que tipifica los objetos del juego
 */
public interface ComparatorSquare<T> {

	public boolean compare(T dataOne, T dataTwo);

	public T getvalue(T t);
}
