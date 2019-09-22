package com.cb.boredproject.menus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.cb.boredproject.BoredMain;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

//Import for a later feature
//import java.io.IOException;

import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer.Status;

//TODO Remake the MainMenu in a way that it has various options, such as search, random playlist aka shuffle, and play by album etc...
public class MainMenu{

	private static MediaPlayer mediaPlayer;
	private int playListLimit = 8;
	private ArrayList<String> songs = new ArrayList<String>();

	public MainMenu(){
		
		fetchSongs("Resources/");
		
	}

	public void initialize(){
		
		printMenu();
		
	}
	
	//Prints the main menu
	private void printMenu() {
		
		System.out.println("-------------MAIN MENU-------------");
		
		String[] options = {"Random Playlist", "Browse Songs", "Create Custom Playlist", "Shuffle", "Exit"};
		MenuGenerator menGen = new MenuGenerator(options.length);
		String[] menu = menGen.getList();
		
		for(int i = 0; i <= options.length - 1; i++) {
			
				menu[i] = menu[i] + " " + options[i];
				
		}
		
		for(int i = 0; i <= menu.length - 1; i++) {
			
			System.out.println(menu[i]);
			
		}
		
		System.out.println("-----------------------------------");
		
		System.out.print("Which would you like me to do for you " + BoredMain.name +"? ");
		
		int choice = userChoice(menu.length);
		
		switch(choice) {
		case 1:
			generatePlayListAndPlay();
		case 2:
			browseAndPlay();
		case 3:
			System.out.println("Further updates coming.");
			printMenu();
		case 4: 
			shuffle();
			printMenu();
		case 5:
			System.exit(0);
		}
		
	}

	private void shuffle() {
		
		ArrayList<String> songsShuffleList = songs;
		Collections.shuffle(songsShuffleList);
		
		for(int i = 0; i <= songsShuffleList.size() - 1; i++) {
			
			playSong(songsShuffleList.get(i).replace(".mp3", ""));
			waitForEnd();
			
		}
		
	}
	
	//Generates a random play list, displays the options, and takes in the users choice
	private void generatePlayListAndPlay(){

		String[] songsList = new String[songs.size()];
		
		for(int i = 0; i < songsList.length; i++) {
			
			songsList[i] = songs.get(i);
			
		}
		
		MenuGenerator menGen = new MenuGenerator(playListLimit);
		String[] list = menGen.getList();
		

		//Generate a random playlist from available songs
		printSongsList(list, songsList, true);

		System.out.print("Choose a song, any song: ");

		int chosenOpt = userChoice(playListLimit);

		//Clears the console to avoid that extra unapealing and useless text, feature doesn't work right now.
		/*try{
		 *	Runtime.getRuntime().exec("clear");
		 *}catch(IOException e){
		 *	e.printStackTrace();
		 *}
		 */		
		
		playSong(chosenOpt, list);

		waitForEnd();

		System.out.print("Would you like to listen to some more " + BoredMain.name + "? ");
		
		//Takes in yes or no input
		//DO NOT close any of the scanners, doing so will result in a no element found exception
		@SuppressWarnings("resource")
		Scanner yesNo = new Scanner(System.in);

		if(yesNo.next().toUpperCase().equals("YES")){

			//Without this recursion the program would be broken after listening to one song
			generatePlayListAndPlay();
			mediaPlayer.stop();
			
		}else{
			
			printMenu();
			
		}
	}

