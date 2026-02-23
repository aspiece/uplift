package battleship;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import battleship.Coordinates;

public class Board {
	public final static char emptySpace = '.', hit = '!', miss = 'M';
	
	char[][] board;
	
	private int Guesses;
	private int hits;
	private int Max;
	private int Wins = 0;
	
	public void Win() {
		this.Wins++;
	}
	
	private void init(int rows, int cols) {
		this.board = new char[rows][cols];
		
		this.hits = 0;
		this.Guesses = 0;
		this.Max = 0;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = emptySpace;
			}
		}
		
		Ship ship = new Ship(new String[] { "SSS" });
		
		this.Spawn(ship);
		this.Spawn(ship);
		this.Spawn(Ship.transpose(ship));
	}
	
	
	Board(int rows, int cols) {
		init(rows, cols);
	}
	
	public Boolean Won() {
		return hits >= Max;
	}
	
	public double Accuracy() {
		return ((double)hits / Guesses);
	}
	
	public void Restart() {
		
	}
	
	public void Selection(Scanner scanner, List<Difficulty> difficulties) {
		System.out.println("Game modes:");
		
		for (int i = 0; i < difficulties.size(); i++) {
			Difficulty gameMode = difficulties.get(i);
			
			System.out.println("[" + (i + 1) + "] "+ gameMode.Name());
		}
		
		Difficulty selectedDifficulty = difficulties.get(0);
		Boolean canAdvance = false;
		
		while(!canAdvance) {
			System.out.print("Make a selection: ");
			if (scanner.hasNextInt()) {
				int selection = scanner.nextInt();
				scanner.nextLine();
				
				try {
					if (selection > 0 && selection <= difficulties.size()) {
						canAdvance = true;
						selectedDifficulty = difficulties.get(selection - 1);
					}
				} catch(NumberFormatException ex) {
					// retry
				}
				
			} else {
				scanner.nextLine();
				continue;
			}
		}
		
		init(selectedDifficulty.Coordinates().X, selectedDifficulty.Coordinates().Y);
		
		BattleShip.selectingDifficulty = false;
	}
	
	public Boolean Mark(Coordinates coords) {
		// true = hit, false = miss
		
		char cell = board[coords.Y][coords.X];
		Guesses++;
		if (cell == emptySpace) {
			
			
			board[coords.Y][coords.X] = miss;
			return false;
		}
		
		if (cell != hit && cell != miss) {
			hits++;
			board[coords.Y][coords.X] = hit;
			return true;
		}
		
		
		return false;
	}
	
	public static void DisplayShip(String[] ship) {
		for (int i = 0; i < ship.length; i++) {
			System.out.println(ship[i]);
		}
		
		
	}
	
	private void DisplayProgress() {
		if (Max == 0) return;
		
		double percentage = Math.round(((double)hits/Max) * 100);
		int bars = (int)Math.floor(percentage / 10);
		
		System.out.println("|".repeat(bars) + "x".repeat(10 - bars) + " (" + hits + " hits, " + percentage+"%)");
	}
	
	public void Display() {
		System.out.print("   ");
		
		for (int j = 0; j < board[0].length; j++) { 
			System.out.print(" " + ("" + (char)(j + 65)));
			
		}
		System.out.print("\n\n");
		
		for (int i = 0; i < board.length; i++) {
			System.out.print((i + 1) + " ".repeat(4- Integer.toString(i + 1).length()));
			for (int j = 0; j < board[0].length; j++) {
				char[] acceptableCharacters = {emptySpace, hit, miss};
				
				Boolean canShow = false;
				for (char k : acceptableCharacters) {
					if (board[i][j] == k) {
						canShow = true;
					}
				}
				System.out.print(String.format("%c ", canShow ? board[i][j] : '.'));
				
			}
			System.out.print("\n");
		}
		this.DisplayProgress();
	}
	
	public void Spawn(Ship ship) {
		
		Random rand = new Random();
		
		int shipDimX = ship.get(0).length(), shipDimY = ship.length();

		int iterations = 0;
		while (true) {
			if (iterations > 50) break;
			
			int rX = rand.nextInt(0, board[0].length - shipDimX + 1), rY = rand.nextInt(0, board.length - shipDimY + 1);
			
			Boolean canPlace = true;
			for (int i = rY; i < shipDimY + rY; i++) {
				for (int j = rX; j < shipDimX + rX; j++) {
					if (board[i][j] != emptySpace) {
						canPlace = false;
						break;
					}
					
				}
			}
			if (canPlace) {
				
				for (int i = 0; i < shipDimY; i++) {
					for (int j = 0; j < shipDimX; j++) {
						board[i + rY][j + rX] = ship.get(i).charAt(j);
						
						if (board[i + rY][j + rX] != emptySpace) {
							Max++;
						}
					}
					
				}
				
				break;
			}
			
			iterations++;
		}
	}
}