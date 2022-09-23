import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GOL extends JComponent{
	JFrame display;
	int[][] world;
	int boxWidth = 20;
//	int frameHeig = 400;
	
	public GOL(int x,int y)
	{
		world = new int[x][y];
		for(int i=0; i<x; i++)
		{
			for(int j=0; j<y; j++)
			{
				world[i][j]=SeededRandom.rnd.nextInt(2);
			}
		}
	}
	
	public GOL(int[][] start)
	{
		this.world = start;
	}
	
	public void step()
	{
		this.world = nextWorld();
//		System.out.println(world);
	}
	
	public int[][] nextWorld()
	{
		int[][] newWorld = new int[world.length][world[0].length];
		for(int i=0; i<world.length; i++)
		{
			for(int j=0; j<world[0].length; j++)
			{
				newWorld[i][j] = applyRule(i,j);
			}
		}
		return newWorld;
	}
	
	public int applyRule(int x,int y)
	{
		int adjacent = 0;
		for(int xmod=-1; xmod<=1; xmod++)
		{
			for(int ymod=-1; ymod<=1; ymod++)
			{
				int newX = x+xmod;
				int newY = y+ymod;
				
				if(newX < 0)
				{
					newX = world.length-1;
				}
				if(newX >= world.length)
				{
					newX = 0;
				}
				if(newY < 0)
				{
					newY = world[0].length-1;
				}
				if(newY >= world[0].length)
				{
					newY = 0;
				}
				if(newX!=x || newY!=y)
				{
					adjacent += world[newX][newY];
				}
			}
		}
//		System.out.println(adjacent);
//		return 0;
//		
		if(adjacent < 2)
		{
			return 0;
		}
		if(adjacent == 2)
		{
			return world[x][y];
		}
		if(adjacent == 3)
		{
			return 1;
		}
		if(adjacent > 3)
		{
			return 0;
		}
		System.out.println("Oh no");
		return -1;
	}
	
	public void frameInit() 
	{
		this.display = new JFrame();
		this.display.setTitle("CAViewer");
		this.display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.display.add(this);
		this.display.setSize(new Dimension(world.length*boxWidth + 15, world[0].length*boxWidth));
		this.display.setVisible(true);
	}
	
	public void updateFrame()
	{
		this.display.repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
	;
		for(int x=0; x<world.length; x++)
		{
			for(int y=0; y<world.length; y++)
			{
				if(world[x][y] == 0)
				{
					g2d.setColor(Color.GRAY);
				}
				else
				{
					g2d.setColor(Color.ORANGE);
				}
				g2d.fillRect(x*boxWidth, y*boxWidth, boxWidth, boxWidth);
				g2d.setColor(Color.BLACK);
				g2d.drawRect(x*boxWidth, y*boxWidth, boxWidth, boxWidth);
			}
		}
	}
}
