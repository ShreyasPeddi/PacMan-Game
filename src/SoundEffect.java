import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//This class creates SoundEffect by allowing to set sound files and play the sound files
public class SoundEffect {
	
		//https://www.youtube.com/watch?v=qPVkRtuf9CQ
		//Clip object can be pre-loaded with audio
		Clip clip;
		
		//This method loads sound file
		public void setFile(String soundFileLocation) {
			
			//Do the following
			try {
				
				//Store the sound file
				File file = new File(soundFileLocation);
				
				//Comvert the file to AudioInputStream object
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				
				//Get the clip from the AudioInputStream object
				clip = AudioSystem.getClip();
				
				//Open the audiostream sound in the clip
				clip.open(sound);
			}
			
			//Display any exception
			catch(Exception error) {
				System.out.println("Problem loading sound file");
			}
		}
		
		//This method continuosly loops the music
		public void loop(){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		//This method plays the music
		public void play() {
			
			//Plays music
			clip.setFramePosition(0);
			clip.start();
			
		}
	}	//End of class