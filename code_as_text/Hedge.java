package hdm.lab.snake;

import java.awt.Point;
import java.util.LinkedList;

import processing.core.PApplet;

public class Hedge implements GameObject {

	int step = 0;
	final static int hedgeLeft = 30;
	final static int hedgeRight = 690;
	final static int hedgeUp = 110;
	final static int hedgeDown = 740;
	SnakeGame game;
	LinkedList<GameObject> objects;
	PApplet p;

	public Hedge(PApplet p, SnakeGame game, LinkedList<GameObject> objects) {

		this.game = game;
		this.objects = objects;
		this.p = p;

	}

	public void move(SnakeGame game,LinkedList<GameObject> objects, PApplet p) {
		// hedge cannot move

	}

	public void collide(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {
		

	}

	public void draw(SnakeGame game, LinkedList<GameObject> objects, PApplet p) {
		
		
		
		for (int i = 0; i < 24; i++) { // vertikal
			for (int k = 0; k < 24; k++) { // horizontal
				if ((i <= 0 || i == 23) || (k < 1 || k > 22)) {
					p.fill(72, 36, 88);
					if(game.schlange.jetPackModeStarted || game.hardCore) {
						p.fill((i+3)*20,(i+3)*10,(i+3)*30);
					}

					p.rect(k * SnakeGame.FIELD_SIZE, (i * SnakeGame.FIELD_SIZE) + SnakeGame.PANEL_HEIGHT, SnakeGame.FIELD_SIZE,
							SnakeGame.FIELD_SIZE);
					
				}
			}
		}

	}

	public boolean isDead(SnakeGame game) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}
