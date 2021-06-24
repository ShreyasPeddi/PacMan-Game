import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This class creates a PacMan GUI that extends the JFrame class. It has a board
 * (JPanel), JMenuBar and includes the constructor method that sets up the frame and adds
 * a key listener to the board.
 * This class also handles transitions between each screen and displaying scores
 */
public class PacManGUI extends JFrame implements ActionListener {

	// FIELDS

	// Create a new board (panel)
	private Board board = new Board();

	// Title panel - to display game title, scores, lives left
	public JPanel titlePanel = new TitlePanel();

	// Menubar to unmute/mute, restart game, and exit game
	PacManMenubar menubar = new PacManMenubar();

	// Music used in the game
	static SoundEffect openingMusic = new SoundEffect();
	static SoundEffect pacManMusic = new SoundEffect();
	static SoundEffect killedMusic = new SoundEffect();

	// Keeps track of when to unmute/mute
	static boolean soundOn = true;

	// Name of the current user
	static String name;

	// Restarts the game and navigates to next game
	public static JButton restartGameButton = new JButton();

	// Restarts the whole match and navigates to home screen
	public static JButton restartMatchButton = new JButton("Restart Game?");

	// Asks the user if he/she wants to check the scores
	public static JButton checkScoresGameButton = new JButton("Check Score Board?");

	// High Score Title Label
	public JLabel highScoresTitle = new JLabel();

	// Background container used to seperate the game board from the scorecard
	public JLabel container = new JLabel();

	// Score board arrays used to display name and scores
	public JLabel scoreBoardNameLabel[] = new JLabel[10];
	public JLabel scoreBoardScoreLabel[] = new JLabel[10];

	//Custom button color
	private Color buttonColor = new Color(255, 0, 0);;

	// Configures custom font settings
	private Font buttonFont = TitlePanel.configureFont();

	// default constructor
	public PacManGUI() {

	}

	// Constructor Method
	public PacManGUI(String nameInput) {

		// Set the name of the user based on the input
		name = nameInput;

		// Set the game running as true
		PacManGame.isGameRunning = true;

		// Set the size of the Window
		setSize(610, 810);

		// Set the background color
		setBackground(Color.black);

		// Set the title of the window
		setTitle("PacMan - Shreyas");

		// Add this property to close the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Setup the scoreboard and change the visibility to false - to make the GUI
		// elements visible when necessary
		createScoreBoard();

		// Setup transition buttons to move between screens - Restart Game, Check
		// Scoreboard, Continue Game
		createTransitionButtons();

		// Do not use any layout manager
		setLayout(null);

		// Add a key listener to receive key strokes from user
		addKeyListener(board);

		// Add the panel to the frame
		add(titlePanel);

		// Add board panel to the frame
		add(board);

		// Set up the menubar
		setJMenuBar(menubar);

		// Make the changes visible
		setVisible(true);

		// Handle menubar actions using this class's actionlistener
		menubar.restartMenuItem.addActionListener(this);

	}

	// This method useful tranistion buttons like restart button (used as continue
	// game button as well) and check scores button
	private void createTransitionButtons() {

		//RESTART BUTTON: Used for restarting game/ continuing into next game
		//Set the location of the restart button
		restartGameButton.setBounds(200, 300, 200, 70);

		//Set the custom font of the button which is already configured
		restartGameButton.setFont(buttonFont.deriveFont(Font.BOLD, 15f));
		
		//Set the background and foreground colors
		restartGameButton.setBackground(buttonColor);
		restartGameButton.setForeground(Color.black);

		// Set the visibility to false to make it visible only after the game ends
		restartGameButton.setVisible(false);

		// Add this class's instance as the actionlistener
		restartGameButton.addActionListener(this);

		// Add the button to the frame
		add(restartGameButton);

		//CHECK SCORES BUTTON: Displays scores after 3 games are played
		// Set the location of the checkScores button
		checkScoresGameButton.setBounds(150, 300, 250, 70);

		//Set the custom font
		checkScoresGameButton.setFont(buttonFont.deriveFont(Font.BOLD, 15f));
		
		//Set the background and foreground colors
		checkScoresGameButton.setBackground(buttonColor);
		checkScoresGameButton.setForeground(Color.black);

		// Set the visibility to false to make it visible only after the game ends
		checkScoresGameButton.setVisible(false);

		// Add this class's instance as the actionlistener
		checkScoresGameButton.addActionListener(this);

		// Add the button to the frame
		add(checkScoresGameButton);

	}

