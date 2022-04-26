package models;

public class Match{
	
	private EnumState[][] statesFirst;
	private  EnumState[][] statesFirstBack;
	private  EnumState[][] statesSecond;
	private  EnumState[][] statesSecondBack;
	private int numberLives;
	private long id; 
	private String name;
	public Match() {
		this.name="default";
		this.id=1;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EnumState[][] getStatesFirst() {
		return statesFirst;
	}
	public void setStatesFirst(EnumState[][] statesFirst) {
		this.statesFirst = statesFirst;
	}
	public EnumState[][] getStatesFirstBack() {
		return statesFirstBack;
	}
	public void setStatesFirstBack(EnumState[][] statesFirstBack) {
		this.statesFirstBack = statesFirstBack;
	}
	public EnumState[][] getStatesSecond() {
		return statesSecond;
	}
	public void setStatesSecond(EnumState[][] statesSecond) {
		this.statesSecond = statesSecond;
	}
	public EnumState[][] getStatesSecondBack() {
		return statesSecondBack;
	}
	public void setStatesSecondBack(EnumState[][] statesSecondBack) {
		this.statesSecondBack = statesSecondBack;
	}
	public int getNumberLives() {
		return numberLives;
	}
	public void setNumberLives(int numberLives) {
		this.numberLives = numberLives;
	}
	public void setNumberLives(long numberLives) {
		this.numberLives = (int)numberLives;
	}
	
}
