package battleship;

public final class Ship {
	public String[] ship;
	
	Ship(String[] shipInfo) {
		ship = shipInfo;
	}
	
	public String get(int index) {
		return ship[index];
	}
	
	public int length() {
		return ship.length;
	}
	
	public static Ship transpose(Ship ship) {
		int rows = ship.length();
		int cols =  ship.get(0).length();
		
		String[] newShip = new String[cols];
		
		for (int i = 0; i < cols; i++) {
			StringBuilder sb = new StringBuilder();
			
			for (int j = 0; j < rows; j++) {
				sb.append(ship.get(j).charAt(i));
			}
			newShip[i] = sb.toString();
		}
		
		
		return new Ship(newShip);
	}
	
	public void Transpose() {
		ship = transpose(this).ship;
	}
}
