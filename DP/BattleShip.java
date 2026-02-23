package battleship;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

public class BattleShip {
	private final static List<Difficulty> difficulties = new ArrayList<>();
	public static Boolean selectingDifficulty = false;
	
	private static void game() {
		
		
		Scanner scanner = new Scanner(System.in);
		Board board = new Board(5, 5);
		board.Selection(scanner, difficulties);
		
		
		board.Display();
		
		
		
		while (true) {
			if (selectingDifficulty) {
				return;
			}
			String positionInput  = scanner.nextLine().trim();
			
			Pattern pattern1 = Pattern.compile("\\/(\\w+)");
			Matcher matcher1 = pattern1.matcher(positionInput);
			
			Pattern pattern2 = Pattern.compile(Coordinates.match);
			Matcher matcher2 = pattern2.matcher(positionInput);
			
			if (matcher1.find()) {
				String command = matcher1.group(1);
				
				switch(command) {
					case "cheat":
						for (int i = 0; i < board.board.length; i++) {
							for (int j = 0; j < board.board[i].length; j++) {
								 board.Mark(new Coordinates(j, i));
							}
						}
						board.Display();
						
						break;
					default:
						System.out.println(command+ " is not a command.");
						
						break;
				}
			} else if (matcher2.find())  {
				board.Mark(Coordinates.stringToCoords(positionInput));
				board.Display();
			}
			else {
				System.out.println("Malformed input");
			}
			
			if (board.Won()) {
				double accuracy = board.Accuracy() * 100;
				
				int bars = (int)Math.round(accuracy / 10);
				
				System.out.printf("\nYou won!\nAccuracy: %.1f%%\n", accuracy);
				System.out.println("|".repeat(bars) + "x".repeat(10 - bars));
				System.out.print("\n");
				
				board.Win();
				
				selectingDifficulty = true;
				board.Selection(scanner, difficulties);
				board.Display();
				
			}
		}
	}
	
	public static void main(String[] args) {
		difficulties.add(new Difficulty("Easy", new Coordinates(5, 5)));
		difficulties.add(new Difficulty("Medium", new Coordinates(8, 10)));
		difficulties.add(new Difficulty("Hard", new Coordinates(16, 16)));
		difficulties.add(new Difficulty("☠Nightmare☠", new Coordinates(30, 26)));
		
		game();
	}
} 
