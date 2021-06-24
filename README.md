# PacMan-Game
This application runs the classic PacMan game. 

PacMan is an action maze game where the player controls the   * pacMan creature to escape from the ghosts. The objective of the game is eat all of the pellets on the screen. If the  ghost comes in contact with pacman, it will kill pacman. This game provides with 3 chances(lives) to play the game.  It also keeps track of the highest scorers and displays it on the leaderboard.

 * Added Features:
 * - Home Screen: This screen gets current user name so that it can be stored and displayed later. This screen has pacMan wallpaper as the background. 
 * - Input Validation: If the user does not enter any name and wants to continue, the application will display an error message to the screen.
 * - New Title Panel which contains score, lives left, and title displayed
 * - Menubar: This menubar has two menus:
 * 		- Sound Menu: To Mute any music being played, or unmute to listen to music. This is controlled using a boolean variable
 * 		- Options Menu: To restart the whole game or to exit the game
 * - Score: Keeps track and displays how many pellets are consumed by the pacMan. A Label displays the score. Score is reset for every game
 * - Gate: PacMan cannot enter the ghost's house.
 * - Code to helping Ghosts to get out of the house. Ghosts will always stay outside till they kill pacMan.
 * - Ghost Chase (AI): From the time the ghosts come out the house, all of the ghost's main motive is to kill PacMan. When ghosts directly see pacMan (physically see), it will keep chasing PacMan. Additionally, ghosts have a sub-conscious knowledge of where pacMan is. It does not know the exact location, but ghost will eventually always get to PacMan.
 * Ghosts will not run into each other. For every move the ghost makes, it will always check if there is another ghost in the its next move.
 * Lives: There are maximum three lives for the user to play. Number of lives left can be seen by looking at the pacMan Images on the screen. After 3 lives are complete, the whole game restarts and user has register his/her name again
 * - Custom font(downloaded) is used to display title
 * - Sounds/ Music: Three background music files are added. Chomping (whenever a key is pressed), killed( when pacMan dies),
 * 					and intro music when new game begins. Sound Effect class handles file loading and playing the music.
 * - Top 10 Scores With Names : A template class "ScoreCard" is created to store each scorecard object.
 * 							An Array of ScoreCard objects is created. After each game is done, program writes each score
 * 							into the text file. After writing, the scores are stored in the scorecard object by setting up
 * 							the name of the user and the score in the fields of the object. This array of the objects is 
 * 							then sorted using Comparator. After 3 games are played, on the board panel, a pop up(label) is 
 * 							displayed showing the top 10 scores of all time. 						
 * 
 * Major Skills: Creating array of objects, reading files, using music, looping music, Timers to animate, ActionListener to add
 * 				functionality, Grid Layout, pop-up JOptionPane messages, abtstact classes, factoring out common behavior into 
 * 				one class, JMenu bar, reading and writing text file, Buffered Writer, handling image files, Using custom colors