	// Action Listener - performs appropriate action based on the event
	@Override
	public void actionPerformed(ActionEvent event) {

		// If the user wants to restart the application
		if (event.getSource() == menubar.restartMenuItem) {

			// Set the frame visibility to false
			setVisible(false);

			// Dispose the current window
			dispose();

			// Stop the game timers
			board.gameTimer.stop();
			board.animateTimer.stop();

			// As the game is not running at the moment, set this field to false
			PacManGame.isGameRunning = false;

			// Reset the lives to start a fresh game
			PacManGame.resetLives();

			// Navigate the user to homescreen, where he/she can register their name
			new HomeScreen();

		}

		// If the user wants to continue the game, do the following
		if (event.getSource() == restartGameButton && !(PacManGame.isGameRunning)) {

			// Remove the button visibility (as it is already clicked)
			restartGameButton.setVisible(false);

			// Remove the current screen
			dispose();

			// Create a new game with the same username as long as there are lives left
			if (PacManGame.lives != 0)
				new PacManGUI(PacManGame.name);

		}

		// If the user wants to check the all time high scores(only available after 3
		// games, do the following
		if (event.getSource() == checkScoresGameButton && !(PacManGame.isGameRunning)) {

			// Display the scoreboard, only if there are no more lives left
			if (PacManGame.lives == 0 && !PacManGame.restartGame) {

				// Display the score board
				displayScoreBoard();

				// As the user needs to restart the game, set it to true
				PacManGame.restartGame = true;
			}
		}

		// If the user wants to restart the whole match(new name registration), do the
		// following
		if (event.getSource() == restartMatchButton && PacManGame.restartGame) {

			// Remove the current screen
			dispose();

			// Navigate the user to homescreen
			new HomeScreen();

			// As the game is already restarted, set this to false
			PacManGame.restartGame = false;

		}

	} // End of method

	// This method creates the scoreboard which will be displayed later
	private void createScoreBoard() {

		// Scoreboard Title Label:
		// Set the location of the label
		highScoresTitle.setBounds(220, 200, 200, 40);

		// Set the text, appropriate font and color
		highScoresTitle.setText("High Scores");
		highScoresTitle.setFont(new Font("Optima", Font.BOLD, 30));
		highScoresTitle.setForeground(Color.WHITE);

		// Set the visibility to false because it should not be displayed right away
		// (later when game is done)
		highScoresTitle.setVisible(false);

		// Add the title label to the GUI Frame
		add(highScoresTitle);

		// Score Card: For each scorecard, do the following
		for (int i = 0; i < scoreBoardNameLabel.length; i++) {

			// Instantiate a new Label for Score Board NAME
			scoreBoardNameLabel[i] = new JLabel();

			// Set the location, size
			scoreBoardNameLabel[i].setBounds(210, 280 + (i * 30), 100, 20);

			// Set the text color
			scoreBoardNameLabel[i].setForeground(Color.white);
			
			//Set the custom font
			scoreBoardNameLabel[i].setFont(buttonFont.deriveFont(Font.BOLD, 10f));

			// Set the visibility to false to make it visible later
			scoreBoardNameLabel[i].setVisible(false);

			// Add each score board name
			add(scoreBoardNameLabel[i]);

			// Instantiate a new Label for Score Board SCORE
			scoreBoardScoreLabel[i] = new JLabel();

			// Set the location
			scoreBoardScoreLabel[i].setBounds(360, 280 + (i * 30), 30, 20);

			// Set the score to a different color (advantage of using a different label for
			// scores)
			scoreBoardScoreLabel[i].setForeground(Color.GREEN);

			// Make it visible later, so set it to false
			scoreBoardScoreLabel[i].setVisible(false);

			// Add the score board score
			add(scoreBoardScoreLabel[i]);

		} // End of loop

		// Set the location of the restart button
		restartMatchButton.setBounds(200, 650, 200, 50);
		
		//Set the custom font
		restartMatchButton.setFont(buttonFont.deriveFont(Font.BOLD, 15f));
		
		//Set the background and foreground colors
		restartMatchButton.setBackground(buttonColor);
		restartMatchButton.setForeground(Color.black);

		// Set the visibility to false to make it visible only after the game ends
		restartMatchButton.setVisible(false);

		// Add this class's instance as the actionlistener
		restartMatchButton.addActionListener(this);

		// Add the button to the frame
		add(restartMatchButton);

		// CONTAINER: This gives the background panel effect, so that the scores don't
		// get mixed up with the game board
		container.setBounds(100, 60, 420, 700);

		// Set the background color
		container.setBackground(Color.black);

		// Make it opaque to make the colors visible
		container.setOpaque(true);

		// Make it visible later after scores are calculated
		container.setVisible(false);

		// Add it to the frame
		add(container);

	} // End of method

	/*
	 * This method sets the scoreboard after each match (3 games) is finished. It
	 * retrieves information from the sorted high score array (taken from a file)
	 * and displays the leaderboard
	 */
	private void displayScoreBoard() {

		// Remove the transition as it is already clicked before
		checkScoresGameButton.setVisible(false);

		// Display the title of the scoreboard
		highScoresTitle.setVisible(true);

		// Do the following for each score label
		for (int i = 0; i < scoreBoardNameLabel.length; i++) {

			// Note: The text file contains default scores (Pac Man Creator - 0) to handle
			// exceptions. As this information should not be displayed, break the loop if
			// the program notices that information in the array
			if (PacManGame.scoresArray[i].getName().equals("Pac Man Creator"))
				break;

			// Display the position and the name
			scoreBoardNameLabel[i].setText((i + 1) + ")       " + PacManGame.scoresArray[i].getName());

			// Display the score
			scoreBoardScoreLabel[i].setText("" + PacManGame.scoresArray[i].getScore());

			// Set the visibility to true to make it visible
			scoreBoardNameLabel[i].setVisible(true);
			scoreBoardScoreLabel[i].setVisible(true);

		} // End of loop

		// Set the background container visible
		container.setVisible(true);

		// Show the restart button to help user navigate to the home screen
		restartMatchButton.setVisible(true);

	} // End of method

} // End of class