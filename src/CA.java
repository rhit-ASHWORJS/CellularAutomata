import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class CA extends JComponent{
	JFrame display;
	ArrayList<int[]> pastWorlds = new ArrayList<int[]>();
	int[] world;
	int rule;
	int[] ruleOutputs = new int[8];
	int boxWidth = 2;
	int frameGens = 400;
	
	public CA(int size, int rule)
	{
		world = new int[size];
		setupRule(rule);
		for(int i=0; i<size; i++)
		{
			world[i]=SeededRandom.rnd.nextInt(2);
		}
		pastWorlds.add(world);
	}
	
	public CA(int[] start, int rule)
	{
		this.world = start;
		setupRule(rule);
		pastWorlds.add(world);
	}
	
	public int calculateTransientDuration()
	{
		while(!worldAlreadySeen(nextWorld()) && pastWorlds.size() <= 1003)
		{
			step();
		}
		if(pastWorlds.size() > 1000)
		{
			return 1000;//transient not found
		}
		return pastWorlds.size()-1; //don't count the initial state
	}
	
	public double getLambda() 
	{
		double numones = 0;
		for(Integer num : ruleOutputs)
		{
			numones += num;
		}
		return numones/ruleOutputs.length;
	}
	
	public boolean worldAlreadySeen(int[] world)
	{
		for(int[] pastWorld : pastWorlds)
		{
			boolean seen = true;
			for(int i=1; i<world.length; i++)//don't count the initial state
			{
				if(pastWorld[i] != world[i])
				{
					seen = false;
				}
			}
			if(seen)
			{
				return true;
			}
		}
		return false;
	}

	public void setupRule(int rule)
	{
		this.rule = rule;
		
		if(this.rule > 255)
		{
			this.rule = 255;
		}
		int index = 0;
		for(int test=128; test>=1; test/=2)
		{
			if(rule >= test)
			{
				rule -= test;
				ruleOutputs[7-index]=1;
			}
			index++;
		}
	}
	
	public void step() 
	{
		world = nextWorld();
		pastWorlds.add(world);
	}
	
	public int[] nextWorld()
	{
		int[] newWorld = new int[world.length];
		for(int i=0; i<world.length; i++)
		{
			newWorld[i] = applyRule(i);
		}
		return newWorld;
	}
	
	public int applyRule(int dest)
	{
		int left;
		int mid;
		int right;
		
		if(dest == 0)
		{
			left = world[world.length-1];
			mid = world[dest];
			right = world[dest+1];
		}
		else if(dest == world.length-1)
		{
			left = world[dest-1];
			mid = world[dest];
			right = world[0];
		}
		else
		{
			left = world[dest-1];
			mid = world[dest];
			right = world[dest+1];
		}
		
		return ruleOutputs[left*4+mid*2+right];
	}
	
	public void frameInit() 
	{
		this.display = new JFrame();
		this.display.setTitle("CAViewer");
		this.display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.display.add(this);
		this.display.setSize(new Dimension(world.length*boxWidth + 15, frameGens*boxWidth));
		this.display.setVisible(true);
	}
	
	public void updateFrame()
	{
		this.display.repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		int init = Math.max(0, pastWorlds.size()-frameGens+2);
		for(int startGen = init; startGen<pastWorlds.size(); startGen++)
		{
			for(int cell=0; cell<world.length; cell++)
			{
				if(pastWorlds.get(startGen)[cell] == 0)
				{
					g2d.setColor(Color.GRAY);
				}
				else
				{
					g2d.setColor(Color.ORANGE);
				}
				g2d.fillRect(cell*boxWidth, (startGen-init)*boxWidth, boxWidth, boxWidth);
				g2d.setColor(Color.BLACK);
				g2d.drawRect(cell*boxWidth, (startGen-init)*boxWidth, boxWidth, boxWidth);
			}
		}
	}
}
