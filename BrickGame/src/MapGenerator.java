import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator 
{
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	public MapGenerator (int row, int col)//4X12
	{
		map = new int[row][col];		
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				map[i][j] = 1;//1 indicates the brick that not yet break, when it breaks its value change into 0.
			}			
		}
		
		brickWidth = 960/col;
		brickHeight = 180/row;
	}	
	
	public void draw(Graphics2D g)//This Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout. This is the fundamental class for rendering 2-dimensional shapes, text and images on the Java(tm) platform.
	{
		for(int i = 0; i<map.length; i++)
		{
			for(int j =0; j<map[0].length; j++)
			{
				if(map[i][j] > 0)
				{
					g.setColor(Color.white);
					g.fillRect(j * brickWidth +220 , i * brickHeight + 50, brickWidth, brickHeight);
					
					// this is just to show separate brick, game can still run without it
//The BasicStroke class states colors in the default sRGB color space or colors in arbitrary color spaces identified by a ColorSpace.
                                        g.setStroke(new BasicStroke(3));
//Constructs a solid BasicStroke with the specified line width and with default values for the cap and join styles.
					
					g.setColor(Color.black);
					g.drawRect(j * brickWidth +220 , i * brickHeight + 50, brickWidth, brickHeight);				
				}
			}
		}
	}
	
	public void setBrickValue(int value, int row, int col)
	{
		map[row][col] = value;
	}
}
