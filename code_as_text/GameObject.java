package hdm.lab.snake;

import java.awt.Point;
import java.util.LinkedList;

import processing.core.PApplet;

public interface GameObject {
		

	abstract void move(SnakeGame game, LinkedList<GameObject> objects, PApplet p);

	abstract void collide(SnakeGame game, LinkedList<GameObject> objects,PApplet p);

	abstract void draw(SnakeGame game, LinkedList<GameObject> objects, PApplet p);

	abstract boolean isDead(SnakeGame game);
	

}
