import javax.swing.ImageIcon;

/*
 * This class extends the Mover class. It contains pacMan image in different directoins.
 * This class' purpose is to set up pacMan image based on its animation step
 */
public class PacMan extends Mover {

	// Create a 2D array of images
	public static final ImageIcon[][] IMAGE = {

			// 4 rows and 2 columns. Rows --> Direction, Column --> Mouth
			{ new ImageIcon("images/PacLeftClosed.bmp"), new ImageIcon("images/PacLeftOpen.bmp") },

			{ new ImageIcon("images/PacUpClosed.bmp"), new ImageIcon("images/PacUpOpen.bmp") },

			{ new ImageIcon("images/PacRightClosed.bmp"), new ImageIcon("images/PacRightOpen.bmp") },

			{ new ImageIcon("images/PacDownClosed.bmp"), new ImageIcon("images/PacDownOpen.bmp") }

	};

	// Constructor method, gets called when pacMan object is created
	public PacMan() {

		// Starts pacMan facing left with a closed mouth
		this.setIcon(IMAGE[0][0]);
	}
	
}	//End of class
