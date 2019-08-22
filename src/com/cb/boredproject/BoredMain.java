package com.cb.boredproject;

import java.util.Scanner;
import com.cb.boredproject.menus.MainMenu;;

/* This program is built to be like a personal mp3 player, to be used offline when in an airplane or without wifi somewhere.
*  This program was written by Nathaniel K Blanquel aka Coding Beyond because he was bored on a plane and was tired of having to navigate a file system to 
*  listen to music. Further updates will come. -NKB   
*  P.S. this project is open source, it's free, enjoy it
*/ 
public class BoredMain{

	public static String name;

	public static void main(String[] args){

		welcome();
		
	}


			//System.out.println("Index: " + i);

	public static void welcome(){
		System.out.println("Hi and welcome to this program written by Nathaniel Blanquel just because he was bored on the plane.");
		System.out.println("Who is my supreme leader today?");

		Scanner welcome_scanner = new Scanner(System.in);
		name = setName(welcome_scanner);

		System.out.println("Hello " + name + ". Nice to meet you my master.");
		System.out.println("Initializing program...");

		
		startup();
		welcome_scanner.close();
	}

	public static void startup(){

		MainMenu mM = new MainMenu();
		mM.initialize();

	}

	private static String setName(Scanner sc){
		return sc.nextLine();
	}

	public static String getName(){
		return name;
	}
}