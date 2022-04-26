package models;

import java.awt.Point;

public class Dog {
	private Point position;
	public Dog() {
		this.position=new Point(0,0);
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
}
