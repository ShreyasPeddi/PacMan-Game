import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This class is a panel placed on the screen. It contains the title, score, lives left.
 * NOTE: This class also handles the custom font registration process
 */
public class TitlePanel extends JPanel{
	
	//Fields
	
	//Displays and updates score regularly
	public static JLabel scoreBoard=new JLabel("SCORE: ");
	
	//Title of the Panel
	private JLabel title=new JLabel("PacMan");
	
	//Lives left label
	private JLabel livesText=new JLabel("LIVES LEFT: ");
	
	//Pacman image - used to display in lives left
	private ImageIcon pacManImage=new ImageIcon("images/lives.png");
	
	//Title font color (custom)
	public static Color titleColor=new Color(255,255,0);
	
	//Displays pacMan images based on the number of lives left
	public static JLabel[] pacManLives=new JLabel[3];
	
	//Configures custom font settings
	Font customGameFont=configureFont();
	

	//Constructor Method
	public TitlePanel() {
		
		//Do not use layout manager
		setLayout(null);
		
		//Set the location of the Title panel
		setBounds(0,0,600,170);
	
		//Set the panel background to black
		setBackground(Color.black);
		
		//TITLE LABEL: Sets the location of the title label
		title.setBounds(200,5,400,50);
		
		//Set opacity to true to make the colors visible
		title.setOpaque(true);
		
		//Set the foreground color to the custom color and background to black
		title.setForeground(titleColor);
		title.setBackground(Color.black);

		//Set the font to custom font
		title.setFont(customGameFont.deriveFont(Font.BOLD,50f));
		
		//Add the title label to the screen
		add(title);
		
		//For each life left
		for(int i=0;i<PacManGame.lives;i++) {
			
			//Create a new label
			pacManLives[i]=new JLabel();
			
			//Set the custom pacMan Image as the icon
			pacManLives[i].setIcon(pacManImage);
			
			//Set the location of the label
			pacManLives[i].setBounds(150+(i*50),100,50,50);
			
			//Add it to the screen
			add(pacManLives[i]);
		}
		
		//Sets the location of the score label
		scoreBoard.setBounds(480,40,200,50);
		scoreBoard.setOpaque(true);
		
		//Set the foreground color to the custom color and background to black
		scoreBoard.setForeground(Color.white);
		scoreBoard.setBackground(Color.black);
		
		//Set the font to custom font
		scoreBoard.setFont(new Font("Optima", Font.BOLD, 18));
		
		//Add the score label to the screen
		add(scoreBoard);
		
		//LIVES LEFT: Sets the location of the lives left label
		livesText.setBounds(31,103,200,40);
		livesText.setOpaque(true);
		
		//Set the foreground color to the custom color and background to black
		livesText.setForeground(Color.white);
		livesText.setBackground(Color.black);
		
		//Set the font to custom font
		livesText.setFont(new Font("Optima", Font.BOLD, 18));
		
		//Add the score label to the screen
		add(livesText);
	}

	
	//Configures a font by registering in the graphics environment
	public static Font configureFont() {
		
		//https://www.youtube.com/watch?v=43duJsYmhxQ&ab_channel=RyiSnow
		try {
			
			//Get the font file location and get the font
            Font scoreBoardFont = Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf"));
            
            //Create a new graphics environment object
            GraphicsEnvironment env= GraphicsEnvironment.getLocalGraphicsEnvironment();
            
            //Register the font in the graphics environment
            env.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font1.ttf")));
            
            //Return the font object
            return scoreBoardFont;
          
           
            //Display any exception
       } catch (FontFormatException | IOException ex) {
           ex.printStackTrace();
       }
		
		//Return null if font was not registered
		return null;
	}

}	//End of class
