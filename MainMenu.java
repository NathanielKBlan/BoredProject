import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer.Status;


public class MainMenu{

	private static MediaPlayer mediaPlayer;
	private int playListLimit = 5;

	public MainMenu(){

		initialize();
		generatePlayListAndPlay();

		System.out.println("Further updates coming.");
		System.exit(0);

	}

	private void initialize(){
		System.out.println("What would you like me to play for you " + BoredMain.name +"?");
	}

	private void generatePlayListAndPlay(){

		String[] songs = {"Radioactive", "What's Up Danger", "Centuries", "Rocket Man", "The Lion Sleeps Tonight", "Soviet Anthem", "Fortunate Son"};

		MenuGenerator menGen = new MenuGenerator(playListLimit);
		String[] list = menGen.getList();
		ArrayList<Integer> usedSongs = new ArrayList<Integer>();

		//Generate a random playlist from available songs
		for(int i = 0; i <= list.length - 1; i++){

			int randomSong = (int) Math.round(Math.random() * (songs.length - 1)) + 0;
			if(usedSongs.contains(randomSong)){
				int randomSong2 = (int) Math.round(Math.random() * (songs.length - 1)) + 0;
				while(usedSongs.contains(randomSong2)){
					randomSong2 = (int) Math.round(Math.random() * (songs.length - 1)) + 0;
				}
				list[i] = list[i] + songs[randomSong2];
				usedSongs.add(randomSong2);
			}else{
				list[i] = list[i] + songs[randomSong];
				usedSongs.add(randomSong);
			}

		}

		//Print playlist
		for(int i = 0; i < list.length; i++){
			System.out.println(list[i]);
		}

		System.out.print("Choose one of those numbers mortal: ");

		int chosenOpt = userChoice();

		playSong(chosenOpt, list);

		waitForEnd();

		System.out.println("Would you like to listen to some more my master? ");
		
		//Takes in yes or no input
		Scanner yesNo = new Scanner(System.in);

		if(yesNo.next().toUpperCase().equals("YES")){

			//Without this recursion the program would be broken after listening to one song
			generatePlayListAndPlay();
			mediaPlayer.stop();
			
		}else{
			System.out.println("Further updates coming.");
			System.exit(0);
		}
	}

	private void playSong(int c, String[] list){
		//Fix for annoying bug.
		final JFXPanel fxPanel = new JFXPanel();

		String chosenSong = list[c - 1].substring(2).replaceAll("\\s+","") + ".mp3";

		System.out.println("Now playing " + chosenSong + "...");

		Media song = new Media(new File(chosenSong).toURI().toString());
		mediaPlayer = new MediaPlayer(song);

		mediaPlayer.setStartTime(new Duration(0));

		
		//Stop when song ends
		mediaPlayer.setOnEndOfMedia(new Runnable() {
    		@Override
    		public void run() {
        		mediaPlayer.stop();
   			 }
		});
		
		mediaPlayer.play();
	}

	private void waitForEnd(){

		//While the song is still in the playing stage continue playing.
		while(mediaPlayer.getStatus() == Status.PLAYING || mediaPlayer.getStatus() == Status.UNKNOWN || mediaPlayer.getStatus() == Status.READY){

			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

	}

	private int userChoice(){

		try{
			Scanner choice = new Scanner(System.in);
			String chosenOption = choice.nextLine();

			if(Integer.parseInt(chosenOption) > playListLimit || Integer.parseInt(chosenOption) < 1){
				System.out.println("That was not a valid option. What of choose one of those numbers do you not understand? Please enter a VALID option");
				return userChoice();
			}else{
				return Integer.parseInt(chosenOption);
			}

		}catch(Exception e){
			System.out.println("That was not a valid option. What of choose one of those numbers do you not understand? Please enter a VALID option");
			return userChoice();
		}

	}
}