	//Checks to see if song is in the resource folder and plays it if it is
	private void playSong(int c, String[] list){
		
		//Fix for bug (Toolkit not initialized bug).
		@SuppressWarnings("unused")
		final JFXPanel fxPanel = new JFXPanel();

		//Removes spaces and number from string to match song
		String chosenSong = list[c - 1];
		chosenSong = chosenSong.replace(c + ". ", "");

		System.out.println("Now playing " + chosenSong + "...");

		Media song = new Media(new File("Resources/" + chosenSong + ".mp3").toURI().toString());
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

	private void playSong(String name){
		
		//Fix for bug (Toolkit not initialized bug).
		@SuppressWarnings("unused")
		final JFXPanel fxPanel = new JFXPanel();

		//Removes spaces and number from string to match song

		System.out.println("Now playing " + name + "...");

		Media song = new Media(new File("Resources/" + name + ".mp3").toURI().toString());
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

	//TODO Make it so that the user can type song name/option name or number in playlist
	private int userChoice(int optionsLimit){

		try{
			
			//DO NOT close any of the scanners, doing so will result in a no element found exception
			@SuppressWarnings("resource")
			Scanner choice = new Scanner(System.in);
			String chosenOption = choice.nextLine();

			if(Integer.parseInt(chosenOption) > optionsLimit || Integer.parseInt(chosenOption) < 1){
				
				System.out.print("That was not a valid option. Please enter a VALID option: ");
				return userChoice(optionsLimit);
				
			}else{
				
				return Integer.parseInt(chosenOption);
				
			}

		}catch(Exception e){
			System.out.print("That was not a valid option. Please enter a VALID option: ");
			return userChoice(optionsLimit);
		}

	}
	
	//This will search for and retrieve every song under the Resources folder
	private void fetchSongs(String musicLocation) {
		
		File musicDir = new File(musicLocation);
		File[] musicList = musicDir.listFiles();
		
		for(File x: musicList) {
			
			if(x == null)
				return;
			if(x.isHidden() || !x.canRead())
				continue;
			else if(x.getName().endsWith(".mp3"))
				songs.add(x.getName());
			
		}
		
	}
	
	//Like the generatePlayListAndPlay function, but this displays every song available
	private void browseAndPlay(){

		String[] songsList = new String[songs.size()];
		
		for(int i = 0; i < songsList.length; i++) {
			
			songsList[i] = songs.get(i);
			
		}
		
		MenuGenerator menGen = new MenuGenerator(songsList.length);
		String[] list = menGen.getList();
		
		printSongsList(list, songsList, false);

		System.out.print("Choose a song, any song: ");

		int chosenOpt = userChoice(list.length);
		
		playSong(chosenOpt, list);

		waitForEnd();

		System.out.print("Would you like to listen to some more " + BoredMain.name + "? ");
		
		//Takes in yes or no input
		//DO NOT close any of the scanners, doing so will result in a no element found exception
		@SuppressWarnings("resource")
		Scanner yesNo = new Scanner(System.in);

		if(yesNo.next().toUpperCase().equals("YES")){

			//Without this recursion the program would be broken after listening to one song
			browseAndPlay();
			mediaPlayer.stop();
			
		}else{
			
			printMenu();
			
		}
		
	}
	
	private void printSongsList(String[] list, String[] songsList, boolean random) {
		
		if(random) {
			
			ArrayList<Integer> usedSongs = new ArrayList<Integer>();
			
			System.out.println("---------------SONGS---------------");
			
			for(int i = 0; i <= list.length - 1; i++){

				int randomSong = (int) Math.round(Math.random() * (songsList.length - 1)) + 0;
				
				if(usedSongs.contains(randomSong)){
					
					int randomSong2 = (int) Math.round(Math.random() * (songsList.length - 1)) + 0;
					
					while(usedSongs.contains(randomSong2)){
						
						randomSong2 = (int) Math.round(Math.random() * (songsList.length - 1)) + 0;
						
					}
					
					list[i] = list[i] + songsList[randomSong2];
					int mp3Index = list[i].indexOf(".mp3");
					list[i] = list[i].substring(0, mp3Index);
					usedSongs.add(randomSong2);
					
				}else{
					
					list[i] = list[i] + songsList[randomSong];
					int mp3Index = list[i].indexOf(".mp3");
					list[i] = list[i].substring(0, mp3Index);
					usedSongs.add(randomSong);
					
				}

			}

			for(int i = 0; i < list.length; i++){
				System.out.println(list[i]);
			}
			
			System.out.print("-----------------------------------\n");
			
		}else{
			

			System.out.println("---------------SONGS---------------");
			
			for(int i = 0; i <= list.length - 1; i++){

				list[i] = list[i] + songsList[i];
				int mp3Index = list[i].indexOf(".mp3");
				list[i] = list[i].substring(0, mp3Index);

			}

			
			for(int i = 0; i < list.length; i++){
				System.out.println(list[i]);
			}
			
			System.out.print("-----------------------------------\n");
		}
		
	}
}