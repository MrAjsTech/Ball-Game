import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class BallView {
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
	
	Color color;
	
	AudioClip out;
	
	BallModel player;
	
	Random rnd = new Random();
	
	public BallView (int radius, int x, int y, int vx, int vy, int ms, Color color, AudioClip out, BallModel player)
	{
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
}