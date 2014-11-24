import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

//hi


public class BallView extends Applet implements Runnable{
	
	//Declare variables
	private int speed;
	
	boolean isStopped = true;
	
	//Declare objects
	private BallModel player;
	private BallController redball;
	private BallController blueball;	
	
	Thread th;
	
	//Audio Clips
	AudioClip shotnoise;
	AudioClip hitnoise;
	AudioClip outnoise;
	
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
		
		Color superblue = new Color (0, 0, 255);
		
		setBackground(Color.black);
		
		setFont(f);
		
		//set speed of applet
		
		if (getParameter ("speed") != null)
		{
			speed = Integer.parseInt(getParameter("speed"));
		}
		else speed = 15;
		
		//preload audio files
		
		hitnoise = getAudioClip (getCodeBase(), "gun.au");
		hitnoise.play();
		hitnoise.stop();
		shotnoise = getAudioClip (getCodeBase(), "miss.au");
		shotnoise.play();
		shotnoise.stop();
		outnoise = getAudioClip (getCodeBase(), "error.au");
		outnoise.play();
		outnoise.stop();
		
		//initialize game objects
		player = new BallModel();
		redball = new BallController (10, 190, 250, 1, -1, 4, Color.red, outnoise, player);
		blueball = new BallController (10, 190, 150, 1, 1, 3, Color.blue, outnoise, player);
		
					
	}
	
	public void start(){
		th = new Thread(this);
		th.start();
	}
	
	public void stop(){
		th.stop();
	}
	
	public boolean mouseDown(Event e, int x, int y){
		
		if (!isStopped)
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
		
		else if (isStopped && e.clickCount == 2)
		{
			isStopped = false;
			init();
		}
		
		return true;
	}
	
	public void run(){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		while (true)
		{
			
			if (player.getLives() >= 0 && !isStopped)
			{
				redball.move();
				blueball.move();
			}
			
			repaint();
			
			try
			{
				Thread.sleep (speed);
			}
			catch (InterruptedException ex)
			{
				//do nothing
			}
			
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		}
	}
	
	public void paint (Graphics g){
		//if lives still remain
		if (player.getLives() >=0)
		{	//set the background color
			g.setColor(Color.yellow);
			//display standings
			g.drawString("Score: " + player.getScore(), 10, 40);
			g.drawString("Lives: " + player.getLives(), 300, 40);
			
			//draw the balls
			redball.drawBall(g);
			blueball.drawBall(g);
			
			if (isStopped)
			{
				g.setColor (Color.yellow);
				g.drawString("Double-click on Applet to start game.", 40, 200);
			}
		}
		
		else if (player.getLives() < 0)
		{
			g.setColor(Color.yellow);
			
			//points and game over
			g.drawString("Game Over!", 130, 100);
			g.drawString("You scored " + player.getScore() + " Points!", 90, 140);
			
			//Assessment of points
			if (player.getScore() < 300) g.drawString ("You got no swag", 100, 190);
			else if (player.getScore() < 600 && player.getScore() >= 300 ) 
				g.drawString ("Do better! No swag", 100, 190);
			else if (player.getScore() < 900 && player.getScore() >= 600 ) 
				g.drawString ("Almost swag", 100, 190);
			else if (player.getScore() < 1200 && player.getScore() >= 900 ) 
				g.drawString ("Beast status", 100, 190);
			else if (player.getScore() < 1500 && player.getScore() >= 1200 ) 
				g.drawString ("Champ status achieved", 100, 190);
			else if (player.getScore() >= 1500) 
				g.drawString ("Major swag status achieved", 100, 190);
			g.drawString ("Double-click on the Applet to play again", 20, 220);
			isStopped = true;
		}
		
		
		
		
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