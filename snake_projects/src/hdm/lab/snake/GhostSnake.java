package hdm.lab.snake;

import java.awt.Point;
import java.util.LinkedList;

import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PImage;

public class GhostSnake implements GameObject {
	int positionX;
	int positionY;

	SnakeGame game;
	StopWatch ghostSnakeSeconds;
	Point head;
	final int LENGTH = 5;
	double SPEED = 0.1; // sollte die Geschwindigkeit doch zu schnell sein, hier können Sie umstellen
	PImage body;
	PImage headLeft;
	PImage headRight;
	PImage headDown;
	PImage headUp;
	PImage image;

	int x;
	int y;
	int direction = -1;

	int left = -2;
	int right = -1;
	int down = 0;
	int up = 1;
	int oldDirection = right;
	int counter;
	int directionPrompt = 0;
	boolean collided = false;

	LinkedList<GameObject> objects;
	LinkedList<Point> ghostSnakeParts = new LinkedList<Point>();

	public GhostSnake(PApplet p, SnakeGame game) {

		body = p.loadImage("hdm/lab/snake/gBody.PNG");
		headLeft = p.loadImage("hdm/lab/snake/gHead left.PNG");
		headRight = p.loadImage("hdm/lab/snake/gHead right.PNG");
		headDown = p.loadImage("hdm/lab/snake/gHead down.PNG");
		headUp = p.loadImage("hdm/lab/snake/gHead up.PNG");

		do {
			x = Toolbox.randomInt(6, 20) * SnakeGame.FIELD_SIZE; // es soll so lange eine neue XPosition gewürfelt
																	// werden, bis GhostSnake nicht auf Snakes Position
																	// ist
		} while (x == game.schlange.positionX);

		do {
			y = Toolbox.randomInt(1, 22) * SnakeGame.FIELD_SIZE + SnakeGame.PANEL_HEIGHT; // ebenso die YPosition
		} while (y == game.schlange.positionY);

		this.positionX = x;
		this.positionY = y;

		ghostSnakeSeconds = new StopWatch();

		spawnSnake(p);

	}

	public LinkedList<Point> spawnSnake(PApplet p) { // GhostSnake spawnt
		image = headRight;

		head = new Point(this.positionX, this.positionY);

		this.ghostSnakeParts.add(head);

		for (int i = 1; i < LENGTH; i++) {
			this.ghostSnakeParts.add(new Point(this.positionX - SnakeGame.FIELD_SIZE * i, this.positionY));
			p.image(body, (ghostSnakeParts.get(i)).x, ghostSnakeParts.get(i).y, SnakeGame.FIELD_SIZE,
					SnakeGame.FIELD_SIZE);
		}

		return ghostSnakeParts;
	}

	public void move(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if (counter == 5) { // GhostSnake soll mindestens 5 mal in die gleiche Richtung laufen, damit die
							// Bewegung nicht zu wirr wird
			direction = Toolbox.randomInt(-3, 2); // neue Richtung wird gewürfelt
			counter = 0;
		}

		collided = false;
		int speedX = 0;
		int speedY = 0;

		collide(game, objects, p); // prüfe nach, ob GhostSnake mit der Hecke kollidiert und passe die direction an

		image = headRight;

		// jetzt wirds lustig

		if (!collided) { // wenn sie nicht kollidiert, wird die Richtung random gewürfelt

			if (direction == left && (oldDirection == right)) { // war die alte Richtung rechts, darf die neue nicht
																// links sein, sonst rennt GhostSnake in sich selbst
																// rein
				// würfel neue Richtung, bis es nicht links ist
				do {
					direction = Toolbox.randomInt(-3, 2);
				} while (direction == -1);
			}
		}

		if (direction == left && (oldDirection != right)) { // nach links laufen

			speedX = -30;
			speedY = 0;
			oldDirection = direction;
			image = headLeft;

		}
		if (!collided) {
			if (direction == right && (oldDirection == left)) { // war die alte Richtung links, darf sie nicht nach
																// rechts laufen
				direction = Toolbox.randomInt(-3, 2);
				do {
					direction = Toolbox.randomInt(-3, 2);
				} while (direction == -2);
			}
		}
		if (direction == right && (oldDirection != left)) { // rechts laufen

			speedX = 30;
			speedY = 0;
			oldDirection = direction;
			image = headRight;

		}
		if (!collided) {
			if (direction == up && (oldDirection == down)) { // war die alte Richtung unten, darf sie nicht hoch laufen
				do {
					direction = Toolbox.randomInt(-3, 2);
				} while (direction == 1);
			}
		}
		if (direction == up && (oldDirection != down)) { // hoch laufen

			speedX = 0;
			speedY = -30;
			oldDirection = direction;
			image = headUp;

		}
		if (!collided) {
			if (direction == down && (oldDirection == up)) { // war die alte Richtung oben, darf sie nicht nach unten
																// laufen
				do {
					direction = Toolbox.randomInt(-3, 2);
				} while (direction == 0);
			}
		}
		if (direction == down && (oldDirection != up)) { // nach unten laufen

			speedX = 0;
			speedY = 30;
			oldDirection = direction;
			image = headDown;

		}

		

		positionX += speedX; // hier wird die eigentliche Position des Kopfes erst verändert
		positionY += speedY;

		
		moveBody(p); // und nun soll der Body dazubewegt werden
	

	}

