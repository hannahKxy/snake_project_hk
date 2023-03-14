package hdm.lab.snake;

import java.util.LinkedList;
import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class SnakeGame extends PApplet {

	final static int FIELD_SIZE = 30;
	final static int FIELD_HEIGHT = 720;
	final static int FIELD_WIDTH = 720;
	final static int PANEL_HEIGHT = 80;

	boolean GAMESTARTED = false;
	boolean GAMEOVER = false;
	boolean hardCore = false;
	int appleX = width / 2;
	int appleY = 0;

	boolean timeUp = false;

	int starX = 0;
	int starY = Toolbox.randomInt(PANEL_HEIGHT, height / 2);
	int time;
	int currentSecond = second();
	StopWatch gameTimer;
	int points;
	int startingTime = 40;
	int seconds;
	PImage title;
	PImage background;
	PFont myFont;
	PImage gameOver;
	PImage score;
	PImage apple;
	Snake schlange;
	static SnakeGame myGame;
	LinkedList<GameObject> objects;
	Apple apfel;
	Hedge hecke;
	Snail schnecke1;
	Snail schnecke2;
	Snail schnecke3;
	GhostSnake geisterschlange1;
	GhostSnake geisterschlange2;
	GhostSnake geisterschlange3;
	Star[] stars = new Star[10];

	public static void main(String args[]) {

		PApplet.main(new String[] { SnakeGame.class.getName() });
		myGame = new SnakeGame();
		myGame.objects = new LinkedList<GameObject>(); // erzeugen einer Liste aller Gameobjects

	}

	public SnakeGame() {

		gameTimer = new StopWatch(); // zur Messung der Zeit
	}

	public void settings() {
		size(720, 800);
	}

	public void setup() {

		title = loadImage("hdm/lab/snake/snake background.png");
		score = loadImage("hdm/lab/snake/score.jpg");
		gameOver = loadImage("hdm/lab/snake/gameover.jpg");
		background = loadImage("hdm/lab/snake/star background.PNG");
		Toolbox.playAudioFileAsychronouslyLooped("/hdm/lab/snake/8-Bit Adventure.wav");
		myFont = createFont("hdm/lab/snake/Cloude_Regular_1.02.ttf", 100);
		apple = loadImage("hdm/lab/snake/Apple.PNG");
		textFont(myFont);

		myGame.schlange = new Snake(this);
		myGame.objects.add(0, myGame.schlange);
		myGame.hecke = new Hedge(this, myGame, myGame.objects);
		myGame.objects.add(1, myGame.hecke);
		myGame.apfel = new Apple(this);
		myGame.objects.add(2, myGame.apfel);
		myGame.schnecke1 = new Snail(this);
		myGame.objects.add(3, myGame.schnecke1);
		myGame.schnecke2 = new Snail(this);
		myGame.objects.add(4, myGame.schnecke2);
		myGame.schnecke3 = new Snail(this);
		myGame.objects.add(5, myGame.schnecke3);
		myGame.geisterschlange1 = new GhostSnake(this, myGame);
		myGame.objects.add(6, myGame.geisterschlange1);
		myGame.geisterschlange2 = new GhostSnake(this, myGame);
		myGame.objects.add(7, myGame.geisterschlange2);
		myGame.geisterschlange3 = new GhostSnake(this, myGame);
		myGame.objects.add(8, myGame.geisterschlange3);

		for (int i = 0; i < 10; i++) {
			myGame.stars[i] = new Star(this);

		}

	}

	public void draw() {

		if (!myGame.GAMESTARTED) { // wenn das Game noch nicht gestartet ist, soll der Titel Bildschirm erscheinen

			background(title);
			TitleScreen myScreen = new TitleScreen();
			myScreen.draw(this, myGame);

		}

		if (myGame.GAMESTARTED) { // hier beginnt das Game

			if (!timeUp) {
				if (!myGame.schlange.isDead(myGame)) { // solange die Snake noch lebt...

					hardCoreMode(myGame);

					background(background);
					fill(255);

					starryBackground(myGame);
					showPoints(myGame);

					strokeWeight(1);
					stroke(0);

					for (int vertical = 30; vertical <= FIELD_WIDTH; vertical += FIELD_SIZE) { // Raster Feld
						line(vertical, 80, vertical, height);
					}
					for (int horizontal = 80; horizontal <= FIELD_HEIGHT + 80; horizontal += FIELD_SIZE) {
						line(0, horizontal, width, horizontal);

					}

					showTimer(myGame);

					myGame.hecke.draw(myGame, myGame.objects, this);

					myGame.schlange.draw(myGame, myGame.objects, this);

					myGame.apfel.draw(myGame, myGame.objects, this);

					myGame.schnecke1.draw(myGame, myGame.objects, this);

					myGame.schnecke2.draw(myGame, myGame.objects, this);

					myGame.schnecke3.draw(myGame, myGame.objects, this);

					myGame.geisterschlange1.draw(myGame, objects, this);

					myGame.geisterschlange2.draw(myGame, objects, this);

					myGame.geisterschlange3.draw(myGame, objects, this);

				}
				if (myGame.schlange.isDead(myGame)) { // GAMEOVER
					myGame.GAMEOVER = true;

					gameOverScreen(); // zeige den GAMEOVER Bildschirm
				}
			}
			if (timeUp) { // wenn die Zeit um ist, zeige Score an
				background(score);
				fill(255, 255, 255);
				textSize(280);
				text("YOUR SCORE: " + myGame.points, 30, 200);
				text(myGame.points, 320, 600);
				rainApples(myGame);
			}
		}

	}

	public void showPoints(SnakeGame game) {

		text(game.points, 630, 60);

	}

	public void showTimer(SnakeGame game) {

		// Timer zählt die vergangene Zeit in Nanosekunden
		seconds = (int) (game.gameTimer.getElapsedTime() / 1E9);
		time = startingTime - seconds;
		if (time > 0) {

			text(time, 60, 60);

			if (game.hardCore) {
				textSize(100);
				text("HARDCORE-SNAKE", 200, 60);
			} else {
				textSize(100);
				text("SPACE-SNAKE", 230, 60);
			}
		}

		if (time <= 0) {

			timeUp = true;

		}

	}

	public void starryBackground(SnakeGame game) { // Eye Candy für den Space-Look

		for (int i = 0; i < stars.length; i++) {
			myGame.stars[i].move(this, myGame);
		}

		if ((time < startingTime / 2 && time > (startingTime / 2) - 10) || game.hardCore) { // ist die Hälfte der Zeit
																							// erreicht oder der
																							// Hardcore-Modus aktiviert,
																							// gibt es einen Meteor
																							// Schauer
			for (int i = 0; i < stars.length; i++) {
				myGame.stars[i].meteorShower(this, myGame);
			}
		}

	}

	public void gameOverScreen() {

		background(gameOver);

		fill(255, 255, 255);
		textSize(300);
		text("GAME OVER", 40, 300);

	}

	public void rainApples(SnakeGame game) { // die erreichten Punkte werden visualisiert durch herunterfallende Äpfel

		int apples[][] = new int[game.points][game.points];
		for (int i = 0; i < apples.length; i++) {
			for (int k = 0; k < apples[i].length; k++) {
				apples[i][k] = Toolbox.randomInt(i, k);
				image(apple, i * 50 + appleX, appleY, 30, 30);

			}
		}
		appleY += 8;

	}

	public void hardCoreMode(SnakeGame game) { // für alle, die zu viele Energy Drinks getrunken haben...

		if (game.hardCore) {

			startingTime = 20;

			game.schlange.SPEED = 0.05; // I AM SPEEEEEED
			game.geisterschlange1.SPEED = 0.02;
			game.geisterschlange2.SPEED = 0.02;
			game.geisterschlange3.SPEED = 0.02;
			game.schnecke1.SPEED = 0.8;
			game.schnecke2.SPEED = 0.8;
			game.schnecke3.SPEED = 0.8;

		}
	}

}
