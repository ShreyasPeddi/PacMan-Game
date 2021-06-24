
/**
 * This class represents the game board and includes methods to 
 * handle keyboard events and game actions.
 */
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.*;

public class Board extends JPanel implements KeyListener, ActionListener {

	// Used for repeated execution, controls the whole game
	public Timer gameTimer = new Timer(500, this);

	// Used for repeated execution, controls pac Man animation
	public Timer animateTimer = new Timer(10, this);

	// PacMan GAME: Wall icon
	private static final ImageIcon WALL = new ImageIcon("images/StdWall.bmp");

	// PacMan GAME: Food icon
	private static final ImageIcon FOOD = new ImageIcon("images/StdFood.bmp");

	// PacMan GAME: Blank icon, used when food is eaten
	private static final ImageIcon BLANK = new ImageIcon("images/Black.bmp");

	// PacMan GAME: Door icon, to teleport to another location on the gameboard
	private static final ImageIcon DOOR = new ImageIcon("images/Black.bmp");

	// PacMan GAME: SKULL icon, used to display when pacman dies
	private static final ImageIcon SKULL = new ImageIcon("images/Skull.bmp");

	// Keeps track of the details of the text file which contains the walls, ghost,
	// pacman, food
	private char[][] maze = new char[25][27];

	// Cell array to display what each element is, in a given ceel
	private JLabel[][] cell = new JLabel[25][27];

	// PacMan object to move pacman, get location, use pacman properties
	private PacMan pacMan;

	// Ghost objects to move ghost, get location and use ghost properties
	private Ghost[] ghost = new Ghost[3];

	// Keeps track of the pellets
	private int pellets = 0;

	// Score increases when pacman eats pellets
	private int score = 0;

	// Animation step variable: used to determine pacman's image to be set to create
	// animation effect
	private int pStep;
	
	private int gStep=0;

	// Constructor Method, creates the game board
	public Board() {

		// Use grid layout to divide panel into equal-sized rectangle
		GridLayout layout = new GridLayout(25, 27);
		setLayout(layout);

		// Set the backgroud color to black
		setBackground(Color.black);

		// Sets up default values for the scores to handle null pointer exception (no
		// elements in the array)
		setUpScoresArray();

		// Create pacman object
		pacMan = new PacMan();

		// Create ghost objects - represents ghost's number and ghost's key
		ghost[0] = new Ghost(0, 0);
		ghost[1] = new Ghost(1, 1);
		ghost[2] = new Ghost(2, 2);

		// Set the location of the panel on the frame
		setBounds(0, 160, 600, 600);

		// Load the board by adding all the GUI elements and images
		loadBoard();

		// Set the file locations of the appropriate sounds
		PacManGUI.pacManMusic.setFile("./sounds/pacchomp.wav");
		PacManGUI.openingMusic.setFile("./sounds/GAMEBEGINNING.wav");
		PacManGUI.killedMusic.setFile("./sounds/killed.wav");

		// If it is unmuted, start playing the intro music
		if (PacManGUI.soundOn)
			PacManGUI.openingMusic.play();

		// Display the current score
		TitlePanel.scoreBoard.setText("SCORE: ");
	}

	// Set up the array which should contain scores of 0, to make sorting possible
	// even if the array is not fully filled
	private void setUpScoresArray() {

		// Set up the array with default values
		for (int i = 0; i < PacManGame.scoresArray.length; i++) {

			// Set the score to 0, to make the sorting possible and set the name so that
			// null pointer exception is not created
			PacManGame.scoresArray[i] = new ScoreCard("Sample Name", 0);
		}

	}