	public boolean samePosition() {

		for (int i = 0; i < ghostSnakeParts.size(); i++) {// geisterschlange soll sich nicht selbst kreuzen
			if ((this.positionX == ghostSnakeParts.get(i).x) && (this.positionY == ghostSnakeParts.get(i).y)) {
				return true;
			}
		}
		return false;
	}

	public void moveBody(PApplet p) {

		Point newHead = new Point(this.positionX, this.positionY);

		head = newHead;

		ghostSnakeParts.add(0, head); // der neue Head wird hinzugefügt
		counter++; // GhostSnake ist einen Schritt gelaufen, deswegen wird Counter erhöht
		ghostSnakeParts.remove(ghostSnakeParts.get(ghostSnakeParts.size() - 1)); // das Ende wird entfernt

		// Bestimmung des Bildes für den Kopf
		if (direction == left) {

			image = headLeft;
		}
		if (direction == right) {

			image = headRight;

		}
		if (direction == up) {

			image = headUp;

		}
		if (direction == down) {

			image = headDown;

		}

	}

	public void collide(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if (positionX <= Hedge.hedgeLeft && direction == left) { // prüfe ob GhostSnake mit dem Gesicht nach vorne in
																	// die linke Hecke rennt
			if (positionY <= Hedge.hedgeUp) { // wenn es die Ecke links oben ist..

				direction = down; // ..weiche nach unten aus
			}
			if (positionY >= Hedge.hedgeDown) { // wenn es die Ecke links unten..

				direction = up; // ..weiche nach oben aus

			} else { // ist GhostSnake in keiner Ecke
				if (positionY < game.height / 2) {

					direction = down; // ..soll sie entweder nach unten
				} else {
					direction = up; // ..oder nach oben ausweichen, je nachdem ob sie eher oben oder unten ist (um
									// weitere Kollisionen aus dem Weg zu gehen)
				}
			}
			collided = true;

		

		}
		if (positionX >= Hedge.hedgeRight - SnakeGame.FIELD_SIZE && direction == right) { // prüfe ob GhostSnake mit dem
																							// Gesicht nach vorne in die
																							// rechte Hecke rennt
			if (positionY <= Hedge.hedgeUp) { // Ecke rechts oben

				direction = down;
			}
			if (positionY >= Hedge.hedgeDown) { // Ecke rechts unten

				direction = up;

			} else { // keine Ecke
				if (positionY < game.height / 2) {
					direction = down;
				} else {
					direction = up;
				}

			}
			collided = true;

			

		}

		if (positionY <= Hedge.hedgeUp && direction == up) { // prüfe ob GhostSnake mit dem Gesicht nach vorne in die
																// obere Hecke rennt

			if (positionX <= Hedge.hedgeLeft) { // Ecke links oben

				direction = right;
			}
			if (positionX >= Hedge.hedgeRight) { // Ecke rechts oben

				direction = left;

			} else { // keine Ecke
				if (positionX < game.width / 2) {
					direction = right;
				} else {
					direction = left;
				}
			}
			collided = true;

			

		}
		if (positionY >= Hedge.hedgeDown - SnakeGame.FIELD_SIZE && direction == down) { // prüfe ob GhostSnake mit dem
																						// Gesicht nach vorne in die
																						// untere Hecke rennt

			if (positionX <= Hedge.hedgeLeft) { // Ecke links unten

				direction = right;
			}
			if (positionX >= Hedge.hedgeRight) { // Ecke rechts unten

				direction = left;

			} else { // keine Ecke
				if (positionX < game.width / 2) {
					direction = right;
				} else {
					direction = left;
				}
			}
			collided = true;
			
		}
	}

	@Override
	public void draw(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {

		if ((double) ghostSnakeSeconds.getElapsedTime() / 1E9 >= SPEED) { // GhostSnake bewegt sich innerhalb einer
																			// gewissen Zeitspanne

			move(game, game.objects, p);
			ghostSnakeSeconds.reset();
		}

		for (int i = 0; i < ghostSnakeParts.size(); i++) { // zeichne GhostSnake
			if (i == 0) { // zeichne den Kopf
				p.image(image, (ghostSnakeParts.get(i)).x, ghostSnakeParts.get(i).y, SnakeGame.FIELD_SIZE,
						SnakeGame.FIELD_SIZE);

			} else { // zeichne den Body
				p.image(body, (ghostSnakeParts.get(i)).x, ghostSnakeParts.get(i).y, SnakeGame.FIELD_SIZE,
						SnakeGame.FIELD_SIZE);

			}
		}

	}

	@Override
	public boolean isDead(SnakeGame game) {
		// GhostSnake ist unbesiegbar muhaha!
		return false;
	}

}
