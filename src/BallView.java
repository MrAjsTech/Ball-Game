import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.awt.Image;


public class BallView extends Applet implements Runnable{
	private int pos_x;
	private int pos_y;
	private int x_speed;
	private int y_speed;
	private int radius;
	
	private int first_x;
	private int first_y;
	
	private int maxspeed;
	
	private final int x_leftout = 10;
	private final int x_rightout = 370;
	private final int y_upout = 45;
	private final int y_downout = 370;
	
	BallController controller;
	BallModel model;
	
	Color color;
	
	AudioClip out;
	
	BallModel player;
	
	Random rnd = new Random();
	
	public BallView (int radius, int x, int y, int vx, int vy, int ms, Color color, AudioClip out, BallModel player)
	{
		controller = new BallController();
		model = new BallModel();
		this.radius = radius;
		pos_x = x;
		pos_y = y;
		first_x = x;
		first_y = y;
		x_speed = vx;
		y_speed = vy;
		maxspeed = ms;
		this.color = color;
		this.out = out;
		this.player = player;
		
	}
	
	public void move()
	{
		pos_x += x_speed;
		pos_y += y_speed;
		isOut(); //method to test if the ball is out of bounds
	}
	
	public void ballWasHit()
	{
		pos_x = first_x;
		pos_y = first_y;
		
		x_speed = (rnd.nextInt()) % maxspeed;
	}
	
	public boolean userHit(int mouse_x, int mouse_y)
	{
		double x = mouse_x - pos_x;
		double y = mouse_y - pos_y;
		double distance = Math.sqrt((x*x) + (y*y));
		
		if (distance < 15)
		{
			player.addScore(10*Math.abs(x_speed)+10);
			return true;
		}
		else return false;
	}
	
	private boolean isOut()
	{
		if (pos_x < x_leftout)
		{
			pos_x = first_x;
			pos_y = first_y;
			
			out.play();
			
			x_speed = (rnd.nextInt()) % maxspeed;
			
			player.loseLife();
			
			return true;
		} 
		else if (pos_x > x_rightout)
		{
			pos_x = first_x;
			pos_y = first_y;
			
			out.play();
			
			x_speed = (rnd.nextInt()) % maxspeed;
			
			player.loseLife();
			
			return true;
		}
		else if (pos_y < y_upout)
		{
			pos_x = first_x;
			pos_y = first_y;
			
			out.play();
			
			x_speed = (rnd.nextInt()) % maxspeed;
			
			player.loseLife();
			
			return true;
		}
		else if (pos_y > y_downout)
		{
			pos_x = first_x;
			pos_y = first_y;
			
			out.play();
			
			x_speed = (rnd.nextInt()) % maxspeed;
			
			player.loseLife();
			
			return true;
		}
		else return false;
	}
	
	public void drawBall(Graphics g)
	{
		g.setColor(color);
		g.fillOval(pos_x - radius, pos_y - radius, 2*radius, 2*radius);
	}
	
	public void run(){
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		
		while (true)
		{
			
			if (player.getLives() >= 0 && !controller.isStopped)
			{
				controller.redball.move();
				controller.blueball.move();
			}
			
			repaint();
			
			try
			{
				Thread.sleep (controller.speed);
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
			controller.redball.drawBall(g);
			controller.blueball.drawBall(g);
			
			if (controller.isStopped)
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
			controller.isStopped = true;
		}
		
		
		
		
	}
	
	public void update (Graphics g){
		
		if (controller.dbImage == null)
		{
			controller.dbImage = createImage (this.getSize().width, this.getSize().height);
			controller.dbg = controller.dbImage.getGraphics();
		}
		
		controller.dbg.setColor (getBackground());
		controller.dbg.fillRect (0, 0, this.getSize().width, this.getSize().height);
		
		controller.dbg.setColor (getForeground());
		paint (controller.dbg);
		
		g.drawImage (controller.dbImage, 0, 0, this);
	}
	
	
}





