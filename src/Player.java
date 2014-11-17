
public class Player {
	public int score;
	public int lives;
	
	public Player()
	{
		lives = 10;
		score = 0;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	public void addScore (int plus)
	{
		score += plus;
	}
	
	public void loseLife()
	{
		lives --;
	}
}
