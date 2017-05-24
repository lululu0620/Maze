// Name: Lu Xie
// USC loginid: luxie
// CS 455 PA3
// Spring 2017

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ListIterator;


/**
   MazeComponent class
 
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent{
	private static final int START_X = 10; // top left of corner of maze in frame
	private static final int START_Y = 10;
	private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
	private static final int BOX_HEIGHT = 20;
	private static final int INSET = 2;  // how much smaller on each side to make entry/exit inner box
	private Maze maze;

   
   /**
    Constructs the component.
    @param maze   the maze to display
   */
	public MazeComponent(Maze maze) {   
		this.maze = maze;
	}

   
   /**
    Draws the current state of maze including the path through it if one has
    been found.
    @param g the graphics context
   */
	public void paintComponent(Graphics g) {  
		
		Rectangle background = new Rectangle(START_X, START_Y, maze.numCols()*BOX_WIDTH, maze.numRows()*BOX_HEIGHT);
		Rectangle startbox = new Rectangle(START_X + maze.getEntryLoc().getCol()*BOX_WIDTH + INSET/2, START_Y + maze.getEntryLoc().getRow()*BOX_HEIGHT + INSET/2, BOX_WIDTH-INSET, BOX_HEIGHT-INSET);
		Rectangle endbox = new Rectangle(START_X + maze.getExitLoc().getCol()*BOX_WIDTH + INSET/2, START_Y + maze.getExitLoc().getRow()*BOX_HEIGHT + INSET/2,BOX_WIDTH-INSET, BOX_HEIGHT-INSET);   
		
		Graphics2D g2 = (Graphics2D) g;
		
		g2.draw(background);
		g2.setColor(Color.WHITE);
		g2.fill(background);
		
		wallComponent(g);
		
		g2.setColor(Color.YELLOW);
		g2.fill(startbox);
		
		g2.setColor(Color.GREEN);
		g2.fill(endbox);
		
		if (maze.getPath().size() > 0) pathComponent(g);
	} 
	
   /**
    Draws the walls in the maze
    @param g the graphics context
   */
	private void wallComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g; 
		g2.setColor(Color.BLACK);
		Rectangle wall = new Rectangle(START_X, START_Y, BOX_WIDTH, BOX_HEIGHT);  
		
		for (int i=0; i<maze.numRows(); i++) {
			for (int j=0; j<maze.numCols(); j++) {
				if (maze.hasWallAt(new MazeCoord(i,j))) {
					wall.translate(j*BOX_WIDTH, i*BOX_HEIGHT);
					g2.fill(wall);
					wall.translate(-j*BOX_WIDTH, -i*BOX_HEIGHT);
				}
			}
		}
	}
	
	/**
    Draws the path that the method Maze.search() find out in the maze
    @param g the graphics context
   */
	private void pathComponent(Graphics g) {
		Line2D.Double line;  
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLUE);
		
		ListIterator<MazeCoord> iter = maze.getPath().listIterator(); 
		MazeCoord pathstart= iter.next(); 
		MazeCoord pathend;
		
		while(iter.hasNext()) {
			pathend = iter.next();
			line = new Line2D.Double(START_X + pathstart.getCol()*BOX_WIDTH + BOX_WIDTH/2, START_Y + pathstart.getRow()*BOX_HEIGHT + BOX_HEIGHT/2, START_X + pathend.getCol()*BOX_WIDTH + BOX_WIDTH/2, START_Y + pathend.getRow()*BOX_HEIGHT + BOX_HEIGHT/2);
			g2.draw(line);
			pathstart = pathend;
		}
	}
}