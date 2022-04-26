package models;

import java.awt.Point;

import views.Constants;

public class Armadillo {
	private Point position;
	private char direction;
	public Armadillo () {
		this.position=new Point(0,0);
		this.direction=Constants.RIGTH;
	}
	public void setPosition(int x,int y) {
		this.position.setLocation(x, y);
	}
	public int getX() {
		return this.position.x;
	}
	public int getY() {
		return this.position.y;
	}
	public char getDirection() {
		return direction;
	}
	public void setDirection(char direc) {
		this.direction=direc;
	}
	public Point getPosition() {
		return this.position;
	}
}
