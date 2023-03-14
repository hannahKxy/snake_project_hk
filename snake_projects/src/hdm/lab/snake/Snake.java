package hdm.lab.snake;

import java.awt.Point;
import java.util.LinkedList;

import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Snake implements GameObject {

	int length = 10;
	int speed;
	int color;
	int positionX;
	int positionY;


	final static int LEFT = PConstants.LEFT;
	final static int RIGHT = PConstants.RIGHT;
	final static int UP = PConstants.UP;
	final static int DOWN = PConstants.DOWN;
	double SPEED = 0.1; // sollte die Geschwindigkeit doch zu schnell sein, hier können Sie umstellen
	SnakeGame game;
	StopWatch snakeSeconds;
	Point head;
	PImage headLeft;
	PImage headRight;
	PImage headUp;
	PImage headDown;
	PImage body;
	PImage image;
	PImage jetpack;
	PImage jetpackUp;
	PImage jetpackDown;
	PImage jetpackLeft;
	PImage jetpackRight;
	int newDirection = RIGHT;

	short[] sizeUp;

	boolean jetPackModeStarted = false;

	LinkedList<GameObject> objects;
	LinkedList<Point> snakeParts = new LinkedList<Point>();

	public Snake(PApplet p) {
		
		// die jeweiligen Grafiken für den Kopf und Körper der Schlange

		headLeft = p.loadImage("hdm/lab/snake/sHead left.PNG");
		headRight = p.loadImage("hdm/lab/snake/sHead right.PNG");
		headUp = p.loadImage("hdm/lab/snake/sHead up.PNG");
		headDown = p.loadImage("hdm/lab/snake/sHead down.PNG");
		body = p.loadImage("hdm/lab/snake/sBody.PNG");
		sizeUp = Toolbox.loadAudioFile("/hdm/lab/snake/powerUp(1).wav");

		// ...und den Jetpack
		jetpackUp = p.loadImage("hdm/lab/snake/jetpack up.PNG");
		jetpackDown = p.loadImage("hdm/lab/snake/jetpack down.PNG");
		jetpackLeft = p.loadImage("hdm/lab/snake/jetpack left.PNG");
		jetpackRight = p.loadImage("hdm/lab/snake/jetpack right.PNG");

		this.positionX = Toolbox.randomInt(11, 23) * SnakeGame.FIELD_SIZE;
		this.positionY = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE + SnakeGame.PANEL_HEIGHT;

		snakeSeconds = new StopWatch();
		spawnSnake(p); // Snake spawnt

	}

	// Snake steuern
	public void move(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		
		
		int speedY = 30; 
		int speedX = 30;

		int direction = p.keyCode;

		image = headRight;

		if (direction == UP && (newDirection != DOWN)) {

			positionY -= speedY; 
			newDirection = direction;
			moveBody(p);
			image = headUp;
			jetpack = jetpackUp;

		}

		if (direction == DOWN && (newDirection != UP)) {
			positionY += speedY;
			newDirection = direction;
			moveBody(p);
			image = headDown;
			jetpack = jetpackDown;

		}
		if (direction == LEFT && (newDirection != RIGHT)) {
			positionX -= speedX;
			newDirection = direction;
			moveBody(p);
			image = headLeft;
			jetpack = jetpackLeft;

		}
		if (direction == RIGHT && (newDirection != LEFT)) { // der schlangenkopf soll nicht in sich zurückreincrashen
			positionX += speedX;
			newDirection = direction;
			moveBody(p);
			image = headRight;
			jetpack = jetpackRight;

		}

	}

	public LinkedList<Point> spawnSnake(PApplet p) {

		head = new Point(this.positionX, this.positionY);

		this.snakeParts.add(head);

		for (int i = 1; i < length; i++) {
			this.snakeParts.add(new Point(this.positionX - SnakeGame.FIELD_SIZE * i, this.positionY));
			p.image(body, (snakeParts.get(i)).x, snakeParts.get(i).y, SnakeGame.FIELD_SIZE, SnakeGame.FIELD_SIZE);
		}


		return snakeParts;
	}

	public void moveBody(PApplet p) {
		Point newHead = new Point(this.positionX, this.positionY);

		head = newHead;

		snakeParts.add(0, head);

		snakeParts.remove(snakeParts.get(snakeParts.size() - 1));

	}

	// collision with other objects
	public void collide(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if ((game.apfel.positionX == this.positionX) && game.apfel.positionY == this.positionY) { // kollision mit apfel
			System.out.println("Nom");
			length++;
			game.points++;

			sizeUp();

		}

		if (((game.schnecke1.positionX == this.positionX) && (game.schnecke1.positionY == this.positionY))
				|| ((game.schnecke2.positionX == this.positionX) && (game.schnecke2.positionY == this.positionY))
				|| ((game.schnecke3.positionX == this.positionX) && (game.schnecke3.positionY == this.positionY))) {
			length++;
			game.points++;

			sizeUp();

		}

	}

	public void sizeUp() { // schlange wächst wenn schnecke oder apfel wird gefressen

		Toolbox.playArrayAsynchronuously(sizeUp);
		Point end = (snakeParts.get(snakeParts.size() - 1));

		Point newEnd = new Point(end.x - SnakeGame.FIELD_SIZE, end.y); // noch nicht ganz richtig aber ok
		end = newEnd;
		snakeParts.add(end);


	}

	// animation
	public void draw(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if (!isDead(game)) {


			if ((double) snakeSeconds.getElapsedTime() / 1E9 >= SPEED) {

			
				move(game, game.objects, p);
				collide(game, game.objects, p);

				snakeSeconds.reset();
			}
			jetPackMode(game, objects, p);

			for (int i = 0; i < snakeParts.size(); i++) { // hier werden snakeParts gezeichnet

				if (i == 0) {
					p.image(image, (snakeParts.get(i)).x, snakeParts.get(i).y, SnakeGame.FIELD_SIZE,
							SnakeGame.FIELD_SIZE);
					
				} else {
					p.image(body, (snakeParts.get(i)).x, snakeParts.get(i).y, SnakeGame.FIELD_SIZE,
							SnakeGame.FIELD_SIZE);
					if (jetPackModeStarted) {
						if (i == length / 2) {
							p.image(jetpack, (snakeParts.get(i)).x, snakeParts.get(i).y, SnakeGame.FIELD_SIZE,
									SnakeGame.FIELD_SIZE);
						}
					}
				}
			}

		} 

	}

	// when dead
	public boolean isDead(SnakeGame game) {

		if (this.positionX < Hedge.hedgeLeft || this.positionX >= Hedge.hedgeRight || this.positionY < Hedge.hedgeUp
				|| this.positionY > Hedge.hedgeDown) {

			return true;
		}

		for (int i = 1; i < snakeParts.size(); i++) {
			if ((head.x == snakeParts.get(i).x) && head.y == snakeParts.get(i).y) { // wenn kollidiert mit sich selbst

				return true;
			}

		}

		if ((this.positionX == game.geisterschlange1.positionX)
				&& (this.positionY == game.geisterschlange1.positionY)) {

			return true;

		}
		if ((this.positionX == game.geisterschlange2.positionX)
				&& (this.positionY == game.geisterschlange2.positionY)) {

			return true;

		}
		if ((this.positionX == game.geisterschlange3.positionX)
				&& (this.positionY == game.geisterschlange3.positionY)) {

			return true;

		}

		return false;
	}

	

	public void jetPackMode(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if (game.points >= 10 && game.points <= 11) {
			
			jetPackModeStarted = true;
			SPEED = 0.05;
			p.fill(255);
			p.text("JETPACK-MODUS YEEEE", 135, 140);

		} else {
			jetPackModeStarted = false;
			
			SPEED = 0.1;
		}

	}



}
