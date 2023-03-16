package hdm.lab.snake;

import java.util.LinkedList;

import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PImage;

public class Snail implements GameObject {

	int positionX;
	int positionY;
	int directionX;
	int directionY;
	PImage snail;

	StopWatch snailSeconds;
	Snail newSnail;
	 double SPEED = 1;

	public Snail(PApplet p) {
		
		snail = p.loadImage("hdm/lab/snake/Snail.PNG");

		this.positionX = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE;
		this.positionY = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE + SnakeGame.PANEL_HEIGHT;
		snailSeconds = new StopWatch();

	}

	public void move(SnakeGame game, LinkedList<GameObject> objects, PApplet p) { // hier nochmal schauen

		directionX = Toolbox.randomInt(-2, 2) * SnakeGame.FIELD_SIZE;
		directionY = Toolbox.randomInt(-2, 2) * SnakeGame.FIELD_SIZE;

		if (directionX != 0) { // snails sollen nicht diagonal laufen
			directionY = 0;
		}
		if (directionY != 0) {
			directionX = 0;
		}

		positionX += directionX;
		positionY += directionY;

	
		collide(game, objects, p);

	}

	public void draw(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if (!isDead(game)) {

			
			if ( (int)snailSeconds.getElapsedTime() / 1E9 >= SPEED) { // snails bewegen sich jede Sekunde

				move(game, objects, p);
				snailSeconds.reset();
			}
			
			p.image(snail, positionX, positionY, SnakeGame.FIELD_SIZE, SnakeGame.FIELD_SIZE);


		}

		if (isDead(game)) { // wenn tot wird snail von Liste removed und neue snail spawnt
			spawnSnail(game,objects,p);

		}

	}
	
	public void spawnSnail(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {
		objects.remove(game.schnecke1); // das stimmt hier nicht ganz
		
		newSnail = new Snail(p);

		newSnail.positionX = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE;
		newSnail.positionY = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE + SnakeGame.PANEL_HEIGHT;

		positionX = newSnail.positionX;
		positionY = newSnail.positionY;

		objects.add(4, newSnail); // neue snail wird zur liste geadded

	// neue snail spawnt
		p.image(snail,positionX, positionY, SnakeGame.FIELD_SIZE, SnakeGame.FIELD_SIZE);
	}


	public boolean isDead(SnakeGame game) {
		int snakePositionX = game.schlange.positionX;
		int snakePositionY = game.schlange.positionY;

		if ((snakePositionX == this.positionX) && snakePositionY == this.positionY) {


			return true;

		}

		return false;
	}

	public void collide(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {
		if (positionX < Hedge.hedgeLeft) {
			positionX += SnakeGame.FIELD_SIZE;
			
		}
		if (positionX >= Hedge.hedgeRight) {
			positionX -= SnakeGame.FIELD_SIZE;
			

		}

		if (positionY <= Hedge.hedgeUp) {
			positionY += SnakeGame.FIELD_SIZE;
			
		}
		if (positionY > Hedge.hedgeDown) {
			positionY -= SnakeGame.FIELD_SIZE;
			
		}

	}

	
}
