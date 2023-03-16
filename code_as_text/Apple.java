package hdm.lab.snake;



import java.util.LinkedList;

import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PImage;

public class Apple implements GameObject {
	int positionX;
	int positionY;
	Apple newApple;
	PImage apple;

	public Apple(PApplet p) {
		apple = p.loadImage("hdm/lab/snake/Apple.PNG");

		this.positionX = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE; 
		this.positionY = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE + SnakeGame.PANEL_HEIGHT;

	}

	public boolean isDead(SnakeGame game) {
		int snakePositionX = game.schlange.positionX;
		int snakePositionY = game.schlange.positionY;

		if ((snakePositionX == this.positionX) && snakePositionY == this.positionY) {

			System.out.println("Me dead rip");

			return true;

		}

		return false;

	}

	public void move(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

	}

	public void draw(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if (!isDead(game)) {
			
			p.image(apple,positionX, positionY, SnakeGame.FIELD_SIZE, SnakeGame.FIELD_SIZE);
		}

		if (isDead(game)) { // wenn tot wird apfel von Liste removed und neuer Apfel spawnt
			objects.remove(game.apfel);
			
			newApple = new Apple(p);

			newApple.positionX = Toolbox.randomInt(11, 23) * SnakeGame.FIELD_SIZE;
			newApple.positionY = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE + SnakeGame.PANEL_HEIGHT;

			positionX = newApple.positionX;
			positionY = newApple.positionY;

			objects.add(2, newApple); // neuer Apfel wird zur liste geadded

			
			p.image(apple,positionX, positionY, SnakeGame.FIELD_SIZE, SnakeGame.FIELD_SIZE);

		}

	}

	public void collide(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

	}

	
	

}
