import javax.swing.JLabel;

/**
 * This class handles common properties of pacman and ghosts.
 * This includes the location of the mover, direction it is moving
 * and if it is dead or not.
 */
public abstract class Mover extends JLabel {

	// Location of the mover object
	private int row;
	private int column;

	// Direction it is moving
	private int dRow;
	private int dColumn;

	// If the current object is dead
	private boolean isDead;

	// GETTERS AND SETTERS
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getdRow() {
		return dRow;
	}

	public void setdRow(int dRow) {
		this.dRow = dRow;
	}

	public int getdColumn() {
		return dColumn;
	}

	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	// UTILITY METHODS

	// To move the object
	public void move() {

		// Perform the appropriate move
		row += dRow;
		column += dColumn;
	}

	// Direction is set based on input (0-left, 1-up, 2-right, 3-down)
	public void setDirection(int direction) {
		
		//Reset drow and dcolumn
		dRow=0;
		dColumn=0;
		
		//If the direction is left
		if(direction==0)
			dColumn = -1;
		
		//If the direction is up
		else if(direction==1)
			dRow = -1;
		
		//If the direction is right
		else if(direction==2)
			dColumn = 1;
		
		//If the direction is down
		else if(direction==3)
			dRow = 1;
		
	}
	
	
	//Returns back the direction (number value)
	public int getDirection() {
		
		if(dRow==0 && dColumn==-1) //Moving Left
			return 0;
		
		else if(dRow==-1 && dColumn==0) //Moving Up
			return 1;
		
		else if(dRow==0 && dColumn==1) //Moving Right
			return 2;
		
		else  //Moving Down
			return 3;
	}
	
	
	//Returns back next row
	public int getNextRow() {
		
		return row+dRow;
	}
	
	//Returns back next column
	public int getNextColumn() {
		return column+dColumn;
	}
	
}	//End of class
