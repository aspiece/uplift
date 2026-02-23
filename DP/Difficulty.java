package battleship;

import java.util.List;
import java.util.Scanner;

public class Difficulty {
	private String name;
	private Coordinates coords;
	
	
	
	public String Name() {
		return name;
	}
	
	public Coordinates Coordinates() {
		return coords;
	}
	
	Difficulty(String _name, Coordinates _coords) {
		name = _name;
		coords = _coords;
	}
	
	
}
