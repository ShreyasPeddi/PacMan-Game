import javax.swing.ImageIcon;

/**
 * This class creates Ghost template class which extends the Mover class. This
 * class contains Ghost images and sets the images accordingly
 */
public class Ghost extends Mover {

	// Create images for the ghost
	public static final ImageIcon[] IMAGE = { new ImageIcon("images/Ghost1.png"), new ImageIcon("images/Ghost2.png"),
			new ImageIcon("images/Ghost3.png")

	};

	//Key to identify ghost
	private int ghostKey;

	// Constructor method
	public Ghost(int ghostNum, int ghostKey) {

		// Set icon based on what ghost it is
		this.setIcon(IMAGE[ghostNum]);
		
		//Set the ghost key
		setGhostKey(ghostKey);
	}

	
	//Getters and Setters
	public int getGhostKey() {
		return ghostKey;
	}

	public void setGhostKey(int ghostKey) {
		this.ghostKey = ghostKey;
	}

}	//End of class
