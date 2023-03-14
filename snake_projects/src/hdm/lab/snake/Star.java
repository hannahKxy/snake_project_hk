package hdm.lab.snake;

import hdm.shared.Toolbox;
import processing.core.PApplet;
import processing.core.PImage;

public class Star {

	int starX;
	int starY;
	PImage meteor;

	public Star(PApplet p) {

		starX = 30;
		starY = Toolbox.randomInt(80, 720);

		meteor = p.loadImage("hdm/lab/snake/meteor.PNG");

	}

	public void move(PApplet p, SnakeGame game) {

		p.strokeWeight(4);
		p.stroke(255);
		p.point(starX, starY);
		starX += 4;
		starY++;

		if ((starX > Hedge.hedgeRight) || starY > Hedge.hedgeDown) {
			starX = 30;
			starY = Toolbox.randomInt(80, 720);

		}

	}

	public void meteorShower(PApplet p, SnakeGame game) {

		p.image(meteor, starX, starY);
		starX +=4;
		starY+=2;
		
	}

}
