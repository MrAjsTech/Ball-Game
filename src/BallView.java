import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;




public class BallView extends Applet implements Runnable{
	
	//Declare variables
	
	
	
	BallController redball, blueball;
	AudioClip shotnoise;
	AudioClip hitnoise;
	AudioClip outnoise;
		
	
	Thread th;
	
	//Audio Clips
	
	
	//new Font
	Font f = new Font("Serif", Font.BOLD, 20);
	
	//Set up cursor
	Cursor c;	//cursor variable	
	
	private Image dbImage;
	private Graphics dbg;
	
	public void init(){
		setSize(400,400);
		c = new Cursor (Cursor.CROSSHAIR_CURSOR);
		this.setCursor(c);
		
		hitnoise = getAudioClip (getCodeBase(), "gun.au");
		hitnoise.play();
		hitnoise.stop();
		shotnoise = getAudioClip (getCodeBase(), "miss.au");
		shotnoise.play();
		shotnoise.stop();
		outnoise = getAudioClip (getCodeBase(), "error.au");
		outnoise.play();
		outnoise.stop();
		
		Color superblue = new Color (0, 0, 255);
		redball = new BallController (10, 190, 250, 1, -1, 4, Color.red, outnoise, BallController.player);
		blueball = new BallController (10, 190, 150, 1, 1, 3, Color.blue, outnoise, BallController.player);
		setBackground(Color.black);
		
		setFont(f);
		
		if (getParameter ("speed") != null)
		{
			redball.speed = Integer.parseInt(getParameter("speed"));
			blueball.speed = Integer.parseInt(getParameter("speed"));
		}
		else {
			redball.speed= 15;
			blueball.speed = 15;
		}
		
		
					
	}
	
	public void start(){
		th = new Thread(this);
		th.start();
	}
	
	public void stop(){
		th.stop();
	}
	
	
	
	public void run(){
		//where this code gets moved... it needs to be called on runtime
		redball.runThread();
		blueball.runThread();
		
	}
	
	public void paint (Graphics g){
		//go back to controller make new method and call here
		//begin boolean method
		if (redball.paint()==true&&blueball.paint()==true) {
			g.setColor(Color.yellow);
			//display standings
			g.drawString("Score: " + redball.player.getScore(), 10, 40);
			g.drawString("Lives: " + redball.player.getLives(), 300, 40);
			
			//draw the balls
			redball.drawBall(g);
			blueball.drawBall(g);
			
			if (redball.isStopped&&blueball.isStopped)
			{
				g.setColor (Color.yellow);
				g.drawString("Double-click on Applet to start game.", 40, 200);
			}
		}
		
		else if (redball.paint()==false&&blueball.paint()==false) {
			g.setColor(Color.yellow);
			
			//points and game over
			g.drawString("Game Over!", 130, 100);
			g.drawString("You scored " + redball.player.getScore() + " Points!", 90, 140);
			
			//Assessment of points
			if (redball.player.getScore() < 300) g.drawString ("Beginner", 100, 190);
			else if (redball.player.getScore() < 600 && redball.player.getScore() >= 300 ) 
				g.drawString ("Amature", 100, 190);
			else if (redball.player.getScore() < 900 && redball.player.getScore() >= 600 ) 
				g.drawString ("Good", 100, 190);
			else if (redball.player.getScore() < 1200 && redball.player.getScore() >= 900 ) 
				g.drawString ("Excellent", 100, 190);
			else if (redball.player.getScore() < 1500 && redball.player.getScore() >= 1200 ) 
				g.drawString ("Champion", 100, 190);
			else if (redball.player.getScore() >= 1500) 
				g.drawString ("Too good for game", 100, 190);
			g.drawString ("Double-click on the Applet to play again", 20, 220);
			redball.isStopped = true;
		}
		
		
		
		
		
		
		
		
		
	}
	
public boolean mouseDown(Event e, int x, int y){
		
		if (!redball.isStopped)
		{
			if (redball.userHit(x,y))
			{
				hitnoise.play();
				redball.ballWasHit();
			}
			if (blueball.userHit(x,y))
			{
				hitnoise.play();
				blueball.ballWasHit();
			}
			else
			{
				shotnoise.play();
			}
		}
		
		else if (redball.isStopped && e.clickCount == 2)
		{
			redball.isStopped = false;
			init();
		}
		
		return true;
	}
	
	public void update (Graphics g){
		
		if (dbImage == null)
		{
			dbImage = createImage (this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics();
		}
		
		dbg.setColor (getBackground());
		dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
		
		dbg.setColor (getForeground());
		paint (dbg);
		
		g.drawImage (dbImage, 0, 0, this);
	}
	
	
}