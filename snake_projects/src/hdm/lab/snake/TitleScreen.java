package hdm.lab.snake;

import hdm.shared.Toolbox;
import processing.core.PApplet;

public class TitleScreen {

	int positionX = 300;
	int positionY = 500;
	int positionX2 = 270;
	int positionY2 = 590;
	short[] click = Toolbox.loadAudioFile("/hdm/lab/snake/pickupCoin.wav");
	
	
	
	public void draw(PApplet p, SnakeGame game) {
		if(PApplet.second()%2 == 0) { // blinkende Schrift
			p.fill(200); 
		}
		else {
			
			p.fill(255);
		}
		
		
		p.textSize(400);
		p.text("SNAKE", 30, 130);
		p.textSize(100);
		p.text("LOST IN SPACE", 220, 220);
		
		p.fill(72, 36, 88,0);
		p.noStroke();
		
		p.rect(positionX, positionY, 200, 60); // unsichtbares Rechteck zum Draufklicken
		
		p.fill(255,255,255,255);
		
		p.text("PLAY",320 , 550);
		
		if((p.mouseX>=positionX)&& ( p.mouseX <= positionX + 200)&&(p.mouseY>=positionY)&&(p.mouseY <= positionY+60)) {
			p.fill(255,0,0);

			p.text("PLAY",320 , 550);
			
			if(p.mousePressed) {
		
				game.GAMESTARTED = true; // Game wird bei Klick gestartet und Timer wird resetted
				game.gameTimer.reset();
				Toolbox.playArrayAsynchronuously(click);
			}
		}
		p.fill(72, 36, 88,0);
		p.noStroke();
		
		p.rect(positionX2, positionY2, 200, 60);  // unsichtbares Rechteck zum Draufklicken
	
		p.fill(255,255,255,255);
		
		p.text("HARDCORE", 280, 630);
		
		if((p.mouseX>=positionX2)&& ( p.mouseX <= positionX2 + 200)&&(p.mouseY>=positionY2)&&(p.mouseY <= positionY2+60)) {
			p.fill(255,0,0);

			p.text("HARDCORE",280 , 630);
			
			if(p.mousePressed) {
			
				game.GAMESTARTED = true; // und ab geht die Post!
				game.hardCore  =true; 
				game.gameTimer.reset();
				Toolbox.playArrayAsynchronuously(click);
			}
		}
	}
	
}
