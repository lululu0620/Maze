// Name: Lu Xie
// USC loginid: luxie
// CS 455 PA3
// Spring 2017
import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

 */

public class Maze {
	
/**
 *  Representation Invariant:
 *  integer row represents the number of rows of the maze.
 *  integer column represents the number of columns of the maze. 
 *  startLoc and endLoc represent the entrance and exit of the maze respectively. the type of them is MazeCoord.
 *  path is used to store the path that we find from the entrance to the exit in the maze.
 *  we store the maze in a 2D array whose type is integer:
	 	1 represents that there exits a wall
	 	0 represents that it is free
 *	when we search the path, we need to mark the maze that whether the box is visited or not. 
 *	if we have visited the box, we increase the value of this box in the array maze by 2.
 *  therefore, if maze[i][j] >= 2, it means we have visited maze[i][j].
 *  we have to recover the maze when we search the path again.
 */
	
	public static final boolean FREE = false;
	public static final boolean WALL = true;
	public static final int FREE_NUMBER = 0;
	public static final int WALL_NUMBER = 1;
	public static final int VISITED = 2;
	private int row;
	private int column;
	private MazeCoord startLoc;
	private MazeCoord endLoc;
	private int[][] maze;
	private LinkedList<MazeCoord> path;

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
	public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) {
		this.path = new LinkedList<MazeCoord>();
		this.startLoc = startLoc;
		this.endLoc = exitLoc;
		this.row = mazeData.length;
		this.column = mazeData[0].length;
		maze = new int[row+2][column+2];
		for (int i=1;i<=row;i++) {
			for (int j=1;j<=column;j++) {
				if (mazeData[i-1][j-1] == WALL) maze[i][j] = WALL_NUMBER;
				else maze[i][j] = FREE_NUMBER;
			}
		}
		for (int n=0;n<=column+1;n++) {
			maze[0][n] = WALL_NUMBER;
			maze[row+1][n] = WALL_NUMBER;
		}
		for (int m=0;m<=row+1;m++) {
			maze[m][0] = WALL_NUMBER;
			maze[m][column+1] = WALL_NUMBER;
		}
	}


   /**
      Returns the number of rows in the maze
      @return number of rows
   */
	public int numRows() {
		return row;   
	}

   
   /**
      Returns the number of columns in the maze
      @return number of columns
   */   
	public int numCols() {
		return column;   
	} 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
	public boolean hasWallAt(MazeCoord loc) {
		if(maze[loc.getRow()+1][loc.getCol()+1] == WALL_NUMBER) return true;
		else return false;
	}
   

   /**
      Returns the entry location of this maze.
    */
	public MazeCoord getEntryLoc() {
		return startLoc;   
	}
   
   
   /**
     Returns the exit location of this maze.
   */
	public MazeCoord getExitLoc() {
		return endLoc;  
	}

   
   /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.

      @return the maze path
    */
	public LinkedList<MazeCoord> getPath() {
		return path;   
	}


   /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.

      @return whether a path was found.
    */
   
	public boolean search()  {
		if (startLoc.getCol() == endLoc.getCol() && startLoc.getRow() == endLoc.getRow()) {
			System.out.println("DEBUG: The entry location is the same with the exit location.");
		}
		if (hasWallAt(startLoc)) {
			System.out.println("DEBUG: The entry location has a wall.");
		}
		if (hasWallAt(endLoc)) {
			System.out.println("DEBUG: The exit location has a wall.");
		}
		for (int i=1;i<=row;i++) {
			for (int j=1;j<=column;j++) {
				if (maze[i][j] >= VISITED) maze[i][j] -= VISITED;
			}
		}
		path = new LinkedList<MazeCoord>();
		return routine(startLoc.getRow(),startLoc.getCol());
	}
  
	private boolean routine(int row, int col) {
		MazeCoord temp = new MazeCoord(row, col);
		if (hasWallAt(temp)) return false;
		if (maze[row+1][col+1] >= VISITED) return false;
		if (row == endLoc.getRow() && col == endLoc.getCol()) {
			path.add(temp);
			return true;
		}
		maze[row+1][col+1] += VISITED;
		boolean result = routine(row,col+1)||routine(row-1,col)||routine(row,col-1)||routine(row+1,col);
		if (result) path.add(temp);
		return result;
	}
}

