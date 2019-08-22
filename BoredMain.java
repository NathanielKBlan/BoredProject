import java.util.Scanner;
import java.io.*;

public class BoredMain{

	static String name;

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

	}

	private static String setName(Scanner sc){
		return sc.nextLine();
	}

	public static String getName(){
		return name;
	}
}