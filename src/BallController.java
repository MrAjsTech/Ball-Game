import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.Random;


public class BallController {
	private int pos_x;
	private int pos_y;
	private int x_speed;
	private int y_speed;
	private int radius;
	public static BallModel player;

	private int first_x;
	private int first_y;
	boolean isStopped = true;
	private int maxspeed;
	BallView view;
	private final int x_leftout = 10;
	private final int x_rightout = 370;
	private final int y_upout = 45;
	private final int y_downout = 370;
	
	public static int speed;
	
	
	
	Color color;
	
	AudioClip out;
	
	
	
	Random rnd = new Random();
	
	public BallController (int radius, int x, int y, int vx, int vy, int ms, Color color, AudioClip out, BallModel player)
	{
		view = new BallView();
		player = new BallModel();
		//make balls in view
		
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


public void runThread() {
	
	Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	
	while (true)
	{

		
		if (player.getLives() >= 0 && !isStopped)
		{
			view.redball.move();
			view.blueball.move();
		}
		
		view.repaint();
		
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

			public boolean paint() {
				//if lives still remain
				boolean check = false;
			if (player.getLives() >=0)
			{	//set the background color
				check=true;
			}
			
			else if (player.getLives() < 0)
			{
					check=false;
				
			}
			return check;
			
			
			
}
}
