import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/*
 * This class creates a menubar which can unmute/mute sound options, exit and restart options as well.
 * Note: Restart menu item is handled my PacManGUI ActionListener
 */
public class PacManMenubar extends JMenuBar implements ActionListener {

	// Image icons for sound menu
	// Unmute Image - resized to appropriate size
	private ImageIcon unmuteImage = new ImageIcon(
			new ImageIcon("./images/unmute.png").getImage().getScaledInstance(20, 20, 0));

	// Mute Image - resized to appropriate size
	private ImageIcon muteImage = new ImageIcon(
			new ImageIcon("./images/mute.jpg").getImage().getScaledInstance(20, 20, 0));


	// Create two menus
	
	//Options contains: Exit and Restart
	JMenu optionsMenu = new JMenu("Options");
	
	//Sound Menu contains: Unmute/ mute
	JMenu soundMenu = new JMenu("Sound");

	// Game option Menu Items - Exit and Restart
	JMenuItem exitMenuItem = new JMenuItem("Exit");
	JMenuItem restartMenuItem = new JMenuItem("Restart");

	// Sound Option menu items
	JMenuItem unmuteMenuItem = new JMenuItem("Unmute", unmuteImage);
	JMenuItem muteMenuItem = new JMenuItem("Mute", muteImage);

	// This method creates menu items and adds the functionality to it
	public PacManMenubar() {

		// Add sound menu items
		soundMenu.add(unmuteMenuItem);
		soundMenu.add(muteMenuItem);
		
		//Add option menu items
		optionsMenu.add(exitMenuItem);
		optionsMenu.add(restartMenuItem);

		// Add action listeners to the menu items
		soundMenu.addActionListener(this);
		unmuteMenuItem.addActionListener(this);
		muteMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);

		// Add the menus to the menu bar
		add(optionsMenu);
		add(soundMenu);

	}	//End of method

	// This method performs an action based on chosen menu item
	@Override
	public void actionPerformed(ActionEvent event) {

		// Perform desired actions when menu items are selected by the user

		//if user clicks unmute
		if (event.getSource() == unmuteMenuItem) {
			
			//Set the sound to true
			PacManGUI.soundOn = true;

		}

		//If user clicks mute option
		if (event.getSource() == muteMenuItem) {
			
			//Set the sound to false
			PacManGUI.soundOn = false;
		}

		// Exit the application
		if (event.getSource() == exitMenuItem)
			System.exit(0);

	}

}	//End of class
