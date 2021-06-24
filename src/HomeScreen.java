import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * This class creates the home screen GUI for the users.
 * It contains a textfield to enter name. This class also pops up an error message if no name is entered.
 */
public class HomeScreen extends JFrame implements ActionListener {

	// Textfield to enter user name
	JTextField nameTextField = new JTextField();

	// Start game button to navigate to board
	JButton startGame = new JButton("Start Game");

	// Title Label to display title
	private JLabel title = new JLabel("PacMan");

	// Custom color used for button background - red
	Color buttonColor = new Color(255, 0, 0);

	// Configures custom font settings. The configureFont method already exists in
	// TitlePanel class
	private Font titleFont = TitlePanel.configureFont();

	// Constructor Method
	public HomeScreen() {

		// Do not use any layout manager
		setLayout(null);

		// Set the screeen size
		setSize(600, 800);

		// Sets the location of the title label
		title.setBounds(100, 15, 400, 100);
		title.setOpaque(true);

		// Set the foreground color to the custom color and background to black
		title.setForeground(TitlePanel.titleColor);
		title.setBackground(Color.black);

		// Set the font to custom font
		title.setFont(titleFont.deriveFont(Font.BOLD, 90f));

		//Try setting the background image of the screen by reading a file
		try {
			//https://stackoverflow.com/questions/1064977/setting-background-images-in-jframe
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/pacManWallpaper.png")))));
			
			//Display an error if there was any
		} catch (IOException error) {
			error.printStackTrace();
		}

		//Name Text Field: Used to enter user name
		nameTextField.setBounds(80, 600, 200, 50);
		
		//Set the custom font
		nameTextField.setFont(titleFont.deriveFont(Font.PLAIN, 15f));
		
		//Set background and foreground colors
		nameTextField.setBackground(Color.black);
		nameTextField.setForeground(Color.green);

		//Start game button: navigates user to the board
		startGame.setBounds(300, 600, 200, 50);
		
		//Set the custom font
		startGame.setFont(titleFont.deriveFont(Font.BOLD, 21f));
		
		//Set background and foreground colors
		startGame.setBackground(buttonColor);
		startGame.setForeground(Color.black);
		
		//Add actionlistener which will handle button clicks
		startGame.addActionListener(this);

		//Add the GUI elements to the screen
		add(nameTextField);
		add(startGame);
		add(title);
		
		//Set the visibility of the frame and refresh
		setVisible(true);
		repaint();

	}

	//This method handles button clicks
	@Override
	public void actionPerformed(ActionEvent event) {
		
		//If start button is clicked
		if (event.getSource() == startGame) {
			
			//If there is no name entered in the textfield
			if (nameTextField.getText().length() == 0)
				
				//Display and Error message to the screen
				displayErrorMessage();

			//If not,
			else {
				
				//Reset pacman lives
				PacManGame.resetLives();
				
				//Get the name from the textfield
				PacManGame.name = nameTextField.getText();
				
				//Create new pacman game
				new PacManGUI(PacManGame.name);
				
				//Remove current screen
				dispose();
			}
		}

	}	//End of mthod

	// Displays error message
	private void displayErrorMessage() {

		//https://stackoverflow.com/questions/7080205/popup-message-boxes
		//Displays a dialog box if no name is entered in the textfield
		JOptionPane.showMessageDialog(null, "Enter your name in the text field", "Error",
				JOptionPane.INFORMATION_MESSAGE);
		
	}	//End of method
	
}	//End of class