	// Reads in the text file and interprets each chraracter. Set up an image based
	// on each character
	private void loadBoard() {

		// Keeps track of the number of rows
		int row = 0;

		// Input object to retrieve data
		Scanner input;

		// Try the following, or display the error
		try {

			// Setup the file and open the File
			input = new Scanner(new File("maze.txt"));

			// Keep reading the file as long as there are characters
			while (input.hasNext()) {

				// Read the entire line of text and breaks it into character array
				maze[row] = input.nextLine().toCharArray();

				// Interpret the array(row). Goes through each item of the array
				for (int column = 0; column < maze[row].length; column++) {

					// Setup cell array
					cell[row][column] = new JLabel();

					// depending on the letter, assign an image

					// If the character is W, set the wall image
					if (maze[row][column] == 'W')
						cell[row][column].setIcon(WALL);

					// If the character is F, set the food image
					else if (maze[row][column] == 'F') {

						cell[row][column].setIcon(FOOD);

						// Keep track of the pellets
						pellets++;
					}

					// If the character is B, set the blank image
					else if (maze[row][column] == 'X')
						cell[row][column].setIcon(BLANK);

					// If the character is D, set the door image
					else if (maze[row][column] == 'D')
						cell[row][column].setIcon(DOOR);

					// If the character is P, set the pacman image
					else if (maze[row][column] == 'P') {

						cell[row][column].setIcon(pacMan.getIcon());

						// Keeps track of pacMan's location
						pacMan.setRow(row);
						pacMan.setColumn(column);

						// start facing left
						pacMan.setDirection(0);
					}
					// If the character is 0,1,2 set the ghost image
					else if (maze[row][column] == '0' || maze[row][column] == '1' || maze[row][column] == '2') {

						// Convert the char to an equivalent integer
						int ghostNum = Character.getNumericValue(maze[row][column]);

						cell[row][column].setIcon(ghost[ghostNum].getIcon());

						// Keep track of ghost's location
						ghost[ghostNum].setRow(row);
						ghost[ghostNum].setColumn(column);
					}

					// Adds the image to the panel
					add(cell[row][column]);
				}

				// Increment the row
				row++;

			}

			// Close the text file
			input.close();

		} catch (FileNotFoundException error) {
			error.printStackTrace();
		}

	}

	// Return if pacman and ghost collided in a location
	private boolean collided() {

		// For each ghost, do the following
		for (int ghostNum = 0; ghostNum < 3; ghostNum++) {

			// If the row and column is equal, then pacMan collided with one of the ghosts
			if (ghost[ghostNum].getRow() == pacMan.getRow() && ghost[ghostNum].getColumn() == pacMan.getColumn())
				return true;
		}

		// If all ghosts are checked and location is not common, then they did not
		// collide
		return false;
	}

