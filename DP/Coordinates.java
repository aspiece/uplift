package battleship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinates {
	public static final String match = "([A-Za-z]+)([0-9]+)";
	
	//
	
	public int X,Y;
	
	Coordinates(int x, int y) {
		X = x;
		Y = y;
	}
	
	public static Coordinates stringToCoords(String input) {
		Pattern pattern = Pattern.compile("([A-Za-z]+)([0-9]+)");
		Matcher matcher = pattern.matcher(input);
		
		if (matcher.find()) {
			int x = (int)matcher.group(1).toUpperCase().charAt(0) - 65, y = Integer.parseInt(matcher.group(2)) - 1;

			return new Coordinates(x, y);
		}
		return new Coordinates(-1, -1);
	}
}