	// When death occurs, pacman will be set dead, game will stop and a skull will
	// appear
	private void death() {

		// If it is unmuted, do the following
		if (PacManGUI.soundOn)

			// Play the killed music
			PacManGUI.killedMusic.play();

		// If pacman is not dead, do the following
		if (!(pacMan.isDead())) {

			// Pacman is set dead
			pacMan.setDead(true);

			// Game is stopped
			stopGame();

			// Skull Image is set
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);
		}
	}

	// This method stops the game
	private void stopGame() {

		// If pacman is dead or all the pellets are consumed
		if (pacMan.isDead() || score == pellets) {

			// Stop the pacMan animation
			animateTimer.stop();

			// Stop the game timer
			gameTimer.stop();

			// As the game is stopped temporarily, set it to false
			PacManGame.isGameRunning = false;

			// Determine the high score
			determineHighScore();

			// If lives are still left, ask the user if he/she wants to continue
			if (PacManGame.lives >= 2) {

				// Decrease the number of lives
				PacManGame.lives--;

				// Set the button text to continue game
				PacManGUI.restartGameButton.setText("Continue Game?");

				// Make the button visible
				PacManGUI.restartGameButton.setVisible(true);

			}

			// If not, the user has to check the score board and play a fresh match
			else {

				// Decrement the lives
				PacManGame.lives--;

				// Display the check scores button, to check the leaderboard
				PacManGUI.checkScoresGameButton.setVisible(true);
			}
		}

	} // End of method

	/*
	 * This method adds the current score of the user, user name to the text file.
	 * Then, in the text file, it seperates values based on comma or new line
	 * character. Next, it stores this information in the scores object array's
	 * fields. Lastly, it sorts the array based on the the scores.
	 */
	private void determineHighScore() {

		// https://stackoverflow.com/questions/26443957/save-game-scores-to-file-and-determine-the-high-score
		// Try writing into a file
		try {

			// Create object which can write into a file
			BufferedWriter output = new BufferedWriter(new FileWriter(PacManGame.highScoreFile, true));

			// Go to a new line
			output.newLine();

			// append the last score to the end of the file
			output.append(PacManGUI.name + "," + this.score);

			// Close the object
			output.close();

			// Display an exception if there was any
		} catch (IOException exception) {
			System.out.printf("ERROR writing score to file: %s\n", exception);
		}

		// Loop counter
		int counter = 0;

		// Try reading a file
		try {

			// Use scanner input to read the text file which contains all the score
			Scanner input = new Scanner(new File(PacManGame.highScoreFile));

			// Delimiter seperates each item based on the following
			input.useDelimiter(",|\\r\\n");

			// As long as there is a next line, do the following
			while (input.hasNextLine()) {

				// Set the name to the array
				PacManGame.scoresArray[counter].setName(input.next());

				// Set the score to the array
				PacManGame.scoresArray[counter].setScore(input.nextInt());

				// Increment the counter
				counter++;

			}

			// Sort the array in reverse order to determine the highest scores
			Arrays.sort(PacManGame.scoresArray, Comparator.comparing(ScoreCard::getScore).reversed());

			// Display an exception if there was any
		} catch (FileNotFoundException error) {
			error.printStackTrace();
		}

	} // End of method

	
	// Updates move of pacman/ ghost and sets the row and column
		private void performMove(Mover mover) {

			// Check to see if somebody is going to run into a door
			if (mover.getColumn() == 1) {

				// The set the location to another door (teleport to a different location)
				mover.setColumn(24);

				// set the icon again back to the door
				cell[12][1].setIcon(DOOR);
			}

			// Check to see if somebody is going to run into the other door
			else if (mover.getColumn() == 25) {

				// The set the location to another door (teleport to a different location)
				mover.setColumn(2);

				// set the icon again back to the door
				cell[12][25].setIcon(DOOR);
			}

			// If the next spot is not wall
			if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W') {

				// If the object is pacMan
				if (mover == pacMan)

					// Start the animate timer
					animateTimer.start();

				// If the object is the ghost
				else {

					// Set the icon back to food icon, if the food icon was present before
					if (maze[mover.getRow()][mover.getColumn()] == 'F')
						cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);

					// Set the icon to blank, if there was no food present before
					else
						cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);

					// Move the ghost
					mover.move();

					// If the ghost collides with pacMan, pacMan dies
					if (collided() && (!pacMan.isDead())) {
						death();
					}

					// If not, set the ghostIcon after it got moved
					else
						// Set the appropriate icon at the given location
						cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());
				}
			}
		} // End of method
	
	// Handles the animation for pacMan - open mouth, draw black square, move
		private void animatePacMan() {

			// If it is the first step in the animation
			if (pStep == 0) {

				// Set the open mouth image for pacman
				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][1]); // 1 represents
																											// open mouth
				// This keeps PacMan's mouth open for 100ms
				animateTimer.setDelay(100);

			}

			// Put a black screen if it is the second step
			else if (pStep == 1)
				cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);

			//If it is the last step, move the pacMan, and do the following
			else if (pStep == 2) {

				// move the pacMan
				pacMan.move();

				// If there is food
				if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F') {

					// Increment the score
					TitlePanel.scoreBoard.setText("SCORE: " + (++score));

					// Set the area as E where the food is consumed
					maze[pacMan.getRow()][pacMan.getColumn()] = 'E';
				}

				// Stop the animation timer
				animateTimer.stop();

				// If pacman is dead, set the icon to skull
				if (pacMan.isDead())
					cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);

				// Set cell icon to match when pacMan closes mouth (0 - closed mouth)
				else
					cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][0]);

			}
		}
	
	// Moves the ghosts in random direction
	private void moveGhosts() {

		//If the ghosts did not perform 9 steps yet, it means that all the ghost did not come out of the house
		if(gStep!=9) {
			
			//Get the ghosts out of the house
			getGhostsOut();
		}
		
		else {
			// For each ghost, do the following
			for (Ghost ghostNum : ghost) {
	
				// Direction that the ghost will be going in
				int direction = 0;
	
				// Pick a random direction
				do {
	
					// If the location of the ghost is at the door, make sure it is not travelling
					// back and forth
					if ((ghostNum.getRow() == 12 && ghostNum.getColumn() == 24 && ghostNum.getDirection() == 2)
							|| (ghostNum.getRow() == 12 && ghostNum.getColumn() == 1 && ghostNum.getDirection() == 2))
	
						// If the direction was right originally, keep moving right
						direction = 2;
	
					// If the location of the ghost is at the door, make sure it is not travelling
					// back and forth
					else if ((ghostNum.getRow() == 12 && ghostNum.getColumn() == 24 && ghostNum.getDirection() == 0)
							|| (ghostNum.getRow() == 12 && ghostNum.getColumn() == 1 && ghostNum.getDirection() == 0))
	
						// If the direction was left originally, keep moving left
						direction = 0;
	
					/*
					 * GHOST MOVING LOGIC: In general, if pacman is in the same row or same column,
					 * ghost will always head in that direction. But, there might be walls in
					 * between to obstruct. Then, ghost will move in a direction which will allow it
					 * to get closer to pacMan. This is done by moving towards PacMan - meaning
					 * Ghost knows the general location of the pacman (if it is left/right or
					 * up/down) but not the exact location. This will allow ghost to eventually get
					 * to pacman.
					 * 
					 * For every move, ghost checks if there is potentially a wall in its next move,
					 * or if there is another ghost in its move. In that case, another direction is
					 * considered. If no direction is helpful, ghost will move in a random
					 * direction.
					 */
	
					// If the ghost is in the same row as pac man and ghost is to the left, head
					// right
					else if (ghostNum.getRow() == pacMan.getRow() && ghostNum.getColumn() < pacMan.getColumn()
							&& maze[ghostNum.getRow()][ghostNum.getColumn() + 1] != 'W'
							&& !collideWithAnotherGhost(ghostNum, 2)) {
	
						// Move right
						direction = 2;
					}
	
					// If the ghost is in the same row as pac man and ghost is to the right, head
					// left. Make sure it is not colliding with another ghost
					else if (ghostNum.getRow() == pacMan.getRow() && ghostNum.getColumn() > pacMan.getColumn()
							&& maze[ghostNum.getRow()][ghostNum.getColumn() - 1] != 'W'
							&& !collideWithAnotherGhost(ghostNum, 0)) {
	
						// Head left
						direction = 0;
					}
	
					// If the ghost is in the same column as pac man and ghost is to the up, head
					// up. Make sure it is not colliding with another ghost
					else if (ghostNum.getColumn() == pacMan.getColumn() && ghostNum.getRow() > pacMan.getRow()
							&& maze[ghostNum.getRow() - 1][ghostNum.getColumn()] != 'W'
							&& !collideWithAnotherGhost(ghostNum, 1)) {
	
						// Head up
						direction = 1;
					}
	
					// If the ghost is in the same column as pac man and ghost is to the down, head
					// down. Make sure it is not colliding with another ghost
					else if (ghostNum.getColumn() == pacMan.getColumn() && ghostNum.getRow() < pacMan.getRow()
							&& maze[ghostNum.getRow() + 1][ghostNum.getColumn()] != 'W'
							&& !collideWithAnotherGhost(ghostNum, 3)) {
	
						// Head down
						direction = 3;
					}
	
					// If ghost's current column is greater, then pacman is to the left
					else if (ghostNum.getColumn() > pacMan.getColumn()
							&& (direction == 0 && maze[ghostNum.getRow()][ghostNum.getColumn() - 1] != 'W')
							&& !collideWithAnotherGhost(ghostNum, 0)) {
	
						// Set the direction as left
						direction = 0;
					}
	
					// If ghost's current column is less, then pacman is to the right
					else if (ghostNum.getColumn() < pacMan.getColumn()
							&& (direction == 0 && maze[ghostNum.getRow()][ghostNum.getColumn() + 1] != 'W')
							&& !collideWithAnotherGhost(ghostNum, 2)) {
						
						//Head right
						direction = 2;
					}
	
					//If ghost's current row is greater, then pacman is up
					else if (ghostNum.getRow() > pacMan.getRow() && maze[ghostNum.getRow() - 1][ghostNum.getColumn()] != 'W'
							&& !collideWithAnotherGhost(ghostNum, 1)) {
						
						//Head up
						direction = 1;
					}
	
					//If ghost's current row is less, then pacman is down
					else if (ghostNum.getRow() < pacMan.getRow() && maze[ghostNum.getRow() + 1][ghostNum.getColumn()] != 'W'
							&& !collideWithAnotherGhost(ghostNum, 3)) {
	
						//Head down
						direction = 3;
					}
	
					//If none of the conditions satisfy, move in random direction
					else {
						direction = (int) (Math.random() * 4);
					}
	
					//Keep getting a new durection as long there is another ghost in its possible next move
				} while (collideWithAnotherGhost(ghostNum, direction));
	
				// Set the ghost's direction
				ghostNum.setDirection(direction);
	
				// Perform the move on the ghost
				performMove(ghostNum);
	
			} // End of loop
		}
	}

	/*
	 * Parameters: Current ghost object which might potentially hit another ghost,
	 * the direction it might potentially travel in. 
	 * Returns true if it could collide
	 * with another ghost in its next move and returns false otherwise
	 */
	private boolean collideWithAnotherGhost(Ghost currentGhost, int direction) {

		// For each ghost, check if it could collide with current ghost
		for (Ghost ghostNum : ghost) {
			
			//If it is the ghost that is being checked, no need to check for conditions as it will never collide with itself
			if (ghostNum.getGhostKey() == currentGhost.getGhostKey()) 
				continue;
			
			// If the current ghost is heading left and there is another ghost there
			if (direction == 0 && currentGhost.getRow() == ghostNum.getRow()
					&& currentGhost.getColumn() - 1 == ghostNum.getColumn()) 
				return true;
			
			
			//If the current ghost is heading up and there is another ghost there
			if (direction == 1 && currentGhost.getRow() - 1 == ghostNum.getRow()
					&& currentGhost.getColumn() == ghostNum.getColumn()) 
				return true;
			
			
			//If the current ghost is heading right and there is another ghost there
			if (direction == 2 && currentGhost.getRow() == ghostNum.getRow()
					&& currentGhost.getColumn() + 1 == ghostNum.getColumn()) 
				return true;
			
			//If the current ghost is heading down and there is another ghost there
			if (direction == 3 && currentGhost.getRow() + 1 == ghostNum.getRow()
					&& currentGhost.getColumn() == ghostNum.getColumn()) 
				return true;
			
		}
		
		//Returns false if no ghost found in the headed direction
		return false;
	}
	
	// This method lets the ghost out of the ghost house in a defined manner
			private void getGhostsOut() {

				//If it is the first step
				if(gStep==0) {
					
					//Ghost 0 heads down, ghost 1 heads up, ghost 2 heads down
					ghost[0].setDirection(3);
					ghost[1].setDirection(1);
					ghost[2].setDirection(3);
				}
				
				//If it is the second step
				else if(gStep==1) {
					
					//Ghost 0 heads down, ghost 1 heads up, ghost 2 heads left
					ghost[0].setDirection(3);
					ghost[1].setDirection(1);
					ghost[2].setDirection(0);
				
				}
				
				//If it is the third step
				else if(gStep==2) {
					
					//Ghost 0 heads right, ghost 1 heads up, ghost 2 heads left
					ghost[0].setDirection(2);
					ghost[1].setDirection(1);
					ghost[2].setDirection(0);
				}
				
				//If it is the fourth step
				else if (gStep==3) {
					
					//Ghost 0 heads right, ghost 1 heads up, ghost 2 heads up
					ghost[0].setDirection(2);
					ghost[1].setDirection(1);
					ghost[2].setDirection(1);
				}
				
				//If it is the fifth step
				else if (gStep==4) {
					
					//Ghost 0 heads up, ghost 1 heads left, ghost 2 heads up
					ghost[0].setDirection(1);
					ghost[1].setDirection(0);
					ghost[2].setDirection(1);
				}
				
				//If it is the sixth step
				else if (gStep==5){
					
					//Ghost 0 heads up, ghost 1 heads left, ghost 2 heads up
					ghost[0].setDirection(1);
					ghost[1].setDirection(0);
					ghost[2].setDirection(1);
				}
			
				//If it is the seventh step
				else if (gStep==6) {
					
					//Ghost 0 heads up, ghost 1 heads left, ghost 2 heads right
					ghost[0].setDirection(1);
					ghost[1].setDirection(0);
					ghost[2].setDirection(2);
				}
				
				//If it is the eigth step
				else if (gStep==7) {
					
					//Ghost 0 heads up, ghost 1 heads left, ghost 2 heads up
					ghost[0].setDirection(1);
					ghost[1].setDirection(0);
					ghost[2].setDirection(1);
				}
				
				//If it is the ninth step
				else if (gStep==8) {
					
					//Ghost 0 heads left, ghost 1 heads down, ghost 2 heads up
					ghost[0].setDirection(0);
					ghost[1].setDirection(3);
					ghost[2].setDirection(1);
				}
				
				//Print an error if this method is called again
				else
					System.out.println("Error");
					
				//Increment ghost step
				gStep++;
			

				// Do the appropriate move based on the direction set for each ghost
				performMove(ghost[0]);
				performMove(ghost[1]);
				performMove(ghost[2]);

				//If it is the ninth step, set the gate
				if(gStep==9)
					// Set the entrance to the house of ghosts as a wall, to prevent both ghosts and
					// pacman to enter the house
					maze[10][13] = 'W';
			}

	
	//This method performs appropriate based on the given timers
	@Override
	public void actionPerformed(ActionEvent event) {

		// If the source of the event was game timer
		if (event.getSource() == gameTimer) {

			// Everytime the game ticks, perform the move for pacMan and move the ghost
			performMove(pacMan);
			moveGhosts();
		}

		//If the source of the event was animate timer
		else if (event.getSource() == animateTimer) {

			// Animate pacMan
			animatePacMan();

			// Switches the step between 0,1,2
			pStep++;

			// If the pStep is 3, set it equal to 0 to repeat the pattern
			if (pStep == 3)
				pStep = 0;
		}

	}	//End of method

	// This method handles any key presses and performs appropriate actions
		// accordingly
		@Override
		public void keyPressed(KeyEvent key) {

			// Play the music, only if it is unmuted already
			if (PacManGUI.soundOn) {
				// Everytime a key is pressed, pacman chomping music is played
				PacManGUI.pacManMusic.play();
			}

			// Check to see if game timer needs to be started
			if (gameTimer.isRunning() == false && pacMan.isDead() == false) {

				// Start the game timer
				gameTimer.start();

				// Get the ghosts out of the house
				getGhostsOut();
			}

			// Otherwise, move in different directions
			if (pacMan.isDead() == false && score != pellets) {

				// gets the ASCII code and subtract 37 to make direction equal to 0,1,2,3
				int direction = key.getKeyCode() - 37;

				// If the player wants to go left, check if there is wall
				if (direction == 0 && maze[pacMan.getRow()][pacMan.getColumn() - 1] != 'W')

					// Allow the pacMan to go left
					pacMan.setDirection(0);

				// If the player wants to go up, check if there is wall
				else if (direction == 1 && maze[pacMan.getRow() - 1][pacMan.getColumn()] != 'W')

					// Allow the pacMan to go up
					pacMan.setDirection(1);

				// If the player wants to go right, check if there is wall
				else if (direction == 2 && maze[pacMan.getRow()][pacMan.getColumn() + 1] != 'W')

					// Allow the pacMan to go right
					pacMan.setDirection(2);

				// If the player wants to go down, check if there is wall
				else if (direction == 3 && maze[pacMan.getRow() + 1][pacMan.getColumn()] != 'W')

					// Allow the pacMan to go down
					pacMan.setDirection(3);
			}

		}
		
		// must remain to satisfy the KeyListener code interface
		@Override
		public void keyReleased(KeyEvent event) {

		}

		// must remain to satisfy the KeyListener code interface
		@Override
		public void keyTyped(KeyEvent event) {

		}
	
}// End of class
